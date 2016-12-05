package com.fishfillet.shoal;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.design.widget.Snackbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Travis Nguyen on 11/27/2016.
 */

public class ForgotPasswordActivity extends NoUserActivity {

    private EditText inputEmail;
    private Button btnReset, btnBack;
    private ImageButton mBtnEmailClear;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forgot_password);
        super.onCreate(savedInstanceState);

        inputEmail = (EditText) findViewById(R.id.forgot_password_et_email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        mBtnEmailClear = (ImageButton) findViewById(R.id.login_btn_idClear);
        progressDialog = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();

        mBtnEmailClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEmail.setText("");
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Snackbar.make(findViewById(android.R.id.content).getRootView(), "Enter your VT email", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Snackbar.make(findViewById(android.R.id.content).getRootView(), "Instructions sent to email to reset your password!", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    Snackbar.make(findViewById(android.R.id.content).getRootView(), "Error: failed to send reset email!", Snackbar.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
            }
        });

    }
}