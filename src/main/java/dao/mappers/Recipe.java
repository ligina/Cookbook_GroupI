package dao.mappers;

import java.io.Serializable;

/**
 * Recipe entity class representing a recipe in the recipe management system.
 * This class contains all the information about a recipe including basic details,
 * nutritional information, and cooking instructions metadata.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class Recipe implements Serializable {

    /** Unique identifier for the recipe */
    private int recipeId;
    
    /** Name of the recipe */
    private String recipeName;
    
    /** Number of servings this recipe makes */
    private int serveAmount;
    
    /** Preparation time in minutes */
    private int preparationTime;
    
    /** Cooking time in minutes */
    private int cookingTime;
    
    /** URL or path to the recipe image */
    private String imageUrl;
    
    /** Total calories per serving */
    private int calories;
    
    /** Protein content in grams per serving */
    private double protein;
    
    /** Carbohydrate content in grams per serving */
    private double carbohydrates;
    
    /** Fat content in grams per serving */
    private double fat;
    
    /** Fiber content in grams per serving */
    private double fiber;

    /**
     * Default constructor for Recipe.
     * Creates an empty Recipe object with default values.
     */
    public Recipe() {
    }

    /**
     * Constructor for Recipe with basic information.
     * Initializes nutritional values to zero.
     * 
     * @param recipeId the unique identifier for the recipe
     * @param recipeName the name of the recipe
     * @param serveAmount the number of servings
     * @param preparationTime the preparation time in minutes
     * @param cookingTime the cooking time in minutes
     */
    public Recipe(int recipeId, String recipeName, int serveAmount,int preparationTime, int cookingTime) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.serveAmount = serveAmount;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.calories = 0;
        this.protein = 0.0;
        this.carbohydrates = 0.0;
        this.fat = 0.0;
        this.fiber = 0.0;
    }

    /**
     * Constructor for Recipe with basic information and image URL.
     * Initializes nutritional values to zero.
     * 
     * @param recipeId the unique identifier for the recipe
     * @param recipeName the name of the recipe
     * @param serveAmount the number of servings
     * @param cookingTime the cooking time in minutes
     * @param preparationTime the preparation time in minutes
     * @param imageUrl the URL or path to the recipe image
     */
    public Recipe(int recipeId, String recipeName, int serveAmount, int cookingTime, int preparationTime, String imageUrl) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.serveAmount = serveAmount;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.imageUrl = imageUrl;
        this.calories = 0;
        this.protein = 0.0;
        this.carbohydrates = 0.0;
        this.fat = 0.0;
        this.fiber = 0.0;
    }

    /**
     * Gets the recipe ID.
     * 
     * @return the unique identifier of the recipe
     */
    public int getRecipeId() {
        return this.recipeId;
    }

    /**
     * Sets the recipe ID.
     * 
     * @param recipeId the unique identifier to set for the recipe
     */
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    /**
     * Gets the recipe name.
     * 
     * @return the name of the recipe
     */
    public String getRecipeName() {
        return this.recipeName;
    }

    /**
     * Sets the recipe name.
     * 
     * @param recipeName the name to set for the recipe
     */
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    /**
     * Gets the serving amount.
     * 
     * @return the number of servings this recipe makes
     */
    public int getServeAmount() {
        return this.serveAmount;
    }

    /**
     * Sets the serving amount.
     * 
     * @param serveAmount the number of servings to set for the recipe
     */
    public void setServeAmount(int serveAmount) {
        this.serveAmount = serveAmount;
    }

    /**
     * Gets the preparation time.
     * 
     * @return the preparation time in minutes
     */
    public int getPreparationTime() {
        return this.preparationTime;
    }

    /**
     * Sets the preparation time.
     * 
     * @param preparationTime the preparation time in minutes to set
     */
    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    /**
     * Gets the cooking time.
     * 
     * @return the cooking time in minutes
     */
    public int getCookingTime() {
        return this.cookingTime;
    }

    /**
     * Sets the cooking time.
     * 
     * @param cookingTime the cooking time in minutes to set
     */
    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    /**
     * Gets the image URL.
     * 
     * @return the URL or path to the recipe image
     */
    public String getImageUrl() {
        return this.imageUrl;
    }

    /**
     * Sets the image URL.
     * 
     * @param imageUrl the URL or path to set for the recipe image
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the calorie content.
     * 
     * @return the total calories per serving
     */
    public int getCalories() {
        return this.calories;
    }

    /**
     * Sets the calorie content.
     * 
     * @param calories the total calories per serving to set
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * Gets the protein content.
     * 
     * @return the protein content in grams per serving
     */
    public double getProtein() {
        return this.protein;
    }

    /**
     * Sets the protein content.
     * 
     * @param protein the protein content in grams per serving to set
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }

    /**
     * Gets the carbohydrate content.
     * 
     * @return the carbohydrate content in grams per serving
     */
    public double getCarbohydrates() {
        return this.carbohydrates;
    }

    /**
     * Sets the carbohydrate content.
     * 
     * @param carbohydrates the carbohydrate content in grams per serving to set
     */
    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    /**
     * Gets the fat content.
     * 
     * @return the fat content in grams per serving
     */
    public double getFat() {
        return this.fat;
    }

    /**
     * Sets the fat content.
     * 
     * @param fat the fat content in grams per serving to set
     */
    public void setFat(double fat) {
        this.fat = fat;
    }

    /**
     * Gets the fiber content.
     * 
     * @return the fiber content in grams per serving
     */
    public double getFiber() {
        return this.fiber;
    }

    /**
     * Sets the fiber content.
     * 
     * @param fiber the fiber content in grams per serving to set
     */
    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    /**
     * Returns a string representation of the Recipe object.
     * Includes all recipe properties for debugging and logging purposes.
     * 
     * @return a string containing all recipe information
     */
    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", recipeName='" + recipeName + '\'' +
                ", serveAmount=" + serveAmount +
                ", preparationTime=" + preparationTime +
                ", cookingTime=" + cookingTime +
                ", imageUrl='" + imageUrl + '\'' +
                ", calories=" + calories +
                ", protein=" + protein +
                ", carbohydrates=" + carbohydrates +
                ", fat=" + fat +
                ", fiber=" + fiber +
                '}';
    }
}
