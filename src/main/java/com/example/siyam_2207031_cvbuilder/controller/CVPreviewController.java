package com.cvbuilder.controller;

import com.cvbuilder.model.CV;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

/**
 * Controller for the CV Preview Screen
 */
public class CVPreviewController {

    @FXML
    private VBox cvContainer;
    @FXML
    private VBox headerSection;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label summaryLabel;
    @FXML
    private VBox experienceSection;
    @FXML
    private VBox educationSection;
    @FXML
    private VBox skillsSection;
    @FXML
    private VBox projectsSection;
    @FXML
    private Button editButton;
    @FXML
    private Button homeButton;

    private SceneManager sceneManager;
    private CV cv;

    @FXML
    public void initialize() {
        editButton.setOnAction(event -> sceneManager.showCVFormScene());
        homeButton.setOnAction(event -> sceneManager.showHomeScene());
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void loadCV(CV cv) {
        this.cv = cv;
        displayCV();
    }

    private void displayCV() {
        // Set personal information
        fullNameLabel.setText(cv.getFullName());
        emailLabel.setText("✉ " + cv.getEmail());
        phoneLabel.setText("☎ " + cv.getPhoneNumber());
        addressLabel.setText("📍 " + cv.getAddress());

        // Display Work Experience
        for (CV.Experience experience : cv.getExperiences()) {
            VBox experienceEntry = createExperienceEntry(experience);
            experienceSection.getChildren().add(experienceEntry);
        }

        // Display Education
        for (CV.Education education : cv.getEducations()) {
            VBox educationEntry = createEducationEntry(education);
            educationSection.getChildren().add(educationEntry);
        }

        // Display Skills
        if (!cv.getSkills().isEmpty()) {
            for (String skill : cv.getSkills()) {
                Label skillLabel = new Label("• " + skill);
                skillLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #34495E;");
                skillsSection.getChildren().add(skillLabel);
            }
        }

        // Display Projects
        for (CV.Project project : cv.getProjects()) {
            VBox projectEntry = createProjectEntry(project);
            projectsSection.getChildren().add(projectEntry);
        }
    }

    private VBox createExperienceEntry(CV.Experience experience) {
        VBox entry = new VBox(3);
        entry.setPadding(new Insets(10, 0, 10, 0));

        Label position = new Label(experience.getPosition());
        position.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        Label company = new Label(experience.getCompany());
        company.setStyle("-fx-font-size: 11; -fx-text-fill: #3498DB; -fx-font-weight: bold;");

        Label dates = new Label(experience.getStartDate() + " - " + experience.getEndDate());
        dates.setStyle("-fx-font-size: 10; -fx-text-fill: #7F8C8D; -fx-font-style: italic;");

        Label description = new Label(experience.getDescription());
        description.setStyle("-fx-font-size: 11; -fx-text-fill: #34495E;");
        description.setWrapText(true);

        entry.getChildren().addAll(position, company, dates, description);
        return entry;
    }

    private VBox createEducationEntry(CV.Education education) {
        VBox entry = new VBox(3);
        entry.setPadding(new Insets(10, 0, 10, 0));

        Label institution = new Label(education.getInstitution());
        institution.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        Label degree = new Label(education.getDegree() + " in " + education.getFieldOfStudy());
        degree.setStyle("-fx-font-size: 11; -fx-text-fill: #3498DB;");

        Label year = new Label("Graduation: " + education.getGraduationYear());
        year.setStyle("-fx-font-size: 10; -fx-text-fill: #7F8C8D; -fx-font-style: italic;");

        entry.getChildren().addAll(institution, degree, year);
        return entry;
    }

    private VBox createProjectEntry(CV.Project project) {
        VBox entry = new VBox(3);
        entry.setPadding(new Insets(10, 0, 10, 0));

        Label title = new Label(project.getTitle());
        title.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        Label technologies = new Label("Technologies: " + project.getTechnologies());
        technologies.setStyle("-fx-font-size: 10; -fx-text-fill: #3498DB; -fx-font-weight: bold;");

        Label description = new Label(project.getDescription());
        description.setStyle("-fx-font-size: 11; -fx-text-fill: #34495E;");
        description.setWrapText(true);

        entry.getChildren().addAll(title, technologies, description);
        return entry;
    }
}
