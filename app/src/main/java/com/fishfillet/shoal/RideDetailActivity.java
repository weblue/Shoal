package com.fishfillet.shoal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fishfillet.shoal.BaseActivity;
import com.fishfillet.shoal.R;
import com.fishfillet.shoal.model.Ride;
import com.google.firebase.database.DatabaseReference;

import static android.graphics.Color.RGBToHSV;
import static android.graphics.Color.argb;

/**
 * Created by Stephen on 11/12/2016.
 */

public class RideDetailActivity  extends BaseActivity{
    Button mJoin;
    Ride.RideBuilder rideBuilder = new Ride.RideBuilder();
    private DatabaseReference mDatabase;
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

    int confirmColor = argb(255,0,255,0);
    int doneColor = argb(255,0,128,0);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);
        setup();
        setForm();

    }

    private void setForm() {
        mJoin = (Button) findViewById(R.id.buttonJoin);
        mJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mJoin.getText().toString();
                if(text.contains("Join")){
                    mJoin.setText("Confirm");
                    mJoin.setBackgroundColor(confirmColor);
                }
                else if(text.contains("Confirm")){
                    mJoin.setText("Pool Joined");
                    mJoin.setBackgroundColor(doneColor);
                    //TODO: Inform Database
                }

            }
        });
        Ride r = new Ride();
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
