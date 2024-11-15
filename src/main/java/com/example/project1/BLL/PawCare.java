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
    public boolean registerUser(String username,String name,String email,String password,String gender) {

            // Checking if username or email is already taken
            if (isUsernameOrEmailTaken(username, email)) {
                //System.out.println("Username or Email is already taken. Registration failed.");
                return false;
            }
            User newUser = new User(username,name,email,password,gender);
            //User newUser = new User(userForm.getUserName(), userForm.getEmail(), userForm.getPassword(),userForm.getGender());
            users.add(newUser);
            dbHandler.addUser(newUser);
            return true;

        }


    // Register a Vet
    public boolean registerVet(String username,String name,String email,String password,String location,String phonenumber) {

            // Checking if username or email is already taken
            if (isUsernameOrEmailTaken(username, email)) {
                //System.out.println("Username or Email is already taken. Registration failed.");
                return false;
            }
            Vets newVet = new Vets(username,name,email,password,location,phonenumber);
            //Vets newVet = new Vets(vetForm.getVetName(), vetForm.getPassword(), vetForm.getLocation(),vetForm.getPhoneNumber());
            // Add the new Vet to the list and database
            vets.add(newVet);
            dbHandler.addVet(newVet);
            return true;

    }

    // Register a Rescue Center
    public boolean registerRescueCenter(String username,String name,String email,String password,String location,String phonenumber) {
            // Checking if username or email is already taken
            if (isUsernameOrEmailTaken(username, email)) {
                //System.out.println("Username or Email is already taken. Registration failed.");
                return false;
            }

            RescueCenter newCenter = new RescueCenter(username,name,email,password,location,phonenumber);
            // RescueCenter newCenter = new RescueCenter(centerForm.getCenterName(), centerForm.getPassword(), centerForm.getLocation(),centerForm.getPhoneNumber());
            rescueCenters.add(newCenter);
            dbHandler.addRescueCenter(newCenter);
           return true;

    }

    //login function for anyone
    public boolean login(String username,String password,String type)
    {
        boolean loginSuccessful = false;

            switch (type.toLowerCase()) {
                case "user":
                    for (User user : users) {
                        if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                           // System.out.println("User successfully logged in!");
                            loginSuccessful = true;
                            break;
                        }
                    }
                    break;

                case "vet":
                    for (Vets vet : vets) {
                        if (vet.getUserName().equals(username) && vet.getPassword().equals(password)) {
                            System.out.println("Vet successfully logged in!");
                            loginSuccessful = true;
                            break;
                        }
                    }
                    break;

                case "rescue center":
                    for (RescueCenter center : rescueCenters) {
                        if (center.getUserName().equals(username) && center.getPassword().equals(password)) {
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
               // System.out.println("Invalid username or password.");
                return false;
            }
            else {
                return true;
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
