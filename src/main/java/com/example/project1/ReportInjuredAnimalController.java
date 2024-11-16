package com.example.project1;

import com.example.project1.BLL.GeoLocation;
import com.example.project1.BLL.PawCare;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ReportInjuredAnimalController extends UserMenuController
{
    @FXML
    private Button btnSubmit;
    @FXML
    private TextField AnimalType;
    @FXML
    private TextField Breed;
    @FXML
    private TextField InjuryDesc;
    @FXML
   private WebView webView;
    private PawCare pawCare;
    private static final Logger LOGGER = Logger.getLogger(com.example.project1.ReportInjuredAnimalController.class.getName());

    @FXML
    public void initialize() {
        super.initialize();
        pawCare = new PawCare();
        String mapHtml=pawCare.generatemap();

        WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.loadContent(mapHtml);
    }
    @FXML
    private void NextPage(){
        try {
            HelloApplication.getInstance().changeScene("NearbyRescueCenters.fxml");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to change scene to Select-UserType.fxml", e);
        }
    }

    @FXML
    private void onSubmitClick() {
        String animalType = AnimalType.getText();
        String breed = Breed.getText();
        String InjuryDescription = InjuryDesc.getText();


        if (animalType.isEmpty() || InjuryDescription.isEmpty() || breed.isEmpty()) {
            showAlert("Error", "Missing Fields", "Please fill out all fields before submitting.");
        } else {
            NextPage();
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}