package com.dicoding.mynoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTitleNoteDetail, mContentNoteDetail;
    FloatingActionButton mGoToEditNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        mTitleNoteDetail = findViewById(R.id.detailNoteTitle);
        mContentNoteDetail = findViewById(R.id.detailNoteContent);
        mGoToEditNote = findViewById(R.id.goToEditNote);
        Toolbar toolbar = findViewById(R.id.toolBarDetailNote);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();

        mGoToEditNote.setOnClickListener(this);

        mContentNoteDetail.setText(data.getStringExtra("content"));
        mTitleNoteDetail.setText(data.getStringExtra("title"));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.goToEditNote){
            Intent data = getIntent();
            Intent intent = new Intent(v.getContext(), EditNoteActivity.class);
            intent.putExtra("title", data.getStringExtra("title"));
            intent.putExtra("content", data.getStringExtra("content"));
            intent.putExtra("noteId", data.getStringExtra("noteId"));
            v.getContext().startActivity(intent);

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}