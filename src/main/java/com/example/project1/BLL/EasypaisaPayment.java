package com.example.project1.BLL;
public class EasypaisaPayment implements PaymentStrategy {
    private String phoneNumber;
    private String firstName;
    private String lastName;

    public EasypaisaPayment(String phoneNumber,String firstName,String lastName) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing Easypaisa payment...");
        System.out.printf("Paid %.2f using Easypaisa number: %s%n", amount, phoneNumber);
        // Logic to process Easypaisa payment goes here
    }
}
