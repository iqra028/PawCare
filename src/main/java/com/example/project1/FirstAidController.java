package com.example.project1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirstAidController extends UserMenuController {

    @FXML
    private VBox ChatVbox;
    @FXML
    private TextField UserTextField;
    @FXML
    private Button sendmessagebtn;
    @FXML
    private ScrollPane ChatScrollingPane;

    private static final Logger LOGGER = Logger.getLogger(com.example.project1.FirstAidController.class.getName());

    @FXML
    public void initialize() {
        super.initialize();

        sendmessagebtn.setOnAction(event -> handleSendMessage());
    }


    @FXML
    private void handleSendMessage() {
        String userMessage = UserTextField.getText().trim();

        if (!userMessage.isEmpty()) {
            addUserMessage(userMessage);
            UserTextField.clear();

            String chatbotResponse = getChatbotResponse(userMessage);
            addChatbotMessage(chatbotResponse);

            ChatScrollingPane.vvalueProperty().bind(ChatVbox.heightProperty());
        }
    }

    private void addUserMessage(String message) {
        Pane userPane = new Pane();
        userPane.setPrefSize(640, 66);

        Pane userTextBubble = new Pane();
        userTextBubble.setLayoutX(216);
        userTextBubble.setLayoutY(14);
        userTextBubble.setPrefSize(410, 45);
        userTextBubble.setStyle("-fx-background-radius: 25; -fx-background-color: #E0E0E0;");

        Text userText = new Text(message);
        userText.setWrappingWidth(349);
        userText.setLayoutX(20);
        userText.setLayoutY(27);

        userTextBubble.getChildren().add(userText);
        userPane.getChildren().add(userTextBubble);
        ChatVbox.getChildren().add(userPane);
    }

    private void addChatbotMessage(String message) {
        Pane botPane = new Pane();
        botPane.setPrefSize(640, 66);

        Pane botTextBubble = new Pane();
        botTextBubble.setLayoutX(64);
        botTextBubble.setLayoutY(11);
        botTextBubble.setPrefSize(410, 45);
        botTextBubble.setStyle("-fx-background-radius: 25; -fx-background-color: #E0E0E0;");

        Text botText = new Text(message);
        botText.setWrappingWidth(349);
        botText.setLayoutX(20);
        botText.setLayoutY(27);

        botTextBubble.getChildren().add(botText);
        botPane.getChildren().add(botTextBubble);
        ChatVbox.getChildren().add(botPane);
    }

    private String getChatbotResponse(String userMessage) {

        return "This is a sample response to: " + userMessage;
    }
}
