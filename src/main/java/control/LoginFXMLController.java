package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import model.Model;
import view.RecipeSelectView;
import view.SignupView;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * FXML Controller class for the login view.
 * This class handles user authentication including login validation,
 * navigation to signup view, and successful login redirection.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class LoginFXMLController {

    /** Text field for username input */
    @FXML
    private TextField usernameField;
    
    /** Password field for password input */
    @FXML
    private PasswordField passwordField;
    
    /** Button to trigger login action */
    @FXML
    private Button loginButton;
    
    /** Button to navigate to signup view */
    @FXML
    public Button signupButton;
    
    /** Model instance for business logic operations */
    private Model model;
    
    /**
     * Initializes the controller after FXML loading.
     * Creates a new Model instance for handling business logic.
     */
    @FXML
    public void initialize() {
        model = new Model();
    }

    /**
     * Handles the login button click event.
     * Validates user input, attempts authentication, and navigates to recipe selection on success.
     * 
     * @param event the ActionEvent triggered by clicking the login button
     */
    @FXML
    private void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate username input
        if (username == null || username.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username cannot be empty!");
            return;
        }

        // Validate password input
        if (password == null || password.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password cannot be empty!");
            return;
        }

        // Attempt login
        boolean loginSuccessful = model.login(username, password);

        if (loginSuccessful) {
            // Close current window and open recipe selection view
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
            RecipeSelectView recipeSelectView = new RecipeSelectView();
            recipeSelectView.show();
        }
    }

    /**
     * Handles the signup button click event.
     * Opens the signup view for new user registration.
     * 
     * @param event the ActionEvent triggered by clicking the signup button
     */
    @FXML
    private void handleSignupButton(ActionEvent event) {
        SignupView signupView = new SignupView();
        signupView.show();
    }

}