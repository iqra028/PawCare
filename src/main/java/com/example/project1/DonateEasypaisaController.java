package com.example.project1;

import com.example.project1.BLL.PawCare;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;


public class DonateEasypaisaController extends UserMenuController {

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField amount;

    @FXML
    private Button submitbutton;
    @FXML
    private Button Missing; // By Card button
    @FXML
    private Button Found;
    private com.example.project1.BLL.PawCare pawCare; // Reference to PawCare instance

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
            HelloApplication.getInstance().changeScene("DonateEasypaisa.fxml");            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSubmit() {
        if (!validateInputs()) {
            System.out.println("Please fill in all required fields.");
            return;
        }
        String phone = phoneNumber.getText();
        String firstname = firstName.getText();
        String lastname = lastName.getText();
        String donationAmount = amount.getText();
        pawCare.processDonation(phone,firstname,lastname,donationAmount);



    }
    private boolean validateInputs() {

        return !phoneNumber.getText().isEmpty() && !firstName.getText().isEmpty() &&
                !lastName.getText().isEmpty() &&
                !amount.getText().isEmpty();
    }

}
