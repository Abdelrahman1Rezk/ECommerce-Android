package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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

public class report_feedback_page extends AppCompatActivity {
    DatabaseReference databaseReference,transRef;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ArrayList barEntriesArrayList;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList<String> productsSaledNames;
    ArrayList<String> productsSaledQuantity;
    FirebaseFirestore db;
    ListView transactionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_feedback_page);
        transactionsList = findViewById(R.id.list_trans);
        databaseReference = FirebaseDatabase.getInstance().getReference("Opinion");
        transRef = FirebaseDatabase.getInstance().getReference("Transaction");
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.items_list_styles,arrayList);
        transactionsList.setAdapter(arrayAdapter);
        productsSaledNames = new ArrayList<>();
        productsSaledQuantity = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        barEntriesArrayList = new ArrayList();
        barChart = findViewById(R.id.bar_chart);
        //---/-/-/-/-/-
        int i = 0;
        db.collection("Saled").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges())
                {
                    String  quantity   =  documentChange.getDocument().getData().get("quantity").toString();
                    String prodname = documentChange.getDocument().getData().get("name").toString();
                    productsSaledNames.add(prodname.toString());
                    productsSaledQuantity.add(quantity.toString());
                }
                for (int j = 0;j< productsSaledNames.size();j++){
                    barEntriesArrayList.add(new BarEntry(Integer.parseInt(productsSaledQuantity.get(j)),j));
                }

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                barEntriesArrayList.add(new BarEntry(44,0));
//                barEntriesArrayList.add(new BarEntry(49,1));
//                barEntriesArrayList.add(new BarEntry(30,2));
//                barEntriesArrayList.add(new BarEntry(15,3));
//                barEntriesArrayList.add(new BarEntry(50,4));
//                barEntriesArrayList.add(new BarEntry(30,5));
//                barEntriesArrayList.add(new BarEntry(27,6));
                ArrayList<String> mes = new ArrayList<>();
                mes.add("Prod");
                mes.add("Prod1");
                mes.add("Prod2");
                mes.add("Prod3");
                mes.add("Prod4");
                mes.add("Prod5");
                mes.add("Prod6");
                barDataSet = new BarDataSet(barEntriesArrayList,"Sales of each product");
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                barData = new BarData(productsSaledNames,barDataSet);
                barChart.setData(barData);

                barChart.setDragEnabled(true);

            }
        },3000);
//        barEntriesArrayList.add(new BarEntry(44f,0));
//        barEntriesArrayList.add(new BarEntry(44f,1));
//        barEntriesArrayList.add(new BarEntry(44f,2));
//        barEntriesArrayList.add(new BarEntry(44f,3));
//        barEntriesArrayList.add(new BarEntry(44f,4));
//        barEntriesArrayList.add(new BarEntry(44f,5));
//        barEntriesArrayList.add(new BarEntry(44f,6));
//        ArrayList<String> mes = new ArrayList<>();
//        mes.add("Prod");
//        mes.add("Prod1");
//        mes.add("Prod2");
//        mes.add("Prod3");
//        mes.add("Prod4");
//        mes.add("Prod5");
//        mes.add("Prod6");
//        barDataSet = new BarDataSet(barEntriesArrayList,"Sales of each product");
//        barData = new BarData(mes,barDataSet);
//        barChart.setData(barData);
//        barChart.setTouchEnabled(true);
//        barChart.setDragEnabled(true);
        transRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String value1 = snapshot.getValue(Transactions.class).getEmail(),
                        value2 = snapshot.getValue(Transactions.class).getProduct(),
                        value3 = snapshot.getValue(Transactions.class).getDate();
                String itemToView = "Email: " + value1 + "\n" +"Product Name: "+ value2 + "\n"+"Date: "+ value3+ "\n" ;
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



    }
}