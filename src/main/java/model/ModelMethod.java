package model;
import dao.mappers.Recipe;
import dao.mappers.RecipeIngredient;
import dao.mappers.PreparationStep;
import java.nio.file.Path;
import java.util.List;
import java.util.HashMap;

/**
 * Interface defining the contract for model operations in the recipe management system.
 * This interface provides method signatures for all business logic operations including
 * user management, recipe operations, validation, and image handling.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public interface ModelMethod {

    /**
     * Registers a new user in the system.
     * 
     * @param name the username for the new account
     * @param password the password for the new account
     * @return true if registration was successful, false otherwise
     */
    public boolean sign(String name, String password);

    /**
     * Authenticates a user login attempt.
     * 
     * @param name the username to authenticate
     * @param password the password to verify
     * @return true if login was successful, false otherwise
     */
    public boolean login(String name, String password);

    /**
     * Updates and retrieves recipe names based on search criteria.
     * 
     * @param recipeName the recipe name to search for
     * @return a list of matching recipe names
     */
    public List<String> updateImageNames(String recipeName);

    /**
     * Updates and retrieves recipe image URLs based on search criteria.
     * 
     * @param recipeName the recipe name to search for
     * @return a map of recipe IDs to their image URLs
     */
    public HashMap<Integer, String> updateImageUrls(String recipeName);

    /**
     * Retrieves a recipe by its unique identifier.
     * 
     * @param id the unique identifier of the recipe
     * @return the Recipe object if found, null otherwise
     */
    public Recipe getRecipeByID(Integer id);

    /**
     * Retrieves all preparation steps for a specific recipe.
     * 
     * @param id the unique identifier of the recipe
     * @return a list of PreparationStep objects for the recipe
     */
    public List<PreparationStep> getRecipePreparationSteps(Integer id);

    /**
     * Adds a new recipe to the system.
     * 
     * @param recipe the Recipe object to add
     * @return the ID of the newly created recipe
     */
    public Integer addRecipe(Recipe recipe);

    /**
     * Adds a new ingredient to a recipe.
     * 
     * @param recipeIngredient the RecipeIngredient object to add
     */
    public void addRecipeIngredient(RecipeIngredient recipeIngredient);

    /**
     * Updates all ingredients for a specific recipe.
     * 
     * @param recipeID the ID of the recipe to update
     * @param recipeIngredients the list of updated ingredients
     */
    public void updateRecipeIngredient(Integer recipeID, List<RecipeIngredient> recipeIngredients);

    /**
     * Retrieves all recipes in the system.
     * 
     * @return a list of all Recipe objects
     */
    public List<Recipe> getAllRecipes();

    /**
     * Duplicates an image file for recipe storage.
     * 
     * @param imageURL the URL or path of the source image
     * @return the Path to the duplicated image file
     */
    public Path duplicateImage(String imageURL);

    /**
     * Validates if a string represents a valid integer for serving numbers.
     * 
     * @param str the string to validate
     * @return true if the string is a valid integer, false otherwise
     */
    public boolean serveNumberIsInteger(String str);

    /**
     * Updates ingredient quantities based on serving number changes.
     * 
     * @param id the recipe ID
     * @param serveNumber the new serving number
     * @return a list of updated RecipeIngredient objects
     */
    public List<RecipeIngredient> updateIngredientByServeNumber(Integer id, String serveNumber);

    /**
     * Retrieves all ingredients for a specific recipe.
     * 
     * @param id the unique identifier of the recipe
     * @return a list of RecipeIngredient objects for the recipe
     */
    public List<RecipeIngredient> getIngredientByID(Integer id);

    /**
     * Updates an existing recipe in the system.
     * 
     * @param recipe the Recipe object with updated information
     */
    public void updateRecipe(Recipe recipe);

    /**
     * Adds a new preparation step to a recipe.
     * 
     * @param preparationStep the PreparationStep object to add
     */
    public void addRecipePreparationStep(PreparationStep preparationStep);

    /**
     * Updates all preparation steps for a specific recipe.
     * 
     * @param recipeID the ID of the recipe to update
     * @param preparationSteps the list of updated preparation steps
     */
    public void updateRecipePreparationStep(Integer recipeID, List<PreparationStep> preparationSteps);

    /**
     * Deletes a recipe and all its associated data.
     * 
     * @param recipeID the ID of the recipe to delete
     */
    public void deleteRecipe(Integer recipeID);

    /**
     * Validates recipe basic information.
     * 
     * @param recipeName the name of the recipe
     * @param cookingTime the cooking time
     * @param preparationTime the preparation time
     * @param recipeImage the recipe image URL
     * @return true if validation passes, false otherwise
     */
    public boolean validateRecipe(String recipeName, String cookingTime, String preparationTime, String recipeImage);

    /**
     * Validates recipe ingredient information.
     * 
     * @param recipeName the name of the ingredient
     * @param quantity the quantity of the ingredient
     * @param unit the unit of measurement
     * @return true if validation passes, false otherwise
     */
    public boolean validateRecipeIngredient(String recipeName, Float quantity, String unit);

}
