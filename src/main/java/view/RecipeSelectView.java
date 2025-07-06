package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * JavaFX Stage class for the recipe selection view of the recipe management application.
 * This class creates and configures the main recipe browsing window where users can
 * view, search, and select recipes from their collection.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class RecipeSelectView extends Stage {

    /** Map of recipe IDs to their image URLs */
    public LinkedHashMap<Integer, String> imageUrls;
    
    /** List of recipe names for display */
    public ArrayList<String> imageNames;
    
    /** Map linking UI buttons to recipe IDs */
    public LinkedHashMap<Button, Integer> buttonMap;
    
    /** Button for initiating recipe search */
    public Button searchButton;
    
    /** Text field for entering search terms */
    public TextField searchField;
    
    /** Button for navigating back to previous view */
    public Button backButton;
    
    /** Button for creating new recipes */
    public Button createRecipeButton;

    /**
     * Constructor that initializes the recipe selection view window.
     * Sets up the stage properties and loads the FXML layout.
     */
    public RecipeSelectView() {
        this.setTitle("Recipe Selection");
        this.setMinWidth(800);
        this.setMinHeight(600);
        this.setWidth(1200);
        this.setHeight(930);
        this.setResizable(false);
        this.setMaximized(false);
        init();
    }
    
    /**
     * Initializes the view by loading the FXML file and setting up the scene.
     * This method is called during construction to set up the UI components.
     */
    private void init() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recipe_select.fxml"));
            Scene scene = new Scene(loader.load());
            this.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load recipe_select.fxml");
        }
    }

    /**
     * Updates the view with new recipe data.
     * This method is used to refresh the displayed recipes and their associated images.
     * 
     * @param imageUrls map of recipe IDs to their image URLs
     * @param imageNames list of recipe names for display
     */
    public void update(LinkedHashMap<Integer, String> imageUrls, ArrayList<String> imageNames) {
        this.imageUrls = imageUrls;
        this.imageNames = imageNames;
    }
}
