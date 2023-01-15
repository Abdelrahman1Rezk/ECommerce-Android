package com.example.mobilecomputingproject;

import android.app.Application;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ItemModule extends Application {
    public ArrayList<String> garrList = new ArrayList<>();
    public ArrayAdapter<String> garrAdpater;
    public String Id;
    public String Product_Name;
    public String gvalue_Price;
    public String gvalue_Desc;
    public String gvalue_Src;


    public String getGvalue_id() {
        return Id;
    }

    public void setGvalue_id(String gvalue_id) {
        this.Id = Id;
    }

    public String getGvalue_Name() {
        return Product_Name;
    }

    public void setGvalue_Name(String gvalue_Name) {
        this.Product_Name = Product_Name;
    }

    public String getGvalue_Price() {
        return gvalue_Price;
    }

    public void setGvalue_Price(String gvalue_Price) {
        this.gvalue_Price = gvalue_Price;
    }

    public String getGvalue_Desc() {
        return gvalue_Desc;
    }

    public void setGvalue_Desc(String gvalue_Desc) {
        this.gvalue_Desc = gvalue_Desc;
    }

    public String getGvalue_Src() {
        return gvalue_Src;
    }

    public void setGvalue_Src(String gvalue_Src) {
        this.gvalue_Src = gvalue_Src;
    }
}
