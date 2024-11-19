package com.example.project1.BLL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.*;
public class PawCare {

    private DonationContext donationContext;
    private ArrayList<Vets> vets;
    private ArrayList<RescueCenter> rescueCenters;
    private ArrayList<User> users;
    private ArrayList<Form> forms;
    private DBhandler db;
    private FormFactory formFactory;
    private GeoLocation geoLocation;

    // Constructor
    public PawCare() {
        this.vets = new ArrayList<>();
        this.rescueCenters = new ArrayList<>();
        this.users = new ArrayList<>();
        this.forms = new ArrayList<>();
        this.db=new DBhandler();
        this.formFactory=new FormFactory();
        this.geoLocation=new GeoLocation();
        loadDataFromDatabase();
        this.donationContext = new DonationContext();
    }
    public void processDonation(String foundation, String firstName, String lastName, String cardNumber, String expirationDate, String pin, String country, String billingAddress, String postalCode,String Amount)
    {

        PaymentStrategy paymentStrategy = new CardPayment(cardNumber, firstName + " " + lastName, expirationDate, pin);
        donationContext.setPaymentStrategy(paymentStrategy);
        double amount = Double.parseDouble(Amount);
        donationContext.executePayment(amount);
    }
    public void processDonation(String phone ,String firstname,String lastname, String donationAmount )
    {
        PaymentStrategy paymentStrategy = new EasypaisaPayment(phone,firstname,lastname);
        donationContext.setPaymentStrategy(paymentStrategy);
        double amount = Double.parseDouble(donationAmount);
        donationContext.executePayment(amount);

    }
    private String formatLocation(String rawLocation) {
        return rawLocation
                .replace("Latitude ", "")
                .replace("Longitude ", "")
                .replace(" ", "");
    }
    public List<String> fetchNearbyRegisteredRescueCenters() {
        List<String> nearbyCenters = SharedData.getInstance().getRescueCenters(); // Fetch nearby shelters using the API
        List<String> registeredNearbyCenters = new ArrayList<>();

        if (nearbyCenters != null) {
            for (String shelterInfo : nearbyCenters) {
                String[] details = shelterInfo.split("\n");
                String shelterName = details[0].replace("Name: ", "").trim();
                String rawLocation = details[1].replace("Location: ", "").trim();
                String shelterLocation = formatLocation(rawLocation);
                for (RescueCenter registeredCenter : rescueCenters) {
                    String registeredLocation = registeredCenter.getLocation();
                    if (registeredCenter.getName().equalsIgnoreCase(shelterName) || isPrefixMatch(registeredLocation, shelterLocation)) {
                        System.out.println("Shelter Location: " + shelterLocation);
                        System.out.println("Registered Center Location: " + registeredLocation);
                        registeredNearbyCenters.add(shelterInfo);
                    }
                }
            }
        }

        return registeredNearbyCenters;
    }
    void sendAlert(Alert alert) {
        System.out.println("successfully sent alert");
    }
    public void createAlert(String animalType, String breed, String InjuryDesc, String imagePath, double[] userLocation) {

        Alert alert = new Alert(animalType, breed,InjuryDesc, imagePath, userLocation);

        sendAlert(alert);
    }

    private boolean isPrefixMatch(String registeredLocation, String shelterLocation) {
        return registeredLocation.toLowerCase().startsWith(shelterLocation.toLowerCase());
    }

    public double[] getLocation()
    {
        double[] location=geoLocation.fetchDynamicLocation();
        geoLocation.setLatitude(location[0]);
        geoLocation.setLongitude(location[1]);
        return location;
    }
    public String generatemap()
    {
        double latitude = geoLocation.getLatitude();
        double longitude = geoLocation.getLongitude();
        double radius = 5000;

         return geoLocation.generateMapHTML(latitude, longitude);
    }
    public List<String> generatenearbycenters(){

        String overpassUrl = "https://overpass-api.de/api/interpreter?data=[out:json];" +
                "node(around:" + geoLocation.getRadius() + "," + geoLocation.getLatitude() + "," + geoLocation.getLongitude() + ")" +
                "[amenity~\"animal_shelter\"];out;";

        try {
            URL url = new URL(overpassUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject jsonResponse = new JSONObject(response.toString());
                return geoLocation.parseAndDisplayShelterInfo(jsonResponse);
            } else {
                System.out.println("Error fetching data: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> fetchRescueCenters() {
        // Initialize rescue centers list
        List<String> rescueCentersList = new ArrayList<>();
        // Return the list of rescue centers
        return rescueCentersList;
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
        users = db.getAllUsers();
        vets = db.getAllVets();
        rescueCenters = db.getAllRescueCenters();
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
            db.storeUserRecord(name,username,email,password,"",phoneNumber);
            String userID = db.getIDByUsername("\"user\"", "userid", "username", username);
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

            db.storeVetRecord(name,username,email,password,location,phonenumber);
            String vetID = db.getIDByUsername("vets", "vetid", "username", username);
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

            db.storeCenterRecord(name,username,password,phonenumber,location,email);
            String rescueCenterID = db.getIDByUsername("rescuecenter", "rescuecenterid", "username", username);

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
