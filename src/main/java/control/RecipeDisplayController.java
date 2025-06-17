package control;
import config.SessionManager;
import dao.mappers.PreparationStep;
import dao.mappers.Recipe;
import dao.mappers.RecipeIngredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import model.Model;
import view.*;
import java.util.List;
/**
 * Controller for handling actions on the Recipe Display page.
 *
 * @author 
 */
public class RecipeDisplayController implements EventHandler<ActionEvent> {
    private final RecipeDisplayView recipeDisplayView;
    private final Model model;
    private RecipeCreateView recipeCreateView;
    private final Recipe selectedRecipe;
    private final List<RecipeIngredient> selectedIngredient;
    private final List<PreparationStep> selectedPreparation;

    /**
     * Constructor to initialize the controller with the given RecipeDisplayView.
     *
     * @param recipeDisplayView The view instance to be associated with this controller.
     */

    public RecipeDisplayController(RecipeDisplayView recipeDisplayView) {
        Integer selectedRecipeNumber = recipeDisplayView.selectedRecipeNumber;
        this.recipeDisplayView = recipeDisplayView;
        this.model = new Model();
        selectedRecipe = model.getRecipeByID(selectedRecipeNumber);
        selectedIngredient = model.getIngredientByID(selectedRecipeNumber);
        selectedPreparation = model.getRecipePreparationSteps(selectedRecipeNumber);
    }

    /**
     * Initializes data to be displayed in the RecipeDisplayView.
     */
    public void initializeData() {
        recipeDisplayView.serveNumberTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                recipeDisplayView.selectedIngredients.clear();
                // Update displayed ingredients based on the new serve number
                if(model.serveNumberIsInteger(recipeDisplayView.serveNumberTextField.getText())) {
                    List<RecipeIngredient> updatedRecipeIngredients = model.updateIngredientByServeNumber(selectedRecipe.getRecipeId(), recipeDisplayView.serveNumberTextField.getText());
                    recipeDisplayView.selectedIngredients.addAll(updatedRecipeIngredients);
                    // Update nutritional information based on new serve number
                    updateNutritionalInfo(updatedRecipeIngredients);
                }
            }
        });
        // Set initial data in the RecipeDisplayView
        recipeDisplayView.recipeNameLabel.setText( selectedRecipe.getRecipeName());
        recipeDisplayView.imageurl = "file:"+selectedRecipe.getImageUrl();
        recipeDisplayView.selectedIngredients.addAll(selectedIngredient);
        recipeDisplayView.serveNumberTextField.setText(String.valueOf(selectedRecipe.getServeAmount()));
        recipeDisplayView.cookingTimeLabel.setText("Cooking Time: "+selectedRecipe.getCookingTime()+"min");
        recipeDisplayView.preparationTimeLabel.setText("Preparation Time: "+selectedRecipe.getPreparationTime()+"min");
        // Append preparation steps to instructions text area
        for(PreparationStep preparationStep : selectedPreparation){
            recipeDisplayView.instructionsTextArea.appendText(preparationStep.getStep()+". "+preparationStep.getDescription()+"\n");
        }
        // Initialize nutritional information display
        updateNutritionalInfo(selectedIngredient);
    }
    /**
     * Handles actions performed in the RecipeDisplayView.
     *
     * @param event The ActionEvent representing the user's action.
     */
    public void handle(ActionEvent event) {
        if (event.getSource() == recipeDisplayView.deleteRecipeButton) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this recipe?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    // If user confirms deletion, delete the recipe and associated image file
                    model.deleteRecipe(selectedRecipe.getRecipeId());
                    Model.displayAlert(Alert.AlertType.INFORMATION,"Info","Successfully deleted this recipe");
                }
            });
            recipeDisplayView.close();
            RecipeSelectView recipeSelectView = new RecipeSelectView();
            recipeSelectView.show();
        }else if (event.getSource() == recipeDisplayView.editRecipeButton) {
            // Handle edit recipe button click
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to edit this recipe?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    // If user confirms edit, open RecipeCreateView for editing
                    recipeDisplayView.close();
                    recipeCreateView = new RecipeCreateView(selectedRecipe.getRecipeId());
                    recipeCreateView.recipeNameTextField.setText(selectedRecipe.getRecipeName());
                    recipeCreateView.cookingTimeTextField.setText(String.valueOf(selectedRecipe.getCookingTime()));
                    recipeCreateView.preparationTextField.setText(String.valueOf(selectedRecipe.getPreparationTime()));
                    recipeCreateView.recipeImage.setImage(new Image("file:"+selectedRecipe.getImageUrl()));
                    ObservableList<RecipeIngredient> selectedIngredients = FXCollections.observableArrayList();
                    ObservableList<PreparationStep> selectedPreparations = FXCollections.observableArrayList();
                    selectedIngredients.addAll(selectedIngredient);

                    selectedPreparations.addAll(selectedPreparation);
                    recipeCreateView.tableView.setItems(selectedIngredients);
                    recipeCreateView.instructionTableView.setItems(selectedPreparations);

                    recipeCreateView.show();
                }
            });
        }else if (event.getSource() == recipeDisplayView.backButton) {
            // Handle back button click to return to RecipeSelectView
            recipeDisplayView.close();
            RecipeSelectView recipeSelectView = new RecipeSelectView();
            recipeSelectView.show();
        }
    }

    /**
     * Updates the nutritional information display based on the given list of ingredients.
     *
     * @param ingredients The list of RecipeIngredient objects to calculate nutrition from.
     */
    private void updateNutritionalInfo(List<RecipeIngredient> ingredients) {
        float totalCalories = 0;
        float totalProtein = 0;
        float totalFat = 0;
        float totalCarbs = 0;

        for (RecipeIngredient ingredient : ingredients) {
            totalCalories += ingredient.getTotalCalories();
            totalProtein += ingredient.getTotalProtein();
            totalFat += ingredient.getTotalFat();
            totalCarbs += ingredient.getTotalCarbohydrates();
        }

        recipeDisplayView.totalCaloriesLabel.setText(String.format("Total Calories: %.1f kcal", totalCalories));
        recipeDisplayView.totalProteinLabel.setText(String.format("Total Protein: %.1f g", totalProtein));
        recipeDisplayView.totalFatLabel.setText(String.format("Total Fat: %.1f g", totalFat));
        recipeDisplayView.totalCarbsLabel.setText(String.format("Total Carbs: %.1f g", totalCarbs));
    }
}
