package com.fishfillet.shoal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.fishfillet.shoal.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Travis Nguyen on 11/27/2016.
 */
public class SignUpActivity extends MainActivity {

    //Static methods needed to create the needed layout
    public static LayoutInflater signupinflater;
    public static View signupView;
    //public static int popupWidth, popupHeight,displayHeight,displayWidth;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("condition");
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Button buttonSignup;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        //editTextAge = (EditText) findViewById(R.id.editTextAge);
        //spannerGender = (Spanner) find

        signupView =this.findViewById(R.id.activity_sign_up);
        signupinflater = getLayoutInflater();
        //Magic
        /*

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayWidth = size.x;
        displayHeight = size.y;
        popupWidth = size.x * 3 / 4;
        popupHeight = size.y/4;
        */

        progressDialog = new ProgressDialog(this);
        //name and email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }


    private void registerUser() {

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String first_name = editTextFirstName.getText().toString().trim();
        final String last_name = editTextLastName.getText().toString().trim();
//        int age = Integer.parseInt(editTextAge.getText().toString().trim());

        //checking if email and passwords are empty

        if (TextUtils.isEmpty(first_name)) {
            Toast.makeText(this, "Please enter first name", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(last_name)) {
            Toast.makeText(this, "Please enter last name", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(this, "Passwords need to be at least 6 characters long", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        Task<ProviderQueryResult> r = firebaseAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                Log.d("email search complete", "postTransaction:onComplete:" + task.getResult().getProviders().size());
            }
        });







//        if (age <= 12) {
//            Toast.makeText(this, "Please enter a valid age", Toast.LENGTH_LONG).show();
//            return;
//        }

        //add user to the database
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog.setMessage("Registering Please Wait...");

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                //checking if success

                if (task.isSuccessful()) {
                    //display some message here
                    Toast.makeText(SignUpActivity.this, "Successfully registered " + firebaseAuth.getCurrentUser().getUid().toString(), Toast.LENGTH_LONG).show();

                    String user_uid = firebaseAuth.getCurrentUser().getUid().toString();

                    User u_info = new User(user_uid, first_name, last_name, email);
                    //might need to push somehow.
                    mFirebaseDatabaseReference.child("user_info").push().setValue(u_info);
                    //Magic
                    AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(signupView.getContext());
                    //alertdialogBuilder.setTitle("Driver?");
                    //alertdialogBuilder.setMessage("Do you have a car?");
                    View layout = signupinflater.inflate(R.layout.activity_popup,null,false);
                    Button noCarButton = (Button)layout.findViewById(R.id.no_car_button);
                    Button yesCarButton = (Button) layout.findViewById(R.id.yes_car_button);
                    noCarButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.cancel();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    overridePendingTransition(R.anim.activity_start_enter, R.anim.activity_start_exit);
                                    finish();
                                }
                            }, 500);
                        }
                    });
                    yesCarButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), EditCarInfoActivity.class);
                            intent.putExtra("firstTime", true);
                            startActivity(intent);
                            finish();
                        }
                    });

                    alertdialogBuilder.setView(layout);
                    alertdialogBuilder.create().show();

                    /*alertdialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int d) {
                            Intent intent = new Intent(getApplicationContext(), EditCarInfoActivity.class);
                            intent.putExtra("firstTime", true);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alertdialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int d) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.cancel();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    overridePendingTransition(R.anim.activity_start_enter, R.anim.activity_start_exit);
                                    finish();
                                }
                            }, 500);
                        }
                    });*/
                    //Magic
                    //Un comment code below to se pretty version
                    /*int OFFSET_X = 0;//displayWidth ;
                    int OFFSET_Y = 0;//displayHeight / 4;
                    View layout = signupinflater.inflate(R.layout.activity_popup,null,false);
                    PopupWindow pw = new PopupWindow(layout,popupWidth,popupHeight, true);
                    //com.fishfillet.shoal.SignUpActivity.showPopup();
                    pw.showAtLocation(signupView,Gravity.CENTER, OFFSET_X, OFFSET_Y);
                    Button noCarButton = (Button) layout.findViewById(R.id.no_car_button);
                    Button yesCarButton = (Button) layout.findViewById(R.id.yes_car_button);
                    noCarButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.cancel();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    overridePendingTransition(R.anim.activity_start_enter, R.anim.activity_start_exit);
                                    finish();
                                }
                            }, 500);
                        }
                    });
                    yesCarButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), EditCarInfoActivity.class);
                            intent.putExtra("firstTime", true);
                            startActivity(intent);
                            finish();
                        }
                    });*/



                } else {
                    //display some message here
                    Exception e = task.getException();
                    Toast.makeText(SignUpActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                    //Magic
                    AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(signupView.getContext());
                    alertdialogBuilder.setMessage("Registration Error");
                    alertdialogBuilder.create().show();
                    /*int OFFSET_X = 0;//displayWidth ;
                    int OFFSET_Y = 0;//displayHeight / 4;
                    View layout = signupinflater.inflate(R.layout.activity_popup,null,false);
                    PopupWindow pw = new PopupWindow(layout,popupWidth,popupHeight, true);
                    //com.fishfillet.shoal.SignUpActivity.showPopup();
                    pw.showAtLocation(signupView,Gravity.CENTER, OFFSET_X, OFFSET_Y);*/
                }
                progressDialog.dismiss();
            }
        });

    }

    /*public static Activity getActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            HashMap activities = (HashMap) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        }
        catch(Exception e){
            return null;
        }
        return null;
    }*/
}
