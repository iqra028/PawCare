package com.example.project1;
import com.example.project1.BLL.LoginClassCredentials;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Parent;
import com.example.project1.BLL.PawCare;
import com.example.project1.BLL.RequiresSharedData;
import java.util.logging.Logger;



public class HelloApplication extends Application {
    public static HelloApplication instance;
    private Stage stage;
    PawCare pawCare;
    LoginClassCredentials loginCredentials;
    private static final Logger LOGGER = Logger.getLogger(HelloController.class.getName());


    @Override
    public void start(Stage primaryStage) throws IOException {
        pawCare = new PawCare();
        loginCredentials = new LoginClassCredentials();
        instance = this;
        this.stage = primaryStage;
        changeScene("hello-view.fxml");
    }

    public static HelloApplication getInstance() {
        return instance;
    }

    public void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Object controller = loader.getController();

        if (controller instanceof RequiresSharedData) {
            Platform.runLater(() -> {
                ((RequiresSharedData) controller).setSharedData(pawCare, loginCredentials);
            });
        } else {
            System.out.println("Error: Controller doesn't implement RequiresSharedData");
        }

        stage.setScene(new Scene(root, 1366, 768));
        stage.show();
    }






    public static void main(String[] args) {
        launch(args);
    }
}