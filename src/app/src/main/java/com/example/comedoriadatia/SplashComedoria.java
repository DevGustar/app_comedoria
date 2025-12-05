package com.example.comedoriadatia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class SplashComedoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_comedoria);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashComedoria.this, SignUp.class);
            startActivity(intent);
            finish();
        }, 3000);

    }
}
