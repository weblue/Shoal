package com.fishfillet.shoal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText mTextStart;
    private EditText mTextDestination;
    private EditText mTextDepartTime;
    private EditText mTextModel;
    private EditText mTextLicensePlate;
    private EditText mTextMaxPassengers;
    private Button mDiveIn;
    private EditText mTextYear;
    private EditText mTextMake;
    private EditText mTextColor;
    private EditText mTextNotes;

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
        mTextStart.setText(r.locstart);
        mTextDestination.setText(r.locdest);
        mTextDepartTime.setText(r.timedepart);
        mTextColor.setText(r.carcolor);
        mTextYear.setText(r.caryear);
        mTextMake.setText(r.carmake);
        mTextModel.setText(r.carmodel);
        mTextLicensePlate.setText(r.plate);
        mTextMaxPassengers.setText(r.maxpassengers);
        mTextNotes.setText(r.notes);
    }
    public void setup(){


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
}
