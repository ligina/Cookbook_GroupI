package control;

import dao.mappers.PreparationStep;
import dao.mappers.Recipe;
import dao.mappers.RecipeIngredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Model;
import view.RecipeSelectView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class RecipeCreateController {

    private int preparationStepNumber = 0;
    private Model model = new Model();
    private String imageUrl = "";
    private boolean isEdited = false;
    private Integer editedRecipeId = 0;

    @FXML private TextField recipeNameTextField;
    @FXML private TextField cookingTimeTextField;
    @FXML private TextField preparationTextField;
    @FXML private ImageView recipeImage;
    @FXML private TabPane tabPane;
    @FXML private Tab ingredientsTab;
    @FXML private Tab instructionTab;
    @FXML private TableView<RecipeIngredient> tableView;
    @FXML private TableView<PreparationStep> instructionTableView;
    @FXML private Button submitButton;
    @FXML private Button backButton;
    @FXML private Button uploadButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Label serveNumberLabel;

    private ObservableList<RecipeIngredient> ingredientList = FXCollections.observableArrayList();
    private ObservableList<PreparationStep> instructionList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableViewColumns();
        setupInstructionTableViewColumns();
        tableView.setItems(ingredientList);
        instructionTableView.setItems(instructionList);
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public void setEditedRecipeId(Integer editedRecipeId) {
        this.editedRecipeId = editedRecipeId;
        loadRecipeData();
    }

    private void loadRecipeData() {
        // 实现加载食谱数据的逻辑
    }

    private void setupTableViewColumns() {
        TableColumn<RecipeIngredient, String> nameColumn = new TableColumn<>("Ingredient name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setName(event.getNewValue());
        });

        TableColumn<RecipeIngredient, Float> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(column -> createFloatTableCell());

        TableColumn<RecipeIngredient, String> unitsColumn = new TableColumn<>("Units");
        unitsColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        unitsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        unitsColumn.setOnEditCommit(event -> {
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setUnit(event.getNewValue());
        });

        TableColumn<RecipeIngredient, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(event -> {
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setDescription(event.getNewValue());
        });

        TableColumn<RecipeIngredient, Float> caloriesColumn = new TableColumn<>("Calories/unit");
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("unitCalories"));
        caloriesColumn.setCellFactory(column -> createFloatTableCell());

        TableColumn<RecipeIngredient, Float> proteinColumn = new TableColumn<>("Protein/unit");
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("unitProtein"));
        proteinColumn.setCellFactory(column -> createFloatTableCell());

        TableColumn<RecipeIngredient, Float> fatColumn = new TableColumn<>("Fat/unit");
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("unitFat"));
        fatColumn.setCellFactory(column -> createFloatTableCell());

        TableColumn<RecipeIngredient, Float> carbsColumn = new TableColumn<>("Carbs/unit");
        carbsColumn.setCellValueFactory(new PropertyValueFactory<>("unitCarbohydrates"));
        carbsColumn.setCellFactory(column -> createFloatTableCell());

        tableView.getColumns().setAll(nameColumn, quantityColumn, unitsColumn, descriptionColumn,
                caloriesColumn, proteinColumn, fatColumn, carbsColumn);
        tableView.setEditable(true);
    }

    private TextFieldTableCell<RecipeIngredient, Float> createFloatTableCell() {
        TextFieldTableCell<RecipeIngredient, Float> cell = new TextFieldTableCell<>(new FloatStringConverter());
        cell.setConverter(new FloatStringConverter() {
            @Override
            public Float fromString(String value) {
                if (value.matches("-?\\d*(\\.\\d+)?")) {
                    return super.fromString(value);
                } else {
                    showAlert("Warn", "Please input number");
                    return null;
                }
            }
        });
        return cell;
    }

    private void setupInstructionTableViewColumns() {
        TableColumn<PreparationStep, Integer> stepColumn = new TableColumn<>("Step");
        stepColumn.setCellValueFactory(new PropertyValueFactory<>("step"));
        stepColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stepColumn.setOnEditCommit(event -> {
            PreparationStep step = event.getRowValue();
            step.setStep(event.getNewValue());
        });

        TableColumn<PreparationStep, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(event -> {
            PreparationStep step = event.getRowValue();
            step.setDescription(event.getNewValue());
        });

        instructionTableView.getColumns().setAll(stepColumn, descriptionColumn);
        instructionTableView.setEditable(true);
    }

    @FXML
    private void handleUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (file != null && file.exists()) {
            imageUrl = file.getAbsolutePath();
            recipeImage.setImage(new Image("file:" + imageUrl));
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        ((Stage) backButton.getScene().getWindow()).close();
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab.equals(ingredientsTab)) {
            ingredientList.add(new RecipeIngredient(0, "", 0.0f, "", "", 0.0f, 0.0f, 0.0f, 0.0f));
        } else if (selectedTab.equals(instructionTab)) {
            instructionList.add(new PreparationStep(0, preparationStepNumber++, ""));
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab.equals(ingredientsTab)) {
            RecipeIngredient selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) ingredientList.remove(selected);
        } else if (selectedTab.equals(instructionTab)) {
            PreparationStep selected = instructionTableView.getSelectionModel().getSelectedItem();
            if (selected != null) instructionList.remove(selected);
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        recipeNameTextField.clear();
        preparationTextField.clear();
        cookingTimeTextField.clear();
        ingredientList.clear();
        instructionList.clear();
        recipeImage.setImage(null);
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String recipeName = recipeNameTextField.getText();
        String cookingTime = cookingTimeTextField.getText();
        String preparationTime = preparationTextField.getText();
        String imagePath = recipeImage.getImage() == null ? "" : recipeImage.getImage().getUrl();

        if (!model.validateRecipe(recipeName, cookingTime, preparationTime, imagePath)) {
            return;
        }

        String fileName = Paths.get(imagePath.replace("file:", "")).getFileName().toString();
        Recipe recipe = new Recipe(
                isEdited ? editedRecipeId : 0,
                recipeName,
                1,
                Integer.parseInt(cookingTime),
                Integer.parseInt(preparationTime),
                isEdited ? imagePath.replace("file:", "") : "src/images/dishes/" + fileName
        );

        int recipeId = isEdited ?
                model.updateRecipe(recipe) :
                model.addRecipe(recipe);

        if (recipeId > 0) {
            saveIngredients(recipeId);
            saveInstructions(recipeId);
            showAlert("Success", "Recipe saved successfully!");
            closeAndOpenRecipeSelect();
        } else {
            showAlert("Error", "Failed to save recipe");
        }
    }

    private void saveIngredients(int recipeId) {
        for (RecipeIngredient ingredient : ingredientList) {
            ingredient.setRecipeId(recipeId);
            if (isEdited) {
                model.updateRecipeIngredient(ingredient);
            } else {
                model.addRecipeIngredient(ingredient);
            }
        }
    }

    private void saveInstructions(int recipeId) {
        for (PreparationStep step : instructionList) {
            step.setRecipeId(recipeId);
            if (isEdited) {
                model.updateRecipePreparationStep(step);
            } else {
                model.addRecipePreparationStep(step);
            }
        }
    }

    private void closeAndOpenRecipeSelect() {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
        try {
            new RecipeSelectView().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
