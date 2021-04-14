package com.birdsolution.model;

public class SuggestionFoodModel {
    private String image, name, dis;

    public SuggestionFoodModel(String image, String name, String dis) {
        this.image = image;
        this.name = name;
        this.dis = dis;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }
}
