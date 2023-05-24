package com.dicoding.mynoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText mSignUpEmail, mSignUpPassword;
    private Button mSignUpButton;
    private TextView mBackToLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        mSignUpEmail = findViewById(R.id.signUpEmail);
        mSignUpPassword = findViewById(R.id.signUpPassword);
        mSignUpButton = findViewById(R.id.signUpButton);
        mBackToLogin = findViewById(R.id.backToLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        mBackToLogin.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.backToLogin){
            finish();
        }
        if(v.getId() == R.id.signUpButton){
            String email = mSignUpEmail.getText().toString().trim();
            String password = mSignUpPassword.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(getApplicationContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
            }
            else if(password.length() < 7){
                Toast.makeText(getApplicationContext(), "Password must more than 7 characters", Toast.LENGTH_SHORT).show();
            }else{
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Registration Success", Toast.LENGTH_SHORT).show();
                            sendEmailVerification();
                        }else{
                            Toast.makeText(getApplicationContext(), "Failed To Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Verification Email was Sent Please Verify it", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Verification Failed to Sent", Toast.LENGTH_SHORT).show();
        }

    }


}