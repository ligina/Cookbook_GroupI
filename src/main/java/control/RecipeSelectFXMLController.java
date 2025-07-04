package control;

import dao.mappers.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import view.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;


public class RecipeSelectFXMLController implements Initializable {

    @FXML private Label titleLabel;
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Pane recipePane;
    @FXML private Button prevButton;
    @FXML private Button nextButton;
    @FXML private Button backButton;
    @FXML private Button createRecipeButton;
    @FXML private Button refreshButton;
    @FXML private Label pageLabel;
    @FXML private Label recipeCountLabel;

    private Model model;
    private LinkedHashMap<Integer, String> imageUrls = new LinkedHashMap<>();
    private List<String> imageNames;
    private HashMap<Button, Integer> buttonMap = new HashMap<>();
    private int currentPage = 0;
    private static final int ITEMS_PER_PAGE = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new Model();
        initializeData();
        updateRecipeButtons();
        updatePageInfo();
    }

    private void initializeData() {
        List<Recipe> allRecipes = model.getAllRecipes();
        imageUrls = new LinkedHashMap<>();
        imageNames = new ArrayList<>();
        for(Recipe recipe : allRecipes) {
            imageNames.add(recipe.getRecipeName());
            imageUrls.put(recipe.getRecipeId(), recipe.getImageUrl());
        }
    }

    @FXML
    private void handleSearchButton(ActionEvent event) {
        String recipeName = searchField.getText();
        
        // EC V3: Search term cannot be empty
        if (recipeName == null || recipeName.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Search term cannot be empty!");
            return;
        }
        
        // EC V2: Search term length cannot exceed 30 characters
        if (recipeName.length() > 30) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Search term length cannot exceed 30 characters!");
            return;
        }
        
        updateImageData(model.updateImageUrls(recipeName), model.updateImageNames(recipeName));
        updateRecipeButtons();
        searchField.setText(recipeName);
    }

    @FXML
    private void handlePrevButton(ActionEvent event) {
        if (currentPage > 0) {
            currentPage--;
            updateRecipeButtons();
            updatePageInfo();
        }
    }

    @FXML
    private void handleNextButton(ActionEvent event) {
        if ((currentPage + 1) * ITEMS_PER_PAGE < imageUrls.size()) {
            currentPage++;
            updateRecipeButtons();
            updatePageInfo();
        }
    }

    @FXML
    private void handleRefreshButton(ActionEvent event) {
        initializeData();
        currentPage = 0;
        updateRecipeButtons();
        updatePageInfo();
        searchField.clear();
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCreateRecipeButton(ActionEvent event) {
        RecipeCreateView recipeCreateView = new RecipeCreateView();
        recipeCreateView.show();
    }

    private void updateImageData(LinkedHashMap<Integer, String> newImageUrls, ArrayList<String> newImageNames) {
        this.imageUrls = newImageUrls;
        this.imageNames = newImageNames;
        this.currentPage = 0; // Reset to first page
        updatePageInfo();
    }

    private void updatePageInfo() {
        int totalPages = (int) Math.ceil((double) imageUrls.size() / ITEMS_PER_PAGE);
        if (totalPages == 0) totalPages = 1;
        pageLabel.setText("Page " + (currentPage + 1) + " of " + totalPages);
        recipeCountLabel.setText(imageUrls.size() + " recipe(s) found");

        prevButton.setDisable(currentPage == 0);
        nextButton.setDisable((currentPage + 1) * ITEMS_PER_PAGE >= imageUrls.size());
    }

    private void updateRecipeButtons() {
        recipePane.getChildren().clear();
        buttonMap.clear();
        
        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, imageUrls.size());
        
        for (int i = start; i < end; i++) {
            Map.Entry<Integer, String> entry = new ArrayList<>(imageUrls.entrySet()).get(i);
            String url = entry.getValue();
            String imageName = imageNames.get(i);
            File imageUrl = new File(url);
            
            if (!imageUrl.exists()) {
                System.out.println("Image not found: " + url);
                continue;
            }

            Image recipeImage;
            try {
                recipeImage = new Image(new FileInputStream(imageUrl));
            } catch (FileNotFoundException e) {
                System.out.println("Image not found: " + e.getMessage());
                continue;
            }

            VBox recipeButton = createButtonWithImage(entry.getKey(), recipeImage, imageName, 
                                                    50 + (i - start) * 320, 20);
            recipePane.getChildren().add(recipeButton);
        }
    }

    private VBox createButtonWithImage(Integer recipeNumber, Image recipeImage, String imageName, int x, int y) {
        VBox recipeCard = new VBox();
        recipeCard.setAlignment(Pos.CENTER);
        recipeCard.setLayoutX(x);
        recipeCard.setLayoutY(y);
        recipeCard.setSpacing(15);
        recipeCard.setPrefWidth(280);
        recipeCard.setPrefHeight(350);

        String cardStyle = "-fx-background-color: rgba(255,255,255,0.95); " +
                          "-fx-background-radius: 20; " +
                          "-fx-padding: 20; " +
                          "-fx-border-color: rgba(255,255,255,0.8); " +
                          "-fx-border-radius: 20; " +
                          "-fx-border-width: 1; " +
                          "-fx-cursor: hand;";
        recipeCard.setStyle(cardStyle);

        javafx.scene.effect.DropShadow shadow = new javafx.scene.effect.DropShadow();
        shadow.setColor(javafx.scene.paint.Color.valueOf("#00000025"));
        shadow.setOffsetX(0);
        shadow.setOffsetY(5);
        shadow.setRadius(15);
        recipeCard.setEffect(shadow);

        VBox imageContainer = new VBox();
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setStyle("-fx-background-color: #f5f5f5; " +
                               "-fx-background-radius: 15; " +
                               "-fx-padding: 10;");
                               
        ImageView imageView = new ImageView(recipeImage);
        imageView.setFitHeight(180);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle();
        clip.setWidth(200);
        clip.setHeight(180);
        clip.setArcHeight(15);
        clip.setArcWidth(15);
        imageView.setClip(clip);
        
        imageContainer.getChildren().add(imageView);

        Label nameLabel = new Label(imageName);
        nameLabel.setStyle("-fx-font-size: 16; " +
                          "-fx-font-weight: bold; " +
                          "-fx-text-fill: #333; " +
                          "-fx-text-alignment: center;");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(240);

        Button viewButton = new Button("View Recipe");
        String buttonStyle = "-fx-background-color: linear-gradient(to bottom, #667eea, #764ba2); " +
                            "-fx-text-fill: white; " +
                            "-fx-font-weight: bold; " +
                            "-fx-background-radius: 15; " +
                            "-fx-cursor: hand; " +
                            "-fx-font-size: 14; " +
                            "-fx-padding: 8 20;";
        viewButton.setStyle(buttonStyle);
        
        String hoverStyle = "-fx-background-color: linear-gradient(to bottom, #5a67d8, #6b46c1); " +
                           "-fx-text-fill: white; " +
                           "-fx-font-weight: bold; " +
                           "-fx-background-radius: 15; " +
                           "-fx-cursor: hand; " +
                           "-fx-font-size: 14; " +
                           "-fx-padding: 8 20;";

        viewButton.setOnMouseEntered(e -> viewButton.setStyle(hoverStyle));
        viewButton.setOnMouseExited(e -> viewButton.setStyle(buttonStyle));
        
        viewButton.setOnAction(e -> {
            Stage currentStage = (Stage) viewButton.getScene().getWindow();
            currentStage.close();
            RecipeDisplayView view = new RecipeDisplayView(recipeNumber);
            view.show();
        });

        String cardHoverStyle = "-fx-background-color: rgba(255,255,255,1.0); " +
                               "-fx-background-radius: 20; " +
                               "-fx-padding: 20; " +
                               "-fx-border-color: #667eea; " +
                               "-fx-border-radius: 20; " +
                               "-fx-border-width: 2; " +
                               "-fx-cursor: hand;";
        
        recipeCard.setOnMouseEntered(e -> {
            recipeCard.setStyle(cardHoverStyle);
            javafx.scene.effect.DropShadow hoverShadow = new javafx.scene.effect.DropShadow();
            hoverShadow.setColor(javafx.scene.paint.Color.valueOf("#00000040"));
            hoverShadow.setOffsetX(0);
            hoverShadow.setOffsetY(8);
            hoverShadow.setRadius(20);
            recipeCard.setEffect(hoverShadow);
        });
        
        recipeCard.setOnMouseExited(e -> {
            recipeCard.setStyle(cardStyle);
            recipeCard.setEffect(shadow);
        });
        
        buttonMap.put(viewButton, recipeNumber);
        
        recipeCard.getChildren().addAll(imageContainer, nameLabel, viewButton);
        
        return recipeCard;
    }
}