package be.wba.worldbuildingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import be.wba.worldbuildingapp.dao.ChapterDao;
import be.wba.worldbuildingapp.dao.impl.ChapterDaoImpl;

import java.util.Optional;

public class ManuscriptController {
    private int projectId;
    private final ChapterDao chapterDao = new ChapterDaoImpl();

    @FXML
    private ListView<String> chapterList;

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void loadChapters() {
        chapterList.getItems().setAll(chapterDao.findByProjectId(projectId));
    }

    @FXML
    private void handleCreateChapter() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Chapter");
        dialog.setHeaderText("Create a New Chapter");
        dialog.setContentText("Chapter name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (chapterDao.save(projectId, name)) {
                chapterList.getItems().add(name);
            }
        });
    }
}
