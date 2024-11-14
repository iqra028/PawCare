package com.example.project1.BLL;

import com.example.project1.BLL.Form;


import java.util.*;
public abstract class  RegistrationForm extends Form {

    protected String userName;
    protected String email;
    protected String password;

    public RegistrationForm(String userName, String email, String password) {
        super("Registration");
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract boolean enterDetails();
}
