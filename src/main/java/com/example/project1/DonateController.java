package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DonateController extends UserMenuController {

    @FXML
    private TextField foundation;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField cardnumber;
    @FXML
    private TextField expirationdate;
    @FXML
    private TextField pin;
    @FXML
    private TextField country;
    @FXML
    private TextField billingaddress;
    @FXML
    private TextField postalcode;
    @FXML
    private Button submitbutton;

    @Override
    public void initialize() {
        super.initialize();
       // submitbutton.setOnAction(event -> handleSubmit());

    }

    private void handleSubmit() {
        String selectedFoundation = foundation.getText();
        String firstName = firstname.getText();
        String lastName = lastname.getText();
        String cardNumber = cardnumber.getText();
        String expirationDate = expirationdate.getText();
        String pinCode = pin.getText();
        String userCountry = country.getText();
        String billingAddress = billingaddress.getText();
        String postalCode = postalcode.getText();

        if (validateInputs()) {
            System.out.println("Donation submitted successfully!");
        } else {
            System.out.println("Please fill in all required fields.");
        }
    }

    private boolean validateInputs()
    {
        return !foundation.getText().isEmpty() || !firstname.getText().isEmpty() ||
                !lastname.getText().isEmpty() || !cardnumber.getText().isEmpty() ||
                !expirationdate.getText().isEmpty() || !pin.getText().isEmpty() ||
                !country.getText().isEmpty() || !billingaddress.getText().isEmpty() ||
                !postalcode.getText().isEmpty();
    }
}
