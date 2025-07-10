package be.wba.worldbuildingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class HomeController {

    @FXML
    private ListView<String> projectList;

    @FXML
    private void initialize() {
        // Temporary dummy data
        projectList.getItems().addAll("My Fantasy World", "Dawn of the Scion");
    }

    @FXML
    private void handleNewProject() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Create New Project");
        dialog.setContentText("Project name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> projectList.getItems().add(name));
    }

    @FXML
    private void handleOpenProject() {
        String selected = projectList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Opening project: " + selected);
            // Later: load project workspace
        }
    }
}