package com.fishfillet.shoal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by TravisNguyen on 11/13/16.
 */

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        int loadingTime = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, loadingTime);
    }

    /**
     * Starts MainActivity
     */
    private void startMainActivity() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Intent intent;
        if (user != null) {
            // User is signed in
            intent = new Intent(IntroActivity.this, MainActivity.class);
        } else {
            // User is signed out
            intent = new Intent(IntroActivity.this, LoginActivity.class);
        }

        startActivity(intent);
        overridePendingTransition(R.anim.activity_start_enter, R.anim.activity_start_exit);
        finish();
    }
}
