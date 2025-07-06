package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;

/**
 * The application launcher class.
 * Responsible for initializing and launching the JavaFX application.
 */
public class ApplicationLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginView loginView = new LoginView();
        loginView.show();
        // The LoginView creates its own Stage, so we don't need to show primaryStage
    }
}