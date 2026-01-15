package org.databases.entity;

import java.util.ArrayList;

public class Product {
    private int product_id;
    private String name;
    private double price;
    private String descriptionText;
    private ArrayList<Review> review;


    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public ArrayList<Review> getReview() {
        return review;
    }

    public void setReview(ArrayList<Review> review) {
        this.review = review;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
