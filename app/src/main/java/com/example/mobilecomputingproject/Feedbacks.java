package com.example.mobilecomputingproject;

public class Feedbacks {
    String name;
    String opinion;
    String order;
    String rate;
    public Feedbacks(){}
    public Feedbacks(String name, String opinion, String order, String rate) {
        this.name = name;
        this.opinion = opinion;
        this.order = order;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
