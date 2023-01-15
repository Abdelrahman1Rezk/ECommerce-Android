package com.example.mobilecomputingproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

public class search_page extends AppCompatActivity {
    EditText input_search ;
    Button search_btn,voiceBtn,cameraBtn;
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListDesc = new ArrayList<>();
    ArrayList<String> arrayListPrice = new ArrayList<>();
    ArrayList<String> arrayListSrc = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    //----.
//    Cursor matchedEmployee;
    int voiceCode = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == voiceCode){
            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            input_search.setText(text.get(0));
        }
    }

    //-----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        input_search = (EditText) findViewById(R.id.editTextTextProductName);
        search_btn = (Button) findViewById(R.id.button_search);
        voiceBtn = (Button) findViewById(R.id.buttonmic);
        cameraBtn = (Button) findViewById(R.id.buttoncamera);
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        listView = (ListView) findViewById(R.id.list_search);
        ArrayList<String> search_arr = new ArrayList<>();
        ArrayList<String> search_desc = new ArrayList<>();
        ArrayList<String> search_src = new ArrayList<>();
        ArrayList<String> search_price = new ArrayList<>();
        ArrayList<String> search_arr_view = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,search_arr_view);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value1 = snapshot.getValue(Products.class).getProduct_Name(),
                        value2 = snapshot.getValue(Products.class).getProduct_Description(),
                        value3 = snapshot.getValue(Products.class).getProduct_Price(),
                        value4 = snapshot.getValue(Products.class).getProduct_SrcImg();
                arrayListDesc.add(value2);
                search_desc.add(value2);
                arrayListPrice.add(value3);
                search_price.add(value3);
                arrayListSrc.add(value4);
                search_src.add(value4);
                arrayList.add(value1);
                search_arr.add(value1);
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
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = input_search.getText().toString().toLowerCase().trim();
                arrayList.clear();
                arrayListDesc.clear();
                arrayListPrice.clear();
                arrayListSrc.clear();
                search_arr_view.clear();
                //-------
                for(int i = 0; i< search_arr.size();i++){
                    if(search_arr.get(i).toLowerCase().contains(item)){
                        arrayList.add(search_arr.get(i));
                        arrayListDesc.add(search_desc.get(i));
                        arrayListPrice.add(search_price.get(i));
                        arrayListSrc.add(search_src.get(i));
                        search_arr_view.add(search_arr.get(i));
                    }
                }
                //-------
            }
        });
        //-------
        voiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intoVoice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intoVoice,voiceCode);
            }
        });
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
        //-------
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intentToDetails = new Intent(getApplicationContext(),product_details.class);
                Bundle bundle = new Bundle();
                bundle.putString("ProductName",arrayList.get(i));
                bundle.putString("ProductDesc",arrayListDesc.get(i));
                bundle.putString("ProductPrice",arrayListPrice.get(i));
                bundle.putString("ProductSrc",arrayListSrc.get(i));
                intentToDetails.putExtras(bundle);
                startActivity(intentToDetails);
                System.out.println(arrayList.get(i));
                System.out.println(arrayListDesc.get(i));
            }
        });

    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLuncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLuncher = registerForActivityResult(new ScanContract(),result -> {
        if(result.getContents()!= null){
            AlertDialog.Builder builder = new AlertDialog.Builder(search_page.this);
            builder.setTitle("Scan Result");
            builder.setMessage(result.getContents());
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    input_search.setText(result.getContents());
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });
}