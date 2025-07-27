package be.wba.worldbuildingapp.controller;

import be.wba.worldbuildingapp.dao.ChapterDao;
import be.wba.worldbuildingapp.dao.impl.ChapterDaoImpl;
import be.wba.worldbuildingapp.domain.Chapter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ManuscriptController {
    private int projectId;
    private final ChapterDao chapterDao = new ChapterDaoImpl();

    @FXML
    private ListView<Chapter> chapterList;

    @FXML
    private ListView<Chapter> chapterListDuplicate;

    @FXML
    private StackPane manuscriptContentPane;

    @FXML
    private HBox browserView;

    @FXML
    private HBox editorView;

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void loadChapters() {
        List<Chapter> chapters = chapterDao.findByProjectId(projectId);
        chapterList.getItems().setAll(chapters);
        chapterListDuplicate.getItems().setAll(chapters);
    }

    @FXML
    private void initialize() {
        // Shared cell factory for consistent look
        ListCellFactory factory = new ListCellFactory();
        chapterList.setCellFactory(factory);
        chapterListDuplicate.setCellFactory(factory);

        // Set shared click handler
        chapterList.setOnMouseClicked(this::handleChapterClick);
        chapterListDuplicate.setOnMouseClicked(this::handleChapterClick);
    }

    private void handleChapterClick(MouseEvent event) {
        ListView<Chapter> sourceList = (ListView<Chapter>) event.getSource();
        Chapter selected = sourceList.getSelectionModel().getSelectedItem();
        if (selected != null && event.getClickCount() == 1) {
            // Sync selection across both lists
            chapterList.getSelectionModel().select(selected);
            chapterListDuplicate.getSelectionModel().select(selected);
            openEditor(selected);
        }
    }

    private void openEditor(Chapter chapter) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WritingArea.fxml"));
            Node editor = loader.load();
            WritingAreaController controller = loader.getController();
            controller.setChapter(chapter);

            manuscriptContentPane.getChildren().setAll(editor);

            browserView.setVisible(false);
            browserView.setManaged(false);
            editorView.setVisible(true);
            editorView.setManaged(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                loadChapters();
            }
        });
    }

    // Shared cell factory class
    private static class ListCellFactory implements javafx.util.Callback<ListView<Chapter>, ListCell<Chapter>> {
        @Override
        public ListCell<Chapter> call(ListView<Chapter> listView) {
            return new ListCell<>() {
                @Override
                protected void updateItem(Chapter item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getName());
                }
            };
        }
    }
}
