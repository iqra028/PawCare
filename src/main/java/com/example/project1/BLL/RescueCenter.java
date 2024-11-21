package com.example.project1.BLL;

import java.util.*;

public class RescueCenter {

    private String rescueCenterID;
    private String userName;
    private String CenterName;
    private String password;
    private String email;
    private String location;    // change this to a Location type later
    private String phoneNumber;

    private ArrayList<Profile> animalProfiles;
    private ArrayList<Profile> adoptionProfiles;

    public RescueCenter(String rescueCenterID,String userName, String CenterName, String email, String password, String location, String phoneNumber) {
        this.rescueCenterID=rescueCenterID;
        this.userName = userName;
        this.CenterName = CenterName;
        this.email = email;
        this.password = password;
        this.location = location;
        this.phoneNumber = phoneNumber;

        this.animalProfiles = new ArrayList<>();
        this.adoptionProfiles = new ArrayList<>();

    }
    public double[] getLocationAsArray() {
        String[] parts = location.split(",");
        double latitude = Double.parseDouble(parts[0].trim());
        double longitude = Double.parseDouble(parts[1].trim());
        return new double[]{latitude, longitude};
    }

    public void addAnimalProfile(Profile pf) {
        animalProfiles.add(pf);
    }

    // Getters and Setters

    public String getRescueCenterID() {
        return rescueCenterID;
    }

    public void setRescueCenterID(String id) {
        this.rescueCenterID = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getName() {
        return CenterName;
    }

    public void setName(String Name) {
        this.CenterName = Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getter and Setter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
