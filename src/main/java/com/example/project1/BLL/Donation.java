package com.example.project1.BLL;

import java.time.LocalDate;
import java.util.*;
public class Donation {

    private String DonationID;
    private double amount;
    private LocalDate dateCreated;
    String userid;
    String rescuecenterid;

    public double makeDonation(double amount, String userid,String rescuecenterid) {
        this.amount = amount;
        this.dateCreated = LocalDate.now();
        this.userid = userid;
        this.rescuecenterid = rescuecenterid;
        return amount;
    }
    public String viewDonationDetails() {
        return "Donation Details:\n" +
                "Donation ID: " + DonationID + "\n" +
                "Amount: " + amount + "\n" +
                "Date Created: " + dateCreated;
    }
}
