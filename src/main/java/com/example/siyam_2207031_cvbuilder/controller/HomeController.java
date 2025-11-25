package com.example.siyam_2207031_cvbuilder.controller;

import com.example.siyam_2207031_cvbuilder.database.CVRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller for the Home Screen with enhanced features
 * Supports JSON import/export and database operations
 */
public class HomeController {

    @FXML
    private Button createNewCVButton;
    @FXML
    private Button viewAllCVsButton;

    private SceneManager sceneManager;

    @FXML
    public void initialize() {
        createNewCVButton.setOnAction(event -> sceneManager.showCVFormScene());
        viewAllCVsButton.setOnAction(event -> sceneManager.showCVListScene());
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
