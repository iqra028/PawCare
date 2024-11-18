package com.example.project1.BLL;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDate;

public class Alert {

    private String alertId;
    private String type;
    private String message;
    private String breed;
    private String image;
    private double[] location;
    private LocalDate dateCreated;
    public Alert(String animalType, String breed, String InjuryDesc,  String imagePath, double[] userLocation) {
        this.alertId = generateAlertId();
        this.type = animalType;
        this.breed = breed;
        this.message = InjuryDesc;
        this.image = imagePath;
        this.location = userLocation;
        this.dateCreated = LocalDate.now();
    }
    private String generateAlertId() {
        return "ALERT-" + System.currentTimeMillis();
    }

    public Alert sendAlert() {
        System.out.println("Alert sent: " + this.message);
        return this;
    }
    public String getAlertId() {
        return alertId;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getImage() {
        return image;
    }

    public double[] getLocation() {
        return location;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }
}

