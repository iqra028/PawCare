package com.example.project1.BLL;

import javafx.scene.image.Image;

import java.time.LocalDate;

public class Alert {

    private String type;
    private String message;
    private String breed;
    private Image image;
    private double[] location;
    private LocalDate dateCreated;
    private String userid;
    private String rescuecenterid;
    private String alertType;
    private Boolean completed;
    private String alertId;
    public Alert(String animalType, String breed, String InjuryDesc,  Image image, double[] userLocation,String userid,String rescuecenterid) {
        this.type = animalType;
        this.breed = breed;
        this.message = InjuryDesc;
        this.image =  image;
        this.location = userLocation;
        this.dateCreated = LocalDate.now();
        this.userid = userid;
        completed=false;
        this.rescuecenterid=rescuecenterid;
        this.alertType="User";
    }
    public void setAlertId(String id)
    {
        this.alertId=id;
    }
    public String getaAlertID()
    {
        return alertId;
    }

    public Alert()
    {

    }
    public void setAlertType(String t)
    {
        this.alertType=t;
    }
    public void setCompleted(Boolean b)
    {
        completed=b;
    }


    public String getBreed() {
        return breed ;
    }
    public String getUserid(){
        return userid;
    }
    public String getRescuecenterid(){
        return rescuecenterid;
    }

    public String getType() {
        return type;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public void setRescuecenterid(String rescuecenterid) {
        this.rescuecenterid = rescuecenterid;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public void setLocation(double[] location) {
        this.location = location;
    }
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Image getImage() {
        return image;
    }

    public double[] getLocation() {
        return location;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }
}

