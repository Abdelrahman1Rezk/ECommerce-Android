package com.example.mobilecomputingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            Intent nn = new Intent(MainActivity.this, LoginPage.class);
                MainActivity.this.startActivity(nn);
                MainActivity.this.finish();
            }
        },3000);
    }
}
/**
 * Login - signup - logout :  hassan
 * manage product & manage category : reefaat
 * search & profile: hatem 
 * payment page & product details : rezk
 * confirm order(rate) & determine location: said
 * display transaction - opinions & chart : hema
 *
 */
