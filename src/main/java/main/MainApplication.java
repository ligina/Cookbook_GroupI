package main;

import javafx.application.Application;

/**
 * Main application entry point for the Recipe Management System.
 * This class contains the main method that launches the JavaFX application.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class MainApplication {
    
    /**
     * The main method that serves as the entry point for the application.
     * Launches the JavaFX application using the ApplicationLauncher class.
     * 
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        Application.launch(ApplicationLauncher.class, args);
    }
}