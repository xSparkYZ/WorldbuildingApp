package be.wba.worldbuildingapp.controller;

import be.wba.worldbuildingapp.dao.ProjectDao;
import be.wba.worldbuildingapp.dao.impl.ProjectDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HomeController {

    @FXML
    private ListView<String> projectList;

    @FXML
    private ImageView backgroundImage;

    private final ProjectDao projectDao = new ProjectDaoImpl();

    @FXML
    private void initialize() {
        // Set fantasy background image
        backgroundImage.setImage(new Image(getClass().getResourceAsStream("/images/homebackground.png")));

        // Load project list
        projectList.getItems().addAll(projectDao.findAllProjectNames());
    }

    @FXML
    private void handleNewProject() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Create New Project");
        dialog.setContentText("Project name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (projectDao.save(name)) {
                projectList.getItems().add(name);
            } else {
                showAlert("Error", "Project could not be saved.");
            }
        });
    }

    @FXML
    private void handleOpenProject() {
        String selected = projectList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
                Parent root = loader.load();

                int projectId = projectDao.findIdByName(selected);

                DashboardController controller = loader.getController();
                controller.setProjectName(selected);
                controller.setProjectId(projectId);

                Stage stage = new Stage();
                stage.setTitle("Dashboard - " + selected);
                stage.setScene(new Scene(root));
                stage.setMaximized(true);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDeleteProject() {
        String selected = projectList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Are you sure you want to delete this project?");
            confirmation.setContentText("This action cannot be reversed.");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (projectDao.deleteByName(selected)) {
                    projectList.getItems().remove(selected);
                } else {
                    showAlert("Error", "Project could not be deleted from the database.");
                }
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(title);
        error.setHeaderText(null);
        error.setContentText(message);
        error.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
