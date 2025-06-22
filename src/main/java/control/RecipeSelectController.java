package control;

import dao.mappers.Recipe;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import view.RecipeCreateView;
import view.RecipeDisplayView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RecipeSelectController {
    @FXML private HBox recipesContainer;
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button backButton;
    @FXML private Button createButton;
    @FXML private Button nextButton;
    @FXML private Button prevButton;

    private LinkedHashMap<Integer, String> imageUrls = new LinkedHashMap<>();
    private List<String> imageNames = new ArrayList<>();
    private int currentPage = 0;
    private static final int ITEMS_PER_PAGE = 3;

    private final Model model = new Model();

    @FXML
    public void initialize() {
        // 设置容器属性以确保正确布局
        recipesContainer.setAlignment(Pos.CENTER);
        recipesContainer.setSpacing(50);
        recipesContainer.setPrefHeight(300);
        recipesContainer.setPrefWidth(700);

        initializeData();
        displayRecipesForCurrentPage();
        updatePaginationControls();
    }

    private void initializeData() {
        List<Recipe> allRecipes = model.getAllRecipes();
        System.out.println("Found " + allRecipes.size() + " recipes");
        for(Recipe recipe : allRecipes) {
            imageNames.add(recipe.getRecipeName());
            imageUrls.put(recipe.getRecipeId(), recipe.getImageUrl());
            System.out.println("Added recipe: " + recipe.getRecipeName() + " with image: " + recipe.getImageUrl());
        }
    }

    public void update(LinkedHashMap<Integer, String> _imageUrls, ArrayList<String> _imageNames) {
        imageUrls = _imageUrls;
        imageNames = _imageNames;
        currentPage = 0;
        displayRecipesForCurrentPage();
        updatePaginationControls();
    }

    @FXML
    private void handleSearch() {
        String recipeName = searchField.getText();
        System.out.println("Searching for: " + recipeName);
        update(model.updateImageUrls(recipeName), model.updateImageNames(recipeName));
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCreate() {
        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();
        new RecipeCreateView().show();
    }

    @FXML
    private void handleNext() {
        if ((currentPage + 1) * ITEMS_PER_PAGE < imageUrls.size()) {
            currentPage++;
            displayRecipesForCurrentPage();
            updatePaginationControls();
        }
    }

    @FXML
    private void handlePrev() {
        if (currentPage > 0) {
            currentPage--;
            displayRecipesForCurrentPage();
            updatePaginationControls();
        }
    }

    private void updatePaginationControls() {
        prevButton.setDisable(currentPage == 0);
        nextButton.setDisable((currentPage + 1) * ITEMS_PER_PAGE >= imageUrls.size());
    }

    private void displayRecipesForCurrentPage() {
        System.out.println("Displaying page " + currentPage + " of recipes");
        recipesContainer.getChildren().clear();

        if (imageUrls.isEmpty()) {
            System.out.println("No recipes to display");
            // 添加空状态提示
            Label noRecipesLabel = new Label("No recipes found");
            noRecipesLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: gray;");
            recipesContainer.getChildren().add(noRecipesLabel);
            return;
        }

        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, imageUrls.size());

        List<Integer> recipeIds = new ArrayList<>(imageUrls.keySet());
        for (int i = startIndex; i < endIndex; i++) {
            Integer recipeId = recipeIds.get(i);
            String imagePath = imageUrls.get(recipeId);
            String recipeName = imageNames.get(i);

            System.out.println("Creating button for recipe: " + recipeName);

            try {
                VBox recipeBox = createRecipeButton(recipeId, imagePath, recipeName);
                recipesContainer.getChildren().add(recipeBox);
            } catch (IOException e) {
                System.err.println("Error creating recipe button: " + e.getMessage());
                VBox errorBox = createErrorButton(recipeName);
                recipesContainer.getChildren().add(errorBox);
            }
        }
    }

    private VBox createRecipeButton(Integer recipeId, String imagePath, String recipeName) throws IOException {
        // 检查文件是否存在
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            System.err.println("Image file not found: " + imagePath);
            throw new IOException("Image file not found: " + imagePath);
        }

        // 加载图片
        Image recipeImage = new Image(new FileInputStream(imageFile));
        ImageView imageView = new ImageView(recipeImage);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        // 创建按钮
        Button recipeButton = new Button();
        recipeButton.setGraphic(imageView);
        recipeButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        recipeButton.setOnAction(e -> {
            System.out.println("Selected recipe: " + recipeName);
            Stage stage = (Stage) recipeButton.getScene().getWindow();
            stage.close();
            new RecipeDisplayView(recipeId).show();
        });

        // 创建标签
        Label nameLabel = new Label(recipeName);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        nameLabel.setMaxWidth(200);
        nameLabel.setWrapText(true);

        // 组合成VBox
        VBox recipeBox = new VBox(10, recipeButton, nameLabel);
        recipeBox.setAlignment(Pos.CENTER);

        return recipeBox;
    }

    private VBox createErrorButton(String recipeName) {
        // 创建错误占位符
        Label errorLabel = new Label("Image Missing");
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        // 创建占位按钮
        Button placeholderButton = new Button();
        placeholderButton.setPrefSize(200, 200);
        placeholderButton.setStyle("-fx-background-color: lightgray;");

        // 创建标签
        Label nameLabel = new Label(recipeName);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        nameLabel.setMaxWidth(200);
        nameLabel.setWrapText(true);

        // 组合成VBox
        VBox recipeBox = new VBox(10, placeholderButton, nameLabel);
        recipeBox.setAlignment(Pos.CENTER);

        return recipeBox;
    }
}
