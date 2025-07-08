package model;

import dao.mappers.PreparationStep;
import dao.mappers.Recipe;
import dao.mappers.RecipeIngredient;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * RecipeService provides comprehensive business logic for recipe management operations.
 * This service handles recipe CRUD operations, ingredient management, preparation steps,
 * and nutritional calculations through database interactions.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class RecipeService {
    private DatabaseManager dbManager;

    /**
     * Constructor for RecipeService.
     * 
     * @param dbManager The database manager instance for data operations
     */
    public RecipeService(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    
    /**
     * Retrieves image URLs for recipes matching the given name.
     * 
     * @param recipeName The name of the recipe to search for
     * @return A LinkedHashMap mapping recipe IDs to their image URLs
     */
    public LinkedHashMap<Integer, String> updateImageUrls(String recipeName) {
        ArrayList<Recipe> recipes = dbManager.getRecipeMapper().getRecipeByName(recipeName);
        LinkedHashMap<Integer, String> imageHashMap = new LinkedHashMap<>();
        for(Recipe recipe : recipes){
            imageHashMap.put(recipe.getRecipeId(), recipe.getImageUrl());
        }
        return imageHashMap;
    }

    
    /**
     * Retrieves recipe names for recipes matching the given name pattern.
     * 
     * @param recipeName The name pattern to search for
     * @return An ArrayList of recipe names
     */
    public ArrayList<String> updateImageNames(String recipeName) {
        ArrayList<Recipe> recipes = dbManager.getRecipeMapper().getRecipeByName(recipeName);
        ArrayList<String> imageNames = new ArrayList<>();
        for(Recipe recipe : recipes){
            imageNames.add(recipe.getRecipeName());
        }
        return imageNames;
    }

    
    /**
     * Retrieves a recipe by its unique identifier.
     * 
     * @param id The unique ID of the recipe
     * @return The Recipe object, or null if not found
     */
    public Recipe getRecipeByID(Integer id) {
        return dbManager.getRecipeMapper().getRecipeById(id);
    }

    
    /**
     * Retrieves all ingredients for a specific recipe.
     * 
     * @param id The recipe ID
     * @return A list of RecipeIngredient objects for the recipe
     */
    public List<RecipeIngredient> getIngredientByID(Integer id) {
        return dbManager.getRecipeIngredientMapper().getRecipeIngredientsByRecipeId(id);
    }

    
    public List<PreparationStep> getRecipePreparationSteps(Integer id) {
        return dbManager.getPreparationStepMapper().getPreparationStepsByRecipeId(id);
    }

    
    public List<RecipeIngredient> updateIngredientByServeNumber(Integer id, String serveNumber) {
        Float serveNumberInt = Float.parseFloat(serveNumber);
        List<RecipeIngredient> updatedIngredients = new ArrayList<>();
        List<RecipeIngredient> ingredients = dbManager.getRecipeIngredientMapper().getRecipeIngredientsByRecipeId(id);
        for(RecipeIngredient ingredient : ingredients){
            updatedIngredients.add(new RecipeIngredient(ingredient));
        }
        for(RecipeIngredient ingredient : updatedIngredients){
            ingredient.setQuantity(serveNumberInt * ingredient.getQuantity());
        }
        return updatedIngredients;
    }

    
    /**
     * Updates an existing recipe in the database.
     * 
     * @param recipe The Recipe object with updated information
     */
    public void updateRecipe(Recipe recipe) {
        try {
            dbManager.getRecipeMapper().updateRecipe(recipe);
            dbManager.getSqlSession().commit();
        } catch (Exception e) {
            dbManager.getSqlSession().rollback();
        }
    }

    
    /**
     * Adds a new recipe to the database.
     * 
     * @param recipe The Recipe object to add
     * @return The ID of the newly created recipe, or 0 if failed
     */
    public Integer addRecipe(Recipe recipe) {
        try {
            dbManager.getRecipeMapper().addRecipe(recipe);
            dbManager.getSqlSession().commit();
            Recipe newRecipe = dbManager.getRecipeMapper().getNewRecipe();
            return newRecipe.getRecipeId();
        } catch (Exception e) {
            dbManager.getSqlSession().rollback();
            return 0;
        }
    }

    
    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        try {
            dbManager.getRecipeIngredientMapper().addRecipeIngredient(recipeIngredient);
            dbManager.getSqlSession().commit();
        } catch (Exception e) {
            dbManager.getSqlSession().rollback();
        }
    }

    
    public void updateRecipeIngredient(Integer recipeID, List<RecipeIngredient> recipeIngredients) {
        try {
            dbManager.getRecipeIngredientMapper().deleteRecipeIngredient(recipeID);
            for (RecipeIngredient recipeIngredient : recipeIngredients) {
                dbManager.getRecipeIngredientMapper().addRecipeIngredient(recipeIngredient);
            }
            dbManager.getSqlSession().commit();
        } catch (Exception e) {
            dbManager.getSqlSession().rollback();
        }
    }

    
    /**
     * Retrieves all recipes from the database.
     * 
     * @return A list of all Recipe objects
     */
    public List<Recipe> getAllRecipes() {
        return dbManager.getRecipeMapper().getAllRecipes();
    }

    
    public void addRecipePreparationStep(PreparationStep preparationStep) {
        try {
            dbManager.getPreparationStepMapper().addPreparationStep(preparationStep);
            dbManager.getSqlSession().commit();
        } catch (Exception e) {
            dbManager.getSqlSession().rollback();
        }
    }

    
    public void updateRecipePreparationStep(Integer recipeID, List<PreparationStep> preparationSteps) {
        try {
            dbManager.getPreparationStepMapper().deletePreparationStep(recipeID);
            for (PreparationStep preparationStep : preparationSteps) {
                dbManager.getPreparationStepMapper().addPreparationStep(preparationStep);
            }
            dbManager.getSqlSession().commit();
        } catch (Exception e) {
            dbManager.getSqlSession().rollback();
        }
    }

    
    /**
     * Deletes a recipe from the database.
     * 
     * @param recipeID The ID of the recipe to delete
     */
    public void deleteRecipe(Integer recipeID) {
        try {
            dbManager.getRecipeMapper().deleteRecipe(recipeID);
            dbManager.getSqlSession().commit();
        } catch (Exception e) {
            dbManager.getSqlSession().rollback();
        }
    }

    // Additional methods for RecipeDisplayFXMLController
    private Recipe currentRecipe;
    private List<RecipeIngredient> currentIngredients;
    private String currentInstructions;

    public void getRecipes() {
        // This method is called to initialize recipe loading
        // Implementation can be empty if not needed
    }

    public void getRecipeBasicData(Integer recipeId) {
        this.currentRecipe = dbManager.getRecipeMapper().getRecipeById(recipeId);
    }

    public String getRecipeName() {
        return currentRecipe != null ? currentRecipe.getRecipeName() : "";
    }

    public String getCookingTime() {
        return currentRecipe != null ? String.valueOf(currentRecipe.getCookingTime()) : "0";
    }

    public String getPreparationTime() {
        return currentRecipe != null ? String.valueOf(currentRecipe.getPreparationTime()) : "0";
    }

    public String getImagePath() {
        return currentRecipe != null ? currentRecipe.getImageUrl() : "";
    }

    public List<RecipeIngredient> getIngredients(Integer recipeId) {
        this.currentIngredients = dbManager.getRecipeIngredientMapper().getRecipeIngredientsByRecipeId(recipeId);
        return currentIngredients;
    }

    public String getInstructions(Integer recipeId) {
        List<PreparationStep> steps = dbManager.getPreparationStepMapper().getPreparationStepsByRecipeId(recipeId);
        StringBuilder instructions = new StringBuilder();
        for (PreparationStep step : steps) {
            instructions.append("Step ").append(step.getStep()).append(": ")
                       .append(step.getDescription()).append("\n\n");
        }
        this.currentInstructions = instructions.toString();
        return currentInstructions;
    }
}