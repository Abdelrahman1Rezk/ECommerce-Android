package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class payment_page extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ProductInCard> productInCardArrayList;
    ArrayList<String> productInCardName;
    ArrayList<String> productsCardNames;
    ArrayList<String> productsCardquantity;
    ArrayList<String> productsSaledNames;
    ArrayList<String> productsSaledQuantity;

    CardAdapter cardAdapter;
    FirebaseFirestore db , firedb;

    //----.

    DatabaseReference databaseReference , transRef;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListDesc = new ArrayList<>();
    ArrayList<String> arrayListPrice = new ArrayList<>();
    ArrayList<String> arrayListSrc = new ArrayList<>();
    ArrayList<String> arrayListId = new ArrayList<>();
    ArrayList<String> arrayListCateg = new ArrayList<>();
    ArrayList<String> arrayListAvail = new ArrayList<>();
    ///----
    FirebaseAuth firebaseAuth;
    String userEmail;
    int totalPrice = 0;
    ProgressDialog progressDialog ;
    TextView totalpriceview;
    Button confirmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        recyclerView = findViewById(R.id.recycler_view_items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        firedb = FirebaseFirestore.getInstance();
        totalpriceview = (TextView) findViewById(R.id.textView8);
        confirmBtn = (Button) findViewById(R.id.button4);
        productsCardNames = new ArrayList<>();
        productsCardquantity = new ArrayList<>();
        productsSaledNames = new ArrayList<>();
        productsSaledQuantity = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        transRef = FirebaseDatabase.getInstance().getReference("Transaction");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        Date date = new Date();
        String storeDate = formatter.format(date).toString();
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
        firebaseAuth = FirebaseAuth.getInstance();
        userEmail = firebaseAuth.getCurrentUser().getEmail();
        productInCardArrayList = new ArrayList<ProductInCard>();
//        ArrayList<ProductInCard> fsfs = new ArrayList<ProductInCard>();
        productInCardName = new ArrayList<>();
        cardAdapter = new CardAdapter(payment_page.this,productInCardArrayList);
        recyclerView.setAdapter(cardAdapter);
        
        EventChangeListener(userEmail);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 ,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                System.out.println("sssssssssssssss"+productInCardName.get(pos));
                String namee = productInCardName.get(pos);

                db.collection("Cart"+userEmail).document(namee).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(payment_page.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
                startActivity(getIntent());
                //                getSnapShots().getSnapshot().getRefrence().delete();
            }
        }).attachToRecyclerView(recyclerView);
        
        firedb.collection("Cart"+userEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges())
                {
                    String   email =  documentChange.getDocument().getData().get("email").toString();
                    String  quantity   =  documentChange.getDocument().getData().get("quantity").toString();
                    String price = documentChange.getDocument().getData().get("price").toString();
                    String prodname = documentChange.getDocument().getData().get("name").toString();
                    //----/.
                    productsCardNames.add(prodname.toString());
                    productsCardquantity.add(quantity.toString());
                    ///------
                    totalPrice += (Integer.parseInt(quantity)*Integer.parseInt(price));
                }
                totalpriceview.setText(Integer.toString(totalPrice));
            }
        });
        db.collection("Saled").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges())
                {
                    String  quantity   =  documentChange.getDocument().getData().get("quantity").toString();
                    String prodname = documentChange.getDocument().getData().get("name").toString();
                    productsSaledNames.add(prodname.toString());
                    productsSaledQuantity.add(quantity.toString());
                    System.out.println("Null:::::" + prodname + quantity);
                }

            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(totalpriceview.getText().toString()) == 0){
                    Toast.makeText(payment_page.this,"Please Add Products",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(payment_page.this,"Order is confirmed",Toast.LENGTH_LONG).show();

//                    for (String e1 : arrayList){
////                        if(e1.equals(productsCardNames.get(0))){
////                            System.out.println(true);
////                        }
////                        for (int i = 0;i < productsCardNames.size();i++){
////                            if(productsCardNames.get(i).equals(e1)){
////                                System.out.println(true + "from inner:   " + productsCardNames.get(i) + productsCardquantity.get(i));
////                                //---
////
////                                //---
////                            }
////                        }
////                        for (int i = 0; i< productsCardNames.size(); i++){
//////                                    if(productsCardNames.get(i).equals(valName.toString())){
//////                                        System.out.println("from Inner DBREF" + productsCardNames.get(i) + " = = = "+ valName);
//////                                    }
////                            System.out.println("from Inner DBREF" + productsCardNames.get(i) + " = = = "+ valName);
////
////                        }
//
////                        databaseReference.addChildEventListener(new ChildEventListener() {
////                            @Override
////                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
////
////                                String value1 = snapshot.getValue(Products.class).getProduct_Name(),
////                                        value2 = snapshot.getValue(Products.class).getProduct_Description(),
////                                        value3 = snapshot.getValue(Products.class).getProduct_Price(),
////                                        value4 = snapshot.getValue(Products.class).getProduct_SrcImg(),
////                                        value5 = snapshot.getValue(Products.class).getId(),
////                                        value6 = snapshot.getValue(Products.class).getProduct_Category(),
////                                        value7 = snapshot.getValue(Products.class).getProduct_Available();
////                                for (int i = 0; i< productsCardNames.size(); i++){
////                                    if(productsCardNames.get(i).equals(value1)){
////                                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Products").child(value5);
////                                        int newNAvail = Integer.parseInt(value7) - Integer.parseInt(productsCardquantity.get(i));
////                                        String newAvail = Integer.toString(newNAvail);
////                                        Products prod = new Products(value5,value1,value2,value3,value4,value6,newAvail);
////                                        dbref.setValue(prod);
////                                    }
////
////                        }
////                            }
////
////                            @Override
////                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
////
////                            }
////
////                            @Override
////                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
////
////                            }
////
////                            @Override
////                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
////
////                            }
////
////                            @Override
////                            public void onCancelled(@NonNull DatabaseError error) {
////
////                            }
////                        });
//
////                        System.out.println("fsfsfs from for loop"+ e1 +" \n");
//                    }
                    //----



                    //----New Trans
                    Date dateT = new Date();
                    String storeDateT = formatter.format(date).toString();
                    for (String eee1 : productsCardNames){
                    String idTrans = transRef.push().getKey();
                    Transactions transactions = new Transactions(userEmail,eee1,storeDateT);
                    transRef.child(idTrans).child("Id").setValue(idTrans.toString());
                    transRef.child(idTrans).child("email").setValue(transactions.getEmail());
                    transRef.child(idTrans).child("product").setValue(transactions.getProduct());
                    transRef.child(idTrans).child("date").setValue(transactions.getDate());
                    }


                    //----New Trans


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
                            for (int i = 0; i< productsCardNames.size(); i++){
                                if(productsCardNames.get(i).equals(value1)){
                                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Products").child(value5);
                                    int newNAvail = Integer.parseInt(value7) - Integer.parseInt(productsCardquantity.get(i));
                                    String newAvail = Integer.toString(newNAvail);
                                    Products prod = new Products(value5,value1,value2,value3,value4,value6,newAvail);
                                    dbref.setValue(prod);
                                }

                            }
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

                    for(int i = 0; i< productsCardNames.size();i++){
                        db.collection("Cart"+userEmail).document(productsCardNames.get(i).toString()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(payment_page.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                        boolean condInn = false;
                        for(int k = 0;k<productsSaledNames.size();k++){
                            if(productsSaledNames.get(k).equals(productsCardNames.get(i))){
                                int newValQ = Integer.parseInt(productsCardquantity.get(i)) + Integer.parseInt(productsSaledQuantity.get(k)) ;
                                HashMap<String,Object> hashMap = new HashMap<>();
                                hashMap.put("name",productsCardNames.get(i).toString());
                                hashMap.put("quantity",Integer.toString(newValQ));

                                db.collection("Saled").document(productsCardNames.get(i).toString()).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

//                                        Toast.makeText(payment_page.this,"Product Saled",Toast.LENGTH_LONG).show();
                                    }
                                });
                                condInn = true;
                                break;
                            }
                        }
                        if(condInn == true){
                            continue;
                        }else{
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("name",productsCardNames.get(i).toString());
                            hashMap.put("quantity",productsCardquantity.get(i));

                            db.collection("Saled").document(productsCardNames.get(i).toString()).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

//                                    Toast.makeText(payment_page.this,"Product Saled",Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }

                    Intent toLoc = new Intent(payment_page.this,new_googlemap_linear.class);
                    startActivity(toLoc);
                }
            }
        });
    }

    private void EventChangeListener(String userEmail) {
        db.collection("Cart"+userEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Log.e("FireBaseFireStore Error",error.getMessage());
                    return ;
                }
                int iname = 0;
                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        productInCardArrayList.add(dc.getDocument().toObject(ProductInCard.class));
//                        ArrayList<String> fs = new ArrayList<>();
//                        fs.add(dc.getDocument().toString());
                        productInCardName.add(productInCardArrayList.get(iname).getName());

                    }
                    cardAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    iname++;
                }
                if(productInCardArrayList.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(payment_page.this,"Data Not Found",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}