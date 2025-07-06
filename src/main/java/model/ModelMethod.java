package model;
import dao.mappers.Recipe;
import dao.mappers.RecipeIngredient;
import dao.mappers.PreparationStep;
import java.nio.file.Path;
import java.util.List;
import java.util.HashMap;


public interface ModelMethod {

    
    public boolean sign(String name, String password);

    
    public boolean login(String name, String password);


    
    public List<String> updateImageNames(String recipeName);

    
    public HashMap<Integer, String> updateImageUrls(String recipeName);

    
    public Recipe getRecipeByID(Integer id);

    
    public List<PreparationStep> getRecipePreparationSteps(Integer id);

    
    public Integer addRecipe(Recipe recipe);

    
    public void addRecipeIngredient(RecipeIngredient recipeIngredient);

    
    public void updateRecipeIngredient(Integer recipeID, List<RecipeIngredient> recipeIngredients);

    
    public List<Recipe> getAllRecipes();

    
    public Path duplicateImage(String imageURL);

    
    public boolean serveNumberIsInteger(String str);

    
    public List<RecipeIngredient> updateIngredientByServeNumber(Integer id, String serveNumber);

    
    public List<RecipeIngredient> getIngredientByID(Integer id);



    
    public void updateRecipe(Recipe recipe);

    
    public void addRecipePreparationStep(PreparationStep preparationStep);

    
    public void updateRecipePreparationStep(Integer recipeID, List<PreparationStep> preparationSteps);

    
    public void deleteRecipe(Integer recipeID);

    
    public boolean validateRecipe(String recipeName, String cookingTime, String preparationTime, String recipeImage);


    
    public boolean validateRecipeIngredient(String recipeName, Float quantity, String unit);

}
