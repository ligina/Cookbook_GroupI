package test;

import control.RecipeSelectFXMLController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class SearchTest {

    private RecipeSelectFXMLController controller;
    private TextField searchField;
    private Button searchButton;
    private Model mockModel;

    /**
     * Initializes the JavaFX environment for testing.
     *
     * @param stage test stage provided by ApplicationExtension
     */
    @Start
    public void start(Stage stage) {
        // Create controller instance
        controller = new RecipeSelectFXMLController();

        // Create mock Model object
        mockModel = mock(Model.class);

        // Create UI components
        searchField = new TextField();
        searchButton = new Button("Search");

        // Inject components using reflection
        setField(controller, "searchField", searchField);
        setField(controller, "searchButton", searchButton);
        setField(controller, "model", mockModel);
    }

    /**
     * Prepares test environment before each test execution.
     * <ul>
     *   <li>Clears search input field</li>
     *   <li>Resets alert tracking</li>
     * </ul>
     */
    @BeforeEach
    public void setUp() {
        // Clear input field
        searchField.clear();
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
        // Reset alert tracking
        Model.resetLastDisplayedAlert();
    }

    /**
     * Simulates search operation and checks for alert presence.
     *
     * @param searchTerm search term to input
     * @return true if no alert was shown, false if alert was displayed
     */
    private boolean checkNoAlertForInput(String searchTerm) {
        CompletableFuture<Boolean> noAlertFuture = new CompletableFuture<>();

        Platform.runLater(() -> {
            try {
                // Reset alert tracking
                Model.resetLastDisplayedAlert();

                // Enter text into search field
                searchField.setText(searchTerm);

                // Simulate search button click via reflection
                try {
                    Method handleMethod = RecipeSelectFXMLController.class.getDeclaredMethod(
                            "handleSearchButton", ActionEvent.class);
                    handleMethod.setAccessible(true);
                    handleMethod.invoke(controller, (ActionEvent) null);
                } catch (Exception e) {
                    // Ignore any invocation exceptions
                }

                // Check if alert was shown
                Alert lastAlert = Model.getLastDisplayedAlert();
                noAlertFuture.complete(lastAlert == null);
            } catch (Exception e) {
                // Check alert presence even if exception occurs
                Alert lastAlert = Model.getLastDisplayedAlert();
                noAlertFuture.complete(lastAlert == null);
            }
        });

        try {
            return noAlertFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            // Default to assuming alert was shown on error
            return false;
        }
    }

    /**
     * Test Case 1: Valid search term input (EC V1).
     * <p>
     * Input: "tomato"
     * Expected: No alert shown
     */
    @Test
    public void testValidTextInput_Tomato() {
        // Verify no alert is shown
        boolean noAlert = checkNoAlertForInput("tomato");
        assertTrue(noAlert, "No alert should be shown for valid input");
    }

    /**
     * Test Case 2: Overlength search term input (EC V2).
     * <p>
     * Input: "averylongIngredientNamewhichexceeds30characters"
     * Expected: Alert shown
     */
    @Test
    public void testLongTextInput() {
        String longTerm = "averylongIngredientNamewhichexceeds30characters";
        boolean noAlert = checkNoAlertForInput(longTerm);
        assertFalse(noAlert, "Alert should be shown for overlength input");
    }

    /**
     * Test Case 3: Empty input (EC V3).
     * <p>
     * Input: "" (represents null)
     * Expected: Alert shown
     */
    @Test
    public void testEmptyTextInput() {
        boolean noAlert = checkNoAlertForInput("");
        assertFalse(noAlert, "Alert should be shown for empty input");
    }

    /**
     * Sets field value using reflection.
     *
     * @param target target object containing the field
     * @param fieldName name of field to set
     * @param value value to assign to field
     * @throws RuntimeException if field access fails
     */
    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Field setting failed: " + fieldName, e);
        }
    }
}