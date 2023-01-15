package com.example.mobilecomputingproject;

public class Transactions {
    String email;
    String product;
    String date;
    public Transactions(){}

    public Transactions(String email, String product, String date) {
        this.email = email;
        this.product = product;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
