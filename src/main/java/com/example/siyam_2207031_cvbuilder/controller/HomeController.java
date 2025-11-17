package com.example.siyam_2207031_cvbuilder.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


 //Controller for the Home Screen

public class HomeController {

    @FXML
    private Button createNewCVButton;

    private SceneManager sceneManager;

    @FXML
    public void initialize() {
        createNewCVButton.setOnAction(event -> sceneManager.showCVFormScene());
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
