package com.birdsolution.model;

public class SuggestionDieaseModel {
    private String name, Prevention, Symptoms, Treatment, image;

    public SuggestionDieaseModel(String name, String prevention, String symptoms, String treatment, String image) {
        this.name = name;
        Prevention = prevention;
        Symptoms = symptoms;
        Treatment = treatment;
        this.image = image;
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

    public String getPrevention() {
        return Prevention;
    }

    public void setPrevention(String prevention) {
        Prevention = prevention;
    }

    public String getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(String symptoms) {
        Symptoms = symptoms;
    }

    public String getTreatment() {
        return Treatment;
    }

    public void setTreatment(String treatment) {
        Treatment = treatment;
    }
}
