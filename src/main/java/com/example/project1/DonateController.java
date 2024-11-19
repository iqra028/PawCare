package com.example.project1;

import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RescueCenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DonateController extends UserMenuController {


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
    @FXML
    private ComboBox<String> rescueCenterDropdown;
    @Override
    public void initialize() {
        super.initialize();

        // Initialize PawCare instance
        pawCare = new PawCare();
        populateRescueCenters();
        Missing.setOnAction(event -> openCardPage());
        Found.setOnAction(event -> openEasypaisaPage());
        submitbutton.setOnAction(event -> handleSubmit());
    }
    private void populateRescueCenters() {
        // Assuming PawCare has a method to fetch rescue center names
        ArrayList<RescueCenter> rescueCenters = pawCare.getRescueCenters();
        List<String> rescueCenterNames = new ArrayList<>();
        for (RescueCenter rescueCenter : rescueCenters) {
            rescueCenterNames.add(rescueCenter.getName());
        }

        // Convert list to ObservableList and populate ComboBox
        ObservableList<String> centerOptions = FXCollections.observableArrayList(rescueCenterNames);
        rescueCenterDropdown.setItems(centerOptions);
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
        String selectedFoundation = rescueCenterDropdown.getValue();;
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
                    postalCode,Amount,
                    pawCare.getRescueCenterIDByName(selectedFoundation)
            );
            System.out.println("Donation to " + selectedFoundation + " submitted successfully!");
        } catch (Exception e) {
            System.out.println("Error processing donation: " + e.getMessage());
        }
    }

    private boolean validateInputs() {
        return  !firstname.getText().isEmpty() &&
                !lastname.getText().isEmpty() && !cardnumber.getText().isEmpty() &&
                !expirationdate.getText().isEmpty() && !pin.getText().isEmpty() &&
                !country.getText().isEmpty() && !billingaddress.getText().isEmpty() &&
                !postalcode.getText().isEmpty()&&!amount.getText().isEmpty();
    }
}
