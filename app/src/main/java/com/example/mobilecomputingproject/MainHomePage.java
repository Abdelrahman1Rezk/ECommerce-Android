package com.example.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class MainHomePage extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListId = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ItemModule module;
    Button searchBtn , cardsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_page);
        searchBtn = (Button) findViewById(R.id.button2);
        cardsBtn = (Button) findViewById(R.id.buttonca);
        databaseReference = FirebaseDatabase.getInstance().getReference("Category");
        listView = (ListView) findViewById(R.id.list_products);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        Button ProfileBtn = (Button) findViewById(R.id.to_profile);
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProf = new Intent(getApplicationContext(),Profile.class);
                startActivity(toProf);
//                SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
//                boolean remembero = chrem.getBoolean("remember", false);
//                SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
//                SharedPreferences.Editor editor = chrem.edit();
//                editor.putBoolean("remember", false);
//                editor.apply();
//                Intent outTologin = new Intent(getApplicationContext(),LoginPage.class);
//                startActivity(outTologin);
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value1 = snapshot.getValue(Categories.class).getName(),
                        value2 = snapshot.getValue(Categories.class).getId();
                        arrayListId.add(value2);
                        arrayList.add(value1);
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
        //------
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intentToDetails = new Intent(getApplicationContext(),category_to_product.class);
                Bundle bundle = new Bundle();
                bundle.putString("CategoryName",arrayList.get(i));
                bundle.putString("CategoryId",arrayListId.get(i));
                intentToDetails.putExtras(bundle);
                startActivity(intentToDetails);
                System.out.println("fsfsfsfs"+arrayList.get(i));
                System.out.println(arrayListId.get(i));
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(MainHomePage.this,search_page.class);
                startActivity(searchIntent);
            }
        });
        cardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cardooI = new Intent(getApplicationContext(),payment_page.class);
                startActivity(cardooI);
            }
        });
        //------
//Logout Button
//     FirebaseAuth mAuth;
//        mAuth = FirebaseAuth.getInstance();
//        Button logoutB = (Button) findViewById(R.id.button);
//        logoutB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(MainHomePage.this,LoginPage.class));
////                FirebaseUser currentuser = mAuth.getCurrentUser();
////                if(currentuser == null){
////                    startActivity(); //and go to Login page
////                }
//            }
//        });
//Logout Button


    }

}