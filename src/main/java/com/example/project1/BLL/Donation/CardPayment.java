package com.example.project1.BLL.Donation;


public class CardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public CardPayment(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing card payment...");
        System.out.printf("Paid %.2f using card: %s%n", amount, cardNumber);
        // Logic to process card payment goes here
    }
}
