package com.fishfillet.shoal;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fishfillet.shoal.model.Car;
import com.fishfillet.shoal.model.Ride;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nader on 11/7/16.
 */


public class DriverActivity extends BaseActivity {
    private static LayoutInflater driverinflater;
    private static View driverView;

    Button mDive;
    Ride.RideBuilder rideBuilder = new Ride.RideBuilder();
    private DatabaseReference mDatabase;
    //needed fields
    private EditText mTextStart;
    private EditText mTextDestination;
    private EditText mTextDepartTime;
    private EditText mTextModel;
    private EditText mTextLicensePlate;
    private EditText mTextMaxPassengers;
    private EditText mTextYear;
    private EditText mTextMake;
    private EditText mTextColor;

    private DatabaseReference mCarRef;
    private ValueEventListener mCarListener;

    private Button mHelpButton;

    private EditText[] requiredFields = {
            mTextStart,
            mTextDestination,
            mTextDepartTime,
            mTextModel,
            mTextLicensePlate,
            mTextMaxPassengers,
            mTextYear,
            mTextMake,
            mTextColor
    };
    //unneeded fields
    private EditText mTextNotes;
    private EditText[] nonRequiredFields = {
            mTextNotes
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_driver);
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCarRef = FirebaseDatabase.getInstance().getReference().child("user_info").child(getUid()).child("car_info");

        this.setForm();


        mTextDepartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(DriverActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            mTextDepartTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            }
        });

        mTextDepartTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DriverActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mTextDepartTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        final Intent i = new Intent(this, WaitingScreenActivity.class);

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
                Snackbar.make(findViewById(android.R.id.content).getRootView(), "Sending Information...", Snackbar.LENGTH_SHORT).show();

                //Getting information
                final String userId = getUid();
                mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //User user = dataSnapshot.getValue(User.class);
                                Calendar cal = Calendar.getInstance();
                                cal.setTimeInMillis(System.currentTimeMillis());
                                if (userId == null) {
                                    Snackbar.make(findViewById(android.R.id.content).getRootView(), "Error: Could not find User", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    rideBuilder.setDriverId(userId).setCarModel(mTextModel.getText().toString())
                                            .setCarColor(mTextColor.getText().toString())
                                            .setCarMake(mTextMake.getText().toString())
                                            .setCarYear(mTextYear.getText().toString())
                                            .setPlate(mTextLicensePlate.getText().toString())
                                            .setNotes(mTextNotes.getText().toString())
                                            .setTimeDepart(mTextDepartTime.getText().toString())
                                            .setTimeCreated(cal.getTime().toString())
                                            .setLocDest(mTextDestination.getText().toString())
                                            .setLocStart(mTextStart.getText().toString())
                                            .setMaxPassengers(Integer.parseInt(mTextMaxPassengers.getText().toString()));
                                    writeNewRide();

                                    Snackbar.make(findViewById(android.R.id.content).getRootView(), "Ride Created, moving to waiting screen.", Snackbar.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Snackbar.make(findViewById(android.R.id.content).getRootView(), "Error: Canceled", Snackbar.LENGTH_SHORT).show();
                                toggleEditing(true);
                            }
                        }
                );

                Bundle bundle = new Bundle();
                bundle.putInt("passengers", Integer.parseInt(mTextMaxPassengers.getText().toString()));
                bundle.putString("time", mTextDepartTime.getText().toString());

                i.putExtras(bundle);
                if (verifyFields()) {
                    startActivity(i);
                    finish();
                }

            }
        });
        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(driverView.getContext());
                View layout = driverinflater.inflate(R.layout.activity_car_help,null,false);
                Button confirmButton = (Button)layout.findViewById(R.id.car_help_close_button);
                Button editButton = (Button) layout.findViewById(R.id.car_help_edit_button);
                alertdialogBuilder.setView(layout);
                final AlertDialog helpdialog = alertdialogBuilder.create();
                confirmButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        helpdialog.dismiss();
                    }
                });
                editButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), EditCarInfoActivity.class);
                        helpdialog.dismiss();
                        startActivity(intent);
                    }
                });
                helpdialog.show();
            }
        });
    }


    @Override
    public void onStart(){
        super.onStart();
        ValueEventListener rideListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Car c = dataSnapshot.getValue(Car.class);
                if(c != null){
                    mTextColor.setText(c.color);
                    mTextYear.setText(c.year);
                    mTextMake.setText(c.make);
                    mTextModel.setText(c.model);
                    mTextLicensePlate.setText(c.plate);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DriverActivity.this, "Failed to load car.", Toast.LENGTH_SHORT).show();
            }
        };
        mCarRef.addValueEventListener(rideListener);

        mCarListener = rideListener;

    }

    @Override
    public void onStop(){
        super.onStop();

        if(mCarListener != null){
            mCarRef.removeEventListener(mCarListener);
        }

    }

    private void writeNewRide() {
        //Transfer all data to the field
        Ride ride = rideBuilder.build();
        //String key = mDatabase.child("rides").child(ride.getDriver()).push().getKey();
        Map<String, Object> rideMap = ride.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/rides/" + ride.driverid, rideMap);

        mDatabase.updateChildren(childUpdates);
    }

    private void setForm() {
        mDive = (Button) findViewById(R.id.buttonDive);
        mHelpButton = (Button) findViewById(R.id.driver_help_button);
        driverinflater = getLayoutInflater();
        driverView =this.findViewById(R.id.activity_driver);

        mTextStart = (EditText) findViewById(R.id.editTextStart);
        mTextDestination = (EditText) findViewById(R.id.editTextDestination);
        mTextDepartTime = (EditText) findViewById(R.id.editTextDepartTime);
        mTextColor = (EditText) findViewById(R.id.editTextColor);
        mTextYear = (EditText) findViewById(R.id.editTextYear);
        mTextMake = (EditText) findViewById(R.id.editTextMake);
        mTextModel = (EditText) findViewById(R.id.editTextModel);
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
        return true;
    }
}
