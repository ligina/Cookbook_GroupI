package control;

import dao.mappers.RecipeIngredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import view.RecipeCreateView;
import view.RecipeDisplayView;
import view.RecipeSelectView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
import java.nio.file.InvalidPathException; 

public class RecipeDisplayFXMLController implements Initializable {

    @FXML private Label recipeNameLabel;
    @FXML private VBox nutritionPanel;
    @FXML private Label totalCaloriesLabel;
    @FXML private Label totalProteinLabel;
    @FXML private Label totalFatLabel;
    @FXML private Label totalCarbsLabel;
    @FXML private Label cookingTimeLabel;
    @FXML private Label preparationTimeLabel;
    @FXML private TextField serveNumberTextField;
    @FXML private ImageView recipeImage;
    @FXML private TabPane tabPane;
    @FXML private Tab ingredientsTab;
    @FXML private Tab instructionsTab;
    @FXML private TableView<RecipeIngredient> ingredientsTableView;
    @FXML private TextArea instructionsTextArea;
    @FXML private Button backButton;
    @FXML private Button editRecipeButton;
    @FXML private Button deleteRecipeButton;
    @FXML private Button saveNutritionButton;
    @FXML private Button convertButton;
    @FXML private ComboBox<String> unitFromCombo;
    @FXML private ComboBox<String> unitToCombo;

    private Integer selectedRecipeNumber;
    private ObservableList<RecipeIngredient> selectedIngredients = FXCollections.observableArrayList();
    private String imageUrl = "";

    public void setRecipeNumber(Integer recipeNumber) {
        this.selectedRecipeNumber = recipeNumber;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupIngredientsTable();
        setupUnitConversion();

        serveNumberTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            updateNutritionalDisplay();
        });
    }

    private void setupUnitConversion() {

        unitFromCombo.getItems().addAll("grams", "kg", "oz", "lbs", "ml", "liters", "cups", "tbsp", "tsp");
        unitToCombo.getItems().addAll("grams", "kg", "oz", "lbs", "ml", "liters", "cups", "tbsp", "tsp");
    }

    public void initializeData() {
        if (selectedRecipeNumber != null) {

            Model model = new Model();
            model.getRecipes();

            model.getRecipeBasicData(selectedRecipeNumber);
            recipeNameLabel.setText(model.getRecipeName());
            cookingTimeLabel.setText("Cook Time: " + model.getCookingTime() + " min");
            preparationTimeLabel.setText("Prep Time: " + model.getPreparationTime() + " min");
            serveNumberTextField.setText("1");

            imageUrl = model.getImagePath();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                setupImageView(imageUrl);
            }

            selectedIngredients.clear();
            selectedIngredients.addAll(model.getIngredients(selectedRecipeNumber));
            ingredientsTableView.setItems(selectedIngredients);

            String instructions = model.getInstructions(selectedRecipeNumber);
            instructionsTextArea.setText(instructions);
            
            updateNutritionalDisplay();
        }
    }

    @FXML
    private void handleBackButton(ActionEvent event) {

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        RecipeSelectView recipeSelectView = new RecipeSelectView();
        recipeSelectView.show();
    }

    @FXML
    private void handleEditButton(ActionEvent event) {
        if (selectedRecipeNumber != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to edit this recipe?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    
                    Stage stage = (Stage) editRecipeButton.getScene().getWindow();
                    stage.close();
                    
                    
                    RecipeCreateView recipeCreateView = new RecipeCreateView(selectedRecipeNumber);
                    recipeCreateView.show();
                }
            });
        }
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        if (selectedRecipeNumber != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this recipe?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    Model model = new Model();
                    model.deleteRecipe(selectedRecipeNumber);
                    Model.displayAlert(Alert.AlertType.INFORMATION, "Info", "Successfully deleted this recipe");
                    
                    
                    Stage stage = (Stage) deleteRecipeButton.getScene().getWindow();
                    stage.close();
                    
                    RecipeSelectView recipeSelectView = new RecipeSelectView();
                    recipeSelectView.show();
                }
            });
        }
    }

    @FXML
    private void handleSaveNutritionButton(ActionEvent event) {

        if (selectedRecipeNumber != null) {
            try {
                Model model = new Model();
                model.updateRecipeIngredient(selectedRecipeNumber, 
                    new ArrayList<>(selectedIngredients));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Nutrition data saved successfully!");
                alert.setContentText("All nutrition values have been updated in the database.");
                alert.showAndWait();
                updateNutritionalDisplay();
                
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to save nutrition data");
                alert.setContentText("Error: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleConvertButton(ActionEvent event) {
        String fromUnit = unitFromCombo.getValue();
        String toUnit = unitToCombo.getValue();
        
        if (fromUnit == null || toUnit == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Unit Conversion");
            alert.setHeaderText("Please select both units");
            alert.setContentText("Choose both 'from' and 'to' units for conversion.");
            alert.showAndWait();
            return;
        }

        double conversionFactor = getConversionFactor(fromUnit, toUnit);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Unit Conversion");
        alert.setHeaderText("Conversion Result");
        alert.setContentText(String.format("1 %s = %.4f %s", fromUnit, conversionFactor, toUnit));
        alert.showAndWait();
    }

    private double getConversionFactor(String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) return 1.0;

        if (fromUnit.equals("kg") && toUnit.equals("grams")) return 1000.0;
        if (fromUnit.equals("grams") && toUnit.equals("kg")) return 0.001;
        if (fromUnit.equals("lbs") && toUnit.equals("grams")) return 453.592;
        if (fromUnit.equals("grams") && toUnit.equals("lbs")) return 0.00220462;
        if (fromUnit.equals("oz") && toUnit.equals("grams")) return 28.3495;
        if (fromUnit.equals("grams") && toUnit.equals("oz")) return 0.035274;
        if (fromUnit.equals("liters") && toUnit.equals("ml")) return 1000.0;
        if (fromUnit.equals("ml") && toUnit.equals("liters")) return 0.001;
        if (fromUnit.equals("cups") && toUnit.equals("ml")) return 236.588;
        if (fromUnit.equals("ml") && toUnit.equals("cups")) return 0.00422675;
        if (fromUnit.equals("tbsp") && toUnit.equals("ml")) return 14.7868;
        if (fromUnit.equals("ml") && toUnit.equals("tbsp")) return 0.067628;
        if (fromUnit.equals("tsp") && toUnit.equals("ml")) return 4.92892;
        if (fromUnit.equals("ml") && toUnit.equals("tsp")) return 0.202884;
        
        return 1.0;
    }

    private void setupIngredientsTable() {
        // Disable editing in display view and show warning when user tries to edit
        ingredientsTableView.setEditable(false);
        
        // Add double-click listener to show warning
        ingredientsTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Model.displayAlert(Alert.AlertType.WARNING, "Warning", 
                    "Cannot modify recipe in display view! Please use the 'Edit Recipe' button to make changes.");
            }
        });
        
        TableColumn<RecipeIngredient, String> nameColumn = new TableColumn<>("Ingredient");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        // Remove cell factory to prevent editing
        // nameColumn.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn());
        // Remove edit commit handler since editing is disabled
        // nameColumn.setOnEditCommit(event -> {
        //     event.getRowValue().setName(event.getNewValue());
        //     updateNutritionalDisplay();
        // });
        nameColumn.setPrefWidth(140);
        TableColumn<RecipeIngredient, Float> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> {
            RecipeIngredient ingredient = cellData.getValue();
            float servingSize = getServingSize();
            float adjustedQuantity = ingredient.getQuantity() * servingSize;
            return new SimpleFloatProperty(adjustedQuantity).asObject();
        });
        // Remove cell factory and edit handler to prevent editing
        // quantityColumn.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn(...));
        // quantityColumn.setOnEditCommit(event -> {...});
        quantityColumn.setPrefWidth(80);
        TableColumn<RecipeIngredient, String> unitColumn = new TableColumn<>("Unit");
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        // Remove cell factory and edit handler to prevent editing
        // unitColumn.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn());
        // unitColumn.setOnEditCommit(event -> {
        //     event.getRowValue().setUnit(event.getNewValue());
        //     updateNutritionalDisplay();
        // });
        unitColumn.setPrefWidth(60);
        TableColumn<RecipeIngredient, String> descriptionColumn = new TableColumn<>("Notes");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        // Remove cell factory and edit handler to prevent editing
        // descriptionColumn.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn());
        // descriptionColumn.setOnEditCommit(event -> {
        //     event.getRowValue().setDescription(event.getNewValue());
        // });
        descriptionColumn.setPrefWidth(100);
        TableColumn<RecipeIngredient, Float> caloriesColumn = new TableColumn<>("Calories (kcal)");
        caloriesColumn.setCellValueFactory(cellData -> {
            RecipeIngredient ingredient = cellData.getValue();
            float servingSize = getServingSize();
            float totalCalories = ingredient.getTotalCalories() * servingSize;
            return new SimpleFloatProperty(totalCalories).asObject();
        });
        // Remove cell factory and edit handler to prevent editing
        // caloriesColumn.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn(...));
        // caloriesColumn.setOnEditCommit(event -> {...});
        caloriesColumn.setPrefWidth(100);

        TableColumn<RecipeIngredient, Float> proteinColumn = new TableColumn<>("Protein (g)");
        proteinColumn.setCellValueFactory(cellData -> {
            RecipeIngredient ingredient = cellData.getValue();
            float servingSize = getServingSize();
            float totalProtein = ingredient.getTotalProtein() * servingSize;
            return new SimpleFloatProperty(totalProtein).asObject();
        });
        // Remove cell factory and edit handler to prevent editing
        // proteinColumn.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn(...));
        // proteinColumn.setOnEditCommit(event -> {...});
        proteinColumn.setPrefWidth(90);

        TableColumn<RecipeIngredient, Float> fatColumn = new TableColumn<>("Fat (g)");
        fatColumn.setCellValueFactory(cellData -> {
            RecipeIngredient ingredient = cellData.getValue();
            float servingSize = getServingSize();
            float totalFat = ingredient.getTotalFat() * servingSize;
            return new SimpleFloatProperty(totalFat).asObject();
        });
        // Remove cell factory and edit handler to prevent editing
        // fatColumn.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn(...));
        // fatColumn.setOnEditCommit(event -> {...});
        fatColumn.setPrefWidth(80);

        TableColumn<RecipeIngredient, Float> carbsColumn = new TableColumn<>("Carbs (g)");
        carbsColumn.setCellValueFactory(cellData -> {
            RecipeIngredient ingredient = cellData.getValue();
            float servingSize = getServingSize();
            float totalCarbs = ingredient.getTotalCarbohydrates() * servingSize;
            return new SimpleFloatProperty(totalCarbs).asObject();
        });
        // Remove cell factory and edit handler to prevent editing
        // carbsColumn.setCellFactory(javafx.scene.control.cell.TextFieldTableCell.forTableColumn(...));
        // carbsColumn.setOnEditCommit(event -> {...});
        carbsColumn.setPrefWidth(80);

        ingredientsTableView.getColumns().addAll(nameColumn, quantityColumn, unitColumn, descriptionColumn, 
                                                caloriesColumn, proteinColumn, fatColumn, carbsColumn);
    }


    private void setupImageView(String url) {
        String imageUrlToLoad = null;
        if (url != null && !url.isEmpty()) {
            try {
                Path imagePath = Paths.get(url);
                if (Files.exists(imagePath)) {
                    imageUrlToLoad = imagePath.toUri().toString();
                } else {
                    imageUrlToLoad = url;
                }
            } catch (InvalidPathException e) {
                imageUrlToLoad = url;
            }
        }

        if (imageUrlToLoad != null) {
            try {
                Image image = new Image(imageUrlToLoad);
                recipeImage.setImage(image);
            } catch (IllegalArgumentException e) {
                System.err.println("Error loading image from URL: " + imageUrlToLoad);
                e.printStackTrace();
                recipeImage.setImage(null);
            }
        } else {
            recipeImage.setImage(null);
        }
    }

    private float getServingSize() {
        String serveText = serveNumberTextField.getText();
        
        
        Model model = new Model();
        if (!model.validateServingNumber(serveText)) {
            
            serveNumberTextField.setText("1");
            return 1.0f;
        }
        
        try {
            return Float.parseFloat(serveText);
        } catch (NumberFormatException e) {
            serveNumberTextField.setText("1");
            return 1.0f;
        }
    }

    public void updateNutritionalDisplay() {
        float totalCalories = 0;
        float totalProtein = 0;
        float totalFat = 0;
        float totalCarbs = 0;
        float servingSize = getServingSize();

        for (RecipeIngredient ingredient : selectedIngredients) {
            if (ingredient != null) {
                
                totalCalories += ingredient.getTotalCalories() * servingSize;
                totalProtein += ingredient.getTotalProtein() * servingSize;
                totalFat += ingredient.getTotalFat() * servingSize;
                totalCarbs += ingredient.getTotalCarbohydrates() * servingSize;
            }
        }
        totalCaloriesLabel.setText(String.format("%.0f", totalCalories));
        totalProteinLabel.setText(String.format("%.1f", totalProtein));
        totalFatLabel.setText(String.format("%.1f", totalFat));
        totalCarbsLabel.setText(String.format("%.1f", totalCarbs));
        ingredientsTableView.refresh();
    }
}