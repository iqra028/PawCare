package com.example.project1.BLL;

import java.util.Scanner;

public class LoginForm extends Form{
    private String userName;
    private String password;
    private String type;

    public LoginForm(String userName,String password,String type)
    {
        super("Login");
        this.password=password;
        this.userName=userName;
        this.type=type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean enterDetails()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter type:");
        do {
            this.type = scanner.nextLine();
            if (this.type.isEmpty()) System.out.println("cannot be empty.");
        } while (this.type.isEmpty());

        System.out.println("Enter User Name:");
        do {
            this.userName = scanner.nextLine();
            if (this.userName.isEmpty()) System.out.println("User name cannot be empty.");
        } while (this.userName.isEmpty());

        System.out.println("Enter password:");
        do {
            this.password = scanner.nextLine();
            if (this.password.isEmpty()) System.out.println("Password cannot be empty.");
        } while (this.password.isEmpty());

        checkForm = true;
        return checkForm;
    }

}
