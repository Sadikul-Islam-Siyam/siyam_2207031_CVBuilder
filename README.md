# siyam_2207031_CVBuilder
# CV Builder Application

A professional Curriculum Vitae (CV) Builder application built with JavaFX and Java 17 LTS.

## Features

- **Home Screen**: Welcome screen with "Create New CV" and "View All CVs" buttons
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
- **Database Integration**: SQLite database for persistent CV storage
- **CV Management**: View, edit, delete, and search all saved CVs
- **Asynchronous Operations**: Non-blocking UI with concurrent database operations
- **Observable Patterns**: Real-time UI updates using JavaFX properties

## Technology Stack

- **Java**: 17 LTS (Long Term Support)
- **JavaFX**: 21.0.5
- **Build Tool**: Maven
- **Architecture**: MVC (Model-View-Controller) with FXML
- **Database**: SQLite (JDBC 3.47.1.0)
- **Concurrency**: ExecutorService with CompletableFuture for async operations

## Project Structure

```
CVBuilder/
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java
│       │   └── com/
│       │       └── example/
│       │           └── siyam_2207031_cvbuilder/
│       │               ├── CVBuilderApp.java          # Main application entry point
│       │               ├── controller/
│       │               │   ├── HomeController.java    # Home screen controller
│       │               │   ├── CVFormController.java  # Form screen controller
│       │               │   ├── CVListController.java  # CV list view controller
│       │               │   ├── CVPreviewController.java # Preview screen controller
│       │               │   └── SceneManager.java      # Scene transition manager
│       │               ├── database/
│       │               │   ├── DatabaseManager.java   # Database connection manager
│       │               │   └── CVRepository.java      # CV CRUD operations
│       │               └── model/
│       │                   └── CV.java                # CV data model
│       └── resources/
│           └── com/
│               └── example/
│                   └── siyam_2207031_cvbuilder/
│                       ├── fxml/
│                       │   ├── home.fxml              # Home screen layout
│                       │   ├── cvForm.fxml            # Form screen layout
│                       │   ├── cvList.fxml            # CV list view layout
│                       │   └── cvPreview.fxml         # Preview screen layout
│                       └── css/
│                           └── styles.css             # Application styling
├── pom.xml                                            # Maven configuration
└── cvbuilder.db                                       # SQLite database (generated)
```

## Prerequisites

- **Java 17 LTS** or higher
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

### Creating a New CV
1. **Start the Application**: Launch the CV Builder application
2. **Click "Create New CV"**: Navigate to the form screen
3. **Fill in Personal Information**: Enter your basic details (all fields marked with * are required)
4. **Add Education Entries**: Fill in education fields and click "+ Add Education" for each qualification
5. **Add Work Experience**: Fill in experience fields and click "+ Add Experience" for each job
6. **Add Projects** (Optional): Fill in project details and click "+ Add Project"
7. **Enter Skills**: Provide a comma-separated list of your skills
8. **Generate CV**: Click "Generate CV" to save to database and see the preview
9. **Review**: View your professionally formatted CV
10. **Edit or Return Home**: Use the navigation buttons as needed

### Managing Saved CVs
1. **View All CVs**: Click "View All CVs" from home screen to see all saved CVs
2. **Search**: Type a name in the search box to filter CVs
3. **Edit CV**: Select a CV from the table and click "Edit CV" to modify
4. **Delete CV**: Select a CV and click "Delete CV" (confirmation required)
5. **Refresh**: Click "Refresh" to reload all CVs from database

## Design Highlights

- **Clean Layout**: Uses BorderPane, GridPane, VBox, and HBox for organized content
- **Professional Styling**: Custom CSS with a modern color scheme
- **Section Separators**: Clear visual divisions between CV sections
- **Responsive Design**: ScrollPane for handling large amounts of content
- **User Feedback**: Alert dialogs for validation errors and success messages

## Database Features

### SQLite Integration
- **Persistent Storage**: All CVs are automatically saved to a local SQLite database (`cvbuilder.db`)
- **Relational Design**: Properly normalized database with 5 tables:
  - `cv` - Main CV information with timestamps
  - `education` - Educational qualifications linked to CVs
  - `experience` - Work experience entries
  - `project` - Project details
  - `skill` - Individual skills
- **Thread-Safe Operations**: ReentrantReadWriteLock ensures safe concurrent database access
- **Transaction Support**: Atomic operations with commit/rollback capabilities

### CRUD Operations
- **Create**: Save new CVs asynchronously to database
- **Read**: Load all CVs or search by name
- **Update**: Edit existing CVs with real-time database synchronization
- **Delete**: Remove CVs with confirmation dialogs

### Observable Patterns
- **JavaFX Properties**: All CV fields use `StringProperty` for two-way data binding
- **ObservableList**: Real-time synchronization between database and TableView
- **Automatic Updates**: UI reflects database changes immediately without manual refresh

### Concurrency Features
- **Asynchronous Operations**: All database operations run in background threads
- **ExecutorService**: Thread pool (5 workers) for database operations
- **CompletableFuture**: Non-blocking async patterns with proper error handling
- **Platform.runLater()**: Safe UI updates from background threads
- **Concurrent Validation**: Parallel validation of form data

### CV List Management
- **TableView Display**: View all saved CVs in a sortable table
- **Search Functionality**: Filter CVs by name in real-time
- **Edit/Delete Operations**: Manage individual CV records
- **Status Indicators**: Real-time feedback on operations

## Validation

The application validates:
- All required personal information fields
- At least one education entry
- At least one work experience entry
- At least one skill entry



