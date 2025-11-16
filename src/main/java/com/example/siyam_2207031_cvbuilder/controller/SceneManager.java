package com.cvbuilder.controller;

import com.cvbuilder.model.CV;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Manager class to handle scene transitions throughout the application
 */
public class SceneManager {
    private Stage primaryStage;
    private CVFormController cvFormController;
    private CVPreviewController cvPreviewController;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showHomeScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cvbuilder/fxml/home.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);

            HomeController controller = loader.getController();
            controller.setSceneManager(this);

            primaryStage.setScene(scene);
            primaryStage.setTitle("CV Builder - Home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCVFormScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cvbuilder/fxml/cvForm.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);

            cvFormController = loader.getController();
            cvFormController.setSceneManager(this);

            primaryStage.setScene(scene);
            primaryStage.setTitle("CV Builder - Create CV");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCVPreviewScene(CV cv) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cvbuilder/fxml/cvPreview.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);

            cvPreviewController = loader.getController();
            cvPreviewController.setSceneManager(this);
            cvPreviewController.loadCV(cv);

            primaryStage.setScene(scene);
            primaryStage.setTitle("CV Builder - Preview CV");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
