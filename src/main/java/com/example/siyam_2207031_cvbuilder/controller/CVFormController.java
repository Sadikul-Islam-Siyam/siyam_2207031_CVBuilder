package com.cvbuilder.controller;

import com.cvbuilder.model.CV;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


 //Controller for the CV Form Screen

public class CVFormController {

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;

    // Education Fields
    @FXML
    private TextField institutionField;
    @FXML
    private TextField degreeField;
    @FXML
    private TextField fieldOfStudyField;
    @FXML
    private TextField graduationYearField;
    @FXML
    private Button addEducationButton;
    @FXML
    private VBox educationListBox;

    // Experience Fields
    @FXML
    private TextField companyField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField startDateField;
    @FXML
    private TextField endDateField;
    @FXML
    private TextArea experienceDescriptionField;
    @FXML
    private Button addExperienceButton;
    @FXML
    private VBox experienceListBox;

    // Project Fields
    @FXML
    private TextField projectTitleField;
    @FXML
    private TextField projectTechnologiesField;
    @FXML
    private TextArea projectDescriptionField;
    @FXML
    private Button addProjectButton;
    @FXML
    private VBox projectListBox;

    // Skills
    @FXML
    private TextArea skillsField;

    // Buttons
    @FXML
    private Button backButton;
    @FXML
    private Button generateCVButton;

    private SceneManager sceneManager;
    private CV cv;

    @FXML
    public void initialize() {
        cv = new CV();

        addEducationButton.setOnAction(event -> addEducation());
        addExperienceButton.setOnAction(event -> addExperience());
        addProjectButton.setOnAction(event -> addProject());

        generateCVButton.setOnAction(event -> generateCV());
        backButton.setOnAction(event -> sceneManager.showHomeScene());
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    private void addEducation() {
        if (!validateEducationFields()) {
            showError("Validation Error", "Please fill all education fields");
            return;
        }

        CV.Education education = new CV.Education(
                institutionField.getText(),
                degreeField.getText(),
                fieldOfStudyField.getText(),
                graduationYearField.getText()
        );

        cv.getEducations().add(education);
        displayEducation(education);
        clearEducationFields();
        showInfo("Success", "Education added successfully!");
    }

    private void addExperience() {
        if (!validateExperienceFields()) {
            showError("Validation Error", "Please fill all experience fields");
            return;
        }

        CV.Experience experience = new CV.Experience(
                companyField.getText(),
                positionField.getText(),
                startDateField.getText(),
                endDateField.getText(),
                experienceDescriptionField.getText()
        );

        cv.getExperiences().add(experience);
        displayExperience(experience);
        clearExperienceFields();
        showInfo("Success", "Experience added successfully!");
    }

    private void addProject() {
        if (!validateProjectFields()) {
            showError("Validation Error", "Please fill all project fields");
            return;
        }

        CV.Project project = new CV.Project(
                projectTitleField.getText(),
                projectDescriptionField.getText(),
                projectTechnologiesField.getText()
        );

        cv.getProjects().add(project);
        displayProject(project);
        clearProjectFields();
        showInfo("Success", "Project added successfully!");
    }

    private void displayEducation(CV.Education education) {
        VBox educationCard = createEducationCard(education);
        educationListBox.getChildren().add(educationCard);
    }

    private void displayExperience(CV.Experience experience) {
        VBox experienceCard = createExperienceCard(experience);
        experienceListBox.getChildren().add(experienceCard);
    }

    private void displayProject(CV.Project project) {
        VBox projectCard = createProjectCard(project);
        projectListBox.getChildren().add(projectCard);
    }

    private VBox createEducationCard(CV.Education education) {
        VBox card = new VBox(5);
        card.setStyle("-fx-border-color: #ECF0F1; -fx-border-width: 1; -fx-background-color: #F8F9FA;");
        card.setPadding(new Insets(10));

        Label institution = new Label(education.getInstitution());
        institution.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        Label degree = new Label(education.getDegree() + " in " + education.getFieldOfStudy());
        Label year = new Label("Graduation: " + education.getGraduationYear());
        year.setStyle("-fx-text-fill: #7F8C8D; -fx-font-size: 10;");

        Button deleteBtn = new Button("Remove");
        deleteBtn.setOnAction(event -> {
            cv.getEducations().remove(education);
            educationListBox.getChildren().remove(card);
        });

        card.getChildren().addAll(institution, degree, year, deleteBtn);
        return card;
    }

    private VBox createExperienceCard(CV.Experience experience) {
        VBox card = new VBox(5);
        card.setStyle("-fx-border-color: #ECF0F1; -fx-border-width: 1; -fx-background-color: #F8F9FA;");
        card.setPadding(new Insets(10));

        Label position = new Label(experience.getPosition());
        position.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        Label company = new Label(experience.getCompany());
        Label dates = new Label(experience.getStartDate() + " - " + experience.getEndDate());
        dates.setStyle("-fx-text-fill: #7F8C8D; -fx-font-size: 10;");
        Label description = new Label(experience.getDescription());
        description.setWrapText(true);

        Button deleteBtn = new Button("Remove");
        deleteBtn.setOnAction(event -> {
            cv.getExperiences().remove(experience);
            experienceListBox.getChildren().remove(card);
        });

        card.getChildren().addAll(position, company, dates, description, deleteBtn);
        return card;
    }

    private VBox createProjectCard(CV.Project project) {
        VBox card = new VBox(5);
        card.setStyle("-fx-border-color: #ECF0F1; -fx-border-width: 1; -fx-background-color: #F8F9FA;");
        card.setPadding(new Insets(10));

        Label title = new Label(project.getTitle());
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        Label technologies = new Label("Tech: " + project.getTechnologies());
        technologies.setStyle("-fx-text-fill: #3498DB; -fx-font-size: 10;");
        Label description = new Label(project.getDescription());
        description.setWrapText(true);

        Button deleteBtn = new Button("Remove");
        deleteBtn.setOnAction(event -> {
            cv.getProjects().remove(project);
            projectListBox.getChildren().remove(card);
        });

        card.getChildren().addAll(title, technologies, description, deleteBtn);
        return card;
    }

    private void generateCV() {
        if (!validateAllRequired()) {
            showError("Validation Error", "Please fill all required fields and add at least one entry for Education and Work Experience");
            return;
        }

        // Set personal information
        cv.setFullName(fullNameField.getText());
        cv.setEmail(emailField.getText());
        cv.setPhoneNumber(phoneField.getText());
        cv.setAddress(addressField.getText());

        // Set skills
        String skillsText = skillsField.getText();
        if (!skillsText.trim().isEmpty()) {
            String[] skillsArray = skillsText.split(",");
            cv.getSkills().clear();
            for (String skill : skillsArray) {
                cv.getSkills().add(skill.trim());
            }
        }

        sceneManager.showCVPreviewScene(cv);
    }

    private boolean validateEducationFields() {
        return !institutionField.getText().trim().isEmpty() &&
                !degreeField.getText().trim().isEmpty() &&
                !fieldOfStudyField.getText().trim().isEmpty() &&
                !graduationYearField.getText().trim().isEmpty();
    }

    private boolean validateExperienceFields() {
        return !companyField.getText().trim().isEmpty() &&
                !positionField.getText().trim().isEmpty() &&
                !startDateField.getText().trim().isEmpty() &&
                !endDateField.getText().trim().isEmpty() &&
                !experienceDescriptionField.getText().trim().isEmpty();
    }

    private boolean validateProjectFields() {
        return !projectTitleField.getText().trim().isEmpty() &&
                !projectDescriptionField.getText().trim().isEmpty() &&
                !projectTechnologiesField.getText().trim().isEmpty();
    }

    private boolean validateAllRequired() {
        return !fullNameField.getText().trim().isEmpty() &&
                !emailField.getText().trim().isEmpty() &&
                !phoneField.getText().trim().isEmpty() &&
                !addressField.getText().trim().isEmpty() &&
                !cv.getEducations().isEmpty() &&
                !cv.getExperiences().isEmpty() &&
                !skillsField.getText().trim().isEmpty();
    }

    private void clearEducationFields() {
        institutionField.clear();
        degreeField.clear();
        fieldOfStudyField.clear();
        graduationYearField.clear();
    }

    private void clearExperienceFields() {
        companyField.clear();
        positionField.clear();
        startDateField.clear();
        endDateField.clear();
        experienceDescriptionField.clear();
    }

    private void clearProjectFields() {
        projectTitleField.clear();
        projectDescriptionField.clear();
        projectTechnologiesField.clear();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
