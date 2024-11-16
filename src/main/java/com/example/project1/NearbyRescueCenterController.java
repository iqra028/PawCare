package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class NearbyRescueCenterController extends UserMenuController{


    @FXML
    private Button FirstAid;

    @FXML
    private VBox rescueCentersContainer; // all the pane's will stack in this like if u add another
    // rescue center nearby, it will stack below the first rescue center nearby

    @FXML
    private Pane btnRescueCenters1;

    @FXML
    private Label PaneLabel;

    @FXML
    private Button Panebutton;

    @FXML
    public void initialize() {
        super.initialize();
        FirstAid.setOnAction(event -> handleFirstAid());
        Panebutton.setOnAction(event -> handleSubmitRequest());

    }

    private void handleFirstAid() {
        // Add your logic here
    }

    private void handleSubmitRequest() {
        // Add your logic here for handling submission
    }

    public void addNewRescueCenter(String centerName) 
    {
        // Create a new Pane dynamically with a label and button

//        Pane newPane = new Pane();
//        newPane.setStyle("-fx-background-color: #EEB673;");
//        Label newLabel = new Label(centerName);
//        newLabel.setStyle("-fx-font-size: 24px;");
//        Button newButton = new Button("Submit");
//        newButton.setStyle("-fx-background-color: #D08122;");
//
//        newPane.getChildren().addAll(newLabel, newButton);
//        rescueCentersContainer.getChildren().add(newPane);
//
//        newButton.setOnAction(event -> handleSubmitRequest());
    }
}


