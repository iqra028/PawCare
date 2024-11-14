package com.example.project1.BLL;

import java.util.*;
import java.util.*;
public class PawCare {


    private ArrayList<Vets> vets;
    private ArrayList<RescueCenter> rescueCenters;
    private ArrayList<User> users;
    private ArrayList<Form> forms;
    private PersistanceHandler dbHandler;
    private FormFactory formFactory;

    // Constructor
    public PawCare() {
        this.vets = new ArrayList<>();
        this.rescueCenters = new ArrayList<>();
        this.users = new ArrayList<>();
        this.forms = new ArrayList<>();
        this.dbHandler = new DBhandler();
        this.formFactory=new FormFactory();
        loadDataFromDatabase();
    }


    public ArrayList<Vets> getVets() {
        return vets;
    }

    public ArrayList<RescueCenter> getRescueCenters() {
        return rescueCenters;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addForm(Form form) {
        this.forms.add(form);
    }

    //loading any existing members of system from database
    private void loadDataFromDatabase() {
        users = dbHandler.getAllUsers();
        vets = dbHandler.getAllVets();
        rescueCenters = dbHandler.getAllRescueCenters();
    }

    private boolean isUsernameOrEmailTaken(String username, String email) {
        for (User user : users) {
            if (user.getUserName().equals(username) || user.getEmail().equals(email)) {
                return true;
            }
        }
        for (Vets vet : vets) {
            if (vet.getUserName().equals(username) || vet.getEmail().equals(email)) {
                return true;
            }
        }
        for (RescueCenter center : rescueCenters) {
            if (center.getUserName().equals(username) || center.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    // Register a User
    public void registerUser() {
        Form form = formFactory.createForm("UserRegistration");
        if (form.enterDetails() && form.submitForm()) {
            UserRegistrationForm userForm = (UserRegistrationForm) form;
            String username = userForm.getUserName();
            String email = userForm.getEmail();

            // Checking if username or email is already taken
            if (isUsernameOrEmailTaken(username, email)) {
                System.out.println("Username or Email is already taken. Registration failed.");
                return;
            }
            User newUser = new User(userForm.getUserName(), userForm.getName(), userForm.getEmail(), userForm.getPassword(), userForm.getGender());
            //User newUser = new User(userForm.getUserName(), userForm.getEmail(), userForm.getPassword(),userForm.getGender());
            users.add(newUser);
            dbHandler.addUser(newUser);
            forms.add(form);
            System.out.println("User registration successful!");
        } else {
            System.out.println("User registration failed.");
        }
    }

    // Register a Vet
    public void registerVet() {
        Form form = formFactory.createForm("VetRegistration");
        if (form.enterDetails() && form.submitForm()) {
            VetRegistrationForm vetForm = (VetRegistrationForm) form;
            String username = vetForm.getUserName();
            String email = vetForm.getEmail();

            // Checking if username or email is already taken
            if (isUsernameOrEmailTaken(username, email)) {
                System.out.println("Username or Email is already taken. Registration failed.");
                return;
            }
            Vets newVet = new Vets(vetForm.getUserName(), vetForm.getVetName(), vetForm.getEmail(), vetForm.getPassword(), vetForm.getLocation(), vetForm.getPhoneNumber());
            //Vets newVet = new Vets(vetForm.getVetName(), vetForm.getPassword(), vetForm.getLocation(),vetForm.getPhoneNumber());
            // Add the new Vet to the list and database
            vets.add(newVet);
            dbHandler.addVet(newVet);
            // Add the form to the list of submitted forms
            forms.add(form);
            System.out.println("Vet registration successful!");
        } else {
            System.out.println("Vet registration failed.");
        }
    }

    // Register a Rescue Center
    public void registerRescueCenter() {
        Form form = formFactory.createForm("RescueCenterRegistration");
        if (form.enterDetails() && form.submitForm()) {
            RescueCenterRegForm centerForm = (RescueCenterRegForm) form;
            String username = centerForm.getUserName();
            String email = centerForm.getEmail();

            // Checking if username or email is already taken
            if (isUsernameOrEmailTaken(username, email)) {
                System.out.println("Username or Email is already taken. Registration failed.");
                return;
            }

            RescueCenter newCenter = new RescueCenter(centerForm.getUserName(), centerForm.getCenterName(),  centerForm.getEmail(),centerForm.getPassword(), centerForm.getLocation(), centerForm.getPhoneNumber());
            // RescueCenter newCenter = new RescueCenter(centerForm.getCenterName(), centerForm.getPassword(), centerForm.getLocation(),centerForm.getPhoneNumber());
            rescueCenters.add(newCenter);
            dbHandler.addRescueCenter(newCenter);
            forms.add(form);
            System.out.println("Rescue center registration successful!");
        } else {
            System.out.println("Rescue center registration failed.");
        }
    }

    //login function for anyone
    public void login()
    {
        Form form= formFactory.createForm("LoginForm");
        if(form.enterDetails())
        {
            LoginForm loginForm = (LoginForm) form;
            String userName = loginForm.getUserName();
            String password = loginForm.getPassword();
            String type = loginForm.getType();

            boolean loginSuccessful = false;

            switch (type.toLowerCase()) {
                case "user":
                    for (User user : users) {
                        if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                            System.out.println("User successfully logged in!");
                            loginSuccessful = true;
                            break;
                        }
                    }
                    break;

                case "vet":
                    for (Vets vet : vets) {
                        if (vet.getUserName().equals(userName) && vet.getPassword().equals(password)) {
                            System.out.println("Vet successfully logged in!");
                            loginSuccessful = true;
                            break;
                        }
                    }
                    break;

                case "rescue center":
                    for (RescueCenter center : rescueCenters) {
                        if (center.getUserName().equals(userName) && center.getPassword().equals(password)) {
                            System.out.println("Rescue Center successfully logged in!");
                            loginSuccessful = true;
                            break;
                        }
                    }
                    break;

                default:
                    System.out.println("Invalid type provided.");
                    break;
            }

            if (!loginSuccessful) {
                System.out.println("Invalid username or password.");
            }

        }
    }

    public void displayUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("List of Users:");
            for (User user : users) {
                System.out.println(", Name: " + user.getUserName() +
                        ", Email: " + user.getEmail() + ", Gender: " + user.getGender());
            }
        }
    }

    public void displayVets() {
        if (vets.isEmpty()) {
            System.out.println("No vets found.");
        } else {
            System.out.println("List of Vets:");
            for (Vets vet : vets) {
                System.out.println(", Name: " + vet.getUserName() +
                        ", location: " + vet.getLocation()+
                        ", email: " + vet.getEmail());
            }
        }
    }

    public void displayRescueCenters() {
        if (rescueCenters.isEmpty()) {
            System.out.println("No rescue centers found.");
        } else {
            System.out.println("List of Rescue Centers:");
            for (RescueCenter center : rescueCenters) {
                System.out.println(", Name: " + center.getUserName() +
                        ", Location: " + center.getLocation());
            }
        }
    }
}
