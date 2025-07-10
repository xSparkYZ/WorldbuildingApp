package be.wba.worldbuildingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file from resources/view/HomeScreen.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage.fxml"));
        Parent root = fxmlLoader.load();

        // Set up the main window
        primaryStage.setTitle("Worldbuilding App");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
