package com.example.mobilecomputingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;

public class SignupPageSecond extends AppCompatActivity {

    DatePicker datePicker;
    Button second_sign_btn;
    EditText phoneNumber ;
    CountryCodePicker countryCodePicker;
//    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page_second);

        Intent secondSignup = getIntent();
        String userName = secondSignup.getStringExtra("userName");
        String userEmail = secondSignup.getStringExtra("userEmail");
        String userPassConf = secondSignup.getStringExtra("userPass");
        Button next_second_btn = (Button) findViewById(R.id.signup_second_btn);
        phoneNumber = (EditText) findViewById(R.id.phone_signup);
        countryCodePicker = findViewById(R.id.code_picker);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        datePicker = findViewById(R.id.age_picker);
        second_sign_btn = (Button) findViewById(R.id.signup_second_btn);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        String date = day+"/"+month+"/"+year;


        next_second_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent second_page_toNext = new Intent(SignupPageSecond.this,verify_code.class);
//                second_page_toNext.putExtra("userName",userName);
//                second_page_toNext.putExtra("userEmail",userEmail);
//                second_page_toNext.putExtra("userPass",userPassConf);
//                second_page_toNext.putExtra("userPhone",userPhoneNum);
//                startActivity(second_page_toNext);
                //-------Save Data
                String userEnteredNum = phoneNumber.getText().toString().trim();
                String userPhoneNum = "+"+countryCodePicker.getSelectedCountryCode()+userEnteredNum;
                Users user = new Users(userName,userEmail,userPassConf,date,userPhoneNum,"Cairo");
                String id = databaseReference.push().getKey();
                databaseReference.child(id).child("email").setValue(user.getEmail());
                databaseReference.child(id).child("name").setValue(user.getName());
                databaseReference.child(id).child("pass").setValue(user.getPass());
                databaseReference.child(id).child("date").setValue(user.getDate());
                databaseReference.child(id).child("phone").setValue(user.getPhone());
                databaseReference.child(id).child("location").setValue(user.getLocation());
                databaseReference.child(id).child("id").setValue(id);

                Toast.makeText(SignupPageSecond.this, "User is added succefully", Toast.LENGTH_SHORT).show();
                Intent toLog = new Intent(getApplicationContext(),LoginPage.class);
                startActivity(toLog);
                SignupPageSecond.this.finish();
                //--------
            }
        });









//        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//        int userAge = datePicker.getYear();
//        if((currentYear - userAge) < 12){
//            Toast.makeText(this, "Your age not allowed to register", Toast.LENGTH_SHORT).show();
//            return;
//        }else{
//            return;
//        }

    }
}