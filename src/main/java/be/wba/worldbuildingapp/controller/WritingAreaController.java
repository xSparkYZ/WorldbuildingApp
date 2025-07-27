package be.wba.worldbuildingapp.controller;

import be.wba.worldbuildingapp.dao.ChapterDao;
import be.wba.worldbuildingapp.dao.impl.ChapterDaoImpl;
import be.wba.worldbuildingapp.domain.Chapter;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

public class WritingAreaController {

    private Chapter chapter;
    private final ChapterDao chapterDao = new ChapterDaoImpl();

    @FXML
    private TextArea textArea;

    @FXML
    private Button saveButton;

    @FXML
    private Label statusLabel;

    private final PauseTransition autoSaveDelay = new PauseTransition(Duration.seconds(1));

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;

        if (chapter.getContent() != null) {
            textArea.setText(chapter.getContent());
        } else {
            textArea.clear();
        }

        setupAutoSave();
    }

    @FXML
    private void handleSave() {
        saveContent();
        showSavedMessage("Saved");
    }

    private void setupAutoSave() {
        autoSaveDelay.setOnFinished(event -> {
            saveContent();
            showSavedMessage("Auto-saved");
        });

        textArea.textProperty().addListener((obs, oldText, newText) -> {
            autoSaveDelay.playFromStart();
        });
    }

    private void saveContent() {
        if (chapter != null) {
            String newContent = textArea.getText();
            chapter.setContent(newContent);
            chapterDao.updateContent(chapter.getId(), newContent);
        }
    }

    private void showSavedMessage(String message) {
        statusLabel.setText(message);
        statusLabel.setOpacity(1.0);

        FadeTransition fade = new FadeTransition(Duration.seconds(1), statusLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setDelay(Duration.seconds(1));
        fade.play();
    }
}
