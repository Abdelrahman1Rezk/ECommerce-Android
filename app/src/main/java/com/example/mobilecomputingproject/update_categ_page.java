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

public class update_categ_page extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListId = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_categ_page);
        listView = (ListView) findViewById(R.id.list_update_category);
        databaseReference = FirebaseDatabase.getInstance().getReference("Category");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String value1 = snapshot.getValue(Categories.class).getName(),
                        value2 = snapshot.getValue(Categories.class).getId();
                arrayList.add(value1);
                arrayListId.add(value2);
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
                showUpdateDialog(arrayList.get(i),arrayListId.get(i));
                return false;
            }
        });

    }
    private void showUpdateDialog(String name,String id){
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.update_categ_dialog,null);
        mDialog.setView(mDialogView);
        EditText updateCateg = mDialogView.findViewById(R.id.newcateogryname);
        Button btnUpdate = mDialogView.findViewById(R.id.newcateogryupdate);
        mDialog.setTitle("Update " + " record");

        mDialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCateg = updateCateg.getText().toString();
                updateData(newCateg,id);
                finish();
                startActivity(getIntent());
                Toast.makeText(update_categ_page.this,"Record is updated",Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void updateData(String categ,String id){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Category").child(id);
        Categories categories = new Categories(categ,id);
        dbref.setValue(categories);
    }
}