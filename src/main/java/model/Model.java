package model;

import dao.mappers.PreparationStep;
import dao.mappers.Recipe;
import dao.mappers.RecipeIngredient;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Model class serves as the main business logic layer for the recipe management application.
 * This class implements the ModelMethod interface and acts as a facade that coordinates
 * between different service layers including user management, recipe operations, validation,
 * and image handling. It provides a unified interface for all business operations.
 * 
 * @author Ziang Liu and Mengfei Chen
 * @version 1.0
 * @since 1.0
 */
public class Model implements ModelMethod {
    /** Database manager for handling database connections and transactions */
    private DatabaseManager dbManager;
    
    /** Service for user-related operations like login and registration */
    private UserService userService;
    
    /** Service for recipe-related CRUD operations */
    private RecipeService recipeService;
    
    /** Service for input validation and data integrity checks */
    private ValidationService validationService;
    
    /** Service for image handling and processing operations */
    private ImageService imageService;

    
    /**
     * Default constructor for Model class.
     * Initializes all service dependencies including database manager,
     * user service, recipe service, validation service, and image service.
     */
    public Model() {
        this.dbManager = new DatabaseManager();
        this.userService = new UserService(dbManager);
        this.recipeService = new RecipeService(dbManager);
        this.validationService = new ValidationService();
        this.imageService = new ImageService();
    }

    
    /**
     * Registers a new user in the system.
     * 
     * @param name The username for the new account
     * @param password The password for the new account
     * @return true if registration was successful, false otherwise
     */
    @Override
    public boolean sign(String name, String password) {
        return userService.sign(name, password);
    }

    /**
     * Authenticates a user login attempt.
     * 
     * @param name The username to authenticate
     * @param password The password to verify
     * @return true if login was successful, false otherwise
     */
    @Override
    public boolean login(String name, String password) {
        return userService.login(name, password);
    }

    
    /**
     * Retrieves image URLs for recipes matching the given name.
     * 
     * @param recipeName The name of the recipe to search for
     * @return A LinkedHashMap mapping recipe IDs to their image URLs
     */
    @Override
    public LinkedHashMap<Integer, String> updateImageUrls(String recipeName) {
        return recipeService.updateImageUrls(recipeName);
    }

    /**
     * Retrieves recipe names for recipes matching the given name pattern.
     * 
     * @param recipeName The name pattern to search for
     * @return An ArrayList of recipe names
     */
    @Override
    public ArrayList<String> updateImageNames(String recipeName) {
        return recipeService.updateImageNames(recipeName);
    }

    /**
     * Retrieves a recipe by its unique identifier.
     * 
     * @param id The unique ID of the recipe
     * @return The Recipe object, or null if not found
     */
    @Override
    public Recipe getRecipeByID(Integer id) {
        return recipeService.getRecipeByID(id);
    }

    /**
     * Retrieves all ingredients for a specific recipe.
     * 
     * @param id The recipe ID
     * @return A list of RecipeIngredient objects for the recipe
     */
    @Override
    public List<RecipeIngredient> getIngredientByID(Integer id) {
        return recipeService.getIngredientByID(id);
    }

    /**
     * Retrieves all preparation steps for a specific recipe.
     * 
     * @param id The recipe ID
     * @return A list of PreparationStep objects for the recipe
     */
    @Override
    public List<PreparationStep> getRecipePreparationSteps(Integer id) {
        return recipeService.getRecipePreparationSteps(id);
    }

    /**
     * Updates ingredient quantities based on serving number changes.
     * 
     * @param id The recipe ID
     * @param serveNumber The new serving number
     * @return A list of updated RecipeIngredient objects
     */
    @Override
    public List<RecipeIngredient> updateIngredientByServeNumber(Integer id, String serveNumber) {
        return recipeService.updateIngredientByServeNumber(id, serveNumber);
    }

    /**
     * Updates an existing recipe in the database.
     * 
     * @param recipe The Recipe object with updated information
     */
    @Override
    public void updateRecipe(Recipe recipe) {
        recipeService.updateRecipe(recipe);
    }

    /**
     * Adds a new recipe to the database.
     * 
     * @param recipe The Recipe object to add
     * @return The ID of the newly created recipe, or 0 if failed
     */
    @Override
    public Integer addRecipe(Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    /**
     * Adds a new ingredient to a recipe.
     * 
     * @param recipeIngredient The RecipeIngredient object to add
     */
    @Override
    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeService.addRecipeIngredient(recipeIngredient);
    }

    /**
     * Updates all ingredients for a specific recipe.
     * 
     * @param recipeID The ID of the recipe to update
     * @param recipeIngredients The list of updated ingredients
     */
    @Override
    public void updateRecipeIngredient(Integer recipeID, List<RecipeIngredient> recipeIngredients) {
        recipeService.updateRecipeIngredient(recipeID, recipeIngredients);
    }

    /**
     * Retrieves all recipes from the database.
     * 
     * @return A list of all Recipe objects
     */
    @Override
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    /**
     * Adds a new preparation step to a recipe.
     * 
     * @param preparationStep The PreparationStep object to add
     */
    @Override
    public void addRecipePreparationStep(PreparationStep preparationStep) {
        recipeService.addRecipePreparationStep(preparationStep);
    }

    /**
     * Updates all preparation steps for a specific recipe.
     * 
     * @param recipeID The ID of the recipe to update
     * @param preparationSteps The list of updated preparation steps
     */
    @Override
    public void updateRecipePreparationStep(Integer recipeID, List<PreparationStep> preparationSteps) {
        recipeService.updateRecipePreparationStep(recipeID, preparationSteps);
    }

    /**
     * Deletes a recipe and all its associated data.
     * 
     * @param recipeID The ID of the recipe to delete
     */
    @Override
    public void deleteRecipe(Integer recipeID) {
        recipeService.deleteRecipe(recipeID);
    }

    
    /**
     * Duplicates an image file for recipe storage.
     * 
     * @param imageURL The URL or path of the source image
     * @return The Path to the duplicated image file
     */
    @Override
    public Path duplicateImage(String imageURL) {
        return imageService.duplicateImage(imageURL);
    }

    
    /**
     * Validates if a string represents a valid integer for serving numbers.
     * 
     * @param str The string to validate
     * @return true if the string is a valid integer, false otherwise
     */
    @Override
    public boolean serveNumberIsInteger(String str) {
        return validationService.serveNumberIsInteger(str);
    }

    /**
     * Validates recipe ingredient information.
     * 
     * @param recipeName The name of the ingredient
     * @param quantity The quantity of the ingredient
     * @param unit The unit of measurement
     * @return true if validation passes, false otherwise
     */
    @Override
    public boolean validateRecipeIngredient(String recipeName, Float quantity, String unit) {
        return validationService.validateRecipeIngredient(recipeName, quantity, unit);
    }

    /**
     * Validates recipe basic information.
     * 
     * @param recipeName The name of the recipe
     * @param cookingTime The cooking time
     * @param preparationTime The preparation time
     * @param recipeImage The recipe image URL
     * @return true if validation passes, false otherwise
     */
    @Override
    public boolean validateRecipe(String recipeName, String cookingTime, String preparationTime, String recipeImage) {
        return validationService.validateRecipe(recipeName, cookingTime, preparationTime, recipeImage);
    }

    
    /**
     * Validates serving number input to ensure it meets requirements.
     * 
     * @param serveNumber The serving number string to validate
     * @return true if the serving number is valid, false otherwise
     */
    public boolean validateServingNumber(String serveNumber) {
        return validationService.validateServingNumber(serveNumber);
    }

    
    /**
     * Validates nutritional value inputs to ensure they are valid numbers.
     * 
     * @param value The nutritional value to validate
     * @param fieldName The name of the field being validated (for error messages)
     * @return true if the nutritional value is valid, false otherwise
     */
    public boolean validateNutritionValue(String value, String fieldName) {
        return validationService.validateNutritionValue(value, fieldName);
    }

    /** Static field to store the last displayed alert for testing purposes */
    public static Alert lastAlert; // Only for junit test
    
    /**
     * Displays an alert dialog and stores it for testing purposes.
     * 
     * @param alertType The type of alert to display
     * @param title The title of the alert dialog
     * @param content The content message of the alert
     */
    public static void displayAlert(Alert.AlertType alertType, String title, String content) {
        UIUtils.displayAlert(alertType, title, content);

        Alert alert = new Alert(alertType); // Only for junit test
        lastAlert = alert; // Only for junit test
    }

    /**
     * Gets the last displayed alert for testing purposes.
     * 
     * @return The last Alert that was displayed, or null if none
     */
    public static Alert getLastDisplayedAlert() {
        return lastAlert;
    } // Only for junit test

    /**
     * Resets the last displayed alert to null for testing purposes.
     */
    public static void resetLastDisplayedAlert() {
        lastAlert = null;
    } // Only for junit test

    /**
     * Creates a text formatter with maximum length constraint.
     * 
     * @param maxLength The maximum allowed length for the text field
     * @return A TextFormatter that enforces the length constraint
     */
    public static TextFormatter<String> textFieldFormatter(int maxLength) {
        return UIUtils.textFieldFormatter(maxLength);
    }

    
    /**
     * Initializes recipe loading process.
     */
    public void getRecipes() {
        recipeService.getRecipes();
    }

    /**
     * Loads basic data for a specific recipe.
     * 
     * @param recipeId The ID of the recipe to load
     */
    public void getRecipeBasicData(Integer recipeId) {
        recipeService.getRecipeBasicData(recipeId);
    }

    /**
     * Gets the name of the currently loaded recipe.
     * 
     * @return The recipe name, or empty string if none loaded
     */
    public String getRecipeName() {
        return recipeService.getRecipeName();
    }

    /**
     * Gets the cooking time of the currently loaded recipe.
     * 
     * @return The cooking time as a string, or "0" if none loaded
     */
    public String getCookingTime() {
        return recipeService.getCookingTime();
    }

    /**
     * Gets the preparation time of the currently loaded recipe.
     * 
     * @return The preparation time as a string, or "0" if none loaded
     */
    public String getPreparationTime() {
        return recipeService.getPreparationTime();
    }

    /**
     * Gets the image path of the currently loaded recipe.
     * 
     * @return The image path, or empty string if none loaded
     */
    public String getImagePath() {
        return recipeService.getImagePath();
    }

    /**
     * Gets all ingredients for a specific recipe.
     * 
     * @param recipeId The ID of the recipe
     * @return A list of RecipeIngredient objects
     */
    public List<RecipeIngredient> getIngredients(Integer recipeId) {
        return recipeService.getIngredients(recipeId);
    }

    /**
     * Gets the formatted instructions for a specific recipe.
     * 
     * @param recipeId The ID of the recipe
     * @return A formatted string containing all preparation steps
     */
    public String getInstructions(Integer recipeId) {
        return recipeService.getInstructions(recipeId);
    }
}