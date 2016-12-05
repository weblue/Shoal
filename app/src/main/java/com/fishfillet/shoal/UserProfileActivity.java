package com.fishfillet.shoal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fishfillet.shoal.model.Car;
import com.fishfillet.shoal.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by steph on 12/4/2016.
 */

public class UserProfileActivity extends BaseActivity {

    DatabaseReference mDatabase;
    DatabaseReference mUserRef;
    DatabaseReference mCarRef;
    FirebaseAuth mFirebaseAuth;

    private String userId;
    private ValueEventListener mUserListener;
    private ValueEventListener mCarListener;
    private Button mHelpButton;
    private TextView mTextFirstName;
    private TextView mTextLastName;
    private TextView mTextEmail;
    private TextView mTextCarMake;
    private TextView mTextCarModel;
    private TextView mTextCarYear;;
    private TextView mTextCarColor;
    private TextView mTextCarPlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("userId");
        setUp();

        mDatabase = FirebaseDatabase.getInstance().getReference();
       // mFirebaseAuth.get
        mUserRef = FirebaseDatabase.getInstance().getReference().child("user_info").child(userId);
        mCarRef = mUserRef.child("car_info");
        if(getUid().equals(userId)){
           // disableEdit();
        }
    }


    @Override
    public void onStart(){
        super.onStart();
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                if(u != null){
                    mTextEmail.setText(u.getEmail());
                    mTextFirstName.setText(u.getFirst_name());
                    mTextLastName.setText(u.getLast_name());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserProfileActivity.this, "Failed to load car.", Toast.LENGTH_SHORT).show();
            }
        };
        ValueEventListener carListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Car c = dataSnapshot.getValue(Car.class);
                if(c != null){
                    mTextCarMake.setText(c.make);
                    mTextCarModel.setText(c.model);
                    mTextCarYear.setText(c.year);
                    mTextCarColor.setText(c.color);
                    mTextCarPlate.setText(c.plate);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserProfileActivity.this, "Failed to load car.", Toast.LENGTH_SHORT).show();
            }
        };

        mUserRef.addValueEventListener(userListener);
        mCarRef.addValueEventListener(carListener);
        mCarListener = carListener;
        mUserListener = userListener;

    }



    @Override
    public void onStop(){
        super.onStop();

        if(mUserListener != null){
            mUserRef.removeEventListener(mUserListener);
        }
        if(mCarListener != null){
            mCarRef.removeEventListener(mCarListener);
        }

    }

    private void setUp(){
        mHelpButton = (Button) findViewById(R.id.driver_help_button);

        mTextFirstName = (TextView) findViewById(R.id.mTextFirstName);
        mTextLastName = (TextView) findViewById(R.id.mTextLastName);
        mTextEmail = (TextView) findViewById(R.id.mTextEmail);
        mTextCarMake = (TextView) findViewById(R.id.mTextMake);
        mTextCarModel = (TextView) findViewById(R.id.mTextModel);
        mTextCarYear = (TextView) findViewById(R.id.mTextYear);
        mTextCarColor  = (TextView) findViewById(R.id.mTextColor);
        mTextCarPlate  = (TextView) findViewById(R.id.mTextLicensePlate);
    }

}
