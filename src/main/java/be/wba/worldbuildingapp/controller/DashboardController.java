package be.wba.worldbuildingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label projectNameLabel;

    @FXML
    private StackPane mainContentPane;

    @FXML
    private void handleSaveAndExit() {
        // TODO: Add save logic later
        // Close this window and return to Home (project list)
        Label label = projectNameLabel; // Just to ensure projectNameLabel is initialized (avoids warnings)
        label.getScene().getWindow().hide(); // Closes the Dashboard window
    }

    public void setProjectName(String name) {
        projectNameLabel.setText(name);
    }

    @FXML
    private void openManuscriptView() {
        loadContent("/view/Manuscript.fxml");
    }

    @FXML
    private void openEncyclopediaView() {
        loadContent("/view/Encyclopedia.fxml");
    }

    @FXML
    private void openMapsView() {
        loadContent("/view/Maps.fxml");
    }

    @FXML
    private void openSettingsView() {
        loadContent("/view/Settings.fxml");
    }

    private void loadContent(String fxmlPath) {
        try {
            Node content = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainContentPane.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
