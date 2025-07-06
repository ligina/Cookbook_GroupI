package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;

/**
 * JavaFX Application launcher class that extends Application.
 * This class is responsible for starting the JavaFX application and displaying the login view.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class ApplicationLauncher extends Application {

    /**
     * The main entry point for the JavaFX application.
     * This method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * 
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set
     * @throws Exception if something goes wrong during application startup
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create and show the login view
        LoginView loginView = new LoginView();
        loginView.show();
    }
}