package be.wba.worldbuildingapp.controller;

import be.wba.worldbuildingapp.controller.ManuscriptController;
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

    private int currentProjectId;

    public void setProjectName(String name) {
        projectNameLabel.setText(name);
    }

    public void setProjectId(int id) {
        this.currentProjectId = id;
    }

    @FXML
    private void handleSaveAndExit() {
        Label label = projectNameLabel;
        label.getScene().getWindow().hide();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node content = loader.load();
            mainContentPane.getChildren().setAll(content);

            if (fxmlPath.contains("Manuscript.fxml")) {
                ManuscriptController controller = loader.getController();
                controller.setProjectId(currentProjectId);
                controller.loadChapters(); // Optional: can be removed if loading happens later
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}