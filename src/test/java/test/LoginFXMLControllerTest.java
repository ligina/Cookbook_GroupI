package test;

import control.LoginFXMLController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test class for LoginFXMLController functionality validation.
 * This class tests the login form validation, user authentication,
 * and UI interaction behaviors using JavaFX TestFX framework.
 *
 * @author Mengfei Chen and Ziang Liu
 * @version 1.0
 * @since 1.0
 */
@ExtendWith(ApplicationExtension.class)
public class LoginFXMLControllerTest {

    private LoginFXMLController controller;
    private Model mockModel;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;

    /**
     * Initializes test environment by creating UI components and mock objects.
     *
     * @param stage test stage provided by ApplicationExtension
     */
    @Start
    public void start(Stage stage) {
        // Create mock Model
        mockModel = mock(Model.class);

        // Create controller instance
        controller = new LoginFXMLController();

        // Create UI components
        usernameField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("Login");

        // Inject components using reflection
        setField(controller, "usernameField", usernameField);
        setField(controller, "passwordField", passwordField);
        setField(controller, "loginButton", loginButton);
        setField(controller, "model", mockModel);

        // Initialize controller
        controller.initialize();

        // Reset alert tracking
        Model.resetLastDisplayedAlert();
    }

    /**
     * Prepares test environment before each test execution.
     * Resets mock objects, clears input fields, and resets alert tracking.
     */
    @BeforeEach
    public void setUp() {
        // Reset mock objects
        reset(mockModel);
        // Clear input fields
        usernameField.clear();
        passwordField.clear();
        // Reset alert tracking
        Model.resetLastDisplayedAlert();
    }

    /**
     * Cleans up test environment after each test execution.
     * Resets alert tracking to ensure clean state for next test.
     */
    @AfterEach
    public void tearDown() {
        // Reset alert tracking
        Model.resetLastDisplayedAlert();
    }

    /**
     * Simulates login operation and returns alert message.
     *
     * @param username username input
     * @param password password input
     * @return content text of displayed alert, or null if no alert shown
     */
    private String simulateLogin(String username, String password) {
        CompletableFuture<String> futureAlertMessage = new CompletableFuture<>();

        Platform.runLater(() -> {
            try {
                // Reset alert tracking
                Model.resetLastDisplayedAlert();

                // Set input field values
                usernameField.setText(username);
                passwordField.setText(password);

                // Invoke private method via reflection
                Method handleMethod = LoginFXMLController.class.getDeclaredMethod("handleLoginButton", ActionEvent.class);
                handleMethod.setAccessible(true);
                handleMethod.invoke(controller, (ActionEvent) null);

                // Retrieve alert message
                Alert lastAlert = Model.getLastDisplayedAlert();
                if (lastAlert != null) {
                    futureAlertMessage.complete(lastAlert.getContentText());
                } else {
                    futureAlertMessage.complete(null);
                }
            } catch (Exception e) {
                futureAlertMessage.completeExceptionally(e);
            }
        });

        try {
            return futureAlertMessage.get();
        } catch (InterruptedException | ExecutionException e) {
            fail("Test interrupted: " + e.getMessage());
            return null;
        }
    }

    /**
     * Tests Test No.3 from Log In table: Empty username (EC V3).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: null</li>
     *   <li>Password: pass1234</li>
     * </ul>
     * Expected:
     * <ul>
     *   <li>Warning: "Username cannot be empty!"</li>
     *   <li>Model login method not called</li>
     * </ul>
     */
    @Test
    public void testLoginWithEmptyUsername() {
        String alertMessage = simulateLogin(null, "pass1234");
        assertEquals("Username cannot be empty!", alertMessage);
        verify(mockModel, never()).login(anyString(), anyString());
    }

    /**
     * Tests Test No.5 from Log In table: Empty password (EC V6).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: ezra1234</li>
     *   <li>Password: null</li>
     * </ul>
     * Expected:
     * <ul>
     *   <li>Warning: "Password cannot be empty!"</li>
     *   <li>Model login method not called</li>
     * </ul>
     */
    @Test
    public void testLoginWithEmptyPassword() {
        String alertMessage = simulateLogin("ezra1234", null);
        assertEquals("Password cannot be empty!", alertMessage);
        verify(mockModel, never()).login(anyString(), anyString());
    }

    /**
     * Sets field value using reflection.
     *
     * @param target target object containing the field
     * @param fieldName name of field to set
     * @param value value to assign to field
     * @throws RuntimeException if field access fails
     */
    /**
     * Helper method to set field values using reflection.
     * 
     * @param target The target object containing the field
     * @param fieldName The name of the field to set
     * @param value The value to assign to the field
     */
    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}