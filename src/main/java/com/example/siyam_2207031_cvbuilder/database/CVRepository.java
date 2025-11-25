package com.example.siyam_2207031_cvbuilder.database;

import com.example.siyam_2207031_cvbuilder.model.CV;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Repository class for CV database operations with concurrency support
 * Uses ExecutorService for asynchronous operations and CompletableFuture for async patterns
 */
public class CVRepository {
    private static CVRepository instance;
    private final DatabaseManager dbManager;
    private final ExecutorService executorService;
    private final ObservableList<CV> cvObservableList;

    private CVRepository() {
        this.dbManager = DatabaseManager.getInstance();
        // Create a thread pool with 5 threads for concurrent operations
        this.executorService = Executors.newFixedThreadPool(5, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("CV-Repository-Thread");
            return thread;
        });
        this.cvObservableList = FXCollections.observableArrayList();
    }

    /**
     * Get singleton instance
     */
    public static synchronized CVRepository getInstance() {
        if (instance == null) {
            instance = new CVRepository();
        }
        return instance;
    }

    /**
     * Get observable list of CVs (for JavaFX bindings)
     */
    public ObservableList<CV> getObservableList() {
        return cvObservableList;
    }

    /**
     * Save CV asynchronously using CompletableFuture
     */
    public CompletableFuture<Long> saveAsync(CV cv) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return save(cv);
            } catch (SQLException e) {
                throw new CompletionException("Failed to save CV", e);
            }
        }, executorService);
    }

    /**
     * Save CV to database (synchronous)
     */
    public long save(CV cv) throws SQLException {
        dbManager.beginTransaction();
        try {
            // Insert CV main record
            String cvQuery = """
                INSERT INTO cv (full_name, email, phone_number, address, profile_photo)
                VALUES (?, ?, ?, ?, ?)
            """;
            long cvId = dbManager.executeInsert(cvQuery, 
                cv.getFullName(), cv.getEmail(), cv.getPhoneNumber(), 
                cv.getAddress(), cv.getProfilePhoto());
            
            cv.setId(cvId);

            // Insert educations
            saveEducations(cvId, cv.getEducations());

            // Insert experiences
            saveExperiences(cvId, cv.getExperiences());

            // Insert projects
            saveProjects(cvId, cv.getProjects());

            // Insert skills
            saveSkills(cvId, cv.getSkills());

            dbManager.commitTransaction();

            // Update observable list on JavaFX thread
            Platform.runLater(() -> cvObservableList.add(cv));

            return cvId;
        } catch (SQLException e) {
            dbManager.rollbackTransaction();
            throw e;
        }
    }

    /**
     * Update CV asynchronously
     */
    public CompletableFuture<Void> updateAsync(CV cv) {
        return CompletableFuture.runAsync(() -> {
            try {
                update(cv);
            } catch (SQLException e) {
                throw new CompletionException("Failed to update CV", e);
            }
        }, executorService);
    }

    /**
     * Update CV in database
     */
    public void update(CV cv) throws SQLException {
        dbManager.beginTransaction();
        try {
            // Update CV main record
            String cvQuery = """
                UPDATE cv SET full_name = ?, email = ?, phone_number = ?, 
                address = ?, profile_photo = ?, updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
            """;
            dbManager.executeUpdate(cvQuery, 
                cv.getFullName(), cv.getEmail(), cv.getPhoneNumber(), 
                cv.getAddress(), cv.getProfilePhoto(), cv.getId());

            // Delete and reinsert related records
            deleteRelatedRecords(cv.getId());
            saveEducations(cv.getId(), cv.getEducations());
            saveExperiences(cv.getId(), cv.getExperiences());
            saveProjects(cv.getId(), cv.getProjects());
            saveSkills(cv.getId(), cv.getSkills());

            dbManager.commitTransaction();

            // Update observable list on JavaFX thread
            Platform.runLater(() -> {
                int index = findCVIndex(cv.getId());
                if (index >= 0) {
                    cvObservableList.set(index, cv);
                }
            });
        } catch (SQLException e) {
            dbManager.rollbackTransaction();
            throw e;
        }
    }

    /**
     * Delete CV asynchronously
     */
    public CompletableFuture<Void> deleteAsync(long cvId) {
        return CompletableFuture.runAsync(() -> {
            try {
                delete(cvId);
            } catch (SQLException e) {
                throw new CompletionException("Failed to delete CV", e);
            }
        }, executorService);
    }

    /**
     * Delete CV from database
     */
    public void delete(long cvId) throws SQLException {
        String query = "DELETE FROM cv WHERE id = ?";
        dbManager.executeUpdate(query, cvId);

        // Update observable list on JavaFX thread
        Platform.runLater(() -> {
            int index = findCVIndex(cvId);
            if (index >= 0) {
                cvObservableList.remove(index);
            }
        });
    }

    /**
     * Find CV by ID asynchronously
     */
    public CompletableFuture<CV> findByIdAsync(long cvId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return findById(cvId);
            } catch (SQLException e) {
                throw new CompletionException("Failed to find CV", e);
            }
        }, executorService);
    }

    /**
     * Find CV by ID
     */
    public CV findById(long cvId) throws SQLException {
        String query = "SELECT * FROM cv WHERE id = ?";
        ResultSet rs = dbManager.executeQuery(query, cvId);
        
        try {
            if (rs.next()) {
                CV cv = extractCVFromResultSet(rs);
                loadRelatedData(cv);
                return cv;
            }
            return null;
        } finally {
            dbManager.releaseReadLock();
        }
    }

    /**
     * Find all CVs asynchronously
     */
    public CompletableFuture<List<CV>> findAllAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return findAll();
            } catch (SQLException e) {
                throw new CompletionException("Failed to load CVs", e);
            }
        }, executorService);
    }

    /**
     * Find all CVs
     */
    public List<CV> findAll() throws SQLException {
        List<CV> cvs = new ArrayList<>();
        String query = "SELECT * FROM cv ORDER BY created_at DESC";
        ResultSet rs = dbManager.executeQuery(query);
        
        try {
            while (rs.next()) {
                CV cv = extractCVFromResultSet(rs);
                loadRelatedData(cv);
                cvs.add(cv);
            }
            return cvs;
        } finally {
            dbManager.releaseReadLock();
        }
    }

    /**
     * Refresh observable list from database asynchronously
     */
    public CompletableFuture<Void> refreshObservableListAsync() {
        return findAllAsync().thenAccept(cvs -> 
            Platform.runLater(() -> {
                cvObservableList.clear();
                cvObservableList.addAll(cvs);
            })
        );
    }

    /**
     * Search CVs by name asynchronously
     */
    public CompletableFuture<List<CV>> searchByNameAsync(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return searchByName(name);
            } catch (SQLException e) {
                throw new CompletionException("Failed to search CVs", e);
            }
        }, executorService);
    }

    /**
     * Search CVs by name
     */
    public List<CV> searchByName(String name) throws SQLException {
        List<CV> cvs = new ArrayList<>();
        String query = "SELECT * FROM cv WHERE full_name LIKE ? ORDER BY created_at DESC";
        ResultSet rs = dbManager.executeQuery(query, "%" + name + "%");
        
        try {
            while (rs.next()) {
                CV cv = extractCVFromResultSet(rs);
                loadRelatedData(cv);
                cvs.add(cv);
            }
            return cvs;
        } finally {
            dbManager.releaseReadLock();
        }
    }

    // Private helper methods

    private void saveEducations(long cvId, List<CV.Education> educations) throws SQLException {
        String query = """
            INSERT INTO education (cv_id, institution, degree, field_of_study, graduation_year)
            VALUES (?, ?, ?, ?, ?)
        """;
        for (CV.Education edu : educations) {
            dbManager.executeUpdate(query, cvId, edu.getInstitution(), 
                edu.getDegree(), edu.getFieldOfStudy(), edu.getGraduationYear());
        }
    }

    private void saveExperiences(long cvId, List<CV.Experience> experiences) throws SQLException {
        String query = """
            INSERT INTO experience (cv_id, company, position, start_date, end_date, description)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        for (CV.Experience exp : experiences) {
            dbManager.executeUpdate(query, cvId, exp.getCompany(), 
                exp.getPosition(), exp.getStartDate(), exp.getEndDate(), exp.getDescription());
        }
    }

    private void saveProjects(long cvId, List<CV.Project> projects) throws SQLException {
        String query = """
            INSERT INTO project (cv_id, title, description, technologies)
            VALUES (?, ?, ?, ?)
        """;
        for (CV.Project proj : projects) {
            dbManager.executeUpdate(query, cvId, proj.getTitle(), 
                proj.getDescription(), proj.getTechnologies());
        }
    }

    private void saveSkills(long cvId, List<String> skills) throws SQLException {
        String query = "INSERT INTO skill (cv_id, skill_name) VALUES (?, ?)";
        for (String skill : skills) {
            dbManager.executeUpdate(query, cvId, skill);
        }
    }

    private void deleteRelatedRecords(long cvId) throws SQLException {
        dbManager.executeUpdate("DELETE FROM education WHERE cv_id = ?", cvId);
        dbManager.executeUpdate("DELETE FROM experience WHERE cv_id = ?", cvId);
        dbManager.executeUpdate("DELETE FROM project WHERE cv_id = ?", cvId);
        dbManager.executeUpdate("DELETE FROM skill WHERE cv_id = ?", cvId);
    }

    private CV extractCVFromResultSet(ResultSet rs) throws SQLException {
        CV cv = new CV();
        cv.setId(rs.getLong("id"));
        cv.setFullName(rs.getString("full_name"));
        cv.setEmail(rs.getString("email"));
        cv.setPhoneNumber(rs.getString("phone_number"));
        cv.setAddress(rs.getString("address"));
        cv.setProfilePhoto(rs.getString("profile_photo"));
        cv.setCreatedAt(rs.getTimestamp("created_at"));
        cv.setUpdatedAt(rs.getTimestamp("updated_at"));
        return cv;
    }

    private void loadRelatedData(CV cv) throws SQLException {
        loadEducations(cv);
        loadExperiences(cv);
        loadProjects(cv);
        loadSkills(cv);
    }

    private void loadEducations(CV cv) throws SQLException {
        String query = "SELECT * FROM education WHERE cv_id = ?";
        ResultSet rs = dbManager.executeQuery(query, cv.getId());
        try {
            while (rs.next()) {
                CV.Education edu = new CV.Education(
                    rs.getString("institution"),
                    rs.getString("degree"),
                    rs.getString("field_of_study"),
                    rs.getString("graduation_year")
                );
                cv.getEducations().add(edu);
            }
        } finally {
            dbManager.releaseReadLock();
        }
    }

    private void loadExperiences(CV cv) throws SQLException {
        String query = "SELECT * FROM experience WHERE cv_id = ?";
        ResultSet rs = dbManager.executeQuery(query, cv.getId());
        try {
            while (rs.next()) {
                CV.Experience exp = new CV.Experience(
                    rs.getString("company"),
                    rs.getString("position"),
                    rs.getString("start_date"),
                    rs.getString("end_date"),
                    rs.getString("description")
                );
                cv.getExperiences().add(exp);
            }
        } finally {
            dbManager.releaseReadLock();
        }
    }

    private void loadProjects(CV cv) throws SQLException {
        String query = "SELECT * FROM project WHERE cv_id = ?";
        ResultSet rs = dbManager.executeQuery(query, cv.getId());
        try {
            while (rs.next()) {
                CV.Project proj = new CV.Project(
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("technologies")
                );
                cv.getProjects().add(proj);
            }
        } finally {
            dbManager.releaseReadLock();
        }
    }

    private void loadSkills(CV cv) throws SQLException {
        String query = "SELECT * FROM skill WHERE cv_id = ?";
        ResultSet rs = dbManager.executeQuery(query, cv.getId());
        try {
            while (rs.next()) {
                cv.getSkills().add(rs.getString("skill_name"));
            }
        } finally {
            dbManager.releaseReadLock();
        }
    }

    private int findCVIndex(long cvId) {
        for (int i = 0; i < cvObservableList.size(); i++) {
            if (cvObservableList.get(i).getId() == cvId) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Shutdown executor service
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
