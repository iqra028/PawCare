package com.example.project1.BLL;

import java.time.LocalDate;
import java.util.*;
public class Donation {

    private String DonationID;
    private double amount;
    private LocalDate dateCreated;

    public double makeDonation(double amount) {

        return amount;
    }

    // Method to view donation details
    public String viewDonationDetails() {
        return "Donation Details:\n" +
                "Donation ID: " + DonationID + "\n" +
                "Amount: " + amount + "\n" +
                "Date Created: " + dateCreated;
    }
}
