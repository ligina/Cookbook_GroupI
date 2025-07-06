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

    private void init() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recipe_create.fxml"));
            Scene scene = new Scene(loader.load());
            this.setScene(scene);
            
            // Get the controller and set the edit parameters
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


    public void updateImage(String temp) {
        if (controller != null) {
            controller.updateImage(temp);
        }
    }
    

    public void updateNutritionPreview() {
        if (controller != null) {
            controller.updateNutritionPreview();
        }
    }
}