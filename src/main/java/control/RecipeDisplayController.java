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

public class RecipeDisplayController implements EventHandler<ActionEvent> {
    private final RecipeDisplayView recipeDisplayView;
    private final Model model;
    private RecipeCreateView recipeCreateView;
    private final Recipe selectedRecipe;
    private final List<RecipeIngredient> selectedIngredient;
    private final List<PreparationStep> selectedPreparation;
    public RecipeDisplayController(RecipeDisplayView recipeDisplayView) {
        Integer selectedRecipeNumber = recipeDisplayView.selectedRecipeNumber;
        this.recipeDisplayView = recipeDisplayView;
        this.model = new Model();
        selectedRecipe = model.getRecipeByID(selectedRecipeNumber);
        selectedIngredient = model.getIngredientByID(selectedRecipeNumber);
        selectedPreparation = model.getRecipePreparationSteps(selectedRecipeNumber);
    }

    public void initializeData() {
        recipeDisplayView.serveNumberTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                recipeDisplayView.selectedIngredients.clear();
                if(model.serveNumberIsInteger(recipeDisplayView.serveNumberTextField.getText())) {
                    List<RecipeIngredient> updatedRecipeIngredients = model.updateIngredientByServeNumber(selectedRecipe.getRecipeId(), recipeDisplayView.serveNumberTextField.getText());
                    recipeDisplayView.selectedIngredients.addAll(updatedRecipeIngredients);
                    updateNutritionalInfo(updatedRecipeIngredients);
                }
            }
        });
        recipeDisplayView.recipeNameLabel.setText( selectedRecipe.getRecipeName());
        recipeDisplayView.imageurl = "file:"+selectedRecipe.getImageUrl();
        recipeDisplayView.selectedIngredients.addAll(selectedIngredient);
        recipeDisplayView.serveNumberTextField.setText(String.valueOf(selectedRecipe.getServeAmount()));
        recipeDisplayView.cookingTimeLabel.setText("Cooking Time: "+selectedRecipe.getCookingTime()+"min");
        recipeDisplayView.preparationTimeLabel.setText("Preparation Time: "+selectedRecipe.getPreparationTime()+"min");
        recipeDisplayView.instructionsTextArea.clear();
        for(PreparationStep preparationStep : selectedPreparation){
            recipeDisplayView.instructionsTextArea.appendText("Step " + preparationStep.getStep() + ": " + preparationStep.getDescription() + "\n\n");
        }

        updateNutritionalInfo(selectedIngredient);
    }
    public void handle(ActionEvent event) {
        if (event.getSource() == recipeDisplayView.deleteRecipeButton) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this recipe?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    model.deleteRecipe(selectedRecipe.getRecipeId());
                    Model.displayAlert(Alert.AlertType.INFORMATION,"Info","Successfully deleted this recipe");
                }
            });
            recipeDisplayView.close();
            RecipeSelectView recipeSelectView = new RecipeSelectView();
            recipeSelectView.show();
        }else if (event.getSource() == recipeDisplayView.editRecipeButton) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to edit this recipe?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
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
                    recipeCreateView.updateNutritionPreview();

                    recipeCreateView.show();
                }
            });
        }else if (event.getSource() == recipeDisplayView.backButton) {
            recipeDisplayView.close();
            RecipeSelectView recipeSelectView = new RecipeSelectView();
            recipeSelectView.show();
        }
    }

    private void updateNutritionalInfo(List<RecipeIngredient> ingredients) {
        float totalCalories = 0;
        float totalProtein = 0;
        float totalFat = 0;
        float totalCarbs = 0;

        if (selectedRecipe.getCalories() > 0 || selectedRecipe.getProtein() > 0 || 
            selectedRecipe.getCarbohydrates() > 0 || selectedRecipe.getFat() > 0) {
            totalCalories = selectedRecipe.getCalories();
            totalProtein = (float) selectedRecipe.getProtein();
            totalFat = (float) selectedRecipe.getFat();
            totalCarbs = (float) selectedRecipe.getCarbohydrates();
        } else {
            for (RecipeIngredient ingredient : ingredients) {
                totalCalories += ingredient.getTotalCalories();
                totalProtein += ingredient.getTotalProtein();
                totalFat += ingredient.getTotalFat();
                totalCarbs += ingredient.getTotalCarbohydrates();
            }
        }
        recipeDisplayView.updateNutritionalDisplay(totalCalories, totalProtein, totalFat, totalCarbs);
    }
}
