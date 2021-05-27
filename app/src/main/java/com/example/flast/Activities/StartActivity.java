package com.example.flast.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.flast.R;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null){
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                }else{
                    Intent entryIntent = new Intent(StartActivity.this, EntryActivity.class);
                    startActivity(entryIntent);
                    finish();
                }
                   /* Intent entryIntent = new Intent(StartActivity.this, EntryActivity.class);
                    startActivity(entryIntent);
                    finish();*/
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}