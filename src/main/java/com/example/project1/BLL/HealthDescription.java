package com.example.project1.BLL;

public class HealthDescription {
    private double temperature;
    private int heartRate;
    private int respiratoryRate;
    private int capillaryRefillTime;
    private int bloodOxygenLevel;
    private int bloodGlucoseLevel;
    private double weight;

    public HealthDescription(double temperature, int heartRate, int respiratoryRate,
                             int capillaryRefillTime, int bloodOxygenLevel,
                             int bloodGlucoseLevel, double weight) {
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.respiratoryRate = respiratoryRate;
        this.capillaryRefillTime = capillaryRefillTime;
        this.bloodOxygenLevel = bloodOxygenLevel;
        this.bloodGlucoseLevel = bloodGlucoseLevel;
        this.weight = weight;
    }
    public HealthDescription(){}
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
}
