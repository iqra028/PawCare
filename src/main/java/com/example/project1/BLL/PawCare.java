package com.example.project1.BLL;

import java.util.*;
import java.util.*;
public class PawCare {


    private ArrayList<Vets> vets;
    private ArrayList<RescueCenter> rescueCenters;
    private ArrayList<User> users;
    private ArrayList<Form> forms;
    private PersistanceHandler userHandler;
    private PersistanceHandler vetHandler;
    private PersistanceHandler centerHandler;
    private FormFactory formFactory;
    private GeoLocation geoLocation;

    // Constructor
    public PawCare() {
        this.vets = new ArrayList<>();
        this.rescueCenters = new ArrayList<>();
        this.users = new ArrayList<>();
        this.forms = new ArrayList<>();
        this.userHandler = new UserRecords();
        this.vetHandler = new VetRecords();
        this.centerHandler = new RescueCenterRecords();
        this.formFactory=new FormFactory();
        this.geoLocation=new GeoLocation();
        loadDataFromDatabase();
    }

    public double[] getLocation()
    {
        return geoLocation.fetchDynamicLocation();
    }
    public String generatemap()
    {
        double[] location= getLocation();
        double latitude = location[0];
        double longitude = location[1];
        double radius = 9000; // You can modify the radius if needed

        // Fetch data (shelters, veterinary centers, etc.) - this is done in the GeoLocation class
        geoLocation.fetchDataFromOverpassAPI(latitude, longitude, radius);

        // Generate the map HTML and load it into the WebView
         return geoLocation.generateMapHTML(latitude, longitude);
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
        users = userHandler.getAllUsers();
        vets = vetHandler.getAllVets();
        rescueCenters = centerHandler.getAllRescueCenters();
    }

    public void printAllIDs() {
        System.out.println("User IDs:");
        for (User user : users) {
            System.out.println(user.getUserID()); // Assuming getID() returns the ID of the user
        }

        System.out.println("\nVet IDs:");
        for (Vets vet : vets) {
            System.out.println(vet.getVetID()); // Assuming getID() returns the ID of the vet
        }

        System.out.println("\nRescue Center IDs:");
        for (RescueCenter center : rescueCenters) {
            System.out.println(center.getRescueCenterID()); // Assuming getID() returns the ID of the rescue center
        }
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
    public boolean registerUser(String username,String name,String email,String password,String phoneNumber) {

            // Checking if username or email is already taken
            if (isUsernameOrEmailTaken(username, email)) {
                //System.out.println("Username or Email is already taken. Registration failed.");
                return false;
            }

            //User newUser = new User(userForm.getUserName(), userForm.getEmail(), userForm.getPassword(),userForm.getGender());
            userHandler.storeRecord(username,name,email,password,"",phoneNumber);
            String userID = userHandler.getIDByUsername("\"user\"", "userid", "username", username);
            User newUser = new User(userID,username,name,email,password,"",phoneNumber);
            users.add(newUser);
           // System.out.println(newUser.getUserID());

            return true;

        }


    // Register a Vet
    public boolean registerVet(String username,String name,String email,String password,String location,String phonenumber) {

            // Checking if username or email is already taken
            if (isUsernameOrEmailTaken(username, email)) {
                //System.out.println("Username or Email is already taken. Registration failed.");
                return false;
            }

            vetHandler.storeRecord(username,name,email,password,location,phonenumber);
            String vetID = vetHandler.getIDByUsername("vets", "vetid", "username", username);
            Vets newVet = new Vets(vetID,username,name,email,password,location,phonenumber);
            vets.add(newVet);
           // System.out.println(newVet.getVetID());
            return true;

    }

    // Register a Rescue Center
    public boolean registerRescueCenter(String username,String name,String email,String password,String location,String phonenumber) {
            // Checking if username or email is already taken
            if (isUsernameOrEmailTaken(username, email)) {
                //System.out.println("Username or Email is already taken. Registration failed.");
                return false;
            }

            centerHandler.storeRecord(username,name,email,password,location,phonenumber);
            String rescueCenterID = centerHandler.getIDByUsername("rescuecenter", "rescuecenterid", "username", username);

            RescueCenter newCenter = new RescueCenter(rescueCenterID,username,name,email,password,location,phonenumber);
            rescueCenters.add(newCenter);
        //System.out.println(newCenter.getRescueCenterID());
            return true;

    }

    //login function for anyone
    public boolean login(String username,String password,String type)
    {
       // printAllIDs();
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
                        ", Email: " + user.getEmail() + ", phonenumber: " + user.getPhoneNumber());
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
