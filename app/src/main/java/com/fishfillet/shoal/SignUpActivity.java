package com.fishfillet.shoal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fishfillet.shoal.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Travis Nguyen on 11/27/2016.
 */
public class SignUpActivity extends AppCompatActivity {

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

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

//        if (age <= 12) {
//            Toast.makeText(this, "Please enter a valid age", Toast.LENGTH_LONG).show();
//            return;
//        }

        //add user to the database
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.activity_start_enter, R.anim.activity_start_exit);
                finish();
            }
        }, 1000);

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(SignUpActivity.this, "Successfully registered " + firebaseAuth.getCurrentUser().getUid().toString(), Toast.LENGTH_LONG).show();

                            String user_uid = firebaseAuth.getCurrentUser().getUid().toString();

                            User u_info = new User(user_uid, first_name, last_name, email);

                            mFirebaseDatabaseReference.child("user_info").push().setValue(u_info);

                        } else {
                            //display some message here
                            Toast.makeText(SignUpActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

        //  MainActivity.knight.setmAge(age);
    }

}
