package be.wba.worldbuildingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("Worldbuilding App");
        primaryStage.setScene(scene);

        // Start in full screen
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(""); // optional: disables the hint text
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
