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

/**
 * Controller for handling actions on the Recipe Select page.
 *
 * @author 
 */
public class RecipeSelectController implements EventHandler<ActionEvent> {
    private final RecipeSelectView recipeSelectView;
    private final Model model;

    /**
     * Constructor to initialize the controller with the given RecipeSelectView.
     *
     * @param recipeSelectView The view instance to be associated with this controller.
     */
    public RecipeSelectController(RecipeSelectView recipeSelectView) {
        this.recipeSelectView = recipeSelectView;
        this.model = new Model();

    }


    /**
     * Initializes data to be displayed in the RecipeSelectView.
     * Retrieves all recipes from the model and populates imageUrls and imageNames in the view.
     */
    public void initializeData() {
        List<Recipe> allRecipes = model.getAllRecipes();
        recipeSelectView.imageUrls =  new LinkedHashMap<>();;
        recipeSelectView.imageNames = new ArrayList<>();
        for(Recipe recipe : allRecipes) {
            recipeSelectView.imageNames.add(recipe.getRecipeName());

            recipeSelectView.imageUrls.put( recipe.getRecipeId(),recipe.getImageUrl());
        }

    }

    /**
     * Handles actions performed in the RecipeSelectView.
     *
     * @param actionEvent The ActionEvent representing the user's action.
     */
    @Override
    public void handle(ActionEvent actionEvent) {

        for(Button button : recipeSelectView.buttonMap.keySet()){
            // Handle recipe button clicks
            if (actionEvent.getSource() == button){
                recipeSelectView.close();
                // Show RecipeDisplayView directly
                Integer recipeNumber = recipeSelectView.buttonMap.get(button);
                RecipeDisplayView view = new RecipeDisplayView(recipeNumber);
                view.show();
            }
        }
        // Handle search button click
        if(actionEvent.getSource() == recipeSelectView.searchButton){
            String recipeName = recipeSelectView.searchField.textProperty().getValue();
            recipeSelectView.update(model.updateImageUrls(recipeName), model.updateImageNames(recipeName));
            recipeSelectView.searchField.setText(recipeName);
        }
        // Handle back button click
        else if(actionEvent.getSource() == recipeSelectView.backButton){
            recipeSelectView.close();
        }

    }


}
