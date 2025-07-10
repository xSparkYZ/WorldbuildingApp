package be.wba.worldbuildingapp.controller;

import be.wba.worldbuildingapp.dao.ProjectDao;
import be.wba.worldbuildingapp.dao.impl.ProjectDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.util.List;
import java.util.Optional;

public class HomeController {

    @FXML
    private ListView<String> projectList;

    private final ProjectDao projectDao = new ProjectDaoImpl();

    @FXML
    private void initialize() {
        // Load projects from database
        List<String> projects = projectDao.findAll();
        projectList.getItems().addAll(projects);
    }

    @FXML
    private void handleNewProject() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Project");
        dialog.setHeaderText("Create a New Project");
        dialog.setContentText("Project name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            projectDao.save(name);
            projectList.getItems().add(0, name);
        });
    }
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleOpenProject() {
        String selected = projectList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Opening project: " + selected);
            // Future: Open workspace view
        }
    }
}
