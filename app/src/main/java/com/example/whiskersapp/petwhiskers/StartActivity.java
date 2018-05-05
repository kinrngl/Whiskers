package com.example.whiskersapp.petwhiskers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void signupView(View view){
        Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void loginView(View view){
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
