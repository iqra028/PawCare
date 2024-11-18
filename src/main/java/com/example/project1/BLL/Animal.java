package com.example.project1.BLL;

public class Animal {

    private String animalID;
    private String type;
    private String breed;
    private String color;
    private HealthDescription health;
    private boolean healthStatus;
    private boolean visitedVet;
    private boolean withVet;
    private boolean upForAdoption;
    private boolean adopted;


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
}
