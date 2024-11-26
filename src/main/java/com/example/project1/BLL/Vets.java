package com.example.project1.BLL;

import com.example.project1.BLL.Profiles.AnimalProfile;
import com.example.project1.BLL.Profiles.Profile;

import java.util.ArrayList;
import java.util.List;

public class Vets {

    private String vetID;
    private String userName;
    private String vetName;
    private String password;
    private String email;
    private String location;  // You can change this to a Location type later
    private String phoneNumber;
    private List<Profile> currentlybeingchecked;

    // Updated constructor to include phone number
    public Vets(String vetID,String userName, String vetName, String email, String password, String location, String phoneNumber) {
        this.vetID=vetID;
        this.userName = userName;
        this.vetName = vetName;
        this.email = email;
        this.password = password;
        this.location = location;
        this.phoneNumber = phoneNumber;
        currentlybeingchecked=new ArrayList<Profile>();
    }
    public void removeanimal (Profile animal)
    {
        currentlybeingchecked.remove(animal);
    }
    public void setCurrentlybeingchecked(Profile animal) {
        currentlybeingchecked.add(animal);
        for(Profile a:currentlybeingchecked)
        {
            System.out.println(a.getAnimal().getName()+" "+a.getAnimal().getAnimalID());
        }
    }
    public void setAnimals(List<Animal> animals){
        for(Animal a:animals)
        {
            Profile f=new AnimalProfile(a);
            currentlybeingchecked.add(f);
        }
    }
    public void setProfiles(List<Profile> animals){
        currentlybeingchecked=animals;
    }
    public void addprofile(Animal animal,String id){
        Profile f=new AnimalProfile(animal);
        f.setRescueCenterId(id);
    }
    public List<Profile> getCurrentlybeingchecked() {
        return currentlybeingchecked;
    }
    // Getters and Setters
    public String getVetID() {
        return vetID;
    }

    public void setVetID(String id) {
        this.vetID = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return vetName;
    }

    public void setName(String Name) {
        this.vetName = Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
}
