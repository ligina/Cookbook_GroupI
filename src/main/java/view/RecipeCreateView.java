package view;

import control.RecipeCreateController;
import dao.mappers.PreparationStep;
import dao.mappers.RecipeIngredient;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * This view is used to create or edit a recipe.
 *
 * @author 
 */
public class RecipeCreateView extends Stage {
    /**
     *  Boolean to judge whether the recipe is edited or created
     */
    public boolean isEdited = false;
    /**
     *  The recipe id of the recipe that is edited.
     */
    public Integer editedRecipeId = 0;
    /**
     *  Button for submit recipe
     */
    public Button submitButton,/**
     *  Button for cancel recipe
     */
    backButton,/**
     *  Button for upload image
     **/
    uploadButton,/**
     *  Button for add ingredient
     **/
    addButton,/**
     *  Button for delete ingredient
     **/
    deleteButton,/**
     *  Button for clear ingredient
     **/
    clearButton;
    /**
     *  TextField for recipe name
     */
    public TextField recipeNameTextField,/**
     *  TextField for recipe description
     **/
    preparationTextField,/**
     *  TextField for recipe cooking time
     **/
    cookingTimeTextField;
    /**
     * ImageView for recipe image
     */
    public ImageView recipeImage;
    /**
     * TableView for recipe ingredients
     */
    public TableView<RecipeIngredient> tableView;
    /**
     * TableView for recipe preparation steps
     */
    public TableView<PreparationStep> instructionTableView;
    /**
     *  Tab for ingredients
     */
    public Tab ingredientsTab;
    /**
     *  Tab for instruction
     */
    public Tab instructionTab;
    /**
     * Tab for ingredients and instruction
     */
    public TabPane tabPane;
    private Label cookingTimeLabel,preparationTimeLabel,serveNumberLabel;
    /**
     *  Instantiates a new create recipe view.
     */
    public RecipeCreateView() {
        this.setTitle("");
        this.setResizable(false);
        this.setWidth(800);
        this.setHeight(600);
        init();
    }

    /**
     * Instantiates a edit recipe view
     *
     * @param _editedRecipeId
     */
    public RecipeCreateView(int _editedRecipeId){
        this.editedRecipeId = _editedRecipeId;
        this.isEdited = true;
        this.setTitle("");
        this.setResizable(false);
        this.setWidth(800);
        this.setHeight(600);
        init();
    }
    /**
     * Setup image
     * @param temp
     */
    public void updateImage(String temp) {
        recipeImage.setImage(new Image("file:"+ temp));
    }

    private void init() {
        AnchorPane background = new AnchorPane();
        background.setPrefSize(800,600);
        background.setStyle("-fx-background-color: #f6ef97;");

        setupImage();
        Pane imagePane = new Pane();
        imagePane.setPrefSize(200,200);
        imagePane.setStyle("-fx-background-color: #f8f5f5;-fx-background-radius: 10;");
        imagePane.setLayoutX(20);
        imagePane.setLayoutY(250);
        imagePane.setEffect(new DropShadow());
        if (recipeImage != null) {
            imagePane.getChildren().add(recipeImage);
        }

        tabPane = new TabPane();
        tabPane.setPrefSize(450,350);
        tabPane.setLayoutX(300);
        tabPane.setLayoutY(150);
        setIngredientTab();
        setInstructionTab();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-background-color: transparent;");
        tabPane.getTabs().addAll(ingredientsTab,instructionTab);

        setRecipename();
        setCookingTime();
        setPrepartionTime();
        setServenumber();
        setUploadButton();
        setSubminButtton();
        setAddButton();
        setDeleteButton();
        setBackButton();
        setClearButton();



        background.getChildren().addAll(
                imagePane,
                tabPane,
                createHeader(),
                cookingTimeLabel,
                cookingTimeTextField,
                preparationTextField,
                preparationTimeLabel,
                serveNumberLabel,
                submitButton,
                uploadButton,
                addButton,
                deleteButton,
                backButton,
                clearButton

        );

        Scene scene = new Scene(background);
        this.setScene(scene);

    }

    private void setClearButton() {
        clearButton = new Button("Remove All");
        clearButton.setLayoutX(320);
        clearButton.setLayoutY(510);
        clearButton.setPrefSize(200,40);
        clearButton.setOnAction(new RecipeCreateController(this));

    }

    private void setBackButton() {
        backButton = new Button("Back");
        backButton.setOnAction(new RecipeCreateController(this));
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);
    }

    private void setDeleteButton() {
        deleteButton = new Button("-");
        deleteButton.setLayoutX(260);
        deleteButton.setLayoutY(285);
        deleteButton.setPrefSize(30,30);
        deleteButton.setOnAction(new RecipeCreateController(this));
    }

    private void setAddButton() {
        addButton = new Button("+");
        addButton.setLayoutX(260);
        addButton.setLayoutY(240);
        addButton.setPrefSize(30,30);
        addButton.setOnAction(new RecipeCreateController(this));
    }

    private void setSubminButtton() {
        submitButton = new Button("Save recipe");
        submitButton.setOnAction(new RecipeCreateController(this));
        submitButton.setLayoutX(550);
        submitButton.setLayoutY(510);
        submitButton.setPrefSize(200,40);
    }

    private void setUploadButton() {
        uploadButton = new Button("Upload pictures");
        uploadButton.setLayoutX(20);
        uploadButton.setLayoutY(470);
        uploadButton.setPrefSize(200,40);
        uploadButton.setOnAction(new RecipeCreateController(this));
    }

    private void setServenumber() {
        serveNumberLabel = new Label("Serve number: 1");
        serveNumberLabel.setLayoutX(300);
        serveNumberLabel.setLayoutY(120);
        serveNumberLabel.setFont(Font.font("System Bold Italic", 15));
    }

    private void setPrepartionTime() {
        preparationTextField = new TextField();
        preparationTextField.setLayoutX(200);
        preparationTextField.setLayoutY(200);
        preparationTextField.setPrefSize(80,20);
        preparationTextField.setAlignment(Pos.CENTER);

        preparationTimeLabel = new Label("Prepare Time(min):");
        preparationTimeLabel.setFont(new Font("Times New Roman", 20));
        preparationTimeLabel.setLayoutX(20);
        preparationTimeLabel.setLayoutY(200);

    }

    private void setCookingTime() {
        cookingTimeTextField = new TextField();
        cookingTimeTextField.setLayoutX(200);
        cookingTimeTextField.setLayoutY(165);
        cookingTimeTextField.setPrefSize(80,20);
        cookingTimeTextField.setAlignment(Pos.CENTER);

        cookingTimeLabel = new Label("Cooking Time(min):");
        cookingTimeLabel.setFont(new Font("Times New Roman", 20));
        cookingTimeLabel.setLayoutX(20);
        cookingTimeLabel.setLayoutY(160);
    }

    private void setRecipename() {
        recipeNameTextField = new TextField("");
        recipeNameTextField.setFont(new Font("Comic Sans MS", 50));
        recipeNameTextField.setStyle("-fx-text-fill: #333;");
        recipeNameTextField.setPrefSize(600,40);
        recipeNameTextField.setAlignment(Pos.CENTER);
    }

    private HBox createHeader(){
        HBox headerBox = new HBox();
        headerBox.setPrefWidth(800);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().add(recipeNameTextField);
        headerBox.setLayoutY(10);
        return headerBox;
    }

    private void setInstructionTab() {
        instructionTab = new Tab("Instructions");
        instructionTableView = new TableView<>();
        instructionTableView.setLayoutX(0);
        instructionTableView.setLayoutY(0);
        instructionTableView.setPrefSize(450,350);
        instructionTableView.setEffect(new DropShadow());
        instructionTableView.setEditable(true);

        TableColumn<PreparationStep,Integer> stepColumn = new TableColumn<>("Step");
        stepColumn.setCellValueFactory(new PropertyValueFactory<>("step"));
        stepColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stepColumn.setOnEditCommit(event ->{
            PreparationStep step = event.getRowValue();
            step.setStep(event.getNewValue());
        });

        TableColumn<PreparationStep,String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(event ->{
            PreparationStep step = event.getRowValue();
            step.setDescription(event.getNewValue());
                }
        );

        instructionTableView.getColumns().addAll(stepColumn,descriptionColumn);
        AnchorPane instructionPane = new AnchorPane();
        instructionPane.setPrefSize(450,350);
        instructionPane.setStyle("-fx-background-color: transparent;");
        instructionPane.setLayoutX(0);
        instructionPane.setLayoutY(0);
        instructionPane.getChildren().add(instructionTableView);

        instructionTab.setContent(instructionPane);

    }

    private void setIngredientTab() {
        ingredientsTab = new Tab("Ingredients");
        tableView = new TableView<>();
        tableView.setLayoutX(0);
        tableView.setLayoutY(0);
        tableView.setPrefSize(450,350);
        tableView.setEffect(new DropShadow());
        tableView.setEditable(true);


        TableColumn<RecipeIngredient, String> nameColumn = new TableColumn<>("Ingredient name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event ->{
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setName(event.getNewValue());
        });

        TableColumn<RecipeIngredient, Float> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(column -> {
            TextFieldTableCell<RecipeIngredient, Float> cell = new TextFieldTableCell<>(new FloatStringConverter());

            cell.setConverter(new FloatStringConverter() {
                @Override
                public Float fromString(String value) {
                    if (value.matches("-?\\d*(\\.\\d+)?")) {
                        return super.fromString(value);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warn");
                        alert.setHeaderText(null);
                        alert.setContentText("Please input number");
                        alert.showAndWait();
                        return null; // 返回 null 表示无效输入
                    }
                }
            });
            return cell;
        });
        quantityColumn.setOnEditCommit(event ->{
            RecipeIngredient ingredient = event.getRowValue();
            System.out.println(event.getNewValue());
            ingredient.setQuantity(event.getNewValue());
        });

        TableColumn<RecipeIngredient, String> unitsColumn = new TableColumn<>("Units");
        unitsColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        unitsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        unitsColumn.setOnEditCommit(event ->{
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setUnit(event.getNewValue());
        });

        TableColumn<RecipeIngredient, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(event ->{
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setDescription(event.getNewValue());
        });

        TableColumn<RecipeIngredient, Float> caloriesColumn = new TableColumn<>("Calories/unit");
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("unitCalories"));
        caloriesColumn.setCellFactory(column -> {
            TextFieldTableCell<RecipeIngredient, Float> cell = new TextFieldTableCell<>(new FloatStringConverter());
            cell.setConverter(new FloatStringConverter() {
                @Override
                public Float fromString(String value) {
                    if (value == null || value.trim().isEmpty()) {
                        return 0.0f;
                    }
                    if (value.matches("-?\\d*(\\.\\d+)?")) {
                        return super.fromString(value);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warn");
                        alert.setHeaderText(null);
                        alert.setContentText("Please input valid number for calories");
                        alert.showAndWait();
                        return 0.0f;
                    }
                }
            });
            return cell;
        });
        caloriesColumn.setOnEditCommit(event ->{
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setUnitCalories(event.getNewValue());
        });

        TableColumn<RecipeIngredient, Float> proteinColumn = new TableColumn<>("Protein/unit");
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("unitProtein"));
        proteinColumn.setCellFactory(column -> {
            TextFieldTableCell<RecipeIngredient, Float> cell = new TextFieldTableCell<>(new FloatStringConverter());
            cell.setConverter(new FloatStringConverter() {
                @Override
                public Float fromString(String value) {
                    if (value == null || value.trim().isEmpty()) {
                        return 0.0f;
                    }
                    if (value.matches("-?\\d*(\\.\\d+)?")) {
                        return super.fromString(value);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warn");
                        alert.setHeaderText(null);
                        alert.setContentText("Please input valid number for protein");
                        alert.showAndWait();
                        return 0.0f;
                    }
                }
            });
            return cell;
        });
        proteinColumn.setOnEditCommit(event ->{
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setUnitProtein(event.getNewValue());
        });

        TableColumn<RecipeIngredient, Float> fatColumn = new TableColumn<>("Fat/unit");
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("unitFat"));
        fatColumn.setCellFactory(column -> {
            TextFieldTableCell<RecipeIngredient, Float> cell = new TextFieldTableCell<>(new FloatStringConverter());
            cell.setConverter(new FloatStringConverter() {
                @Override
                public Float fromString(String value) {
                    if (value == null || value.trim().isEmpty()) {
                        return 0.0f;
                    }
                    if (value.matches("-?\\d*(\\.\\d+)?")) {
                        return super.fromString(value);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warn");
                        alert.setHeaderText(null);
                        alert.setContentText("Please input valid number for fat");
                        alert.showAndWait();
                        return 0.0f;
                    }
                }
            });
            return cell;
        });
        fatColumn.setOnEditCommit(event ->{
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setUnitFat(event.getNewValue());
        });

        TableColumn<RecipeIngredient, Float> carbsColumn = new TableColumn<>("Carbs/unit");
        carbsColumn.setCellValueFactory(new PropertyValueFactory<>("unitCarbohydrates"));
        carbsColumn.setCellFactory(column -> {
            TextFieldTableCell<RecipeIngredient, Float> cell = new TextFieldTableCell<>(new FloatStringConverter());
            cell.setConverter(new FloatStringConverter() {
                @Override
                public Float fromString(String value) {
                    if (value == null || value.trim().isEmpty()) {
                        return 0.0f;
                    }
                    if (value.matches("-?\\d*(\\.\\d+)?")) {
                        return super.fromString(value);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warn");
                        alert.setHeaderText(null);
                        alert.setContentText("Please input valid number for carbohydrates");
                        alert.showAndWait();
                        return 0.0f;
                    }
                }
            });
            return cell;
        });
        carbsColumn.setOnEditCommit(event ->{
            RecipeIngredient ingredient = event.getRowValue();
            ingredient.setUnitCarbohydrates(event.getNewValue());
        });

        tableView.getColumns().addAll(nameColumn, quantityColumn, unitsColumn, descriptionColumn, caloriesColumn, proteinColumn, fatColumn, carbsColumn);

        AnchorPane ingredientsPane = new AnchorPane();
        ingredientsPane.setPrefSize(450,350);
        ingredientsPane.setStyle("-fx-background-color: transparent;");
        ingredientsPane.setLayoutX(0);
        ingredientsPane.setLayoutY(0);
        ingredientsPane.getChildren().add(tableView);
        ingredientsTab.setContent(ingredientsPane);
    }

    private void setupImage() {
            recipeImage = new ImageView();
            recipeImage.setFitWidth(200);
            recipeImage.setFitHeight(200);
            recipeImage.setLayoutX(0);
            recipeImage.setLayoutY(0);
        }

}



