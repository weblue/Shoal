package com.fishfillet.shoal;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.fishfillet.shoal.BaseActivity;
import com.fishfillet.shoal.R;
import com.fishfillet.shoal.model.Car;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by steph on 11/29/2016.
 */

public class EditCarInfoActivity extends BaseActivity {

    String nextActivity = "";
    Car.CarBuilder carBuilder = new Car.CarBuilder();

    EditText editTextCarModel, editTextCarMake,editTextCarPlate,editTextCarYear,editTextCarColor;
    ArrayList<EditText> carFields;
    Button buttonFinish;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private SharedPreferences mSharedPref;

    public EditCarInfoActivity(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        mSharedPref = getSharedPreferences("com.fishfillet.shoal.car ", Context.MODE_PRIVATE);
        this.Setup();
        buttonFinish = (Button) findViewById(R.id.buttonFinishUserEdit);

        Bundle bundle = getIntent().getExtras();
        nextActivity = bundle.getString("nextActivity");

        progressDialog = new ProgressDialog(this);
        //name and email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidFields()){
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    carBuilder.setDriverId(getUid()).setModel(editTextCarModel.getText().toString())
                            .setColor(editTextCarColor.getText().toString())
                            .setMake(editTextCarMake.getText().toString())
                            .setYear(editTextCarYear.getText().toString())
                            .setPlate(editTextCarPlate.getText().toString());
                    writeNewCar();
                    if(nextActivity.equals("Main")) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.cancel();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                overridePendingTransition(R.anim.activity_start_enter, R.anim.activity_start_exit);
                                finish();
                            }
                        }, 1000);
                    }
                    //might need other cases
                    else{
                        finish();
                    }
                }
            }
        });

    }

    private void Setup(){
        editTextCarPlate = (EditText) findViewById(R.id.editTextCarPlate);
        editTextCarMake = (EditText) findViewById(R.id.editTextCarMake);
        editTextCarModel = (EditText) findViewById(R.id.editTextCarModel);
        editTextCarYear = (EditText) findViewById(R.id.editTextCarYear);
        editTextCarColor = (EditText) findViewById(R.id.editTextCarColor);
        carFields = new ArrayList<EditText>();
        //carFields.add(editTextCarPlate);
        carFields.add(editTextCarMake);
        carFields.add(editTextCarModel);
        carFields.add(editTextCarYear);
        carFields.add(editTextCarColor);
    }
    private boolean ValidFields(){
        for(EditText field : carFields){
            if(TextUtils.isEmpty(field.getText().toString()));
            return false;
        }
        if(TextUtils.isEmpty(editTextCarPlate.getText().toString())){
            return false;
        }
        return true;
    }

    private void writeNewCar() {
        //Transfer all data to the field
        Car car = carBuilder.build();
        //String key = mDatabase.child("rides").child(ride.getDriver()).push().getKey();
        Map<String, Object> rideMap = car.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + car.driverid, rideMap);

        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString("color", editTextCarColor.getText().toString());
        editor.putString("make", editTextCarMake.getText().toString());
        editor.putString("year", editTextCarYear.getText().toString());
        editor.putString("plate", editTextCarPlate.getText().toString());
        editor.apply();

        mDatabase.updateChildren(childUpdates);
    }

}
