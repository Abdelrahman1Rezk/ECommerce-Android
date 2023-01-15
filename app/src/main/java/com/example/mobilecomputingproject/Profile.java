package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    EditText userName,userEmail,userDate,userPhone;
    Button logoutBtn ;
    FirebaseAuth firebaseAuth;
    String userMailAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName = (EditText) findViewById(R.id.user_name);
        userEmail = (EditText) findViewById(R.id.user_email);
        userDate = (EditText) findViewById(R.id.user_date);
        userPhone = (EditText) findViewById(R.id.user_phone);
        logoutBtn = (Button) findViewById(R.id.log_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        userMailAuth = firebaseAuth.getCurrentUser().getEmail();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String value1 = snapshot.getValue(Users.class).getName(),
                        value2 = snapshot.getValue(Users.class).getEmail(),
                        value3 = snapshot.getValue(Users.class).getDate(),
                        value4 = snapshot.getValue(Users.class).getPhone();

                if(userMailAuth.equals(value2)){
                    userName.setText(value1.toString());
                    userEmail.setText(value2.toString());
                    userDate.setText(value3.toString());
                    userPhone.setText(value4.toString());
                }else{
//                    userName.setText(value1.toString());
//                    userEmail.setText(value2.toString());
//                    userDate.setText(value3.toString());
//                    userPhone.setText(value4.toString());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
                SharedPreferences.Editor editor = chrem.edit();
                editor.putBoolean("remember", false);
                editor.apply();
                firebaseAuth.signOut();
                Intent toLogin = new Intent(getApplicationContext(),LoginPage.class);
                startActivity(toLogin);
                Profile.this.finish();

            }
        });

    }
}