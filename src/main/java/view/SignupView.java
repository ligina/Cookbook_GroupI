package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * JavaFX Stage class for the signup view of the recipe management application.
 * This class creates and configures the registration window where new users can create accounts.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class SignupView extends Stage {

    /**
     * Constructor that initializes the signup view window.
     * Loads the FXML layout file and configures the stage properties for user registration.
     */
    public SignupView() {
        try {
            // Load the FXML file for the signup view
            URL fxmlLocation = getClass().getResource("/signup_view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            Pane root = loader.load();

            // Set up the scene and stage properties
            Scene scene = new Scene(root);
            this.setScene(scene);
            this.setTitle("Sign Up");
            this.setResizable(false);
            this.setWidth(700);
            this.setHeight(650);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}