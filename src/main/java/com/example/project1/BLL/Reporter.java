package com.example.project1.BLL;

import com.example.project1.BLL.User;

import java.util.*;
public class Reporter extends User {

    private String reporterID;
    private String userType;

    public Reporter(String userName, String name, String email, String password, String gender, String reporterID) {
        super(userName, name, email, password, gender);
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
