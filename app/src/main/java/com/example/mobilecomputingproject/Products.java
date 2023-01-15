package com.example.mobilecomputingproject;

import java.util.ArrayList;

public class Products {
    String Id;
    String Product_Name;
    String Product_Description;
    String Product_Price;
    String Product_SrcImg;
    String Product_Category;
    String Product_Available;
    public Products(){}

    public Products(String id, String product_Name, String product_Description, String product_Price, String product_SrcImg, String product_Category, String product_Available) {
        Id = id;
        Product_Name = product_Name;
        Product_Description = product_Description;
        Product_Price = product_Price;
        Product_SrcImg = product_SrcImg;
        Product_Category = product_Category;
        Product_Available = product_Available;
    }

    public String getProduct_Category() {
        return Product_Category;
    }

    public void setProduct_Category(String product_Category) {
        Product_Category = product_Category;
    }

    public String getProduct_Available() {
        return Product_Available;
    }

    public void setProduct_Available(String product_Available) {
        Product_Available = product_Available;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_Description() {
        return Product_Description;
    }

    public void setProduct_Description(String product_Description) {
        Product_Description = product_Description;
    }

    public String getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(String product_Price) {
        Product_Price = product_Price;
    }

    public String getProduct_SrcImg() {
        return Product_SrcImg;
    }

    public void setProduct_SrcImg(String product_SrcImg) {
        Product_SrcImg = product_SrcImg;
    }

    public ArrayList<String> getAllData(){
        ArrayList<String> allData = new ArrayList<>();
        allData.add(this.getProduct_Name());
        allData.add(this.getProduct_Description());
        allData.add(this.getProduct_Price());
        allData.add(this.getProduct_SrcImg());
        return allData;
    }
    public String toString()
    {
        return  this.getProduct_Name();

    }
}
