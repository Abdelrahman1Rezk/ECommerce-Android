package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import kotlin.text.Regex;

public class SignupPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //---------
        mAuth = FirebaseAuth.getInstance();
        //---------
        TextView toSigninView = (TextView) findViewById(R.id.textView5);
        EditText userSignup = (EditText) findViewById(R.id.user_signup);
        EditText emailSignup = (EditText) findViewById(R.id.email_signup);
        EditText passwordSignup = (EditText) findViewById(R.id.password_signup);
        EditText passwordConfSignup = (EditText) findViewById(R.id.password_conf_signup);
        Button signupbtn = (Button) findViewById(R.id.signup_btn);
        toSigninView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSignLogin = new Intent(getApplicationContext(),LoginPage.class);
                startActivity(toSignLogin);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userText = userSignup.getText().toString().trim();
                String emailText = emailSignup.getText().toString().trim();
                String passwordText = passwordSignup.getText().toString().trim();
                String passwordConfText = passwordConfSignup.getText().toString().trim();
                if(emailText.isEmpty()){
                    emailSignup.setError("Please Enter Email");
                    emailSignup.requestFocus();
                    return ;
                }
                if(passwordText.isEmpty()){

                    passwordSignup.setError("Please Enter Password");
                    passwordSignup.requestFocus();
                    return ;
                }
                if(passwordConfText.isEmpty()){

                    passwordConfSignup.setError("Please Confirm Password");
                    passwordConfSignup.requestFocus();
                    return ;
                }
                if(userText.isEmpty()){
                    userSignup.setError("Please Enter UserName");
                    userSignup.requestFocus();
                }
//                if(!userText.matches("\\A\\w{1,20}\\z")){
//                    userSignup.setError("Please Enter UserName");
//                    userSignup.requestFocus();
//                }
//                if(!passwordText.equals(passwordConfText)){
//                    passwordConfSignup.setError("password didn't match");
//                    passwordConfSignup.requestFocus();
//                    return ;
//                }
                if(!emailText.isEmpty() && !passwordText.isEmpty() && !passwordConfText.isEmpty()){
                    if(emailText.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                        if(passwordText.length() >= 5){
                            if(passwordText.equals(passwordConfText)){
                                registerFunc(emailText,passwordConfText);
                                emailSignup.setText("");
                                userSignup.setText("");
                                passwordSignup.setText("");
                                passwordConfSignup.setText("");
                            Intent secondsignup = new Intent(getApplicationContext(),SignupPageSecond.class);
                            secondsignup.putExtra("userName",userText);
                            secondsignup.putExtra("userEmail",emailText);
                            secondsignup.putExtra("userPass",passwordConfText);
                            startActivity(secondsignup);
                            }
                            else{
                                passwordConfSignup.setError("password didn't match");
                                passwordConfSignup.requestFocus();
                                return ;
                            }

                        }else{
                            passwordSignup.setError("Enter at least 5 character");
                            passwordSignup.requestFocus();
                            return ;
                        }
                    }else{
                        emailSignup.setError("Enter Valid Email ID");
                        emailSignup.requestFocus();
                        return ;
                    }
                }
                //----------Intent to second screen

                //----------
            }
        });


    }

    private void registerFunc(String emailText, String PasswordConfText) {
        String userEmailVal =  emailText;
        String userPasswordConfVal = PasswordConfText;
        //------
        mAuth.createUserWithEmailAndPassword(userEmailVal,userPasswordConfVal).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupPage.this,"Register Success || Complete in Next Page",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(SignupPage.this,"Register Failed || Try again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}