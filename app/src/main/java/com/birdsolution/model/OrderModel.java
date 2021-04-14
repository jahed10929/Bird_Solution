package com.birdsolution.model;

public class OrderModel {
    private String Name, price;

    public OrderModel(String name, String price) {
        Name = name;
        this.price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
