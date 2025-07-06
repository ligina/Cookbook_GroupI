package model;

import config.SessionManager;
import dao.mappers.User;
import javafx.scene.control.Alert;

/**
 * Service class for handling user-related operations including authentication and registration.
 * This class provides methods for user login, signup, and session management.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class UserService {
    /** Database manager instance for accessing user data */
    private DatabaseManager dbManager;

    /**
     * Constructor that initializes the UserService with a database manager.
     * 
     * @param dbManager the DatabaseManager instance for database operations
     */
    public UserService(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Registers a new user in the system.
     * Validates that the username is unique before creating the account.
     * 
     * @param name the username for the new account
     * @param password the password for the new account
     * @return true if registration was successful, false if username exists or operation failed
     */
    public boolean sign(String name, String password) {
        // Check if username already exists
        if (dbManager.getUserMapper().getUserByName(name) != null) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Username already exists!");
            return false;
        }
        
        try {
            // Create new user and add to database
            User user = new User();
            user.setUser(name, password);
            dbManager.getUserMapper().addUser(user);
            dbManager.getSqlSession().commit();
            return true;
        } catch (Exception e) {
            dbManager.getSqlSession().rollback();
            return false;
        }
    }

    /**
     * Authenticates a user login attempt.
     * Validates username and password, and sets up user session if successful.
     * 
     * @param name the username to authenticate
     * @param password the password to verify
     * @return true if login was successful, false if credentials are invalid
     */
    public boolean login(String name, String password) {
        try {
            User user = dbManager.getUserMapper().getUserByName(name);
            
            // Check if user exists
            if (user == null) {
                UIUtils.displayAlert(Alert.AlertType.ERROR, "Error", "Username does not exist!");
                return false;
            } 
            // Check if password matches
            else if (!user.getPassword().equals(password)) {
                UIUtils.displayAlert(Alert.AlertType.ERROR, "Error", "Password is incorrect!");
                return false;
            } else {
                // Set up user session
                SessionManager.setCurrentUserName(name);
                return true;
            }
        } catch (Exception e) {
            dbManager.getSqlSession().rollback();
            return false;
        }
    }
}