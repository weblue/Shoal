package com.fishfillet.shoal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.Snackbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by nader on 11/6/16.
 */

public class LoginActivity extends NoUserActivity{
    //UI controls
    private Button mSignin, mSignup, mForgotPassword;
    private EditText mEmail, mPassword;
    private ProgressDialog mProgress;

    //Firebase Auth class
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        //UI components
        mSignin = (Button) findViewById(R.id.signin);
        mSignup = (Button) findViewById(R.id.signup);
        mForgotPassword = (Button) findViewById(R.id.forgot);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signin();
            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


    }

    private void Signin() {

        //Getting Email and password from users
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Snackbar.make(findViewById(android.R.id.content).getRootView(), "Please enter email", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Snackbar.make(findViewById(android.R.id.content).getRootView(), "Please enter Password", Snackbar.LENGTH_LONG).show();
            return;
        }

        //If email and password provided display progress dialog
        mProgress.setMessage("Signing in please wait.....");
        mProgress.show();

        final Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Snackbar.make(findViewById(android.R.id.content).getRootView(), "Successfully signed in", Snackbar.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }else{
                    Snackbar.make(findViewById(android.R.id.content).getRootView(), "There was an error....", Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }
}
