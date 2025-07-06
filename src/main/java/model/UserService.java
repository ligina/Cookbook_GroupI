package model;

import config.SessionManager;
import dao.mappers.User;
import javafx.scene.control.Alert;

/**
 * User authentication and management service
 */
public class UserService {
    private DatabaseManager dbManager;

    public UserService(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * User registration
     */
    public boolean sign(String name, String password) {
        // Check if username already exists
        if (dbManager.getUserMapper().getUserByName(name) != null) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Username already exists!");
            return false;
        }
        
        try {
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
     * User login
     */
    public boolean login(String name, String password) {
        try {
            User user = dbManager.getUserMapper().getUserByName(name);
            // Username does not exist in database
            if (user == null) {
                UIUtils.displayAlert(Alert.AlertType.ERROR, "Error", "Username does not exist!");
                return false;
            } 
            // Password does not match username
            else if (!user.getPassword().equals(password)) {
                UIUtils.displayAlert(Alert.AlertType.ERROR, "Error", "Password is incorrect!");
                return false;
            } else {
                SessionManager.setCurrentUserName(name);
                return true;
            }
        } catch (Exception e) {
            dbManager.getSqlSession().rollback();
            return false;
        }
    }
}