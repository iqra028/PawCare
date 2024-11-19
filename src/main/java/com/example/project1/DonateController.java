package com.example.project1;

import com.example.project1.BLL.PawCare;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

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
    private TextField amount;
    @FXML
    private Button submitbutton;
    @FXML
    private Button Missing; // By Card button
    @FXML
    private Button Found;
    private PawCare pawCare; // Reference to PawCare instance

    @Override
    public void initialize() {
        super.initialize();

        // Initialize PawCare instance
        pawCare = new PawCare();
        Missing.setOnAction(event -> openCardPage());
        Found.setOnAction(event -> openEasypaisaPage());
        submitbutton.setOnAction(event -> handleSubmit());
    }
    private void openCardPage() {
        try {
            HelloApplication.getInstance().changeScene("DonateForm.fxml");        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openEasypaisaPage() {

        try {
            HelloApplication.getInstance().changeScene("DonateEasypaisa.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSubmit() {
        if (!validateInputs()) {
            System.out.println("Please fill in all required fields.");
            return;
        }

        // Gather donation details
        String selectedFoundation = foundation.getText();
        String firstName = firstname.getText();
        String lastName = lastname.getText();
        String cardNumber = cardnumber.getText();
        String expirationDate = expirationdate.getText();
        String pinCode = pin.getText();
        String userCountry = country.getText();
        String billingAddress = billingaddress.getText();
        String postalCode = postalcode.getText();
        String Amount = amount.getText();

        // Use PawCare to process donation
        try {
            pawCare.processDonation(
                    selectedFoundation,
                    firstName,
                    lastName,
                    cardNumber,
                    expirationDate,
                    pinCode,
                    userCountry,
                    billingAddress,
                    postalCode,Amount
            );
            System.out.println("Donation to " + selectedFoundation + " submitted successfully!");
        } catch (Exception e) {
            System.out.println("Error processing donation: " + e.getMessage());
        }
    }

    private boolean validateInputs() {
        return !foundation.getText().isEmpty() && !firstname.getText().isEmpty() &&
                !lastname.getText().isEmpty() && !cardnumber.getText().isEmpty() &&
                !expirationdate.getText().isEmpty() && !pin.getText().isEmpty() &&
                !country.getText().isEmpty() && !billingaddress.getText().isEmpty() &&
                !postalcode.getText().isEmpty()&&!amount.getText().isEmpty();
    }
}
