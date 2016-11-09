package com.fishfillet.shoal;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fishfillet.shoal.model.Ride;
import com.fishfillet.shoal.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nader on 11/7/16.
 */


public class DriverActivity extends BaseActivity {
    Button mDive;
    private DatabaseReference mDatabase;
    private static final String REQUIRED = "Required";
    //needed fields
    private EditText mTextStart;
    private EditText mTextDestination;
    private EditText mTextDepartTime;
    private EditText mTextCar;
    private EditText mTextLicensePlate;
    private EditText mTextMaxPassengers;
    private EditText []  requiredFields= {
            mTextStart,
            mTextDestination,
            mTextDepartTime,
            mTextCar,
            mTextLicensePlate,
            mTextMaxPassengers
    };
    //unneeded fields
    private EditText mTextNotes;
    private EditText[] nonRequiredFields = {
        mTextNotes
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.setForm();
        final Intent driverIntent = new Intent(this, DriverActivity.class);
        mDive.setOnClickListener(new View.OnClickListener() {
            //Check fields. do setError(REQUIRED);

            @Override
            public void onClick(View v) {
                //Check fields for non empty info
                if(!verifyFields()){
                    toggleEditing(true);//might not be needed
                    return;
                }
                toggleEditing(false);
                Toast.makeText(DriverActivity.this, "Sending Information...", Toast.LENGTH_SHORT).show();

                //Getting information
                final String userId = getUid();
                mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                if(user == null){
                                    Toast.makeText(DriverActivity.this, "Error:Could not find User", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    writeNewRider(userId,
                                            mTextCar.toString(),
                                            "color not supported",
                                            "car make not supported",
                                            mTextLicensePlate.toString(),
                                            mTextNotes.toString(),
                                    0L,//long timeDepart,
                                    1L,//long timeCreated,
                                    new Location("Provider"),//Location locDest,
                                    new Location("Provider"));//Location locDepart);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                toggleEditing(true);
                            }
                        }
                );


            }
        });
    }

    private void writeNewRider(String driverId,
                               String carModel,
                               String carColor,
                               String carMake,
                               String plate,
                               String notes,
                               long timeDepart,
                               long timeCreated,
                               Location locDest,
                               Location locDepart){
        //Transfer all data to the field
        String key = mDatabase.child("rides").push().getKey();
        Ride ride = new Ride(driverId,carModel, carColor, carMake, plate, notes, timeDepart, timeCreated, locDest, locDepart);
        Map<String, Object> rideMap = ride.toMap();
        Map<String,Object> childUpdates = new HashMap<>();
        childUpdates.put("/rides/" + key, rideMap);

        mDatabase.updateChildren(childUpdates);
    };

    private void setForm(){
        mDive =                 (Button) findViewById(R.id.buttonDive);

        mTextStart =         (EditText) findViewById(R.id.editTextStart);
        mTextDestination =   (EditText) findViewById(R.id.editTextDestination);
        mTextDepartTime =    (EditText) findViewById(R.id.editTextDepartTime);
        mTextCar =           (EditText) findViewById(R.id.editTextCar);
        mTextLicensePlate =  (EditText) findViewById(R.id.editTextLicensePlate);
        mTextMaxPassengers = (EditText) findViewById(R.id.editTextMaxPassengers);
        mTextNotes =         (EditText) findViewById(R.id.editTextNotes);

    }

    private void toggleEditing(boolean setting){
        for(EditText field: requiredFields){
            field.setEnabled(setting);
        }
        for(EditText field : nonRequiredFields){
            field.setEnabled(setting);
        }
        mDive.setEnabled(setting);
    }

    private boolean verifyFields(){
        return true;
      /*
        boolean validFields = true;
        for(EditText requiredField : requiredFields){
            String text = requiredField.getText().toString();
            if(TextUtils.isEmpty(text)){
                requiredField.setError(REQUIRED);
                validFields = false;
            }
        }
        return validFields;*/
    }
}
