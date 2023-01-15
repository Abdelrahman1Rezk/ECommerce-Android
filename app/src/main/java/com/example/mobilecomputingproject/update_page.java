package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class update_page extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListDesc = new ArrayList<>();
    ArrayList<String> arrayListPrice = new ArrayList<>();
    ArrayList<String> arrayListSrc = new ArrayList<>();
    ArrayList<String> arrayListId = new ArrayList<>();
    ArrayList<String> arrayListCateg = new ArrayList<>();
    ArrayList<String> arrayListAvail = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_page);
        listView = (ListView) findViewById(R.id.list_update);
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
                        value5 = snapshot.getValue(Products.class).getId(),
                        value6 = snapshot.getValue(Products.class).getProduct_Category(),
                        value7 = snapshot.getValue(Products.class).getProduct_Available();
                arrayListDesc.add(value2);
                arrayListPrice.add(value3);
                arrayListSrc.add(value4);
                arrayList.add(value1);
                arrayListId.add(value5);
                arrayListCateg.add(value6);
                arrayListAvail.add(value7);
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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Products prod = arrayList.get(i);
                showUpdateDialog(arrayListId.get(i),arrayList.get(i));
                return false;
            }
        });
    }
    private void showUpdateDialog(String id , String name){
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.update_dialog,null);
        mDialog.setView(mDialogView);
        EditText updateName = mDialogView.findViewById(R.id.nameofproduct);
        EditText updateDesc = mDialogView.findViewById(R.id.descofproduct);
        EditText updatePrice = mDialogView.findViewById(R.id.priceofproduct);
        EditText updateSrcImg = mDialogView.findViewById(R.id.srcofproduct);
        EditText updateCateg = mDialogView.findViewById(R.id.catgofproduct);
        EditText updateAvail = mDialogView.findViewById(R.id.avaofproduct);
        Button btnUpdate = mDialogView.findViewById(R.id.updateofproduct);
        mDialog.setTitle("Updateing " + name + " record");

        mDialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = updateName.getText().toString();
                String newDesc = updateDesc.getText().toString();
                String newPrice = updatePrice.getText().toString();
                String newImg = updateSrcImg.getText().toString();
                String newCateg = updateCateg.getText().toString();
                String newAvail = updateAvail.getText().toString();
                updateData(id,newName,newDesc,newPrice,newImg,newCateg,newAvail);
                finish();
                startActivity(getIntent());
                Toast.makeText(update_page.this,"Record is updated",Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void updateData(String id,String name,String desc,String price, String src , String categ , String avail){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Products").child(id);
        Products prod = new Products(id,name,desc,price,src,categ,avail);
        dbref.setValue(prod);
    }
}