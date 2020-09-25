package com.suek.ex86firebasecloudmessagepush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent= getIntent();
        String name= intent.getStringExtra("name");
        String msg= intent.getStringExtra("msg");

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setSubtitle(msg);
    }
}
