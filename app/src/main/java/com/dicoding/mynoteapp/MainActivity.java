package com.dicoding.mynoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailLogin, mPasswordLogin;
    private Button mButtonLogin, mButtonSignup;
    private TextView mForgotPassword;

    private FirebaseAuth firebaseAuth;

    ProgressBar mProgressBarMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        mEmailLogin = findViewById(R.id.loginEmail);
        mPasswordLogin = findViewById(R.id.loginPassword);
        mButtonLogin = findViewById(R.id.login);
        mForgotPassword = findViewById(R.id.forgotPassword);
        mButtonSignup = findViewById(R.id.signup);
        mProgressBarMainActivity = findViewById(R.id.progressBarMainActivity);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            finish();
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(intent);
        }

        mButtonSignup.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signup){
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.forgotPassword){
            Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.login){
            String email = mEmailLogin.getText().toString().trim();
            String password = mPasswordLogin.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
            }else if(password.length() < 7){
                Toast.makeText(getApplicationContext(), "Password must more than 7 characters", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
                mProgressBarMainActivity.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           checkEmailVerification();

                        }else{
                            Toast.makeText(getApplicationContext(), "Account doesn't exist", Toast.LENGTH_SHORT).show();
                            mProgressBarMainActivity.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }

        }
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified() == true){
            Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(intent);
        }else{
            mProgressBarMainActivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Sign Up and Verified your email first", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}