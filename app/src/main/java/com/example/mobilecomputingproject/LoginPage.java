package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class LoginPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //--------
        mAuth = FirebaseAuth.getInstance();
        //--------
        EditText emailField = (EditText) findViewById(R.id.email_id);
        EditText passwordField = (EditText) findViewById(R.id.password_id);
        Button loginbtn = (Button) findViewById(R.id.login_btn_id);
        Button forgotPassBtn = (Button) findViewById(R.id.forgotPass);
        TextView signupTextView = (TextView) findViewById(R.id.view_signup);
        CheckBox rememberme = (CheckBox) findViewById(R.id.check_remember);
        SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
        boolean remembero = chrem.getBoolean("remember", false);
        if(remembero == true){
            Intent mainPageHHome = new Intent(LoginPage.this,MainHomePage.class);
            startActivity(mainPageHHome);
        }else if(remembero == false){
            Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
        }
        rememberme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(LoginPage.this, "beeeeeeeeeeeeeeeeeeeeee", Toast.LENGTH_SHORT).show();
                if(rememberme.isChecked()){
                    SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
                    SharedPreferences.Editor editor = chrem.edit();
                    editor.putBoolean("remember", true);
                    editor.apply();
                }else{
                    SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
                    SharedPreferences.Editor editor = chrem.edit();
                    editor.putBoolean("remember", false);
                    editor.apply();
                }
//                SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
//                boolean remembero = chrem.getBoolean("remember", false);
            }
        });
        rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Toast.makeText(LoginPage.this, "beeeeeeeeeeeeeeeeeeeeee", Toast.LENGTH_SHORT).show();
                    if(b){

                    }else{
                        SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
                        SharedPreferences.Editor editor = chrem.edit();
                        editor.putBoolean("remember", false);
                        editor.apply();
                    }
                SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
                boolean remembero = chrem.getBoolean("remember", false);

            }
        });
        forgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter Your E-Mail");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mailres = resetMail.getText().toString().trim();
                        mAuth.sendPasswordResetEmail(mailres).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginPage.this, "Reset Link is Sent to Your E-mail", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginPage.this, "Link is not sent ! Try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //----
                    }
                });
                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Close Dialog
                    }
                });
                passwordResetDialog.create().show();
            }
        });
        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iToSignup = new Intent(LoginPage.this,SignupPage.class);
                startActivity(iToSignup);
            }
        });
        rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailTextF = emailField.getText().toString().trim();
                String passwordTextF = passwordField.getText().toString().trim();
                if(emailTextF.isEmpty()){
                    emailField.setError("Please Enter Email");
                    emailField.requestFocus();
                    return ;
                }
                if(passwordTextF.isEmpty()){

                    passwordField.setError("Please Enter Password");
                    passwordField.requestFocus();
                    return ;
                }
                if(!emailTextF.isEmpty() && !passwordTextF.isEmpty()){
                    if(emailTextF.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
//                        Toast.makeText(LoginPage.this, "Login Success",Toast.LENGTH_LONG).show();
                        if(rememberme.isChecked()){
//                            Paper.book().write(Prevalent.UserEmailKey,emailTextF);
//                            Paper.book().write(Prevalent.UserPasswordKey,passwordTextF);
                        }
                        if(emailTextF.equals("admin@admin.com") && passwordTextF.equals("admin")){
                            Intent toAdminPages = new Intent(getApplicationContext(),admin_Home_page.class);
                            startActivity(toAdminPages);
                        }else{
                            loginFunc(emailTextF,passwordTextF);
                        }

                        //-----
                        //-----

                    }else{
                        emailField.setError("Enter Valid Email ID");
                        emailField.requestFocus();
                        return ;
                    }
                }
//                loginFunc(emailTextF,passwordTextF);
            }
        });
    }

    private void loginFunc(String emailTextF , String PasswordTextF) {
        String userEmailVal =  emailTextF;
        String userPasswordVal = PasswordTextF;
        mAuth.signInWithEmailAndPassword(userEmailVal,userPasswordVal).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginPage.this,"Authentication Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginPage.this,MainHomePage.class));
                }else{
                    Toast.makeText(LoginPage.this,"Authentication Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}