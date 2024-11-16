package com.example.project1.BLL;

public class Vets {

    private String vetID;
    private String userName;
    private String vetName;
    private String password;
    private String email;
    private String location;  // You can change this to a Location type later
    private String phoneNumber;

    // Updated constructor to include phone number
    public Vets(String vetID,String userName, String vetName, String email, String password, String location, String phoneNumber) {
        this.vetID=vetID;
        this.userName = userName;
        this.vetName = vetName;
        this.email = email;
        this.password = password;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getVetID() {
        return vetID;
    }

    public void setVetID(String id) {
        this.vetID = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return vetName;
    }

    public void setName(String Name) {
        this.vetName = Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
