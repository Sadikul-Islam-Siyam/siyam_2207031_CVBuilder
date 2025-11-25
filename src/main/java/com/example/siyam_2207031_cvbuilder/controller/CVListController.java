package com.example.siyam_2207031_cvbuilder.controller;

import com.example.siyam_2207031_cvbuilder.database.CVRepository;
import com.example.siyam_2207031_cvbuilder.model.CV;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;

/**
 * Controller for CV List View with Observable patterns
 * Demonstrates JavaFX TableView with ObservableList for real-time updates
 */
public class CVListController {

    @FXML
    private TableView<CV> cvTableView;
    @FXML
    private TableColumn<CV, Long> idColumn;
    @FXML
    private TableColumn<CV, String> nameColumn;
    @FXML
    private TableColumn<CV, String> emailColumn;
    @FXML
    private TableColumn<CV, String> phoneColumn;
    @FXML
    private TableColumn<CV, Timestamp> createdAtColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button viewButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button backButton;
    @FXML
    private Label statusLabel;

    private SceneManager sceneManager;
    private CVRepository cvRepository;
    private ObservableList<CV> cvObservableList;

    @FXML
    public void initialize() {
        cvRepository = CVRepository.getInstance();
        cvObservableList = cvRepository.getObservableList();

        // Setup table columns with property binding
        setupTableColumns();

        // Bind the ObservableList to the TableView
        cvTableView.setItems(cvObservableList);

        // Setup button actions
        setupButtons();

        // Load initial data asynchronously
        loadAllCVsAsync();

        // Enable multiple selection
        cvTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Setup table columns with property value factories
     */
    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        // Use property binding for observable updates
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        
        // Format timestamp column
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        createdAtColumn.setCellFactory(column -> new TableCell<CV, Timestamp>() {
            private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            
            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(format.format(item));
                }
            }
        });
    }

    /**
     * Setup button actions
     */
    private void setupButtons() {
        searchButton.setOnAction(event -> searchCVs());
        refreshButton.setOnAction(event -> refreshList());
        viewButton.setOnAction(event -> viewSelectedCV());
        editButton.setOnAction(event -> editSelectedCV());
        deleteButton.setOnAction(event -> deleteSelectedCV());
        backButton.setOnAction(event -> sceneManager.showHomeScene());
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Load all CVs asynchronously using concurrency
     */
    private void loadAllCVsAsync() {
        updateStatus("Loading CVs...");
        
        cvRepository.refreshObservableListAsync()
            .thenRun(() -> Platform.runLater(() -> {
                updateStatus("Loaded " + cvObservableList.size() + " CVs");
            }))
            .exceptionally(throwable -> {
                Platform.runLater(() -> {
                    showError("Load Error", "Failed to load CVs: " + throwable.getMessage());
                    updateStatus("Error loading CVs");
                });
                return null;
            });
    }

    /**
     * Search CVs by name asynchronously
     */
    private void searchCVs() {
        String searchTerm = searchField.getText().trim();
        
        if (searchTerm.isEmpty()) {
            refreshList();
            return;
        }

        updateStatus("Searching...");
        
        cvRepository.searchByNameAsync(searchTerm)
            .thenAccept(results -> Platform.runLater(() -> {
                cvObservableList.clear();
                cvObservableList.addAll(results);
                updateStatus("Found " + results.size() + " CVs");
            }))
            .exceptionally(throwable -> {
                Platform.runLater(() -> {
                    showError("Search Error", "Failed to search CVs: " + throwable.getMessage());
                    updateStatus("Search failed");
                });
                return null;
            });
    }

    /**
     * Refresh the list
     */
    private void refreshList() {
        searchField.clear();
        loadAllCVsAsync();
    }

    /**
     * View selected CV
     */
    private void viewSelectedCV() {
        CV selectedCV = cvTableView.getSelectionModel().getSelectedItem();
        
        if (selectedCV == null) {
            showWarning("No Selection", "Please select a CV to view");
            return;
        }

        sceneManager.showCVPreviewScene(selectedCV);
    }

    /**
     * Edit selected CV
     */
    private void editSelectedCV() {
        CV selectedCV = cvTableView.getSelectionModel().getSelectedItem();
        
        if (selectedCV == null) {
            showWarning("No Selection", "Please select a CV to edit");
            return;
        }

        sceneManager.showCVFormScene(selectedCV);
    }

    /**
     * Delete selected CV with confirmation
     */
    private void deleteSelectedCV() {
        CV selectedCV = cvTableView.getSelectionModel().getSelectedItem();
        
        if (selectedCV == null) {
            showWarning("No Selection", "Please select a CV to delete");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete CV: " + selectedCV.getFullName());
        confirmAlert.setContentText("Are you sure you want to delete this CV? This action cannot be undone.");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            updateStatus("Deleting CV...");
            
            cvRepository.deleteAsync(selectedCV.getId())
                .thenRun(() -> Platform.runLater(() -> {
                    showInfo("Success", "CV deleted successfully");
                    updateStatus("CV deleted");
                }))
                .exceptionally(throwable -> {
                    Platform.runLater(() -> {
                        showError("Delete Error", "Failed to delete CV: " + throwable.getMessage());
                        updateStatus("Delete failed");
                    });
                    return null;
                });
        }
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
