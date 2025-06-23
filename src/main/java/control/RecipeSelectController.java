package control;
import dao.mappers.Recipe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import model.Model;
import javafx.scene.control.Button;
import view.*;
import config.SessionManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RecipeSelectController implements EventHandler<ActionEvent> {
    private final RecipeSelectView recipeSelectView;
    private final Model model;

    public RecipeSelectController(RecipeSelectView recipeSelectView) {
        this.recipeSelectView = recipeSelectView;
        this.model = new Model();

    }

    public void initializeData() {
        List<Recipe> allRecipes = model.getAllRecipes();
        recipeSelectView.imageUrls =  new LinkedHashMap<>();;
        recipeSelectView.imageNames = new ArrayList<>();
        for(Recipe recipe : allRecipes) {
            recipeSelectView.imageNames.add(recipe.getRecipeName());

            recipeSelectView.imageUrls.put( recipe.getRecipeId(),recipe.getImageUrl());
        }

    }

    @Override
    public void handle(ActionEvent actionEvent) {

        for(Button button : recipeSelectView.buttonMap.keySet()){
            if (actionEvent.getSource() == button){
                recipeSelectView.close();
                Integer recipeNumber = recipeSelectView.buttonMap.get(button);
                RecipeDisplayView view = new RecipeDisplayView(recipeNumber);
                view.show();
            }
        }

        if(actionEvent.getSource() == recipeSelectView.searchButton){
            String recipeName = recipeSelectView.searchField.textProperty().getValue();
            recipeSelectView.update(model.updateImageUrls(recipeName), model.updateImageNames(recipeName));
            recipeSelectView.searchField.setText(recipeName);
        }
        else if(actionEvent.getSource() == recipeSelectView.backButton){
            recipeSelectView.close();
        }
        else if(actionEvent.getSource() == recipeSelectView.createRecipeButton){
            RecipeCreateView recipeCreateView = new RecipeCreateView();
            recipeCreateView.show();
        }

    }


}
