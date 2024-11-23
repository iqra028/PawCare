package com.example.project1.BLL;

public class Vitals {
    private String id;
    private double lowerTemperature;
    private double upperTemperature;
    private String animalType;
    private int lowerHeartRate;
    private int upperHeartRate;
    private int lowerRespiratoryRate;
    private int upperRespiratoryRate;
    private int capillaryRefillTime;
    private int lowerBloodOxygen;
    private int upperBloodOxygen;
    private int lowerBloodGlucose;
    private int upperBloodGlucose;

    // Constructor
    public Vitals(String id, double lowerTemperature, double upperTemperature, String animalType,
                  int lowerHeartRate, int upperHeartRate, int lowerRespiratoryRate, int upperRespiratoryRate,
                  int capillaryRefillTime, int lowerBloodOxygen, int upperBloodOxygen,
                  int lowerBloodGlucose, int upperBloodGlucose) {
        this.id = id;
        this.lowerTemperature = lowerTemperature;
        this.upperTemperature = upperTemperature;
        this.animalType = animalType;
        this.lowerHeartRate = lowerHeartRate;
        this.upperHeartRate = upperHeartRate;
        this.lowerRespiratoryRate = lowerRespiratoryRate;
        this.upperRespiratoryRate = upperRespiratoryRate;
        this.capillaryRefillTime = capillaryRefillTime;
        this.lowerBloodOxygen = lowerBloodOxygen;
        this.upperBloodOxygen = upperBloodOxygen;
        this.lowerBloodGlucose = lowerBloodGlucose;
        this.upperBloodGlucose = upperBloodGlucose;
    }

    // Getters and Setters
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public double getLowerTemperature() {return lowerTemperature;}
    public void setLowerTemperature(double lowerTemperature) {this.lowerTemperature = lowerTemperature;}
    public double getUpperTemperature() {return upperTemperature;}
    public void setUpperTemperature(double upperTemperature) {this.upperTemperature = upperTemperature;}
    public String getAnimalType() {return animalType;}
    public void setAnimalType(String animalType) {this.animalType = animalType;}
    public int getLowerHeartRate() {return lowerHeartRate;}
    public void setLowerHeartRate(int lowerHeartRate) {this.lowerHeartRate = lowerHeartRate;}
    public int getUpperHeartRate() {return upperHeartRate;}
    public void setUpperHeartRate(int upperHeartRate) {this.upperHeartRate = upperHeartRate;}
    public int getLowerRespiratoryRate() {return lowerRespiratoryRate;}
    public void setLowerRespiratoryRate(int lowerRespiratoryRate) {this.lowerRespiratoryRate = lowerRespiratoryRate;}
    public int getUpperRespiratoryRate() {return upperRespiratoryRate;}
    public void setUpperRespiratoryRate(int upperRespiratoryRate) {this.upperRespiratoryRate = upperRespiratoryRate;}
    public int getCapillaryRefillTime() {return capillaryRefillTime;}
    public void setCapillaryRefillTime(int capillaryRefillTime) {this.capillaryRefillTime = capillaryRefillTime;}
    public int getLowerBloodOxygen() {return lowerBloodOxygen;}
    public void setLowerBloodOxygen(int lowerBloodOxygen) {this.lowerBloodOxygen = lowerBloodOxygen;}
    public int getUpperBloodOxygen() {return upperBloodOxygen;}
    public void setUpperBloodOxygen(int upperBloodOxygen) {this.upperBloodOxygen = upperBloodOxygen;}
    public int getLowerBloodGlucose() {return lowerBloodGlucose;}
    public void setLowerBloodGlucose(int lowerBloodGlucose) {this.lowerBloodGlucose = lowerBloodGlucose;}
    public int getUpperBloodGlucose() {return upperBloodGlucose;}
    public void setUpperBloodGlucose(int upperBloodGlucose) {this.upperBloodGlucose = upperBloodGlucose;}
}