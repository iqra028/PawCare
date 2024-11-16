package com.example.project1.BLL;

import com.example.project1.BLL.User;

import java.util.*;
public class Reporter extends User {

    private String reporterID;
    private String userType;

    public Reporter(String userID, String userName, String name, String email, String password, String location, String phoneNumber, String reporterID) {
        super(userID, userName, name, email, password, location, phoneNumber);
        this.reporterID = reporterID;
        this.userType = "Reporter";
    }
    //getter n setters
    public String getReporterID() {
        return reporterID;
    }

    public void setReporterID(String reporterID) {
        this.reporterID = reporterID;
    }

    //methods
    public void reportInjuredAnimal() {

    }

    public void provideFirstAid() {

    }

    public void alertNearbyCenters() {

    }
}
