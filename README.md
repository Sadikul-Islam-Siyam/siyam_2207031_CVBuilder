# siyam_2207031_CVBuilder
# CV Builder Application

A professional Curriculum Vitae (CV) Builder application built with JavaFX and Java 21 LTS.

## Features

- **Home Screen**: Welcome screen with "Create New CV" button
- **CV Form**: Interactive form for entering:
    - Personal information (Full Name, Email, Phone, Address)
    - Educational qualifications (multiple entries supported)
    - Work experience (multiple entries supported)
    - Projects (multiple entries supported)
    - Skills (comma-separated list)
- **CV Preview**: Professionally formatted CV display with clean layout
- **Navigation**: Seamless transitions between screens
- **Validation**: Error checking for required fields
- **Responsive Design**: Clean, modern UI with custom CSS styling

## Technology Stack

- **Java**: 21 LTS (Long Term Support)
- **JavaFX**: 21.0.5
- **Build Tool**: Maven
- **Architecture**: MVC (Model-View-Controller) with FXML

## Project Structure

```
CVBuilder/
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java
│       │   └── com/
│       │       └── cvbuilder/
│       │           ├── CVBuilderApp.java          # Main application entry point
│       │           ├── controller/
│       │           │   ├── HomeController.java    # Home screen controller
│       │           │   ├── CVFormController.java  # Form screen controller
│       │           │   ├── CVPreviewController.java # Preview screen controller
│       │           │   └── SceneManager.java      # Scene transition manager
│       │           └── model/
│       │               └── CV.java                # CV data model
│       └── resources/
│           └── com/
│               └── cvbuilder/
│                   ├── fxml/
│                   │   ├── home.fxml              # Home screen layout
│                   │   ├── cvForm.fxml            # Form screen layout
│                   │   └── cvPreview.fxml         # Preview screen layout
│                   └── css/
│                       └── styles.css             # Application styling
└── pom.xml                                        # Maven configuration
```

## Prerequisites

- **Java 21 LTS** or higher
- **Maven 3.6+** or higher

## Building and Running

### Using Maven

1. **Clean and compile the project:**
   ```bash
   mvn clean compile
   ```

2. **Run the application:**
   ```bash
   mvn javafx:run
   ```

3. **Package as JAR:**
   ```bash
   mvn clean package
   ```

### Running the JAR

After packaging, you can run the application using:
```bash
java -jar target/cv-builder-1.0.0.jar
```

## Usage Guide

1. **Start the Application**: Launch the CV Builder application
2. **Click "Create New CV"**: Navigate to the form screen
3. **Fill in Personal Information**: Enter your basic details (all fields marked with * are required)
4. **Add Education Entries**: Fill in education fields and click "+ Add Education" for each qualification
5. **Add Work Experience**: Fill in experience fields and click "+ Add Experience" for each job
6. **Add Projects** (Optional): Fill in project details and click "+ Add Project"
7. **Enter Skills**: Provide a comma-separated list of your skills
8. **Generate CV**: Click "Generate CV" to see the preview
9. **Review**: View your professionally formatted CV
10. **Edit or Return Home**: Use the navigation buttons as needed

## Design Highlights

- **Clean Layout**: Uses BorderPane, GridPane, VBox, and HBox for organized content
- **Professional Styling**: Custom CSS with a modern color scheme
- **Section Separators**: Clear visual divisions between CV sections
- **Responsive Design**: ScrollPane for handling large amounts of content
- **User Feedback**: Alert dialogs for validation errors and success messages

## Validation

The application validates:
- All required personal information fields
- At least one education entry
- At least one work experience entry
- At least one skill entry



