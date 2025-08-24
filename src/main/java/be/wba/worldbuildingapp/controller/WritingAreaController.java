package be.wba.worldbuildingapp.controller;

import be.wba.worldbuildingapp.dao.ChapterDao;
import be.wba.worldbuildingapp.dao.impl.ChapterDaoImpl;
import be.wba.worldbuildingapp.domain.Chapter;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.fxmisc.richtext.InlineCssTextArea;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class WritingAreaController {

    private Chapter chapter;
    private final ChapterDao chapterDao = new ChapterDaoImpl();

    @FXML
    private VBox editorContainer;

    @FXML
    private Label statusLabel;

    private final PauseTransition autoSaveDelay = new PauseTransition(Duration.seconds(1));

    private InlineCssTextArea writingArea;

    @FXML
    public void initialize() {
        ToolBar toolbar = new ToolBar();

        Button boldBtn = new Button("B");
        boldBtn.setOnAction(e -> toggleStyle("-fx-font-weight: bold;"));

        Button italicBtn = new Button("I");
        italicBtn.setOnAction(e -> toggleStyle("-fx-font-style: italic;"));

        Button underlineBtn = new Button("U");
        underlineBtn.setOnAction(e -> toggleStyle("-fx-underline: true;"));

        Button h1Btn = new Button("H1");
        h1Btn.setOnAction(e -> applyStyle("-fx-font-size: 24px; -fx-font-weight: bold;"));

        Button h2Btn = new Button("H2");
        h2Btn.setOnAction(e -> applyStyle("-fx-font-size: 20px; -fx-font-weight: bold;"));

        Button h3Btn = new Button("H3");
        h3Btn.setOnAction(e -> applyStyle("-fx-font-size: 16px; -fx-font-weight: bold;"));

        Button linkBtn = new Button("Link");
        linkBtn.setOnAction(e -> createHyperlink());

        toolbar.getItems().addAll(boldBtn, italicBtn, underlineBtn, h1Btn, h2Btn, h3Btn, linkBtn);

        writingArea = new InlineCssTextArea();
        writingArea.setWrapText(true);
        writingArea.setPrefHeight(600);
        writingArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                int currentParagraph = writingArea.getCurrentParagraph();

                if (currentParagraph > 0) {
                    String prev = writingArea.getParagraph(currentParagraph - 1).getText();
                    if (!prev.strip().isEmpty()) {
                        writingArea.insertText(writingArea.getCaretPosition(), "    ");
                    }
                } else {
                    writingArea.insertText(writingArea.getCaretPosition(), "    ");
                }
            }
        });

        writingArea.textProperty().addListener((obs, oldText, newText) -> {
            autoSaveDelay.playFromStart();
        });

        editorContainer.getChildren().addAll(toolbar, writingArea);
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;

        if (chapter.getContent() != null) {
            writingArea.replaceText(chapter.getContent());
        } else {
            writingArea.clear();
        }

        setupAutoSave();
    }

    private void setupAutoSave() {
        autoSaveDelay.setOnFinished(event -> {
            saveContent();
            showSavedMessage("Auto-saved");
        });
    }

    @FXML
    private void handleSave() {
        saveContent();
        showSavedMessage("Saved");
    }

    private void saveContent() {
        if (chapter != null) {
            String content = writingArea.getText();
            chapter.setContent(content);
            chapterDao.updateContent(chapter.getId(), content);
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

    private void toggleStyle(String style) {
        IndexRange selection = writingArea.getSelection();
        if (selection.getLength() == 0) return;

        String currentStyle = writingArea.getStyleAtPosition(selection.getStart());
        if (currentStyle != null && currentStyle.contains(style)) {
            writingArea.setStyle(selection.getStart(), selection.getEnd(), "");
        } else {
            writingArea.setStyle(selection.getStart(), selection.getEnd(), style);
        }
    }

    private void applyStyle(String style) {
        IndexRange selection = writingArea.getSelection();
        if (selection.getLength() == 0) return;
        writingArea.setStyle(selection.getStart(), selection.getEnd(), style);
    }

    private void createHyperlink() {
        IndexRange selection = writingArea.getSelection();
        if (selection.getLength() == 0) return;

        TextInputDialog dialog = new TextInputDialog("https://");
        dialog.setTitle("Insert Hyperlink");
        dialog.setHeaderText("Enter the URL:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(url -> {
            writingArea.setStyle(selection.getStart(), selection.getEnd(),
                    "-fx-fill: blue; -fx-underline: true;");
            System.out.println("Hyperlink (display only): " + url);
        });
    }
}
