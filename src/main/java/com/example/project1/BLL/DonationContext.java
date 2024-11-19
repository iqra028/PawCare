package com.example.project1.BLL;

import java.util.*;

public class DonationContext {
    private PaymentStrategy paymentStrategy;
    private List<Donation> donations;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
        this.donations = new ArrayList<>();
    }
    public void addDonation(double amount) {
        Donation donation = new Donation();
        donation.makeDonation(amount);
        donations.add(donation);
    }
    public List<Donation> getDonations() {
        return donations;
    }

    public void executePayment(double amount) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy is not set");
        }
        paymentStrategy.pay(amount);
    }
}
