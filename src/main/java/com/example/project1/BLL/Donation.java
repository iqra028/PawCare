package com.example.project1.BLL;

import java.time.LocalDate;
import java.util.*;
public class Donation {

    private double amount;
    private LocalDate dateCreated;
    String userid;
    String rescuecenterid;
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public LocalDate getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getRescuecenterid() {
        return rescuecenterid;
    }
    public void setRescuecenterid(String rescuecenterid) {
        this.rescuecenterid = rescuecenterid;
    }
    public double makeDonation(double amount, String userid,String rescuecenterid) {
        this.amount = amount;
        this.dateCreated = LocalDate.now();
        this.userid = userid;
        this.rescuecenterid = rescuecenterid;
        return amount;
    }
    public String viewDonationDetails() {
        return "Donation Details:\n" +
                "Amount: " + amount + "\n" +
                "Date Created: " + dateCreated;
    }
}
