package com.example.siyam_2207031_cvbuilder.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.*;

import java.sql.Timestamp;

/**
 * Model class to represent and store CV data
 * Supports database persistence and Observable properties for JavaFX
 */
public class CV {
    // Database fields
    private long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Observable properties for JavaFX data binding
    private final StringProperty fullName = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty phoneNumber = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty profilePhoto = new SimpleStringProperty();

    private ObservableList<Education> educations;
    private ObservableList<Experience> experiences;
    private ObservableList<Project> projects;
    private ObservableList<String> skills;

    public CV() {
        this.educations = FXCollections.observableArrayList();
        this.experiences = FXCollections.observableArrayList();
        this.projects = FXCollections.observableArrayList();
        this.skills = FXCollections.observableArrayList();
    }

    // Database field getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Personal Information Getters & Setters with Observable Property Support
    public String getFullName() {
        return fullName.get();
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getProfilePhoto() {
        return profilePhoto.get();
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto.set(profilePhoto);
    }

    public StringProperty profilePhotoProperty() {
        return profilePhoto;
    }

    // Collections Getters
    public ObservableList<Education> getEducations() {
        return educations;
    }

    public ObservableList<Experience> getExperiences() {
        return experiences;
    }

    public ObservableList<Project> getProjects() {
        return projects;
    }

    public ObservableList<String> getSkills() {
        return skills;
    }

    // Validation method
    public boolean isValid() {
        return getFullName() != null && !getFullName().trim().isEmpty() &&
                getEmail() != null && !getEmail().trim().isEmpty() &&
                getPhoneNumber() != null && !getPhoneNumber().trim().isEmpty() &&
                getAddress() != null && !getAddress().trim().isEmpty() &&
                !educations.isEmpty() &&
                !skills.isEmpty() &&
                !experiences.isEmpty();
    }

    @Override
    public String toString() {
        return "CV{" +
                "id=" + id +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }

    /**
     * Inner class for Education entries
     */
    public static class Education {
        private String institution;
        private String degree;
        private String fieldOfStudy;
        private String graduationYear;

        public Education(String institution, String degree, String fieldOfStudy, String graduationYear) {
            this.institution = institution;
            this.degree = degree;
            this.fieldOfStudy = fieldOfStudy;
            this.graduationYear = graduationYear;
        }

        public String getInstitution() {
            return institution;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getFieldOfStudy() {
            return fieldOfStudy;
        }

        public void setFieldOfStudy(String fieldOfStudy) {
            this.fieldOfStudy = fieldOfStudy;
        }

        public String getGraduationYear() {
            return graduationYear;
        }

        public void setGraduationYear(String graduationYear) {
            this.graduationYear = graduationYear;
        }
    }

    /**
     * Inner class for Work Experience entries
     */
    public static class Experience {
        private String company;
        private String position;
        private String startDate;
        private String endDate;
        private String description;

        public Experience(String company, String position, String startDate, String endDate, String description) {
            this.company = company;
            this.position = position;
            this.startDate = startDate;
            this.endDate = endDate;
            this.description = description;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    /**
     * Inner class for Project entries
     */
    public static class Project {
        private String title;
        private String description;
        private String technologies;

        public Project(String title, String description, String technologies) {
            this.title = title;
            this.description = description;
            this.technologies = technologies;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTechnologies() {
            return technologies;
        }

        public void setTechnologies(String technologies) {
            this.technologies = technologies;
        }
    }
}
