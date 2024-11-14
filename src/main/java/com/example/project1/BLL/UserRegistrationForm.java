package com.example.project1.BLL;

import java.util.*;
public class UserRegistrationForm extends RegistrationForm {
    private String gender;
    private String name;


    public UserRegistrationForm(String userName, String name, String email, String password, String gender) {
        super(userName, email, password);
        this.name = name;
        this.gender = gender;
    }

    // Getter and Setter for gender

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean enterDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter User Name:");
        do {
            this.userName = scanner.nextLine();
            if (this.userName.isEmpty()) System.out.println("User name cannot be empty.");
        } while (this.userName.isEmpty());

        System.out.println("Enter Name:");
        do {
            this.name = scanner.nextLine();
            if (this.name.isEmpty()) System.out.println("Name cannot be empty.");
        } while (this.name.isEmpty());

        System.out.println("Enter Email:");
        do {
            this.email = scanner.nextLine();
            if (this.email.isEmpty()) System.out.println("Email cannot be empty.");
        } while (this.email.isEmpty());

        System.out.println("Enter Password:");
        do {
            this.password = scanner.nextLine();
            if (this.password.isEmpty()) System.out.println("Password cannot be empty.");
        } while (this.password.isEmpty());

        System.out.println("Enter Gender:");
        do {
            this.gender = scanner.nextLine();
            if (this.gender.isEmpty()) System.out.println("Gender cannot be empty.");
        } while (this.gender.isEmpty());

        checkForm = true;
        return checkForm;
    }

}
