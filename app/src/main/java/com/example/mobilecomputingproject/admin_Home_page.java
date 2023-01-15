package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class admin_Home_page extends AppCompatActivity {
    Button manage_prod,manage_categ,feedbackPage;
    ListView feedbacks;
    DatabaseReference databaseReference;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        manage_categ = (Button) findViewById(R.id.button5);
        manage_prod = (Button) findViewById(R.id.button6);
        feedbackPage = (Button) findViewById(R.id.buttonFeed);
        feedbacks = findViewById(R.id.feedbacks);
        databaseReference = FirebaseDatabase.getInstance().getReference("Opinion");
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.items_list_styles,arrayList);
        feedbacks.setAdapter(arrayAdapter);
        //-----
        db = FirebaseFirestore.getInstance();
        feedbackPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedbackInto = new Intent(getApplicationContext(),report_feedback_page.class);
                startActivity(feedbackInto);
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String value1 = snapshot.getValue(Feedbacks.class).getName(),
                 value2 = snapshot.getValue(Feedbacks.class).getOpinion(),
                 value3 = snapshot.getValue(Feedbacks.class).getRate(),
                 value4 = snapshot.getValue(Feedbacks.class).getOrder();

//                String val = snapshot.getKey().toString();
//                String val1 = String.valueOf(snapshot.getValue());

//                System.out.println("mohmohmohmohmoh"+value1);
//                System.out.println("mohmohmohmohmoh");
                String itemToView = "Email: " + value1 + "\n" +"Rate: "+ value3 + "\n"+"Product Name: "+ value4+ "\n" + "Opinion: "+value2;
                arrayList.add(itemToView);
                arrayAdapter.notifyDataSetChanged();

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

        manage_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manageP = new Intent(getApplicationContext(),admin_page.class);
                startActivity(manageP);
            }
        });
        manage_categ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manageC = new Intent(getApplicationContext(),category_admin_page.class);
                startActivity(manageC);
            }
        });



    }

}