package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class admin_page extends AppCompatActivity {
     String productName ,productDesc , productSrc , productPrice,productAvail,productCateg,saveCurrentDate , saveCurrentTime,productRandomKey;
     EditText product_Name,product_Desc , product_Price , product_Image_Src ,product_Categ , product_Avail;
     Button add_product_btn , update_product_btn , delete_product_btn;
     Spinner spinner_categ ;
//     FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference,dbcateg;
     ArrayList<String> spinnerArr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products_admin);
         product_Name = (EditText) findViewById(R.id.product_name);
         product_Desc = (EditText) findViewById(R.id.product_desc);
         product_Price = (EditText) findViewById(R.id.product_price);
         product_Image_Src = (EditText) findViewById(R.id.product_src_image);
         spinner_categ = (Spinner) findViewById(R.id.spinner_categ);
         product_Avail = (EditText) findViewById(R.id.product_avail);
         add_product_btn = (Button) findViewById(R.id.product_btn);
        update_product_btn = (Button) findViewById(R.id.update_btn);
        delete_product_btn = (Button) findViewById(R.id.delete_btn);
        spinnerArr = new ArrayList<>();
        dbcateg = FirebaseDatabase.getInstance().getReference("Category");
        ArrayAdapter<String> spinnerAdapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArr);
        spinnerAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categ.setAdapter(spinnerAdapt);

        dbcateg.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String value = snapshot.getValue(Categories.class).getName();
                spinnerArr.add(value);
                spinnerAdapt.notifyDataSetChanged();

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



//         FirebaseDatabase database = FirebaseDatabase.getInstance();
//         DatabaseReference  ProductsRef = database.getReference("Products");
            databaseReference = FirebaseDatabase.getInstance().getReference("Products");
            delete_product_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent deleteInt = new Intent(admin_page.this,delete_page.class);
                    startActivity(deleteInt);
                }
            });
            update_product_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent updateInt = new Intent(admin_page.this,update_page.class);
                    startActivity(updateInt);
                }
            });

        add_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();


            }
        });

    }

    private void ValidateProductData() {
        productName = product_Name.getText().toString().trim();
        productDesc = product_Desc.getText().toString().trim();
        productPrice = product_Price.getText().toString().trim();
        productSrc = product_Image_Src.getText().toString().trim();
        productAvail = product_Avail.getText().toString().trim();
        productCateg = spinner_categ.getSelectedItem().toString();


        if(productName.isEmpty()){
            Toast.makeText(admin_page.this,"Enter Product Name",Toast.LENGTH_SHORT).show();
        } else if(productDesc.isEmpty()){
            Toast.makeText(admin_page.this,"Enter Product Description",Toast.LENGTH_SHORT).show();
        } else if(productPrice.isEmpty()){
            Toast.makeText(admin_page.this,"Enter Product Price",Toast.LENGTH_SHORT).show();
        } else if(productSrc.isEmpty()){
            Toast.makeText(admin_page.this,"Enter Product Image Src",Toast.LENGTH_SHORT).show();
        }else if(productAvail.isEmpty()){
            Toast.makeText(admin_page.this,"Enter Number of Available products",Toast.LENGTH_SHORT).show();
        }else if(productCateg.isEmpty()){
            Toast.makeText(admin_page.this,"Enter Product Category ",Toast.LENGTH_SHORT).show();
        }else{
            String id = databaseReference.push().getKey();
            Products products = new Products(id,productName,productDesc,productPrice,productSrc,productCateg,productAvail);
            databaseReference.child(id).child("Id").setValue(id.toString());
            databaseReference.child(id).child("Product_Name").setValue(productName.toString());
            databaseReference.child(id).child("Product_Description").setValue(productDesc.toString());
            databaseReference.child(id).child("Product_Price").setValue(productPrice.toString());
            databaseReference.child(id).child("Product_SrcImg").setValue(productSrc.toString());
            databaseReference.child(id).child("Product_Category").setValue(productCateg.toString());
            databaseReference.child(id).child("Product_Available").setValue(productAvail.toString());
            Toast.makeText(admin_page.this,"Succefully Product Added",Toast.LENGTH_LONG).show();
            clearTXT();

        }
    }

    private void clearTXT() {
        product_Name.setText("");
        product_Desc.setText("");
        product_Price.setText("");
        product_Image_Src.setText("");
        product_Avail.setText("");
    }

//    private void StoreProductInfo() {
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
//        saveCurrentDate = currentDate.format(calendar.getTime());
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        saveCurrentTime = currentTime.format(calendar.getTime());
//        productRandomKey = productName+"_"+saveCurrentDate + saveCurrentTime ;
//
//        saveProductInfoToDB();
//    }

//    private void saveProductInfoToDB() {
//        HashMap<String,Object> productMap = new HashMap<>();
//        productMap.put("prodID",productRandomKey);
//        productMap.put("prodNAME",productName);
//        productMap.put("prodDESC",productDesc);
//        productMap.put("prodPRICE",productPrice);
//        productMap.put("prodIMG",productSrc);
//
//        ProductsRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(admin_page.this,"Product is added succefully",Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(admin_page.this,"Product is failed to upload ",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
}