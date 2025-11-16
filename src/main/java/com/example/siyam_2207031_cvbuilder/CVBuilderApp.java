package com.cvbuilder;

import com.cvbuilder.controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;


 // Main entry point for the CV Builder Application

public class CVBuilderApp extends Application {

    private SceneManager sceneManager;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CV Builder");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        // Initialize scene manager
        sceneManager = new SceneManager(primaryStage);

        // Show home scene
        sceneManager.showHomeScene();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
