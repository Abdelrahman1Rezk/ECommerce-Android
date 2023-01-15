package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class delete_page extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListDesc = new ArrayList<>();
    ArrayList<String> arrayListPrice = new ArrayList<>();
    ArrayList<String> arrayListSrc = new ArrayList<>();
    ArrayList<String> arrayListId = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_page);
        listView = (ListView) findViewById(R.id.list_delete);
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String value1 = snapshot.getValue(Products.class).getProduct_Name(),
                        value2 = snapshot.getValue(Products.class).getProduct_Description(),
                        value3 = snapshot.getValue(Products.class).getProduct_Price(),
                        value4 = snapshot.getValue(Products.class).getProduct_SrcImg(),
                        value5 = snapshot.getValue(Products.class).getId();
                arrayListDesc.add(value2);
                arrayListPrice.add(value3);
                arrayListSrc.add(value4);
                arrayList.add(value1);
                arrayListId.add(value5);
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
        //--------
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("mohsen  "+ arrayList.get(i));
                System.out.println("lolo  "+ arrayListId.get(i));
                DatabaseReference dId = FirebaseDatabase.getInstance().getReference("Products").child(arrayListId.get(i));
                dId.removeValue();
                Toast.makeText(delete_page.this,"Deleted",Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
                return false;
            }
        });
    }
}