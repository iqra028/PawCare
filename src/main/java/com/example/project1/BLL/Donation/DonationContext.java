package com.example.project1.BLL.Donation;

import java.util.*;

public class DonationContext {
    private PaymentStrategy paymentStrategy;
    private List<Donation> donations;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
        this.donations = new ArrayList<>();
    }
    public Donation addDonation(double amount,String userid,String rescuecenterid) {
        Donation donation = new Donation();
        donation.makeDonation(amount,userid,rescuecenterid);
        donations.add(donation);
        return donation;
    }
    public List<Donation> getDonations() {
        return donations;
    }

    public Donation executePayment(double amount,String userid,String rescuecenterid) {

        Donation donation = addDonation(amount,userid,rescuecenterid);
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy is not set");
        }
        paymentStrategy.pay(amount);
        System.out.println(userid);
        return donation;
    }
}
