package com.example.project1.BLL;

public class Volunteer extends User{

    private String volunteerID;
    private String userType;

    public Volunteer(String userName, String name, String email, String password, String gender, String volunteerID) {
        super(userName, name, email, password, gender);
        this.volunteerID = volunteerID;
        this.userType = "Volunteer";
    }
    //getter n setter
    public String getVolunteerID() {
        return volunteerID;
    }

    public void setVolunteerID(String volunteerID) {
        this.volunteerID = volunteerID;
    }

    //methods
    public void rescueRequest() {

    }

    public void animalRescued() {


    }

    public void transport() {

    }
}
