package com.fishfillet.shoal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fishfillet.shoal.BaseActivity;
import com.fishfillet.shoal.R;
import com.fishfillet.shoal.model.Ride;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.graphics.Color.RGBToHSV;
import static android.graphics.Color.argb;

/**
 * Created by Stephen on 11/12/2016.
 */

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

    int startColor = argb(255,211,211,211);
    int confirmColor = argb(255,0,255,0);
    int doneColor = argb(255,0,128,0);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        Bundle bundle = getIntent().getExtras();
        mRideKey = bundle.getString(EXTRA_RIDE_KEY);

        setup();
        setForm();

    }

    private void setForm() {
        mJoin = (Button) findViewById(R.id.buttonJoin);
        mJoin.setBackgroundColor(startColor);
        mJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mJoin.getText().toString();
                if(text.contains("Join Pool")){
                    mJoin.setText("Confirm");
                    mJoin.setBackgroundColor(confirmColor);
                }
                else if(text.contains("Confirm")){
                    mJoin.setText("Pool Joined");
                    mJoin.setBackgroundColor(doneColor);
                    //TODO: Inform Database
                }
                //Else do nothing

            }
        });

        //mRideKey = .getStringExtra("EXTRA_RIDE_KEY");
        if(mRideKey == null){
            throw new IllegalArgumentException("Must pass EXTRA_RIDE_KEY");
        }
        mRideRef = FirebaseDatabase.getInstance().getReference().child("rides").child(mRideKey);
    }

    @Override
    public void onStart(){
        super.onStart();
        ValueEventListener rideListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Ride r = dataSnapshot.getValue(Ride.class);
                mTextStart.setText(mTextStart.getText().toString() + r.locstart);
                mTextDestination.setText(mTextDestination.getText().toString() + r.locdest);
                mTextDepartTime.setText(mTextDepartTime.getText().toString() + r.timedepart);
                mTextColor.setText(mTextColor.getText().toString() + r.carcolor);
                mTextYear.setText(mTextYear.getText().toString() + r.caryear);
                mTextMake.setText(mTextMake.getText().toString() + r.carmake);
                mTextModel.setText(mTextModel.getText().toString() + r.carmodel);
                mTextLicensePlate.setText(mTextLicensePlate.getText().toString() + r.plate);
                mTextMaxPassengers.setText(mTextMaxPassengers.getText().toString() + String.valueOf(r.maxpassengers));
                mTextNotes.setText(mTextNotes.getText().toString() + r.notes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RideDetailActivity.this, "Failed to load ride.", Toast.LENGTH_SHORT).show();
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
}
