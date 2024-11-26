package com.example.project1.BLL.Location;

import com.example.project1.BLL.Vets;
import javafx.scene.image.Image;

import java.util.List;

public class SharedData {

    private static SharedData instance;
    private List<String> rescueCenters;
    private double latitude;
    private double longitude;
    private String animalType;
    private String breed;
    private String injuryDesc;
    private Image image;
    private Vets selectedVet;
    public void setSelectedVet(Vets vet) {
        selectedVet = vet;
    }
    public Vets getSelectedVet() {
        return selectedVet;
    }

    // Private constructor to enforce singleton pattern
    private SharedData() {}
    public void show(){
        System.out.println(latitude+","+longitude);
        System.out.println(animalType);
        System.out.println(breed);
        System.out.println(injuryDesc);

    }
    public static SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public Image getImage() {
        return image;
    }

    // Rescue centers
    public void setRescueCenters(List<String> rescueCenters) {
        this.rescueCenters = rescueCenters;
    }

    public List<String> getRescueCenters() {
        return rescueCenters;
    }

    // Location
    public void setLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double[] getLocation() {
        return new double[]{latitude, longitude};
    }

    // Animal Report Fields
    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getBreed() {
        return breed;
    }

    public void setInjuryDesc(String injuryDesc) {
        this.injuryDesc = injuryDesc;
    }

    public String getInjuryDesc() {
        return injuryDesc;
    }
}
