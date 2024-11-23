package com.example.project1.BLL;
import javafx.scene.image.Image;
public class Animal {

    private String animalID;
    private String name;
    private String type;
    private String breed;
    private String color;
    private HealthDescription health;
    private boolean healthStatus;
    private boolean visitedVet;
    private boolean withVet;
    private boolean upForAdoption;
    private boolean adopted;
    private Image image;

    public Animal(String animalID, String name,String type, String breed, String color, HealthDescription health,
                  boolean healthStatus, boolean visitedVet, boolean withVet,
                  boolean upForAdoption, boolean adopted, Image image) {
        this.animalID = animalID;
        this.name=name;
        this.type = type;
        this.breed = breed;
        this.color = color;
        this.health = health;
        this.healthStatus = healthStatus;
        this.visitedVet = visitedVet;
        this.withVet = withVet;
        this.upForAdoption = upForAdoption;
        this.adopted = adopted;
        this.image = image;
    }
    public Animal(){}



    public String getAnimalID() {
        return animalID;
    }

    public void setAnimalID(String animalID) {
        this.animalID = animalID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public HealthDescription getHealth() {
        return health;
    }

    public void setHealth(HealthDescription health) {
        this.health = health;
    }

    public boolean isHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(boolean healthStatus) {
        this.healthStatus = healthStatus;
    }

    public boolean isVisitedVet() {
        return visitedVet;
    }

    public void setVisitedVet(boolean visitedVet) {
        this.visitedVet = visitedVet;
    }

    public boolean isWithVet() {
        return withVet;
    }

    public void setWithVet(boolean withVet) {
        this.withVet = withVet;
    }

    public boolean isUpForAdoption() {
        return upForAdoption;
    }

    public void setUpForAdoption(boolean upForAdoption) {
        this.upForAdoption = upForAdoption;
    }

    public boolean isAdopted() {
        return adopted;
    }

    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
