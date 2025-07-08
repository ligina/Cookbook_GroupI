package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import control.SignupFXMLController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ExtendWith(ApplicationExtension.class)
public class SignupFXMLControllerTest {

    private SignupFXMLController controller;
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField;
    private Model mockModel;

    /**
     * Initializes JavaFX environment for testing.
     * <ul>
     *   <li>Creates controller instance</li>
     *   <li>Creates mock Model object</li>
     *   <li>Injects mock Model into controller</li>
     *   <li>Creates and injects UI components</li>
     *   <li>Resets alert tracking</li>
     * </ul>
     *
     * @param stage test stage provided by ApplicationExtension
     * @throws Exception if reflection fails
     */
    @Start
    public void start(Stage stage) throws Exception {
        // Create controller instance
        controller = new SignupFXMLController();

        // Create mock Model
        mockModel = mock(Model.class);

        // Inject mock Model using reflection
        Field modelField = SignupFXMLController.class.getDeclaredField("model");
        modelField.setAccessible(true);
        modelField.set(controller, mockModel);

        // Create UI components
        usernameField = new TextField();
        passwordField = new PasswordField();
        repeatPasswordField = new PasswordField();

        // Inject UI components into controller using reflection
        Field usernameFieldField = SignupFXMLController.class.getDeclaredField("usernameField");
        usernameFieldField.setAccessible(true);
        usernameFieldField.set(controller, this.usernameField);

        Field passwordFieldField = SignupFXMLController.class.getDeclaredField("passwordField");
        passwordFieldField.setAccessible(true);
        passwordFieldField.set(controller, this.passwordField);

        Field repeatPasswordFieldField = SignupFXMLController.class.getDeclaredField("repeatPasswordField");
        repeatPasswordFieldField.setAccessible(true);
        repeatPasswordFieldField.set(controller, this.repeatPasswordField);

        // Reset alert tracking
        Model.resetLastDisplayedAlert();
    }

    /**
     * Cleans up test environment after each test execution.
     * <ul>
     *   <li>Resets alert tracking</li>
     * </ul>
     */
    @AfterEach
    public void tearDown() {
        // Reset alert tracking after each test
        Model.resetLastDisplayedAlert();
    }

    /**
     * Simulates signup operation and returns alert message content.
     *
     * @param username username input
     * @param password password input
     * @param repeatPassword confirm password input
     * @return content text of displayed alert, or null if no alert shown
     */
    private String simulateSignup(String username, String password, String repeatPassword) {
        CompletableFuture<String> futureAlertMessage = new CompletableFuture<>();

        Platform.runLater(() -> {
            try {
                // Reset alert tracking
                Model.resetLastDisplayedAlert();

                // Set input field values
                usernameField.setText(username);
                passwordField.setText(password);
                repeatPasswordField.setText(repeatPassword);

                // Invoke handleCreateButton via reflection
                Method handleMethod = SignupFXMLController.class.getDeclaredMethod(
                        "handleCreateButton", ActionEvent.class);
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
     * Test Case 2: Numeric username (EC V2).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: "123456" (pure numbers)</li>
     *   <li>Password: "pass1234" (valid)</li>
     *   <li>Confirm Password: "pass1234" (matches)</li>
     * </ul>
     * Expected:
     * <ul>
     *   <li>Alert: "Username cannot be pure numbers! Must contain at least one letter."</li>
     *   <li>Model sign method not called</li>
     * </ul>
     */
    @Test
    public void testUsernameNumeric() {
        String alertMessage = simulateSignup("123456", "pass1234", "pass1234");
        assertEquals("Username cannot be pure numbers! Must contain at least one letter.", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    /**
     * Test Case 4: Overlength username (EC V4).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: "averylongUserName" (>15 characters)</li>
     *   <li>Password: "pass1234" (valid)</li>
     *   <li>Confirm Password: "pass1234" (matches)</li>
     * </ul>
     * Expected:
     * <ul>
     *   <li>Alert: "Username length cannot exceed 15 characters!"</li>
     *   <li>Model sign method not called</li>
     * </ul>
     */
    @Test
    public void testUsernameTooLong() {
        String alertMessage = simulateSignup("averylongUserName", "pass1234", "pass1234");
        assertEquals("Username length cannot exceed 15 characters!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    /**
     * Test Case 5: Empty username (EC V5).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: "" (empty)</li>
     *   <li>Password: "pass1234" (valid)</li>
     *   <li>Confirm Password: "pass1234" (matches)</li>
     * </ul>
     * Expected:
     * <ul>
     *   <li>Alert: "Username cannot be empty!"</li>
     *   <li>Model sign method not called</li>
     * </ul>
     */
    @Test
    public void testUsernameEmpty() {
        String alertMessage = simulateSignup("", "pass1234", "pass1234");
        assertEquals("Username cannot be empty!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    /**
     * Test Case 6: Numeric password (EC V7).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: "ezra123" (valid)</li>
     *   <li>Password: "123456" (pure numbers)</li>
     *   <li>Confirm Password: "123456" (matches)</li>
     * </ul>
     * Expected:
     * <ul>
     *   <li>Alert: "Password cannot contain only numbers! Must contain both letters and numbers."</li>
     *   <li>Model sign method not called</li>
     * </ul>
     */
    @Test
    public void testPasswordNumeric() {
        String alertMessage = simulateSignup("ezra123", "123456", "123456");
        assertEquals("Password cannot contain only numbers! Must contain both letters and numbers.", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    /**
     * Test Case 7: Alphabetic password (EC V8).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: "ezra123" (valid)</li>
     *   <li>Password: "abcd" (pure letters)</li>
     *   <li>Confirm Password: "abcd" (matches)</li>
     * </ul>
     * Expected:
     * <ul>
     *   <li>Alert: "Password cannot contain only letters! Must contain both letters and numbers."</li>
     *   <li>Model sign method not called</li>
     * </ul>
     */
    @Test
    public void testPasswordAlpha() {
        String alertMessage = simulateSignup("ezra123", "abcd", "abcd");
        assertEquals("Password cannot contain only letters! Must contain both letters and numbers.", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    /**
     * Test Case 8: Overlength password (EC V9).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: "ezra123" (valid)</li>
     *   <li>Password: "averylongPassword" (>15 characters)</li>
     *   <li>Confirm Password: "averylongPassword" (matches)</li>
     * </ul>
     * Expected:
     * <ul>
     *   <li>Alert: "Password length cannot exceed 15 characters!"</li>
     *   <li>Model sign method not called</li>
     * </ul>
     */
    @Test
    public void testPasswordTooLong() {
        String alertMessage = simulateSignup("ezra123", "averylongPassword", "averylongPassword");
        assertEquals("Password length cannot exceed 15 characters!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    /**
     * Test Case 9: Empty password (EC V10).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: "ezra123" (valid)</li>
     *   <li>Password: "" (empty)</li>
     *   <li>Confirm Password: "" (empty)</li>
     * </ul>
     * Expected:
     * <ul>
     *   <li>Alert: "Password cannot be empty!"</li>
     *   <li>Model sign method not called</li>
     * </ul>
     */
    @Test
    public void testPasswordEmpty() {
        String alertMessage = simulateSignup("ezra123", "", "");
        assertEquals("Password cannot be empty!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    /**
     * Test Case 10: Password mismatch (EC V12).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: "ezra123" (valid)</li>
     *   <li>Password: "pass1234"</li>
     *   <li>Confirm Password: "word5678" (mismatch)</li>
     * </ul>
     * Expected:
     * <ul>
     *   <li>Alert: "The passwords entered twice do not match!"</li>
     *   <li>Model sign method not called</li>
     * </ul>
     */
    @Test
    public void testPasswordMismatch() {
        String alertMessage = simulateSignup("ezra123", "pass1234", "word5678");
        assertEquals("The passwords entered twice do not match!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    /**
     * Test Case 11: Empty confirm password (EC V13).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: "ezra123" (valid)</li>
     *   <li>Password: "pass1234"</li>
     *   <li>Confirm Password: "" (empty)</li>
     * </ul>
     * Expected:
     * <ul>
     *   <li>Alert: "Confirm password cannot be empty!"</li>
     *   <li>Model sign method not called</li>
     * </ul>
     */
    @Test
    public void testConfirmPasswordEmpty() {
        String alertMessage = simulateSignup("ezra123", "pass1234", "");
        assertEquals("Confirm password cannot be empty!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }
}