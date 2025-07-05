package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class RecipeSelectView extends Stage {

    public LinkedHashMap<Integer, String> imageUrls;
    public ArrayList<String> imageNames;
    public LinkedHashMap<Button, Integer> buttonMap;
    public Button searchButton;
    public TextField searchField;
    public Button backButton;
    public Button createRecipeButton;

    public RecipeSelectView() {
        this.setTitle("Recipe Selection");
        this.setMinWidth(800);
        this.setMinHeight(600);
        this.setWidth(1200);
        this.setHeight(930);
        this.setResizable(true);
        this.setMaximized(false);
        init();
    }
    
    private void init() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recipe_select.fxml"));
            Scene scene = new Scene(loader.load());
            this.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load recipe_select.fxml");
        }
    }

    public void update(LinkedHashMap<Integer, String> imageUrls, ArrayList<String> imageNames) {
        this.imageUrls = imageUrls;
        this.imageNames = imageNames;
    }
}
