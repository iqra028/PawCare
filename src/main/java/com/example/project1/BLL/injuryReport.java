package com.example.project1.BLL;

public class injuryReport {

    private String reportid;
    private String vetid;
    private String rescuecenterid;
    private String animal_id;
    private String description;
    private double temperature;
    private int heartRate;
    private int respiratoryRate;
    private int capillaryRefillTime;
    private int bloodOxygenLevel;
    private int bloodGlucoseLevel;
    private double weight;

    // Default constructor
    public injuryReport() {
    }

    // Parameterized constructor
    public injuryReport(
             String vetid, String rescuecenterid, String animal_id, String description,
            double temperature, int heartRate, int respiratoryRate,
            int capillaryRefillTime, int bloodOxygenLevel, int bloodGlucoseLevel, double weight) {
        //this.reportid = reportid;
        this.vetid = vetid;
        this.rescuecenterid = rescuecenterid;
        this.animal_id = animal_id;
        this.description = description;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.respiratoryRate = respiratoryRate;
        this.capillaryRefillTime = capillaryRefillTime;
        this.bloodOxygenLevel = bloodOxygenLevel;
        this.bloodGlucoseLevel = bloodGlucoseLevel;
        this.weight = weight;
    }

    // Getters and Setters
    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getVetid() {
        return vetid;
    }

    public void setVetid(String vetid) {
        this.vetid = vetid;
    }

    public String getRescuecenterid() {
        return rescuecenterid;
    }

    public void setRescuecenterid(String rescuecenterid) {
        this.rescuecenterid = rescuecenterid;
    }

    public String getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(String animal_id) {
        this.animal_id = animal_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(int respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public int getCapillaryRefillTime() {
        return capillaryRefillTime;
    }

    public void setCapillaryRefillTime(int capillaryRefillTime) {
        this.capillaryRefillTime = capillaryRefillTime;
    }

    public int getBloodOxygenLevel() {
        return bloodOxygenLevel;
    }

    public void setBloodOxygenLevel(int bloodOxygenLevel) {
        this.bloodOxygenLevel = bloodOxygenLevel;
    }

    public int getBloodGlucoseLevel() {
        return bloodGlucoseLevel;
    }

    public void setBloodGlucoseLevel(int bloodGlucoseLevel) {
        this.bloodGlucoseLevel = bloodGlucoseLevel;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // toString method for debugging

    public String hell() {
        return "InjuryReport{" +
                "reportid='" + reportid + '\'' +
                ", vetid='" + vetid + '\'' +
                ", rescuecenterid='" + rescuecenterid + '\'' +
                ", animal_id='" + animal_id + '\'' +
                ", description='" + description + '\'' +
                ", temperature=" + temperature +
                ", heartRate=" + heartRate +
                ", respiratoryRate=" + respiratoryRate +
                ", capillaryRefillTime=" + capillaryRefillTime +
                ", bloodOxygenLevel=" + bloodOxygenLevel +
                ", bloodGlucoseLevel=" + bloodGlucoseLevel +
                ", weight=" + weight +
                '}';
    }
}
