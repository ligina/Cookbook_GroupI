package control;
import dao.mappers.PreparationStep;
import dao.mappers.Recipe;
import dao.mappers.RecipeIngredient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Model;
import view.RecipeCreateView;
import view.RecipeSelectView;
import service.NutritionService;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for handling actions on the Recipe Create page.
 *
 * @author 
 */

public class RecipeCreateController implements EventHandler<ActionEvent> {

    private int preparationStepNumber = 0;
    private final RecipeCreateView recipeCreateView;
    private final Model model;
    public  String imageUrl = "";

    /**
     * Constructs a RecipeCreateController with a RecipeCreateView instance.
     *
     * @param recipeCreateView The RecipeCreateView associated with this controller.
     */
    public RecipeCreateController(RecipeCreateView recipeCreateView) {
        this.recipeCreateView = recipeCreateView;
        this.model = new Model();
        // Initialize preparation step number based on existing steps for edited recipes
        List<PreparationStep> preparationSteps = model.getRecipePreparationSteps(recipeCreateView.editedRecipeId);
        for(PreparationStep preparationStep : preparationSteps){
            preparationStepNumber = Math.max(preparationStepNumber,preparationStep.getStep());
        }
        preparationStepNumber++;
        
        // Set up listeners to update nutrition preview in real-time
        setupNutritionPreviewListeners();
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == recipeCreateView.uploadButton){
            imageUrl = imageChoose(recipeCreateView);
            if(imageUrl != null) {
                recipeCreateView.updateImage(model.duplicateImage(imageUrl).toString());
            }

        }
        // Handle back button click
        if(event.getSource() == recipeCreateView.backButton){
            recipeCreateView.close();
        }
        // Handle add button click
        if (event.getSource() == recipeCreateView.addButton){
            handleAddButtonAction();
        }
        // Handle delete button click
        if (event.getSource() == recipeCreateView.deleteButton){
            handleDeleteButtonAction();
        }
        // Handle clear button click
        if (event.getSource() == recipeCreateView.clearButton) {
            handleClearButtonAction();
        }
        // Handle submit button click
        if(event.getSource() == recipeCreateView.submitButton){
            // Retrieve input values
            String recipeName = recipeCreateView.recipeNameTextField.getText();
            String cookingTime = recipeCreateView.cookingTimeTextField.getText();
            String preparationTime = recipeCreateView.preparationTextField.getText();
            // Validate input
            if (!model.validateRecipe(
                    recipeName,
                    cookingTime,
                    preparationTime,
                    recipeCreateView.recipeImage.getImage() == null ? "" : recipeCreateView.recipeImage.getImage().getUrl()
            ))   {
                return;
            }
            Recipe recipe;
            Integer recipeId = 0;
            String fullImageUrl = recipeCreateView.recipeImage.getImage().getUrl().replace("file:", "");
            String fileName = Paths.get(fullImageUrl).getFileName().toString();
            // Calculate total nutrition from ingredients
            int totalCalories = 0;
            double totalProtein = 0.0;
            double totalCarbohydrates = 0.0;
            double totalFat = 0.0;
            double totalFiber = 0.0;
            
            for(RecipeIngredient ingredient : recipeCreateView.tableView.getItems()) {
                if (ingredient.getUnitCalories() != null && ingredient.getUnitCalories() > 0) {
                    totalCalories += ingredient.getUnitCalories() * ingredient.getQuantity();
                    totalProtein += ingredient.getUnitProtein() * ingredient.getQuantity();
                    totalCarbohydrates += ingredient.getUnitCarbohydrates() * ingredient.getQuantity();
                    totalFat += ingredient.getUnitFat() * ingredient.getQuantity();
                }
            }
            
            // Create or update recipe based on editing status
            if(!recipeCreateView.isEdited) {
                System.out.println(fileName);
                recipe = new Recipe(0,recipeCreateView.recipeNameTextField.getText(),1,Integer.parseInt(recipeCreateView.cookingTimeTextField.getText()),Integer.parseInt(recipeCreateView.preparationTextField.getText()), "src/images/dishes/" + fileName);

                recipe.setCalories(totalCalories);
                recipe.setProtein(totalProtein);
                recipe.setCarbohydrates(totalCarbohydrates);
                recipe.setFat(totalFat);
                recipe.setFiber(totalFiber);
                recipeId = model.addRecipe(recipe);
            }
            else{
                recipe = new Recipe(recipeCreateView.editedRecipeId,recipeCreateView.recipeNameTextField.getText(),1,Integer.parseInt(recipeCreateView.cookingTimeTextField.getText()),Integer.parseInt(recipeCreateView.preparationTextField.getText()),recipeCreateView.recipeImage.getImage().getUrl().replace("file:", ""));
                recipe.setCalories(totalCalories);
                recipe.setProtein(totalProtein);
                recipe.setCarbohydrates(totalCarbohydrates);
                recipe.setFat(totalFat);
                recipe.setFiber(totalFiber);
                recipeId = recipeCreateView.editedRecipeId;
                model.updateRecipe(recipe);
            }
            List<RecipeIngredient> updatedRecipeIngredients = new ArrayList<>();
            for(RecipeIngredient recipeIngredient: recipeCreateView.tableView.getItems()){
                recipeIngredient.setRecipeId(recipeId);
                // Validate ingredient fields
                if(!model.validateRecipeIngredient(recipeIngredient.getName(),recipeIngredient.getQuantity(),recipeIngredient.getUnit())){
                    if(!recipeCreateView.isEdited) {
                        model.deleteRecipe(recipeId);
                    }
                    return;
                }
                
                // Auto-fill nutrition data if not manually entered and ingredient is recognized
                if (recipeIngredient.getUnitCalories() == null || recipeIngredient.getUnitCalories() == 0.0f) {
                    NutritionService.NutritionData nutritionData = NutritionService.calculateNutritionForQuantity(
                        recipeIngredient.getName(), recipeIngredient.getQuantity(), recipeIngredient.getUnit());
                    if (nutritionData != null) {
                        // Set unit nutrition values (per unit of measurement)
                        recipeIngredient.setUnitCalories((float) nutritionData.calories / recipeIngredient.getQuantity());
                        recipeIngredient.setUnitProtein(nutritionData.protein / recipeIngredient.getQuantity());
                        recipeIngredient.setUnitFat(nutritionData.fat / recipeIngredient.getQuantity());
                        recipeIngredient.setUnitCarbohydrates(nutritionData.carbohydrates / recipeIngredient.getQuantity());
                    }
                }
                if(!recipeCreateView.isEdited) {
                    model.addRecipeIngredient(recipeIngredient);
                }
                else {
                    updatedRecipeIngredients.add(recipeIngredient);

                }

            }

            if(recipeCreateView.isEdited){
                model.updateRecipeIngredient(recipeId,updatedRecipeIngredients);
            }

            List<PreparationStep> updatedPreparationSteps = new ArrayList<>();
            for(PreparationStep preparationStep : recipeCreateView.instructionTableView.getItems()){
                preparationStep.setRecipeId(recipeId);
                if(!recipeCreateView.isEdited) {
                    model.addRecipePreparationStep(preparationStep);
                }
                else{
                    updatedPreparationSteps.add(preparationStep);

                }
            }

            if(recipeCreateView.isEdited){
                model.updateRecipePreparationStep(recipeId, updatedPreparationSteps);
            }
            String message = "Recipe saved successfully!";
            int autoFilledCount = 0;
            for(RecipeIngredient ingredient : recipeCreateView.tableView.getItems()) {
                if (NutritionService.hasNutritionData(ingredient.getName())) {
                    autoFilledCount++;
                }
            }
            if (autoFilledCount > 0) {
                message += "\n\nAuto-filled nutrition data for " + autoFilledCount + " ingredients.";
            }
            Model.displayAlert(Alert.AlertType.INFORMATION,"Success", message);
            
            // Close the stage
            recipeCreateView.close();
            RecipeSelectView recipeSelectView = new RecipeSelectView();
            recipeSelectView.show();

        }


    }

    public String imageChoose(Stage stage){
        FileChooser fileChooser = new FileChooser();
        stage.setOpacity(0);
        Stage tempWindow = new Stage();
        File temp = fileChooser.showOpenDialog(tempWindow);
        stage.setOpacity(1);
        if (imageStore(temp)) {
            return temp.getAbsolutePath();
        }
        return null;
    }

    private boolean imageStore(File file) {
        return file != null && file.exists();
    }

    private void handleAddButtonAction() {
        Tab selectedTab = recipeCreateView.tabPane.getSelectionModel().getSelectedItem();
        if(selectedTab.equals(recipeCreateView.ingredientsTab)){
            // Create new ingredient with default values
            RecipeIngredient newIngredient = new RecipeIngredient(0,"",1.0f,"g","", 0.0f, 0.0f, 0.0f, 0.0f);
            recipeCreateView.tableView.getItems().add(newIngredient);
            // Update nutrition preview
            recipeCreateView.updateNutritionPreview();
        }
        else if(selectedTab.equals(recipeCreateView.instructionTab)){
            PreparationStep newInstruction = new PreparationStep(0, preparationStepNumber++,"");
            recipeCreateView.instructionTableView.getItems().add(newInstruction);
        }
    }


    private void handleDeleteButtonAction() {
        Tab selectedTab = recipeCreateView.tabPane.getSelectionModel().getSelectedItem();

        if (selectedTab.equals(recipeCreateView.ingredientsTab)) {
            int selectedIndex = recipeCreateView.tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                recipeCreateView.tableView.getItems().remove(selectedIndex);
                // Update nutrition preview after deletion
                recipeCreateView.updateNutritionPreview();
            } else {
                Model.displayAlert(Alert.AlertType.INFORMATION, "No rows selected", "Please select a row to delete.");
            }
        } else if (selectedTab.equals(recipeCreateView.instructionTab)) {
            int selectedIndex = recipeCreateView.instructionTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                recipeCreateView.instructionTableView.getItems().remove(selectedIndex);
            } else {
                Model.displayAlert(Alert.AlertType.INFORMATION, "No rows selected", "Please select a row to delete.");
            }
        }
    }


    private void handleClearButtonAction() {
        recipeCreateView.recipeNameTextField.clear();
        recipeCreateView.preparationTextField.clear();
        recipeCreateView.cookingTimeTextField.clear();
        recipeCreateView.tableView.getItems().clear();
        recipeCreateView.instructionTableView.getItems().clear();
        recipeCreateView.recipeImage.setImage(null);
        // Update nutrition preview after clearing
        recipeCreateView.updateNutritionPreview();
    }


    private void setupNutritionPreviewListeners() {
        // Check if tableView is available before setting up listeners
        if (recipeCreateView.tableView != null) {
            // Listen for changes in the ingredients table
            recipeCreateView.tableView.itemsProperty().addListener((obs, oldList, newList) -> {
                if (newList != null) {
                    newList.addListener((javafx.collections.ListChangeListener<RecipeIngredient>) change -> {
                        recipeCreateView.updateNutritionPreview();
                    });
                }
            });

            recipeCreateView.updateNutritionPreview();
        }
    }
}
