package view;

import control.RecipeCreateFXMLController;
import dao.mappers.PreparationStep;
import dao.mappers.RecipeIngredient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * RecipeCreateView is a JavaFX Stage that provides the user interface for creating and editing recipes.
 * This view loads the recipe creation FXML layout and manages the interaction between the UI components
 * and the controller for recipe creation and editing functionality.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class RecipeCreateView extends Stage {

    public boolean isEdited = false;

    public Integer editedRecipeId = 0;

    public Button submitButton, backButton, uploadButton, addButton, deleteButton, clearButton;
    public TextField recipeNameTextField, preparationTextField, cookingTimeTextField;
    public ImageView recipeImage;
    public TableView<RecipeIngredient> tableView;
    public TableView<PreparationStep> instructionTableView;
    public Tab ingredientsTab, instructionTab;
    public TabPane tabPane;
    
    private RecipeCreateFXMLController controller;

    /**
     * Default constructor for creating a new recipe.
     * Initializes the stage with default settings for recipe creation.
     */
    public RecipeCreateView() {
        this.setTitle("Create Recipe");
        this.setMinWidth(1000);
        this.setMinHeight(700);
        this.setResizable(true);
        this.setWidth(1100);
        this.setHeight(800);
        this.setMaximized(false);
        init();
    }


    /**
     * Constructor for editing an existing recipe.
     * 
     * @param _editedRecipeId The ID of the recipe to be edited
     */
    public RecipeCreateView(int _editedRecipeId){
        this.editedRecipeId = _editedRecipeId;
        this.isEdited = true;
        this.setTitle("Edit Recipe");
        this.setMinWidth(1000);
        this.setMinHeight(700);
        this.setResizable(true);
        this.setWidth(1100);
        this.setHeight(800);
        this.setMaximized(false);
        init();
    }

    /**
     * Initializes the view by loading the FXML file and setting up the controller.
     * Sets the editing state and recipe ID if this is an edit operation.
     */
    private void init() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recipe_create.fxml"));
            Scene scene = new Scene(loader.load());
            this.setScene(scene);
            
            
            controller = loader.getController();
            if (controller != null) {
                controller.setEdited(isEdited);
                controller.setEditedRecipeId(editedRecipeId);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load recipe_create.fxml");
        }
    }


    /**
     * Updates the recipe image through the controller.
     * 
     * @param temp The image path or URL to update
     */
    public void updateImage(String temp) {
        if (controller != null) {
            controller.updateImage(temp);
        }
    }
    

    /**
     * Updates the nutrition preview display through the controller.
     * This method refreshes the nutritional information shown in the UI.
     */
    public void updateNutritionPreview() {
        if (controller != null) {
            controller.updateNutritionPreview();
        }
    }
}