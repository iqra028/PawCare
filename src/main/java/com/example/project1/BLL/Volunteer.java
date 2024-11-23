package com.example.project1.BLL;
import javafx.scene.image.Image;

import java.util.UUID;

public class Volunteer {

    private String volunteerId;
    private String userId;
    private String cnic;
    private String vehicleType;
    private Image vehicleImage;
    private String vehicleModel;
    private Boolean available;


    // Default constructor
    public Volunteer() {

    }

    public Boolean getAvailable() {
        return available;
    }
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    // Constructor with parameters
    public Volunteer( String userId, String cnic, String vehicleType, Image vehicleImage, String vehicleModel, Boolean availability) {
        this.userId = userId;
        this.cnic = cnic;
        this.vehicleType = vehicleType;
        this.vehicleImage = vehicleImage;
        this.vehicleModel = vehicleModel;
        this.available = availability;
    }

    // Getters and setters
    public String getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Image getVehicleImage() {
        return vehicleImage;
    }

    public void setVehicleImage(Image vehicleImage) {
        this.vehicleImage = vehicleImage;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    // Override toString() for better debugging and logging
    @Override
    public String toString() {
        return "Volunteer{" +
                "volunteerId=" + volunteerId +
                ", userId=" + userId +
                ", cnic='" + cnic + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                '}';
    }
}

