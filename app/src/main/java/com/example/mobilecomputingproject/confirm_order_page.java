package com.example.mobilecomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilecomputingproject.databinding.ActivityGoogleMapLocBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class confirm_order_page extends AppCompatActivity {
    Spinner spinner_rate ;
    EditText user_opinion,user_prod;
    Button getLoc ;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order_page);
        spinner_rate = (Spinner) findViewById(R.id.spinner_rate);
        user_opinion = (EditText) findViewById(R.id.opinion);
        user_prod = (EditText) findViewById(R.id.opinionprod);
        getLoc = (Button) findViewById(R.id.loc_btn_id);
        mAuth = FirebaseAuth.getInstance();
        ArrayList<String> spinnerRate = new ArrayList<>();
        spinnerRate.add("1");
        spinnerRate.add("2");
        spinnerRate.add("3");
        spinnerRate.add("4");
        spinnerRate.add("5");
        ArrayAdapter<String> spinnerAdapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerRate);
        spinnerAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rate.setAdapter(spinnerAdapt);

        databaseReference = FirebaseDatabase.getInstance().getReference("Opinion");
        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtext = mAuth.getCurrentUser().getEmail().toString();
                String opiniontext = user_opinion.getText().toString().trim();
                String ordertext = user_prod.getText().toString().trim();
                String spinnertext = spinner_rate.getSelectedItem().toString();
                String id = databaseReference.push().getKey();
                databaseReference.child(id).child("name").setValue(emailtext.toString());
                databaseReference.child(id).child("opinion").setValue(opiniontext.toString());
                databaseReference.child(id).child("rate").setValue(spinnertext.toString());
                databaseReference.child(id).child("id").setValue(id.toString());
                databaseReference.child(id).child("order").setValue(ordertext.toString());
                user_opinion.setText("");
                Toast.makeText(confirm_order_page.this, "Your Opinion about the order is recorded", Toast.LENGTH_SHORT).show();
                Intent toHome = new Intent(getApplicationContext(),MainHomePage.class);
                startActivity(toHome);

            }
        });
    }

}