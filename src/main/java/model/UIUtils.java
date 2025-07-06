package model;

import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;

/**
 * Utility class providing common UI-related helper methods.
 * This class contains static methods for displaying alerts and creating text field formatters.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class UIUtils {
    
    /**
     * Displays an alert dialog with the specified type, title, and content.
     * The alert will show and wait for user interaction before continuing.
     * 
     * @param alertType the type of alert to display (ERROR, WARNING, INFORMATION, etc.)
     * @param title the title text for the alert dialog
     * @param content the main content text to display in the alert
     */
    public static void displayAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Creates a TextFormatter that limits the maximum length of text input.
     * When the maximum length is reached, additional characters will be rejected.
     * 
     * @param maxLength the maximum number of characters allowed in the text field
     * @return a TextFormatter configured with the specified maximum length
     */
    public static TextFormatter<String> textFieldFormatter(int maxLength) {
        TextFormatter<String> temp;
        temp = new TextFormatter<>(change -> {
            // Check if text is being added
            if(change.isAdded()){
                // If the current text length exceeds max length, reject the change
                if(change.getControlText().length() >= maxLength) {
                    change.setText("");
                }
            }
            return change;
        });
        return temp;
    }
}