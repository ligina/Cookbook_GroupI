package view;

import control.RecipeDisplayFXMLController;
import dao.mappers.RecipeIngredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * RecipeDisplayView is a JavaFX Stage that provides the user interface for displaying recipe details.
 * This view shows comprehensive recipe information including ingredients, instructions, nutritional data,
 * and provides options to edit or delete the recipe.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class RecipeDisplayView extends Stage {

    public Button editRecipeButton;
    public Button deleteRecipeButton;
    public Button backButton;
    public ObservableList<RecipeIngredient> selectedIngredients = FXCollections.observableArrayList();
    public Label recipeNameLabel = new Label("");
    public Label cookingTimeLabel = new Label();
    public Label preparationTimeLabel = new Label();
    public TextField serveNumberTextField = new TextField("1");
    public Label totalCaloriesLabel = new Label("Total Calories: 0 kcal");
    public Label totalProteinLabel = new Label("Total Protein: 0 g");
    public Label totalFatLabel = new Label("Total Fat: 0 g");
    public Label totalCarbsLabel = new Label("Total Carbs: 0 g");
    public String imageurl = "";
    public TextArea instructionsTextArea = new TextArea();
    public Integer selectedRecipeNumber;
    private RecipeDisplayFXMLController controller;
    /**
     * Constructor for displaying a specific recipe.
     * 
     * @param recipeNumber The ID of the recipe to display
     */
    public RecipeDisplayView(Integer recipeNumber) {
        this.setTitle("Recipe Details");
        this.setMinWidth(1000);
        this.setMinHeight(700);
        this.setResizable(false);
        this.setWidth(1100);
        this.setHeight(830);
        this.setMaximized(false);
        this.selectedRecipeNumber = recipeNumber;
        init();
    }

    /**
     * Initializes the view by loading the FXML file and setting up the controller.
     * Sets the recipe number and initializes the recipe data display.
     */
    private void init() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recipe_display.fxml"));
            Scene scene = new Scene(loader.load());
            this.setScene(scene);
            controller = loader.getController();
            if (controller != null) {
                controller.setRecipeNumber(selectedRecipeNumber);
                controller.initializeData();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load recipe_display.fxml");
        }
    }

    /**
     * Updates the nutritional information display through the controller.
     * 
     * @param totalCalories Total calories for the recipe
     * @param totalProtein Total protein content
     * @param totalFat Total fat content
     * @param totalCarbs Total carbohydrate content
     */
    public void updateNutritionalDisplay(float totalCalories, float totalProtein, float totalFat, float totalCarbs) {
        if (controller != null) {
            controller.updateNutritionalDisplay();
        }
    }
}