package com.example.project1.BLL;

import java.util.*;

public class User {
    private String userName;
    private String name;
    private String email;
    private String password;
    private String gender;

    public User( String userName,String name, String email, String password, String gender) {

        this.userName = userName;
        this.name=name;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    // Getters and Setters

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //methods
    public void adopt() {
      //call function of the system of getAdoptionProfiles and gets a list
      //call fucntion of system selectanimal and get an Adoption form in return
      //will call enter details and submit form function
        //sytem has to forward req to rescue center


    }
    public boolean confirmation()
    {
        if(true) //if yes selected on interface
            return true;
        else
            return false;

    }

    public void findMissingAnimals() {

    }

    public void volunteer() {

    }
}
