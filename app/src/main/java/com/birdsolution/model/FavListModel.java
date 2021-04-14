package com.birdsolution.model;

public class FavListModel {
    private String name;
    private String shortDis;
    private String image;
    private String price;
    private String dis;
    private String status;

    public FavListModel(String name, String shortDis, String image, String price, String dis, String status) {
        this.name = name;
        this.shortDis = shortDis;
        this.image = image;
        this.price = price;
        this.dis = dis;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDis() {
        return shortDis;
    }

    public void setShortDis(String shortDis) {
        this.shortDis = shortDis;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
