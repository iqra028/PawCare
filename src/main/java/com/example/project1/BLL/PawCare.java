package com.example.project1.BLL;

import javafx.scene.image.Image;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class PawCare {

    private DonationContext donationContext;
    private ArrayList<Vets> vets;
    private ArrayList<RescueCenter> rescueCenters;
    private ArrayList<Volunteer> volunteers;
    private ArrayList<User> users;
    private ArrayList<Form> forms;
    private ArrayList<Vitals> vitals;
    private DBhandler db;
    private FormFactory formFactory;
    private GeoLocation geoLocation;
    private ProfileFactory profileFactory;
    private List<injuryReport> InjuryReport;
    private FirstAid firstAid;

    // Constructor
    public PawCare()  {
        this.vets = new ArrayList<>();
        this.volunteers= new ArrayList<>();
        this.rescueCenters = new ArrayList<>();
        this.users = new ArrayList<>();
        this.forms = new ArrayList<>();
        this.db=new DBhandler();
        this.formFactory=new FormFactory();
        this.geoLocation=new GeoLocation();
        this.vitals=new ArrayList<>();
        this.profileFactory = new ProfileFactory();
        loadDataFromDatabase();
        this.donationContext = new DonationContext();
        this.InjuryReport = new ArrayList<>();
        this.firstAid=new FirstAid();

    }

    public String getFirstAidResponse(String userMessage) {
        String response =firstAid.getResponse(userMessage);
        return  response;
    }
    public void saveInjuryReport(injuryReport report) {

        this.InjuryReport.add(report);
        db.saveReport(report);
        db.updateVetAnimalHandled(report.getVetid(),report.getAnimal_id(),true);


    }
    public void visitedvet(injuryReport report)
    {
        db.visitedvet(report.getAnimal_id());
    }
    public injuryReport retreivereport(Profile animal, String username) {
        System.out.println(animal.getAnimal().getName());
        InjuryReport=db.loadReports();
        System.out.println("Number of reports loaded: " + InjuryReport.size());
        for(injuryReport i:InjuryReport) {
            System.out.println(i.hell());
        }

        for (injuryReport report : InjuryReport) {
          //  System.out.println(report.hell());
            //System.out.println("Checking report for Animal ID: " + report.getAnimal_id());

            // Check if the report's animal ID matches
            if (report.getAnimal_id().equals(animal.getAnimal().getAnimalID())) {
                // Uncomment and complete the rescue center check if needed

            if (animal.getRescueCenterId().equals(getRescueCenterByUsername(username).getRescueCenterID())) {
                System.out.println("Matching report found.");
                return report;
            }


                // If rescue center check isn't required, return the matching report
            //    System.out.println("Matching report found.");
              //  return report;
            }
        }

        System.out.println("No matching report found for Animal ID: " + animal.getAnimal().getAnimalID());
        return null;
    }

    public String getRescuecenteridthroughanimalid(Profile p){
        return p.getRescueCenterId();
    }
    public void create_volunteer( String userId, String cnic, String vehicleType, Image vehicleImage, String vehicleModel)
    {
        Volunteer v=new Volunteer( userId, cnic, vehicleType,  vehicleImage, vehicleModel,false);
        volunteers.add(v);
        db.storeVolunteerRecord(userId, cnic, vehicleType,  vehicleImage, vehicleModel,false);

    }

    public void processDonation(String foundation, String firstName, String lastName, String cardNumber, String expirationDate, String pin, String country, String billingAddress, String postalCode,String Amount,String rescuecenterid,String id )
    {

        PaymentStrategy paymentStrategy = new CardPayment(cardNumber, firstName + " " + lastName, expirationDate, pin);
        donationContext.setPaymentStrategy(paymentStrategy);
        double amount = Double.parseDouble(Amount);
        Donation donation= donationContext.executePayment(amount,id,rescuecenterid);
        addDonationTodatabase(donation);
    }
    public void processDonation(String phone ,String firstname,String lastname, String donationAmount,String rescuecenterid ,String id )
    {
        PaymentStrategy paymentStrategy = new EasypaisaPayment(phone,firstname,lastname);
        donationContext.setPaymentStrategy(paymentStrategy);
        double amount = Double.parseDouble(donationAmount);
        Donation donation= donationContext.executePayment(amount,id,rescuecenterid);
        addDonationTodatabase(donation);

    }
    public void addDonationTodatabase(Donation donation){
        db.storeDonationRecord(donation);
    }
    public List<Donation> DisplayDonationRecords(String id){
        return db.displayDonationRecords(id);
    }
    public String getUserNameByUserid(String id)
    {
        for(User user : users)
        {
            if(user.getUserID().equals(id))
                return user.getName();
        }
        return "";

    }
    public String getUserIDByUsername(String name)
    {
        for(User user : users)
        {
            if(user.getUserName().equals(name))
                return user.getUserID();
        }
        return "";

    }
    private String formatLocation(String rawLocation) {
        return rawLocation
                .replace("Latitude ", "")
                .replace("Longitude ", "")
                .replace(" ", "");
    }
    public List<RescueCenter> fetchNearbyRegisteredRescueCenters() {
        List<String> nearbyCenters = SharedData.getInstance().getRescueCenters(); // Fetch nearby shelters using the API
        List<RescueCenter> registeredNearbyCenters = new ArrayList<>();

        if (nearbyCenters != null) {
            for (String shelterInfo : nearbyCenters) {
                String[] details = shelterInfo.split("\n");
                String shelterName = details[0].replace("Name: ", "").trim();
                String rawLocation = details[1].replace("Location: ", "").trim();
                String shelterLocation = formatLocation(rawLocation); // Assuming formatLocation formats the location string

                for (RescueCenter registeredCenter : rescueCenters) {
                    String registeredLocation = registeredCenter.getLocation();
                    if (registeredCenter.getName().equalsIgnoreCase(shelterName) || isPrefixMatch(registeredLocation, shelterLocation)) {
                        registeredNearbyCenters.add(registeredCenter);
                    }
                }
            }
        }

        return registeredNearbyCenters;
    }

    public String getRescueCenterIDByName(String name) {
        for (RescueCenter center : rescueCenters) {
            if (center.getName().equalsIgnoreCase(name)) {
                return center.getRescueCenterID();
            }
        }
        return null;
    }
    public String getRescueCenterIDByUserName(String username) {
        for (RescueCenter center : rescueCenters) {
            if (center.getUserName().equalsIgnoreCase(username)) {
                return center.getRescueCenterID();
            }
        }
        return null;
    }
    public String getVetname(String id)
    {
        for(Vets v : vets)
        {
            if(v.getVetID().equals(id))
                return v.getName();
        }
        return "";
    }
    public String getRescuecentername(String id){
        for(RescueCenter center : rescueCenters)
        {
            if(center.getRescueCenterID().equalsIgnoreCase(id)){
                return center.getName();
            }
        }
        return "";
    }
    public String createAlert(String animalType, String breed, String InjuryDesc, Image imagePath,
                              double[] userLocation, String userid, String rescuecenterid, String type) {
        // Validate userid and rescuecenterid before proceeding
        System.out.println("create alert");
        if (userid == null || userid.isEmpty()) {
            System.err.println("User ID is null or empty.");
            return null; // Exit early if the user ID is invalid
        }
        if(imagePath.equals(null))
            System.err.println("image is null from here");

        if (rescuecenterid == null ) {
            System.err.println("Rescue Center ID is null or empty.");
            return null; // Exit early if the rescue center ID is invalid
        }
        System.out.println("image path is"+imagePath);
        Alert alert = new Alert(animalType, breed, InjuryDesc, imagePath, userLocation, userid, rescuecenterid);
        String alertid=db.storeAlertRecord(alert, type);

        for( RescueCenter r : rescueCenters)
        {
            if(rescuecenterid.equals(r.getRescueCenterID()))
            {
                r.addalert(alert);
                break;
            }
        }
        return alertid;

    }


    private boolean isPrefixMatch(String registeredLocation, String shelterLocation) {
        return registeredLocation.toLowerCase().startsWith(shelterLocation.toLowerCase());
    }
    public List<Alert> getAlertsFromDatabase(String username){
        List<Alert> alert= loadAlerts(getRescueCenterByUsername(username));
        for(RescueCenter r: rescueCenters)
        {
            if(r.getUserName().equalsIgnoreCase(username))
            {
                r.setAlert(alert);
            }
        }
        return alert;
    }
    public double[] getLocation()
    {   System.out.println("getLocation called");
        double[] location=geoLocation.fetchDynamicLocation();
        geoLocation.setLatitude(location[0]);
        geoLocation.setLongitude(location[1]);
        return location;
    }
    public String generatemap()
    {
       // double latitude = getLocation()[0];
        //double longitude = getLocation()[1];
        double latitude=geoLocation.getLongitude();
        double longitude=geoLocation.getLatitude();
        double radius = 5000;

        return geoLocation.generateMapHTML(latitude, longitude);
    }
    public RescueCenter getRescueCenterbyUsername(String username) {
        for(RescueCenter center : rescueCenters)
        {
            if(center.getUserName().equalsIgnoreCase(username))
                return center;
        }
        return null;
    }
    public boolean addAnimalProf(String name,String type,String breed,String color,Image image,double temperature, int heartRate, int respiratoryRate,
                                 int capillaryRefillTime, int bloodOxygenLevel,
                                 int bloodGlucoseLevel, double weight,String username)
    {
        HealthDescription hd= new HealthDescription(temperature, heartRate,respiratoryRate, capillaryRefillTime,bloodOxygenLevel, bloodGlucoseLevel,weight);
        Animal animal=new Animal("",name,type,breed,color,hd,false,false,false,false,false,image);
        Profile animalProfile = profileFactory.createProfile("animal", animal);
        RescueCenter r=getRescueCenterbyUsername(username);
        r.addAnimalProfile(animalProfile);
        String rid=getRescueCenterIDByUserName(username);
        String rescueCenterID=rid;
        String id = db.addAnimal(animal,rescueCenterID);
        if(id!=null)
        {
            animal.setAnimalID(id);
            return true;
        }
        else {
            return false;
        }
    }
    public void deleteAnimalProf(Profile pf,String username) {
        RescueCenter loggedInRescueCenter = getRescueCenterbyUsername(username);

        if (loggedInRescueCenter != null) {
            if (pf instanceof AnimalProfile) {
                loggedInRescueCenter.removeAnimalProfile(pf);
            } else if (pf instanceof AdoptionProfile) {
                loggedInRescueCenter.removeAdoptionProfile(pf);
            } else {
                System.err.println("Unknown profile type: Cannot delete");
            }
        } else {
            System.err.println("No rescue center is currently logged in.");
        }
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

    public void storeAdoptionRequest(AdoptionRequest adoptionRequest)
    {
        db.addAdoptionRequest(adoptionRequest);
        RescueCenter rescueCenter = getRescueCenterById(adoptionRequest.getRescueCenterId());
        rescueCenter.addAdoptionRequest(adoptionRequest);

    }
    public ArrayList<AdoptionRequest> getAdoptionRequestsForRescueCenter(String rescueCenterUsername) {
        RescueCenter rescueCenter = getRescueCenterByUsername(rescueCenterUsername);
        if (rescueCenter == null) {
            return new ArrayList<>();
        }
        return rescueCenter.getRequests();
    }

    private RescueCenter getRescueCenterById(String rescueCenterId) {
        for (RescueCenter rescueCenter : getRescueCenters()) {
            if (rescueCenter.getRescueCenterID().equals(rescueCenterId)) {
                return rescueCenter;
            }
        }
        return null;
    }

    //loading any existing members of system from database
    private void loadDataFromDatabase()  {
        users = db.getAllUsers();
        vets = db.getAllVets();
        volunteers=db.getAllVolunteers();
        rescueCenters = db.getAllRescueCenters();
        InjuryReport=db.loadReports();

        loadVitals();
        for(Vets v: vets)
        {
            loadvetsrequests(v);
        }
        for (RescueCenter rescueCenter : rescueCenters) {
            loadAnimals(rescueCenter);
            //loadAlerts(rescueCenter);
            loadAdoptionRequests(rescueCenter);
        }
    }
    public void savereport(){

    }
    public Vets getVetfromUsername(String username){
        for(Vets v: vets)
        {
            if(v.getUserName().equals(username))
                return v;

        }
        System.out.println("no found");
        return null;
    }
    public void loadvetsrequests(Vets vet) {
        db.loadanimalsinvet(vet);

    }
    public void loadAdoptionRequests(RescueCenter rescueCenter)
    {
        ArrayList<AdoptionRequest> ad= db.getAdoptionRequests(rescueCenter);
        for (AdoptionRequest request : ad) {
            rescueCenter.addAdoptionRequest(request);
        }

    }

    public boolean handleAdoptionRequest(String rescueCenterName,AdoptionRequest request)
    {
        RescueCenter rc =getRescueCenterByUsername(rescueCenterName);
        if (rc == null) {
            System.out.println("Rescue Center not found for username: " + rescueCenterName);
            return false;
        }
        db.updateAdoptionReqStatus( rc.getRescueCenterID(),request);

        ArrayList<AdoptionRequest> adoptionRequests = rc.getRequests();
        for (AdoptionRequest ar : adoptionRequests) {
            if (ar.getRequestID().equals(request.getRequestID())) {
                ar.setApplicationStatus(request.isApplicationStatus());
                ar.setIs_resolved(request.getIsResolved());
                System.out.println("Adoption request updated in memory for ID: " + request.getRequestID());
                return true;
            }
        }

        System.out.println("Adoption request not found in the rescue center's list.");
        return false;
    }

    public AdoptionRequest getAdoptionRequestByProfile(String username,Profile animalProf) {

        String userId = getUserIdFromUsername(username);
        String animalId = animalProf.getAnimal().getAnimalID();

        for (RescueCenter rescueCenter : getRescueCenters()) {
            for (AdoptionRequest request : rescueCenter.getRequests()) {
                if (request.getUserId().equals(userId) && request.getAnimalId().equals(animalId)) {
                    return request;
                }
            }
        }

        return null;
    }
    private String getUserIdFromUsername(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return user.getUserID();
            }
        }
        return null;
    }


    public void loadVitals() {
        try {
            vitals = db.getVitals();
        } catch (SQLException e) {
            System.err.println("Error retrieving vitals from the database: " + e.getMessage());
            e.printStackTrace();
        }
    }


    List<Alert> loadAlerts(RescueCenter rescueCenter) {
        List<Alert> alert =db.getAlertsByRescueCenter(rescueCenter.getRescueCenterID());
        for(Alert a:alert) {
            rescueCenter.addalert(a);
        }
        return alert;
    }
    public GeoLocation getGeoLocation(){
        return geoLocation;
    }
    public void loadAnimals(RescueCenter rescueCenter) {
        List<Animal> animals = db.getAnimalsByRescueCenter(rescueCenter.getRescueCenterID());
        for (Animal animal : animals) {
            Profile profile;
            if (animal.isUpForAdoption()) {
                profile = profileFactory.createProfile("adoption", animal);
                profile.setRescueCenterId(rescueCenter.getRescueCenterID());
                rescueCenter.addAdoptionProfile(profile);
            } else {
                profile = profileFactory.createProfile("animal", animal);
                profile.setRescueCenterId(rescueCenter.getRescueCenterID());
                rescueCenter.addAnimalProfile(profile);
            }
        }
    }public void sendAlerttoVolunteer(Alert alert) {
        System.out.println("Attempting to send alert to a random available volunteer for alert: " + alert.getaAlertID());
        System.out.println("I came on line 2");

        if (volunteers == null || volunteers.isEmpty()) {
            System.out.println("No volunteers available to send the alert.");
            return;
        }

        System.out.println("I came on line 3");

        Random random = new Random();
        boolean alertSent = false;

        // Try to find an appropriate volunteer up to the size of the list.
        for (int attempts = 0; attempts < volunteers.size(); attempts++) {
            int randomIndex = random.nextInt(volunteers.size());
            Volunteer selectedVolunteer = volunteers.get(randomIndex);

            System.out.println("Randomly selected volunteer: " + selectedVolunteer.getUserId());

            // Check if the volunteer is available and the user IDs do not match.
            if (selectedVolunteer.getAvailable())
            {
                System.out.println(selectedVolunteer.getUserId()+"    "+alert.getUserid());

                if(!(selectedVolunteer.getUserId().equals(alert.getUserid())))
                    {
                System.out.println("Volunteer " + selectedVolunteer.getUserId() + " is available and user IDs do not match. Sending alert...");
                if(alert.getImage()==null)
                {
                    System.out.println("alert image is null");
                }
                alert.setCompleted(true);
                createAlert(
                        alert.getType(),
                        alert.getBreed(),
                        alert.getMessage(),
                        alert.getImage(),
                        alert.getLocation(),
                        selectedVolunteer.getUserId(),
                        alert.getRescuecenterid(),
                        "RescueCenter"
                );
                setAlertToCompleted(alert.getaAlertID());

                alertSent = true;
                break; // Exit loop once the alert is successfully sent.
            } }else {
                System.out.println("Volunteer " + selectedVolunteer.getUserId() + " is not available or user IDs match. Trying another...");
            }
        }

        if (!alertSent) {
            System.out.println("No suitable volunteers found to send the alert after random selection attempts.");
        }
    }



    public void sendAnimalToVet(Profile animal, String vet)
    {
        for(Vets v: vets)
        {
            if(v.getUserName().equals(vet))
            {
                v.setCurrentlybeingchecked(animal);
                System.out.println(v.getVetID());
                db.addvet_animal(v.getVetID(),animal.getAnimal().getAnimalID());
                db.updateanimalwenttovet(animal.getAnimal().getAnimalID());
            }
        }

    }


    public void setAlertToCompleted(String id)
    {   System.out.println("Dispatching team for alert: " + id);
        db.setCompletedToTrue(id);

    }
    public void setCompletedToTrueRC(String id)
    {
        setCompletedToTrueRC(id);
    }
    public List<Alert> getRescueCenterAlerts(String id)
    {
        return db.getRescueCenterAlerts(id);

    }

    public ArrayList<RescueCenter> getRescueCentersProfiles(String animalType, String name, String breed, String color) {
        ArrayList<RescueCenter> rc = new ArrayList<>();
        for (RescueCenter rescueCenter : rescueCenters) {
            RescueCenter matchedRescueCenter = new RescueCenter(rescueCenter.getRescueCenterID(), rescueCenter.getUserName(),rescueCenter.getName(),rescueCenter.getEmail(),rescueCenter.getPassword(), rescueCenter.getLocation(),rescueCenter.getPhoneNumber());

            for (Profile pf : rescueCenter.getAnimalProfiles()) {
                Profile matchedProfile = pf.FindMatch(animalType, name, breed, color);
                if (matchedProfile != null) {
                    matchedRescueCenter.addAnimalProfile(matchedProfile);
                }
            }

            for (Profile pf : rescueCenter.getAdoptionProfiles()) {
                Profile matchedProfile = pf.FindMatch(animalType, name, breed, color);
                if (matchedProfile != null) {
                    matchedRescueCenter.addAdoptionProfile(matchedProfile);
                }
            }

            if (!matchedRescueCenter.getAnimalProfiles().isEmpty() || !matchedRescueCenter.getAdoptionProfiles().isEmpty()) {
                rc.add(matchedRescueCenter);
            }
        }
        return rc;
    }
    public boolean isUserAvailable(String id){
        return db.isUserAvailable(id);
    }

    public Boolean ifUserisaVolunter(String id)
    {
        return db.isUserVolunteer(id);
    }
    public void setVolunteerAvailability(Boolean b,String id)
    {
        db.setVolunteerAvailability(b,id);
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

    public boolean updateAnimalProfile(Animal updatedAnimal) {
        return db.updateAnimalInDB(updatedAnimal);
    }

    public boolean deleteAnimalProfile(Profile animalProfile) {
        Animal animal = animalProfile.getAnimal();
        String animalId = animal.getAnimalID();

        return db.deleteAnimal(animalId);
    }
    public RescueCenter getRescueCenterByUsername(String username) {

        for (RescueCenter rc : rescueCenters) {
            if (rc.getUserName().equals(username)) {
                return rc;
            }
        }
        return null;
    }
    public boolean putAnimalUpForAdoption(Profile animalProf, String username) {
        RescueCenter rc = getRescueCenterByUsername(username);
        if (rc == null) {
            System.out.println("Rescue Center not found for username: " + username);
            return false;
        }
        Animal animal = animalProf.getAnimal();
        if (!animal.isVisitedVet()) {
            return false;
        }

        if (animal.isWithVet()) {
            return false;
        }
        boolean isHealthNormal = checkAnimalHealthStatus(animal);
        if (!isHealthNormal) {
            return false;
        }

        animal.setHealthStatus(true);
        animal.setUpForAdoption(true);
        rc.removeAnimalProfile(animalProf);
        rc.addAdoptionProfile(animalProf);

        db.updateAnimalInDB(animal);
        return true;
    }
    public boolean checkAnimalHealthStatus(Animal animal) {
        String animalType = animal.getType();

        for (Vitals vital : vitals) {
            if (vital.getAnimalType().equalsIgnoreCase(animalType)) {
                // Compare the animal's vitals with the normal ranges
                boolean isTemperatureNormal = animal.getHealth().getTemperature() >= vital.getLowerTemperature()
                        && animal.getHealth().getTemperature() <= vital.getUpperTemperature();

                boolean isHeartRateNormal = animal.getHealth().getHeartRate() >= vital.getLowerHeartRate()
                        && animal.getHealth().getHeartRate() <= vital.getUpperHeartRate();

                boolean isRespiratoryRateNormal = animal.getHealth().getRespiratoryRate() >= vital.getLowerRespiratoryRate()
                        && animal.getHealth().getRespiratoryRate() <= vital.getUpperRespiratoryRate();

                boolean isCapillaryRefillTimeNormal = animal.getHealth().getCapillaryRefillTime() == vital.getCapillaryRefillTime();

                boolean isBloodOxygenNormal = animal.getHealth().getBloodOxygenLevel() >= vital.getLowerBloodOxygen()
                        && animal.getHealth().getBloodOxygenLevel() <= vital.getUpperBloodOxygen();

                boolean isBloodGlucoseNormal = animal.getHealth().getBloodGlucoseLevel() >= vital.getLowerBloodGlucose()
                        && animal.getHealth().getBloodGlucoseLevel() <= vital.getUpperBloodGlucose();

                return isTemperatureNormal && isHeartRateNormal && isRespiratoryRateNormal
                        && isCapillaryRefillTimeNormal && isBloodOxygenNormal && isBloodGlucoseNormal;
            }
        }
        return false;
    }

}


