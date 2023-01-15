package com.example.mobilecomputingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class category_admin_page extends AppCompatActivity {
    String categoryName ,categoryId ,saveCurrentDate , saveCurrentTime,productRandomKey;
    EditText category_Name,category_Id;
    Button add_categ_btn , update_categ_btn , delete_categ_btn;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_admin_page);
        category_Name = (EditText) findViewById(R.id.category_name);
        category_Id = (EditText) findViewById(R.id.category_id);
        add_categ_btn = (Button) findViewById(R.id.product_btn);
        update_categ_btn = (Button) findViewById(R.id.update_btn);
        delete_categ_btn = (Button) findViewById(R.id.delete_btn);
        databaseReference = FirebaseDatabase.getInstance().getReference("Category");
        delete_categ_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deleteInt = new Intent(category_admin_page.this,delete_category.class);
                startActivity(deleteInt);
            }
        });
        update_categ_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateInt = new Intent(category_admin_page.this,update_categ_page.class);
                startActivity(updateInt);
            }
        });

        add_categ_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();


            }
        });

    }

    private void ValidateProductData() {
        categoryName = category_Name.getText().toString().trim();
        categoryId = category_Id.getText().toString().trim();
        if(categoryName.isEmpty()){
            Toast.makeText(category_admin_page.this,"Enter Category Name",Toast.LENGTH_SHORT).show();
        } else if(categoryId.isEmpty()){
            Toast.makeText(category_admin_page.this,"Enter Category ID",Toast.LENGTH_SHORT).show();
        }else{
//            String id = databaseReference.push().getKey();
            Categories categ = new Categories(categoryName,categoryId);
            databaseReference.child(categ.getId()).child("id").setValue(categoryId.toString());
            databaseReference.child(categ.getId()).child("name").setValue(categoryName.toString());
            Toast.makeText(category_admin_page.this,"Succefully Category Added",Toast.LENGTH_LONG).show();
            clearTXT();

        }
    }
    private void clearTXT() {
        category_Name.setText("");
        category_Id.setText("");
    }
}