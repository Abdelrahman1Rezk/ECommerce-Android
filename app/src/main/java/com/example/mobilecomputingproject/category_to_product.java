package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class category_to_product extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListDesc = new ArrayList<>();
    ArrayList<String> arrayListPrice = new ArrayList<>();
    ArrayList<String> arrayListSrc = new ArrayList<>();
    ArrayList<String> arrayListAvail = new ArrayList<>();
    ArrayList<String> arrayListCateg = new ArrayList<>();
    DatabaseReference databaseReference;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_to_product);
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        listView = (ListView) findViewById(R.id.list_prods);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String  name = bundle.getString("CategoryName");
            String  id = bundle.getString("CategoryId");
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String value1 = snapshot.getValue(Products.class).getProduct_Name(),
                            value2 = snapshot.getValue(Products.class).getProduct_Description(),
                            value3 = snapshot.getValue(Products.class).getProduct_Price(),
                            value4 = snapshot.getValue(Products.class).getProduct_SrcImg(),
                            value5 = snapshot.getValue(Products.class).getProduct_Available(),
                            value6 = snapshot.getValue(Products.class).getProduct_Category();
                    if(snapshot.getValue(Products.class).getProduct_Category().equals(name)){
                        System.out.println("Ay Haaaaaaaaaaaaaaaabd");
                        arrayListDesc.add(value2);
                        arrayListPrice.add(value3);
                        arrayListSrc.add(value4);
                        arrayList.add(value1);
                        arrayListAvail.add(value5);
                        arrayListCateg.add(value6);
                    }
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
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intentToDetails = new Intent(getApplicationContext(),product_details.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ProductName",arrayList.get(i));
                    bundle.putString("ProductDesc",arrayListDesc.get(i));
                    bundle.putString("ProductPrice",arrayListPrice.get(i));
                    bundle.putString("ProductSrc",arrayListSrc.get(i));
                    bundle.putString("ProductAvail",arrayListAvail.get(i));
                    bundle.putString("ProductCateg",arrayListCateg.get(i));
                    intentToDetails.putExtras(bundle);
                    startActivity(intentToDetails);
                }
            });
        }


    }
}