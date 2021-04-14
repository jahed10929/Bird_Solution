package com.birdsolution.model;

public class CartListModel  {
    private String name;
    private String shortDis;
    private String image;
    private String price;
    private String dis;
    private String status;
    private String fav;
    String total;
    String total_product;


    public CartListModel(String name, String shortDis, String image, String price,
                         String dis, String status, String fav, String total, String total_product) {
        this.name = name;
        this.shortDis = shortDis;
        this.image = image;
        this.price = price;
        this.dis = dis;
        this.status = status;
        this.fav = fav;
        this.total = total;
        this.total_product = total_product;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal_product() {
        return total_product;
    }

    public void setTotal_product(String total_product) {
        this.total_product = total_product;
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

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
