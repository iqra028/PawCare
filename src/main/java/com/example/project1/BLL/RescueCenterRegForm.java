package com.example.project1.BLL;

import java.util.*;

public class RescueCenterRegForm extends RegistrationForm {

    private String centerName;
    private String phoneNumber;
    private String location; // later we have to change this to actual location?
    private String password;

    public RescueCenterRegForm(String userName, String email, String password, String centerName, String phoneNumber, String location) {
        super(userName, email, password);
        this.centerName = centerName;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.password = password;
    }

    // Getters and Setters
    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean enterDetails() {
        Scanner scanner = new Scanner(System.in);

        // Enter Username
        System.out.println("Enter Username:");
        do {
            this.userName = scanner.nextLine();
            if (this.userName.isEmpty()) System.out.println("Username cannot be empty.");
        } while (this.userName.isEmpty());

        // Enter Email
        System.out.println("Enter Email:");
        do {
            this.email = scanner.nextLine();
            if (this.email.isEmpty()) System.out.println("Email cannot be empty.");
        } while (this.email.isEmpty());

        // Enter Center Name
        System.out.println("Enter Center Name:");
        do {
            this.centerName = scanner.nextLine();
            if (this.centerName.isEmpty()) System.out.println("Center name cannot be empty.");
        } while (this.centerName.isEmpty());

        // Enter Phone Number
        System.out.println("Enter Phone Number:");
        do {
            this.phoneNumber = scanner.nextLine();
            if (this.phoneNumber.isEmpty()) System.out.println("Phone number cannot be empty.");
        } while (this.phoneNumber.isEmpty());

        // Enter Location
        System.out.println("Enter Location:");
        do {
            this.location = scanner.nextLine();
            if (this.location.isEmpty()) System.out.println("Location cannot be empty.");
        } while (this.location.isEmpty());

        // Enter Password
        System.out.println("Enter Password:");
        do {
            this.password = scanner.nextLine();
            if (this.password.isEmpty()) System.out.println("Password cannot be empty.");
        } while (this.password.isEmpty());

        checkForm = true;
        return checkForm;
    }
}
