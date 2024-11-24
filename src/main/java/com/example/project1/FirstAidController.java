package com.example.project1;

import com.example.project1.BLL.LoginClassCredentials;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstAidController extends UserMenuController implements RequiresSharedData {

    @FXML
    private VBox ChatVbox;
    @FXML
    private TextField UserTextField;
    @FXML
    private Button sendmessagebtn;
    @FXML
    private ScrollPane ChatScrollingPane;

    private PawCare pawCare;
    private LoginClassCredentials loginCredentials;

    private static final Logger LOGGER = Logger.getLogger(FirstAidController.class.getName());
    private HostServices hostServices;

    @FXML
    public void initialize() {
        super.initialize();
        sendmessagebtn.setOnAction(event -> handleSendMessage());
    }

    public void setSharedData(PawCare pawCare, LoginClassCredentials loginCredentials) {
        if (pawCare == null || loginCredentials == null) {
            LOGGER.warning("Shared data is null. Unable to proceed.");
        } else {
            this.pawCare = pawCare;
            this.loginCredentials = loginCredentials;
            LOGGER.info("Shared data set successfully: " + pawCare + ", " + loginCredentials);
        }
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    private void handleSendMessage() {
        String userMessage = UserTextField.getText().trim();
        if (!userMessage.isEmpty()) {
            addUserMessage(userMessage);
            UserTextField.clear();

            if (pawCare != null) {
                String chatbotResponse = pawCare.getFirstAidResponse(userMessage);
                addChatbotMessage(chatbotResponse);
            } else {
                LOGGER.warning("PawCare instance is null. Cannot fetch chatbot response.");
                addChatbotMessage("Sorry, I'm unable to assist right now.");
            }

            // Ensure the chat scrolls to the latest message
            ChatScrollingPane.setVvalue(1.0);
        }
    }

    private void addUserMessage(String message) {
        // Change alignment from "RIGHT" to "LEFT" for the user message
        Pane userPane = createMessageBubble(message, 300, "#E0E0E0", "LEFT");  // "LEFT" instead of "RIGHT"
        ChatVbox.getChildren().add(userPane);
    }

    private void addChatbotMessage(String message) {
        Pane botPane = createMessageBubble(message, 50, "#B3D9FF", "LEFT");
        ChatVbox.getChildren().add(botPane);
    }

    private Pane createMessageBubble(String message, double layoutX, String color, String alignment) {
        // Create a VBox to contain the text bubble and the hyperlink
        VBox messageContainer = new VBox();
        messageContainer.setAlignment(Pos.TOP_LEFT); // Default alignment

        // Adjust alignment based on the sender
        if ("RIGHT".equalsIgnoreCase(alignment)) {
            messageContainer.setAlignment(Pos.TOP_RIGHT);
        }

        // Create the text bubble
        Pane textBubble = new Pane();
        textBubble.setStyle("-fx-background-radius: 25; -fx-background-color: " + color + ";");
        double bubbleWidth = 100; // Adjusted width
        textBubble.setPrefWidth(bubbleWidth);

        // Regex for detecting URLs
        String urlPattern = "(http://|https://|www\\.)[\\w\\-\\.]+(?:\\.[a-z]{2,})+(?:/[^\\s]*)?";
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(message);

        int lastEnd = 0;
        Hyperlink hyperlink = null;

        // Create a TextFlow for the bubble's content
        javafx.scene.text.TextFlow textFlow = new javafx.scene.text.TextFlow();

        // Process the message to identify and handle URLs
        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                // Add non-URL text
                String beforeUrl = message.substring(lastEnd, matcher.start());
                Text normalText = new Text(beforeUrl);
                textFlow.getChildren().add(normalText);
            }

            // Add the URL as a clickable hyperlink
            String url = matcher.group();
            hyperlink = new Hyperlink(url);
            hyperlink.setOnAction(event -> {
                if (hostServices != null) {
                    hostServices.showDocument(url);
                } else {
                    try {
                        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
                    } catch (Exception e) {
                        LOGGER.warning("Failed to open URL: " + url);
                    }
                }
            });

            lastEnd = matcher.end();  // Update the last end position
        }

        // Add remaining text after the last URL
        if (lastEnd < message.length()) {
            String afterUrl = message.substring(lastEnd);
            Text normalText = new Text(afterUrl);
            textFlow.getChildren().add(normalText);
        }

        // Add the TextFlow to the bubble
        textBubble.getChildren().add(textFlow);

        // Wrap text in the bubble and adjust its height
        double bubbleHeight = textFlow.getBoundsInLocal().getHeight() + 20;
        textBubble.setPrefSize(240, bubbleHeight);

        // Add the bubble to the message container
        messageContainer.getChildren().add(textBubble);

        // Add the hyperlink below the bubble (if found)
        if (hyperlink != null) {
            VBox.setMargin(hyperlink, new javafx.geometry.Insets(5, 0, 0, 10)); // Add margin for proper spacing
            messageContainer.getChildren().add(hyperlink);
        }

        return messageContainer;
    }

}
