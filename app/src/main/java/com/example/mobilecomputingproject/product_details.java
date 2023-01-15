package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilecomputingproject.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class product_details extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userEmail;
    TextView show_name;
    TextView show_desc;
    TextView show_counter;
    TextView show_price;
    TextView show_quantity;
    Button increaseBtn , decreaseBtn,addToCardBtn ;
    ImageView imageView ;
    ActivityMainBinding binding;
    Handler mainHandler = new Handler();
    Picasso picasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        show_name = (TextView) findViewById(R.id.prod_name);
        show_desc = (TextView) findViewById(R.id.textView7desc);
        show_price = (TextView) findViewById(R.id.product_details_price);
        show_quantity = (TextView) findViewById(R.id.product_details_quantity);
        show_counter = (TextView) findViewById(R.id.counter);
        increaseBtn = (Button) findViewById(R.id.increase_btn);
        decreaseBtn = (Button) findViewById(R.id.decrease_btn);
        addToCardBtn = (Button) findViewById(R.id.id_card);
        imageView = (ImageView) findViewById(R.id.imageView2);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userEmail = firebaseAuth.getCurrentUser().getEmail();
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int valueofPrice = Integer.parseInt(show_counter.getText().toString());
                int valueofQuantiry  = Integer.parseInt(show_quantity.getText().toString());
                    if(valueofQuantiry <= 0){
                        show_counter.setText(Integer.toString(valueofPrice));
                        show_quantity.setText("0");
                    }else{
                        valueofPrice = valueofPrice + 1;
                        valueofQuantiry = valueofQuantiry - 1;


                        show_quantity.setText(Integer.toString(valueofQuantiry));
                        show_counter.setText(Integer.toString(valueofPrice));
                    }


            }
        });
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = Integer.parseInt(show_counter.getText().toString());
                int valueofQuantiry  = Integer.parseInt(show_quantity.getText().toString());
                //----.
                //-----
                if(value <= 0){
                    show_counter.setText("0");
                    show_quantity.setText(Integer.toString(valueofQuantiry));
                }else{
                    value = value - 1;
                    valueofQuantiry = valueofQuantiry + 1;
                    show_quantity.setText(Integer.toString(valueofQuantiry));
                    show_counter.setText(Integer.toString(value));
                }

            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String name = bundle.getString("ProductName");
            String desc = bundle.getString("ProductDesc");
            String price = bundle.getString("ProductPrice");
            String srcImg = bundle.getString("ProductSrc");
            String avail = bundle.getString("ProductAvail");
            String categ = bundle.getString("ProductCateg");
            show_name.setText(name);
            show_desc.setText(desc);
            show_price.setText(price);
            show_quantity.setText(avail);
            Picasso.get().load(srcImg).into(imageView);
            addToCardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantityValue = Integer.parseInt(show_counter.getText().toString());
                    if(quantityValue == 0){
                        Toast.makeText(product_details.this,"Nothing Added to Card || Add Quantity",Toast.LENGTH_LONG).show();
                    }else{
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("email",userEmail);
                        hashMap.put("name",name);
                        hashMap.put("price",price);
                        hashMap.put("quantity",Integer.toString(quantityValue));

                        firestore.collection("Cart"+userEmail).document(name).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(product_details.this,"Product is added Successfully",Toast.LENGTH_LONG).show();
                            }
                        });
//                        Toast.makeText(product_details.this,"Product is Added",Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    }

//    private void AddToCard() {
//        HashMap<String,Object> hashMap = new HashMap<>();
//        hashMap.put("email",email);
//        hashMap.put("name",name);
//        hashMap.put("price",price);
//        hashMap.put("quantity",quantity);
//
//        firestore.collection("Cart"+userEmail).document(name).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//            }
//        });
//    }


}