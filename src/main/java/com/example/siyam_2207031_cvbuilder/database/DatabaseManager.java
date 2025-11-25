package com.example.siyam_2207031_cvbuilder.database;

import java.sql.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Database Manager class for handling SQLite database operations
 * Implements connection pooling and thread-safe database access
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:cvbuilder.db";
    private static DatabaseManager instance;
    private Connection connection;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private DatabaseManager() {
        initializeDatabase();
    }

    /**
     * Singleton pattern to get database manager instance
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Initialize database and create tables if they don't exist
     */
    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
            System.out.println("Database initialized successfully");
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create all necessary tables
     */
    private void createTables() throws SQLException {
        String createCVTable = """
            CREATE TABLE IF NOT EXISTS cv (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                full_name TEXT NOT NULL,
                email TEXT NOT NULL,
                phone_number TEXT NOT NULL,
                address TEXT NOT NULL,
                profile_photo TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;

        String createEducationTable = """
            CREATE TABLE IF NOT EXISTS education (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cv_id INTEGER NOT NULL,
                institution TEXT NOT NULL,
                degree TEXT NOT NULL,
                field_of_study TEXT NOT NULL,
                graduation_year TEXT NOT NULL,
                FOREIGN KEY (cv_id) REFERENCES cv(id) ON DELETE CASCADE
            )
        """;

        String createExperienceTable = """
            CREATE TABLE IF NOT EXISTS experience (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cv_id INTEGER NOT NULL,
                company TEXT NOT NULL,
                position TEXT NOT NULL,
                start_date TEXT NOT NULL,
                end_date TEXT NOT NULL,
                description TEXT NOT NULL,
                FOREIGN KEY (cv_id) REFERENCES cv(id) ON DELETE CASCADE
            )
        """;

        String createProjectTable = """
            CREATE TABLE IF NOT EXISTS project (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cv_id INTEGER NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                technologies TEXT NOT NULL,
                FOREIGN KEY (cv_id) REFERENCES cv(id) ON DELETE CASCADE
            )
        """;

        String createSkillTable = """
            CREATE TABLE IF NOT EXISTS skill (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cv_id INTEGER NOT NULL,
                skill_name TEXT NOT NULL,
                FOREIGN KEY (cv_id) REFERENCES cv(id) ON DELETE CASCADE
            )
        """;

        lock.writeLock().lock();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createCVTable);
            stmt.execute(createEducationTable);
            stmt.execute(createExperienceTable);
            stmt.execute(createProjectTable);
            stmt.execute(createSkillTable);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Get database connection
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException e) {
            System.err.println("Failed to get connection: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Execute a query with read lock
     */
    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        lock.readLock().lock();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(query);
            setParameters(pstmt, params);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            lock.readLock().unlock();
            throw e;
        }
    }

    /**
     * Execute an update with write lock
     */
    public int executeUpdate(String query, Object... params) throws SQLException {
        lock.writeLock().lock();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(query);
            setParameters(pstmt, params);
            return pstmt.executeUpdate();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Execute an insert and return generated key
     */
    public long executeInsert(String query, Object... params) throws SQLException {
        lock.writeLock().lock();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setParameters(pstmt, params);
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new SQLException("Failed to retrieve generated key");
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Begin transaction
     */
    public void beginTransaction() throws SQLException {
        lock.writeLock().lock();
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            lock.writeLock().unlock();
            throw e;
        }
    }

    /**
     * Commit transaction
     */
    public void commitTransaction() throws SQLException {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Rollback transaction
     */
    public void rollbackTransaction() {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Failed to rollback transaction: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Release read lock
     */
    public void releaseReadLock() {
        lock.readLock().unlock();
    }

    /**
     * Set parameters for prepared statement
     */
    private void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            if (param == null) {
                pstmt.setNull(i + 1, Types.NULL);
            } else if (param instanceof String) {
                pstmt.setString(i + 1, (String) param);
            } else if (param instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) param);
            } else if (param instanceof Long) {
                pstmt.setLong(i + 1, (Long) param);
            } else if (param instanceof Double) {
                pstmt.setDouble(i + 1, (Double) param);
            } else if (param instanceof Boolean) {
                pstmt.setBoolean(i + 1, (Boolean) param);
            } else if (param instanceof Timestamp) {
                pstmt.setTimestamp(i + 1, (Timestamp) param);
            } else {
                pstmt.setObject(i + 1, param);
            }
        }
    }

    /**
     * Close database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection: " + e.getMessage());
        }
    }
}
