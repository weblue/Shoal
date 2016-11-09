package com.fishfillet.shoal;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
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
    private static final String REQUIRED = "Required";
    Button mDive;
    Ride.RideBuilder rideBuilder = new Ride.RideBuilder();
    private DatabaseReference mDatabase;
    //needed fields
    private EditText mTextStart;
    private EditText mTextDestination;
    private EditText mTextDepartTime;
    private EditText mTextCar;
    private EditText mTextLicensePlate;
    private EditText mTextMaxPassengers;
    private EditText[] requiredFields = {
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

        mDive.setOnClickListener(new View.OnClickListener() {
            //Check fields. do setError(REQUIRED);

            @Override
            public void onClick(View v) {
                //Check fields for non empty info
                if (!verifyFields()) {
                    toggleEditing(true);//might not be needed
                    return;
                }
                //toggleEditing(false);
                Log.d(getLocalClassName(), "ride into database");
                Toast.makeText(DriverActivity.this, "Sending Information...", Toast.LENGTH_SHORT).show();

                //Getting information
                final String userId = getUid();
                mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //User user = dataSnapshot.getValue(User.class);
                                if (userId == null) {
                                    Toast.makeText(DriverActivity.this, "Error: Could not find User", Toast.LENGTH_SHORT).show();
                                } else {
                                    rideBuilder.setDriverId(userId).setCarModel(mTextCar.getText().toString())
                                            .setCarColor("nocolor").setCarMake("nomake")
                                            .setPlate(mTextLicensePlate.getText().toString())
                                            .setNotes(mTextNotes.getText().toString())
                                            .setTimeDepart(0L).setTimeCreated(1L)
                                            .setLocDest(new Location("provider"))
                                            .setLocDepart(new Location("Provider"));
                                    writeNewRide();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(DriverActivity.this, "Error: Canceled", Toast.LENGTH_SHORT).show();
                                toggleEditing(true);
                            }
                        }
                );
            }
        });
    }

    private void writeNewRide() {
        //Transfer all data to the field
        String key = mDatabase.child("rides").push().getKey();
        Ride ride = rideBuilder.build();
        Map<String, Object> rideMap = ride.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/rides/" + key, rideMap);

        mDatabase.updateChildren(childUpdates);
    }

    private void setForm() {
        mDive = (Button) findViewById(R.id.buttonDive);

        mTextStart = (EditText) findViewById(R.id.editTextStart);
        mTextDestination = (EditText) findViewById(R.id.editTextDestination);
        mTextDepartTime = (EditText) findViewById(R.id.editTextDepartTime);
        mTextCar = (EditText) findViewById(R.id.editTextCar);
        mTextLicensePlate = (EditText) findViewById(R.id.editTextLicensePlate);
        mTextMaxPassengers = (EditText) findViewById(R.id.editTextMaxPassengers);
        mTextNotes = (EditText) findViewById(R.id.editTextNotes);

    }

    private void toggleEditing(boolean setting) {
        for (EditText field : requiredFields) {
            field.setEnabled(setting);
        }
        for (EditText field : nonRequiredFields) {
            field.setEnabled(setting);
        }
        mDive.setEnabled(setting);
    }

    private boolean verifyFields() {
        return true;/*
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
