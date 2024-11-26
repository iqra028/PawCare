package com.example.project1.BLL;

import java.util.*;

public class User {
    private String userID;
    private String userName;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String location;
    private boolean volunteer;

    public void setVolunteer(boolean volunteer) {
        this.volunteer = volunteer;
    }
    public boolean getVolunteer() {
        return volunteer;
    }

    public User(String userID, String userName, String name, String email, String password, String location, String phoneNumber) {

        this.userID=userID;
        this.userName = userName;
        this.name=name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.location=location;
        this.volunteer=false;
    }

    // Getters and Setters

    public String getUserID() {
        return userID;
    }

    public void setUserID(String id) {
        this.userID = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String Location) {
        this.location = Location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
