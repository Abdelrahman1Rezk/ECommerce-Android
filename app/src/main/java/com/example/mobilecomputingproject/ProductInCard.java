package com.example.mobilecomputingproject;

public class ProductInCard {
    String email;
    String name;
    String price;
    String quantity;

    public ProductInCard(){}
    public ProductInCard(String email, String name, String price, String quantity) {
        this.email = email;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
