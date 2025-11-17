package com.cvbuilder.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model class to represent and store CV data
 */
public class CV {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String profilePhoto;

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

    // Personal Information Getters & Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
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
        return fullName != null && !fullName.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                phoneNumber != null && !phoneNumber.trim().isEmpty() &&
                address != null && !address.trim().isEmpty() &&
                !educations.isEmpty() &&
                !skills.isEmpty() &&
                !experiences.isEmpty();
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
