package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * JavaFX Stage class for the login view of the recipe management application.
 * This class creates and configures the login window where users can authenticate.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class LoginView extends Stage {

    /**
     * Constructor that initializes the login view window.
     * Loads the FXML layout file and configures the stage properties.
     */
    public LoginView() {
        try {
            // Load the FXML file for the login view
            URL fxmlLocation = getClass().getResource("/login_view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            Pane root = loader.load();

            // Set up the scene and stage properties
            Scene scene = new Scene(root);
            this.setScene(scene);
            this.setTitle("login");
            this.setResizable(false);
            this.setWidth(700);
            this.setHeight(550);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}