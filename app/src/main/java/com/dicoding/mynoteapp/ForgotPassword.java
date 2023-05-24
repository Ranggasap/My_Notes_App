package com.dicoding.mynoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText mForgotPassword;
    private Button mForgotPasswordButton;
    private TextView mBackToLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();

        mForgotPassword = findViewById(R.id.forgotPassword);
        mForgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        mBackToLogin = findViewById(R.id.backToLogin);

        firebaseAuth = FirebaseAuth.getInstance();


        mBackToLogin.setOnClickListener(this);

        mForgotPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backToLogin){
            // Stacking Activity
//            Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
//            startActivity(intent);
            finish();
        }
        if(v.getId() == R.id.forgotPasswordButton){
            String email = mForgotPassword.getText().toString().trim();
            if(email.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            }else{
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Forgot Password has been sent, Please check out your email", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Sorry, Email is not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

}