package com.example.project1.BLL;

import java.util.*;

public class RescueCenter {

    private String rescueCenterID;
    private String userName;
    private String CenterName;
    private String password;
    private String email;
    private String location;
    private String phoneNumber;

    private ArrayList<Profile> animalProfiles;
    private ArrayList<Profile> adoptionProfiles;
    private ArrayList<Alert> alerts;
    private ArrayList<AdoptionRequest> requests;

    public RescueCenter(String rescueCenterID,String userName, String CenterName, String email, String password, String location, String phoneNumber) {
        this.rescueCenterID=rescueCenterID;
        this.userName = userName;
        this.CenterName = CenterName;
        this.email = email;
        this.password = password;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.alerts=new ArrayList<>();
        this.animalProfiles = new ArrayList<>();
        this.adoptionProfiles = new ArrayList<>();
        this.requests=new ArrayList<>();

    }
    public void addalert(Alert alert) {
        alerts.add(alert);
    }
    public List<Alert> getAlerts(){
        return alerts;
    }
    public double[] getLocationAsArray() {
        String[] parts = location.split(",");
        double latitude = Double.parseDouble(parts[0].trim());
        double longitude = Double.parseDouble(parts[1].trim());
        return new double[]{latitude, longitude};
    }

    public void addAdoptionRequest( AdoptionRequest adoptionRequest) {requests.add(adoptionRequest);}
    public void removeAdoptionRequest( AdoptionRequest adoptionRequest) {requests.remove(adoptionRequest);}
    public void addAnimalProfile(Profile pf) {
        animalProfiles.add(pf);
    }
    public void addAdoptionProfile(Profile pf) {
        adoptionProfiles.add(pf);
    }
    public void removeAnimalProfile(Profile pf) {
        animalProfiles.remove(pf);
    }
    public void removeAdoptionProfile(Profile pf) {
        adoptionProfiles.remove(pf);
    }


    // Getters and Setters

    public String getRescueCenterID() {
        return rescueCenterID;
    }

    public void setRescueCenterID(String id) {
        this.rescueCenterID = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getName() {
        return CenterName;
    }

    public void setName(String Name) {
        this.CenterName = Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getter and Setter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public ArrayList<Profile> getAnimalProfiles() {
        return animalProfiles;
    }

    public ArrayList<Profile> getAdoptionProfiles() {
        return adoptionProfiles;
    }

    public void displayAnimalProfiles() {
        if (animalProfiles.isEmpty()) {
            System.out.println("No animal profiles found.");
            return;
        }

        for (Profile profile : animalProfiles) {
            Animal animal = profile.getAnimal();
            System.out.println("Animal Profile:");
            System.out.println("Animal ID: " + animal.getAnimalID());
            System.out.println("Name: " + animal.getName());
            System.out.println("Type: " + animal.getType());
            System.out.println("Breed: " + animal.getBreed());
            System.out.println("Color: " + animal.getColor());
            System.out.println("Health Status: " + animal.isHealthStatus());
            System.out.println("Visited Vet: " + animal.isVisitedVet());
            System.out.println("With Vet: " + animal.isWithVet());
            System.out.println("Up For Adoption: " + animal.isUpForAdoption());
            System.out.println("Adopted: " + animal.isAdopted());
            System.out.println("Image: " + (animal.getImage() != null ? animal.getImage().getUrl() : "No image"));

            // Display the health description if available
            if (animal.getHealth() != null) {
                HealthDescription healthDescription = animal.getHealth();
                System.out.println("Health Description:");
                System.out.println("Temperature: " + healthDescription.getTemperature());
                System.out.println("Heart Rate: " + healthDescription.getHeartRate());
                System.out.println("Respiratory Rate: " + healthDescription.getRespiratoryRate());
                System.out.println("Capillary Refill Time: " + healthDescription.getCapillaryRefillTime());
                System.out.println("Blood Oxygen Level: " + healthDescription.getBloodOxygenLevel());
                System.out.println("Blood Glucose Level: " + healthDescription.getBloodGlucoseLevel());
                System.out.println("Weight: " + healthDescription.getWeight());
            }
            System.out.println("------------------------");
        }
    }

    // Method to display adoption profiles
    public void displayAdoptionProfiles() {
        if (adoptionProfiles.isEmpty()) {
            System.out.println("No adoption profiles found.");
            return;
        }

        for (Profile profile : adoptionProfiles) {
            Animal animal = profile.getAnimal();
            System.out.println("Adoption Profile:");
            System.out.println("Animal ID: " + animal.getAnimalID());
            System.out.println("Name: " + animal.getName());
            System.out.println("Type: " + animal.getType());
            System.out.println("Breed: " + animal.getBreed());
            System.out.println("Color: " + animal.getColor());
            System.out.println("Health Status: " + animal.isHealthStatus());
            System.out.println("Visited Vet: " + animal.isVisitedVet());
            System.out.println("With Vet: " + animal.isWithVet());
            System.out.println("Up For Adoption: " + animal.isUpForAdoption());
            System.out.println("Adopted: " + animal.isAdopted());
            System.out.println("Image: " + (animal.getImage() != null ? animal.getImage().getUrl() : "No image"));

            // Display the health description if available
            if (animal.getHealth() != null) {
                HealthDescription healthDescription = animal.getHealth();
                System.out.println("Health Description:");
                System.out.println("Temperature: " + healthDescription.getTemperature());
                System.out.println("Heart Rate: " + healthDescription.getHeartRate());
                System.out.println("Respiratory Rate: " + healthDescription.getRespiratoryRate());
                System.out.println("Capillary Refill Time: " + healthDescription.getCapillaryRefillTime());
                System.out.println("Blood Oxygen Level: " + healthDescription.getBloodOxygenLevel());
                System.out.println("Blood Glucose Level: " + healthDescription.getBloodGlucoseLevel());
                System.out.println("Weight: " + healthDescription.getWeight());
            }
            System.out.println("------------------------");
        }
    }
}
