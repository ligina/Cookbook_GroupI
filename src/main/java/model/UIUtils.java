package model;

import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;

/**
 * UI utility methods for alerts and text formatting
 */
public class UIUtils {
    
    /**
     * Display alert dialog with specified type, title and content
     */
    public static void displayAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Create text formatter with maximum length limit
     */
    public static TextFormatter<String> textFieldFormatter(int maxLength) {
        TextFormatter<String> temp;
        temp = new TextFormatter<>(change -> {
            if(change.isAdded()){
                if(change.getControlText().length() >= maxLength) {
                    change.setText("");
                }
            }
            return change;
        });
        return temp;
    }
}