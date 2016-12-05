package com.fishfillet.shoal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.fishfillet.shoal.BaseActivity;
import com.fishfillet.shoal.R;
import com.fishfillet.shoal.model.Ride;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.RGBToHSV;
import static android.graphics.Color.argb;

/**
 * Created by Stephen on 11/12/2016.
 */
//TODO: show how many seats are left.
public class RideDetailActivity  extends BaseActivity{

    public static final String EXTRA_RIDE_KEY = "ride_key";

    Button mJoin;
    Ride.RideBuilder rideBuilder = new Ride.RideBuilder();
    private DatabaseReference mDatabase;
    private String mRideKey;
    private DatabaseReference mRideRef;
    private ValueEventListener mRideListener;
    //needed fields
    private TextView mTextStart;
    private TextView mTextDestination;
    private TextView mTextDepartTime;
    private TextView mTextModel;
    private TextView mTextLicensePlate;
    private TextView mTextMaxPassengers;
    private Button mDiveIn;
    private TextView mTextYear;
    private TextView mTextMake;
    private TextView mTextColor;
    private TextView mTextNotes;

    private String time;
    private int passengers;
    private int maxPassengers;

    int startColor = argb(255,211,211,211);
    int confirmColor = argb(255,0,255,0);
    int doneColor = argb(255,0,128,0);
    int fullColor = argb(255,128,0,0);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_ride_detail);
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        mRideKey = bundle.getString(EXTRA_RIDE_KEY);

        setup();
        setForm();

    }

    private void setForm() {
        //mRideKey = .getStringExtra("EXTRA_RIDE_KEY");
        if(mRideKey == null){
            throw new IllegalArgumentException("Must pass EXTRA_RIDE_KEY");
        }
        mRideRef = FirebaseDatabase.getInstance().getReference().child("rides").child(mRideKey);

        mJoin = (Button) findViewById(R.id.buttonJoin);
        mJoin.setBackgroundColor(startColor);
        mJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mJoin.getText().toString();
                if(text.contains("Join Pool")){
                    mJoin.setText("Confirm");
                    mJoin.getBackground().setColorFilter(confirmColor, PorterDuff.Mode.DARKEN);
                    Snackbar.make(findViewById(android.R.id.content).getRootView(), "Press Again to confirm", Snackbar.LENGTH_SHORT).show();
                }
                else if(text.contains("Confirm")){
                    mJoin.setText("Pool Joined");
                    mJoin.getBackground().setColorFilter(doneColor, PorterDuff.Mode.DARKEN);
                    Snackbar.make(findViewById(android.R.id.content).getRootView(), "Succesfully Joined Ride", Snackbar.LENGTH_SHORT).show();
                    //TODO: Inform Database
                    onConfirmClick();

                    DatabaseReference passengersRef = mRideRef.child("riders");
                    //if(maxPassengers != passengers){
                        Map<String, Object> riderUpdates = new HashMap<String, Object>();
                        riderUpdates.put(getUid(), getUid());
                        passengersRef.updateChildren(riderUpdates);



                        Intent intent = new Intent(RideDetailActivity.this, WaitingScreenActivity.class);
                        intent.putExtra("time", time);
                        intent.putExtra("passengers",passengers);
                        intent.putExtra("maxPassengers",mTextMaxPassengers.getText().toString());
                        intent.putExtra("ride_key", mRideKey);
                        startActivity(intent);
                   // }

                }
                //Else do nothing

            }
        });


    }

    @Override
    public void onStart(){
        super.onStart();
        ValueEventListener rideListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Ride r = dataSnapshot.getValue(Ride.class);
                mTextStart.setText("Start Location: " + r.locstart);
                mTextDestination.setText("Destination: " + r.locdest);
                mTextDepartTime.setText("Departure time: " + r.timedepart);
                mTextColor.setText("Car color: " + r.carcolor);
                mTextYear.setText("Car year: " + r.caryear);
                mTextMake.setText("Car make: " + r.carmake);
                mTextModel.setText("Car model: " + r.carmodel);
                mTextLicensePlate.setText("License plate: " + r.plate);
                mTextMaxPassengers.setText("Spots Left: " + String.valueOf(r.passengersleft));
                mTextNotes.setText("Notes: " + r.notes);

                time = r.timedepart;
                passengers = r.maxpassengers - r.passengersleft;
                maxPassengers = r.maxpassengers;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(findViewById(android.R.id.content).getRootView(), "Failed to load ride.", Snackbar.LENGTH_SHORT).show();
            }
        };
        mRideRef.addValueEventListener(rideListener);

        mRideListener = rideListener;

    }

    @Override
    public void onStop(){
        super.onStop();

        if(mRideListener != null){
            mRideRef.removeEventListener(mRideListener);
        }

    }

    public void setup(){


        mTextStart = (TextView) findViewById(R.id.editTextStart);
        mTextDestination = (TextView) findViewById(R.id.editTextDestination);
        mTextDepartTime = (TextView) findViewById(R.id.editTextDepartTime);
        mTextColor = (TextView) findViewById(R.id.editTextColor);
        mTextYear = (TextView) findViewById(R.id.editTextYear);
        mTextMake = (TextView) findViewById(R.id.editTextMake);
        mTextModel = (TextView) findViewById(R.id.editTextModel);
        mTextLicensePlate = (TextView) findViewById(R.id.editTextLicensePlate);
        mTextMaxPassengers = (TextView) findViewById(R.id.editTextMaxPassengers);
        mTextNotes = (TextView) findViewById(R.id.editTextNotes);

    }

    public void onConfirmClick(){
        mRideRef.runTransaction(new Transaction.Handler(){

            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Ride r = mutableData.getValue(Ride.class);
                if(r == null){
                    return Transaction.success(mutableData);
                }
                if(r.passengersleft > 0){
                    r.passengersleft = r.passengersleft - 1;
                    mutableData.setValue(r);
                    return Transaction.success(mutableData);

                }
                else{
                    mJoin.setText("Full Shoal");
                    mJoin.getBackground().setColorFilter(fullColor, PorterDuff.Mode.DARKEN);
                    return Transaction.abort();//Not sure if this is right :/
                }
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("Passenger added", "postTransaction:onComplete:" + databaseError);
            }
        });

    }
}
