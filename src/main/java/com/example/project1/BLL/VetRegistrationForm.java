package com.example.project1.BLL;

import java.util.*;

public class VetRegistrationForm extends RegistrationForm {

    private String VetName;
    private String phoneNumber;
    private String location; // Later we can change this to actual location
    private String password;


    public VetRegistrationForm(String userName, String email, String password, String vetName, String phoneNumber, String location) {
        super(userName, email, password);
        this.VetName = vetName;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.password = password;
    }

    // Getters and Setters
    public String getVetName() {
        return VetName;
    }

    public void setVetName(String vetName) {
        this.VetName = vetName;
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

        System.out.println("Enter User Name:");
        do {
            this.userName = scanner.nextLine();
            if (this.userName.isEmpty()) System.out.println("User name cannot be empty.");
        } while (this.userName.isEmpty());

        System.out.println("Enter Email:");
        do {
            this.email = scanner.nextLine();
            if (this.email.isEmpty()) System.out.println("Email cannot be empty.");
        } while (this.email.isEmpty());

        System.out.println("Enter Vet Name:");
        do {
            this.VetName = scanner.nextLine();
            if (this.VetName.isEmpty()) System.out.println("Vet name cannot be empty.");
        } while (this.VetName.isEmpty());

        System.out.println("Enter Phone Number:");
        do {
            this.phoneNumber = scanner.nextLine();
            if (this.phoneNumber.isEmpty()) System.out.println("Phone number cannot be empty.");
        } while (this.phoneNumber.isEmpty());

        System.out.println("Enter Location:");
        do {
            this.location = scanner.nextLine();
            if (this.location.isEmpty()) System.out.println("Location cannot be empty.");
        } while (this.location.isEmpty());

        System.out.println("Enter Password:");
        do {
            this.password = scanner.nextLine();
            if (this.password.isEmpty()) System.out.println("Password cannot be empty.");
        } while (this.password.isEmpty());

        checkForm = true;
        return checkForm;
    }
}
