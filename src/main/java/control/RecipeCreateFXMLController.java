package control;

import dao.mappers.PreparationStep;
import dao.mappers.Recipe;
import dao.mappers.RecipeIngredient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Model;
import service.NutritionService;
import view.RecipeCreateView;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller for Recipe Create page
 */
public class RecipeCreateFXMLController implements Initializable {

    @FXML private TextField recipeNameTextField;
    @FXML private TextField cookingTimeTextField;
    @FXML private TextField preparationTextField;
    @FXML private Label serveNumberLabel;
    @FXML private VBox nutritionPreview;
    @FXML private Label previewCaloriesLabel;
    @FXML private Label previewProteinLabel;
    @FXML private Label previewFatLabel;
    @FXML private Label previewCarbsLabel;
    @FXML private ImageView recipeImage;
    @FXML private TabPane tabPane;
    @FXML private Tab ingredientsTab;
    @FXML private Tab instructionTab;
    @FXML private TableView<RecipeIngredient> tableView;
    @FXML private TableView<PreparationStep> instructionTableView;
    @FXML private Button backButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button uploadButton;
    @FXML private Button clearButton;
    @FXML private Button submitButton;

    private boolean isEdited = false;
    private Integer editedRecipeId = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupIngredientsTable();
        setupInstructionsTable();
        updateNutritionPreview();
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        // Close current window
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        
        // Open recipe select view
        view.RecipeSelectView recipeSelectView = new view.RecipeSelectView();
        recipeSelectView.show();
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        // Add new ingredient row
        if (ingredientsTab.isSelected()) {
            RecipeIngredient newIngredient = new RecipeIngredient(0, "", 1.0f, "g", "", 0.0f, 0.0f, 0.0f, 0.0f);
            tableView.getItems().add(newIngredient);
        } else if (instructionTab.isSelected()) {
            PreparationStep newStep = new PreparationStep();
            newStep.setStep(instructionTableView.getItems().size() + 1);
            instructionTableView.getItems().add(newStep);
        }
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        // Delete selected row
        if (ingredientsTab.isSelected()) {
            RecipeIngredient selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                tableView.getItems().remove(selected);
                updateNutritionPreview();
            }
        } else if (instructionTab.isSelected()) {
            PreparationStep selected = instructionTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                instructionTableView.getItems().remove(selected);
            }
        }
    }

    @FXML
    private void handleUploadButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Recipe Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        
        File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (selectedFile != null) {
            // Copy the image to the project directory and update the image view
            Model model = new Model();
            String duplicatedImagePath = model.duplicateImage(selectedFile.getAbsolutePath()).toString();
            updateImage(duplicatedImagePath);
        }
    }

    @FXML
    private void handleClearButton(ActionEvent event) {
        // Show confirmation dialog using project's standard format
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove all entered content? This action cannot be undone.", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                // User confirmed, proceed with clearing all content
                recipeNameTextField.clear();
                preparationTextField.clear();
                cookingTimeTextField.clear();
                tableView.getItems().clear();
                instructionTableView.getItems().clear();
                recipeImage.setImage(null);
                updateNutritionPreview();
            }
            // If user clicked NO or closed dialog, do nothing
        });
    }

    @FXML
    private void handleSubmitButton(ActionEvent event) {
        // Handle the save logic directly in this controller
        boolean saveSuccessful = saveRecipe();
        
        // Only close window and navigate if save was successful
        if (saveSuccessful) {
            // Close this window after saving
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
            
            // Open recipe select view
            view.RecipeSelectView recipeSelectView = new view.RecipeSelectView();
            recipeSelectView.show();
        }
        // If save failed, stay on current window so user can fix errors
    }

    /**
     * Save the recipe with all its ingredients and preparation steps
     * @return true if save was successful, false if validation failed
     */
    private boolean saveRecipe() {
        Model model = new Model();
        
        // Retrieve input values
        String recipeName = recipeNameTextField.getText();
        String cookingTime = cookingTimeTextField.getText();
        String preparationTime = preparationTextField.getText();
        
        // Validate input
        if (!model.validateRecipe(
                recipeName,
                cookingTime,
                preparationTime,
                recipeImage.getImage() == null ? "" : recipeImage.getImage().getUrl()
        )) {
            // Clear invalid fields but keep valid data
            clearInvalidRecipeFields(recipeName, cookingTime, preparationTime);
            return false; // Validation failed, stay on current window
        }
        
        Recipe recipe;
        Integer recipeId = 0;
        String fullImageUrl = recipeImage.getImage().getUrl().replace("file:", "");
        String fileName = Paths.get(fullImageUrl).getFileName().toString();
        
        // Calculate total nutrition from ingredients
        int totalCalories = 0;
        double totalProtein = 0.0;
        double totalCarbohydrates = 0.0;
        double totalFat = 0.0;
        double totalFiber = 0.0;
        
        for(RecipeIngredient ingredient : tableView.getItems()) {
            if (ingredient.getUnitCalories() != null && ingredient.getUnitCalories() > 0) {
                totalCalories += ingredient.getUnitCalories() * ingredient.getQuantity();
                totalProtein += ingredient.getUnitProtein() * ingredient.getQuantity();
                totalCarbohydrates += ingredient.getUnitCarbohydrates() * ingredient.getQuantity();
                totalFat += ingredient.getUnitFat() * ingredient.getQuantity();
            }
        }
        
        // Create or update recipe based on editing status
        if(!isEdited) {
            System.out.println(fileName);
            recipe = new Recipe(0, recipeName, 1, Integer.parseInt(cookingTime), Integer.parseInt(preparationTime), "src/images/dishes/" + fileName);
            // Set calculated nutrition values
            recipe.setCalories(totalCalories);
            recipe.setProtein(totalProtein);
            recipe.setCarbohydrates(totalCarbohydrates);
            recipe.setFat(totalFat);
            recipe.setFiber(totalFiber);
            recipeId = model.addRecipe(recipe);
        } else {
            recipe = new Recipe(editedRecipeId, recipeName, 1, Integer.parseInt(cookingTime), Integer.parseInt(preparationTime), recipeImage.getImage().getUrl().replace("file:", ""));
            // Set calculated nutrition values
            recipe.setCalories(totalCalories);
            recipe.setProtein(totalProtein);
            recipe.setCarbohydrates(totalCarbohydrates);
            recipe.setFat(totalFat);
            recipe.setFiber(totalFiber);
            recipeId = editedRecipeId;
            model.updateRecipe(recipe);
        }
        
        // Prepare updated recipe ingredients
        List<RecipeIngredient> updatedRecipeIngredients = new ArrayList<>();
        for(RecipeIngredient recipeIngredient: tableView.getItems()){
            recipeIngredient.setRecipeId(recipeId);
            // Validate ingredient fields
            if(!model.validateRecipeIngredient(recipeIngredient.getName(), recipeIngredient.getQuantity(), recipeIngredient.getUnit())){
                // Clear invalid ingredient fields but keep valid data
                clearInvalidIngredientFields(recipeIngredient);
                if(!isEdited) {
                    model.deleteRecipe(recipeId);
                }
                return false; // Validation failed, stay on current window
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
            // Directly add recipe ingredient when creating recipe
            if(!isEdited) {
                model.addRecipeIngredient(recipeIngredient);
            }
            // Temporarily store recipe ingredient to List when editing recipe
            else {
                updatedRecipeIngredients.add(recipeIngredient);
            }
        }
        
        // Update recipe ingredients if editing
        if(isEdited){
            model.updateRecipeIngredient(recipeId, updatedRecipeIngredients);
        }

        // Prepare updated preparation steps
        List<PreparationStep> updatedPreparationSteps = new ArrayList<>();
        for(PreparationStep preparationStep : instructionTableView.getItems()){
            preparationStep.setRecipeId(recipeId);
            // Directly add recipe preparation step when creating recipe
            if(!isEdited) {
                model.addRecipePreparationStep(preparationStep);
            }
            // Temporarily store recipe preparation step to List when editing recipe
            else{
                updatedPreparationSteps.add(preparationStep);
            }
        }
        
        // Update preparation steps if editing
        if(isEdited){
            model.updateRecipePreparationStep(recipeId, updatedPreparationSteps);
        }
        
        // Show success message with nutrition auto-fill info
        String message = "Recipe saved successfully!";
        int autoFilledCount = 0;
        for(RecipeIngredient ingredient : tableView.getItems()) {
            if (NutritionService.hasNutritionData(ingredient.getName())) {
                autoFilledCount++;
            }
        }
        if (autoFilledCount > 0) {
            message += "\n\nAuto-filled nutrition data for " + autoFilledCount + " ingredients.";
        }
        Model.displayAlert(Alert.AlertType.INFORMATION,"Success", message);
        
        return true; // Save was successful
    }

    private void setupIngredientsTable() {
        TableColumn<RecipeIngredient, String> nameColumn = new TableColumn<>("Ingredient");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setPrefWidth(130);
        nameColumn.setOnEditCommit(event -> {
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setName(event.getNewValue());
            autoFillNutrition(ingredient);
            updateNutritionPreview();
        });

        TableColumn<RecipeIngredient, Float> quantityColumn = new TableColumn<>("Qty");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setPrefWidth(70);
        quantityColumn.setCellFactory(column -> new TextFieldTableCell<>(new FloatStringConverter()));
        quantityColumn.setOnEditCommit(event -> {
            RecipeIngredient ingredient = event.getRowValue();
            Float newValue = event.getNewValue();
            
            // Validate quantity value
            if (newValue != null && newValue < 0) {
                Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Quantity cannot be negative!");
                // Reset to previous value
                tableView.refresh();
                return;
            }
            
            ingredient.setQuantity(newValue);
            autoFillNutrition(ingredient);
            updateNutritionPreview();
        });

        TableColumn<RecipeIngredient, String> unitsColumn = new TableColumn<>("Unit");
        unitsColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        unitsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        unitsColumn.setPrefWidth(60);
        unitsColumn.setOnEditCommit(event -> {
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setUnit(event.getNewValue());
            autoFillNutrition(ingredient);
            updateNutritionPreview();
        });

        TableColumn<RecipeIngredient, String> descriptionColumn = new TableColumn<>("Notes");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setPrefWidth(90);
        descriptionColumn.setOnEditCommit(event -> {
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setDescription(event.getNewValue());
        });

        TableColumn<RecipeIngredient, Float> caloriesColumn = new TableColumn<>("Cal/u");
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("unitCalories"));
        caloriesColumn.setPrefWidth(65);
        caloriesColumn.setCellFactory(column -> new TextFieldTableCell<>(new FloatStringConverter()));
        caloriesColumn.setOnEditCommit(event -> {
            RecipeIngredient ingredient = event.getRowValue();
            Float newValue = event.getNewValue();
            
            // Validate nutrition value
            if (newValue != null && newValue < 0) {
                Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Calories cannot be negative!");
                // Reset to previous value
                tableView.refresh();
                return;
            }
            
            ingredient.setUnitCalories(newValue);
            updateNutritionPreview();
        });

        TableColumn<RecipeIngredient, Float> proteinColumn = new TableColumn<>("Pro/u");
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("unitProtein"));
        proteinColumn.setPrefWidth(65);
        proteinColumn.setCellFactory(column -> new TextFieldTableCell<>(new FloatStringConverter()));
        proteinColumn.setOnEditCommit(event -> {
            RecipeIngredient ingredient = event.getRowValue();
            Float newValue = event.getNewValue();
            
            // Validate nutrition value
            if (newValue != null && newValue < 0) {
                Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Protein cannot be negative!");
                // Reset to previous value
                tableView.refresh();
                return;
            }
            
            ingredient.setUnitProtein(newValue);
            updateNutritionPreview();
        });

        TableColumn<RecipeIngredient, Float> fatColumn = new TableColumn<>("Fat/u");
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("unitFat"));
        fatColumn.setPrefWidth(60);
        fatColumn.setCellFactory(column -> new TextFieldTableCell<>(new FloatStringConverter()));
        fatColumn.setOnEditCommit(event -> {
            RecipeIngredient ingredient = event.getRowValue();
            Float newValue = event.getNewValue();
            
            // Validate nutrition value
            if (newValue != null && newValue < 0) {
                Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Fat cannot be negative!");
                // Reset to previous value
                tableView.refresh();
                return;
            }
            
            ingredient.setUnitFat(newValue);
            updateNutritionPreview();
        });

        TableColumn<RecipeIngredient, Float> carbsColumn = new TableColumn<>("Carb/u");
        carbsColumn.setCellValueFactory(new PropertyValueFactory<>("unitCarbohydrates"));
        carbsColumn.setPrefWidth(65);
        carbsColumn.setCellFactory(column -> new TextFieldTableCell<>(new FloatStringConverter()));
        carbsColumn.setOnEditCommit(event -> {
            RecipeIngredient ingredient = event.getRowValue();
            Float newValue = event.getNewValue();
            
            // Validate nutrition value
            if (newValue != null && newValue < 0) {
                Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Carbohydrates cannot be negative!");
                // Reset to previous value
                tableView.refresh();
                return;
            }
            
            ingredient.setUnitCarbohydrates(newValue);
            updateNutritionPreview();
        });

        tableView.getColumns().addAll(nameColumn, quantityColumn, unitsColumn, descriptionColumn, 
                                     caloriesColumn, proteinColumn, fatColumn, carbsColumn);
        tableView.setEditable(true);
    }

    private void setupInstructionsTable() {
        TableColumn<PreparationStep, Integer> stepColumn = new TableColumn<>("#");
        stepColumn.setCellValueFactory(new PropertyValueFactory<>("step"));
        stepColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stepColumn.setPrefWidth(40);
        stepColumn.setOnEditCommit(event -> {
            PreparationStep step = event.getRowValue();
            step.setStep(event.getNewValue());
        });

        TableColumn<PreparationStep, String> descriptionColumn = new TableColumn<>("Instruction Details");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setPrefWidth(530);
        descriptionColumn.setOnEditCommit(event -> {
            PreparationStep step = event.getRowValue();
            step.setDescription(event.getNewValue());
        });

        instructionTableView.getColumns().addAll(stepColumn, descriptionColumn);
        instructionTableView.setEditable(true);
    }

    private void autoFillNutrition(RecipeIngredient ingredient) {
        if (ingredient.getName() != null && !ingredient.getName().isEmpty() && 
            ingredient.getQuantity() != null && ingredient.getUnit() != null) {
            
            NutritionService.NutritionData nutritionData = NutritionService.calculateNutritionForQuantity(
                ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit());
            
            if (nutritionData != null) {
                ingredient.setUnitCalories((float) nutritionData.calories / ingredient.getQuantity());
                ingredient.setUnitProtein(nutritionData.protein / ingredient.getQuantity());
                ingredient.setUnitFat(nutritionData.fat / ingredient.getQuantity());
                ingredient.setUnitCarbohydrates(nutritionData.carbohydrates / ingredient.getQuantity());
                tableView.refresh();
            }
        }
    }

    public void updateNutritionPreview() {
        float totalCalories = 0;
        float totalProtein = 0;
        float totalFat = 0;
        float totalCarbs = 0;

        for (RecipeIngredient ingredient : tableView.getItems()) {
            if (ingredient != null) {
                totalCalories += ingredient.getTotalCalories();
                totalProtein += ingredient.getTotalProtein();
                totalFat += ingredient.getTotalFat();
                totalCarbs += ingredient.getTotalCarbohydrates();
            }
        }

        previewCaloriesLabel.setText(String.format("%.0f", totalCalories));
        previewProteinLabel.setText(String.format("%.1f", totalProtein));
        previewFatLabel.setText(String.format("%.1f", totalFat));
        previewCarbsLabel.setText(String.format("%.1f", totalCarbs));
    }

    public void updateImage(String imagePath) {
        Image image = new Image("file:" + imagePath);
        recipeImage.setImage(image);
    }

    // Getters and setters for external access
    public boolean isEdited() { return isEdited; }
    public void setEdited(boolean edited) { isEdited = edited; }
    public Integer getEditedRecipeId() { return editedRecipeId; }
    public void setEditedRecipeId(Integer editedRecipeId) { 
        this.editedRecipeId = editedRecipeId; 
        if (editedRecipeId != null && editedRecipeId > 0) {
            loadRecipeDataForEditing();
        }
    }
    
    /**
     * Clear invalid recipe fields while preserving valid data
     */
    private void clearInvalidRecipeFields(String recipeName, String cookingTime, String preparationTime) {
        // Clear recipe name if invalid
        if (recipeName == null || recipeName.isEmpty() || recipeName.length() > 70) {
            recipeNameTextField.clear();
        }
        
        // Clear cooking time if invalid
        if (cookingTime == null || cookingTime.isEmpty() || !cookingTime.matches("^\\d+$") || cookingTime.length() > 5) {
            cookingTimeTextField.clear();
        }
        
        // Clear preparation time if invalid
        if (preparationTime == null || preparationTime.isEmpty() || !preparationTime.matches("^\\d+$") || preparationTime.length() > 5) {
            preparationTextField.clear();
        }
        
        // Clear image if invalid (no valid URL)
        if (recipeImage.getImage() == null) {
            recipeImage.setImage(null);
        }
    }
    
    /**
     * Clear invalid ingredient fields while preserving valid data
     */
    private void clearInvalidIngredientFields(RecipeIngredient ingredient) {
        // Clear ingredient name if invalid
        if (ingredient.getName() == null || ingredient.getName().isEmpty() || ingredient.getName().length() > 30) {
            ingredient.setName("");
        }
        
        // Clear quantity if invalid
        if (ingredient.getQuantity() == null || ingredient.getQuantity() == 0.0f || 
            String.valueOf(ingredient.getQuantity()).length() > 10) {
            ingredient.setQuantity(1.0f); // Set to default value instead of null
        }
        
        // Clear unit if invalid
        if (ingredient.getUnit() == null || ingredient.getUnit().isEmpty() || 
            ingredient.getUnit().length() > 10 || !ingredient.getUnit().matches("^[a-zA-Z]+$")) {
            ingredient.setUnit("");
        }
        
        // Refresh the table to show cleared fields
        tableView.refresh();
    }
    
    /**
     * Load recipe data for editing
     */
    private void loadRecipeDataForEditing() {
        if (editedRecipeId != null && editedRecipeId > 0) {
            try {
                // Use Model to get recipe data
                Model model = new Model();
                model.getRecipes();
                
                // Get recipe basic data
                model.getRecipeBasicData(editedRecipeId);
                recipeNameTextField.setText(model.getRecipeName());
                cookingTimeTextField.setText(String.valueOf(model.getCookingTime()));
                preparationTextField.setText(String.valueOf(model.getPreparationTime()));
                
                // Load image if available
                String imageUrl = model.getImagePath();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    try {
                        Image image = new Image("file:" + imageUrl);
                        recipeImage.setImage(image);
                    } catch (Exception e) {
                        System.err.println("Error loading image: " + e.getMessage());
                    }
                }
                
                // Load ingredients
                tableView.getItems().clear();
                tableView.getItems().addAll(model.getIngredientByID(editedRecipeId));
                
                // Load instructions
                instructionTableView.getItems().clear();
                instructionTableView.getItems().addAll(model.getRecipePreparationSteps(editedRecipeId));
                
                // Update nutrition preview
                updateNutritionPreview();
                
            } catch (Exception e) {
                System.err.println("Error loading recipe data for editing: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}