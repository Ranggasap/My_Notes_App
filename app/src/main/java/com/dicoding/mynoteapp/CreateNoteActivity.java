package com.dicoding.mynoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mCreateNoteTitle, mCreateNoteContent;
    FloatingActionButton mSaveNote;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    ProgressBar mProgressBarCreateNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        mCreateNoteTitle = findViewById(R.id.createNoteTitle);
        mCreateNoteContent = findViewById(R.id.createNoteContent);
        mSaveNote = findViewById(R.id.saveNoteFab);

        mProgressBarCreateNote = findViewById(R.id.progressBarCreateNote);

        Toolbar toolbar = findViewById(R.id.toolBarCreateNote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = firebaseAuth.getInstance();
        firebaseFirestore = firebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();

        mSaveNote.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.saveNoteFab){
            String title = mCreateNoteTitle.getText().toString();
            String content = mCreateNoteContent.getText().toString();

            if(title.isEmpty() || content.isEmpty()){
                Toast.makeText(getApplicationContext(), "All Fields are Required",
                        Toast.LENGTH_SHORT).show();
            }else{
                mProgressBarCreateNote.setVisibility(View.VISIBLE);
                DocumentReference documentReference = firebaseFirestore
                        .collection("notes")
                        .document(firebaseUser.getUid()).collection("myNotes").document();

                Map<String, Object> note = new HashMap<>();
                note.put("title", title);
                note.put("content", content);

                documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Note Has Been Created ",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to Create Note",
                                Toast.LENGTH_SHORT).show();
                        mProgressBarCreateNote.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }
    }
}