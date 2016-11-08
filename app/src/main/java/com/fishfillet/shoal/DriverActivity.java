package com.fishfillet.shoal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nader on 11/7/16.
 */


public class DriverActivity extends AppCompatActivity {
    Button mDive;
    private DatabaseReference mDatabase;
    //Name fields


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDive = (Button) findViewById(R.id.buttonDive);
        final Intent driverIntent = new Intent(this, DriverActivity.class);
        mDive.setOnClickListener(new View.OnClickListener() {
            //Check fields. do setError(REQUIRED);

            @Override
            public void onClick(View v) {
                Map<String,Object> childUpdates = new HashMap<>();
                String key = "testKey";
                childUpdates.put(key,"doot diddly donger cuckerino haha");
                mDatabase.updateChildren(childUpdates);
            }
        });
    }
}
