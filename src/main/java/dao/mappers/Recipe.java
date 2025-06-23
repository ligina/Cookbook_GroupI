package dao.mappers;

import java.io.Serializable;

/**
 * Represents a Recipe entity with essential attributes.
 * This class encapsulates details such as recipe ID, name, serving amount,
 * preparation time, cooking time, and image URL.
 *
 * @author 
 */
public class Recipe implements Serializable {

    private int recipeId;
    private String recipeName;
    private int serveAmount;
    private int preparationTime;
    private int cookingTime;
    private String imageUrl;
    private int calories;
    private double protein;
    private double carbohydrates;
    private double fat;
    private double fiber;

    /**
     * Default constructor for the Recipe class.
     */
    public Recipe() {
    }

    /**
     * Default constructor for the Recipe class with parameters.
     * @param recipeId               The unique identifier of the recipe.
     * @param recipeName             The name of the ingredient.
     * @param serveAmount            The quantity or amount of the serve of the recipe.
     * @param preparationTime        The preparation time of the recipe cooking.
     * @param cookingTime`           The cooking time of the recipe cooking.
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
     * Constructs a Recipe object with specified attributes.
     *
     * @param recipeId        The unique identifier for the recipe.
     * @param recipeName      The name or title of the recipe.
     * @param serveAmount     The number of servings the recipe yields.
     * @param cookingTime     The time required for cooking the recipe, in minutes.
     * @param preparationTime The time required for preparing the recipe, in minutes.
     * @param imageUrl        The URL or path to the image associated with the recipe.
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
     * Retrieves the recipe ID.
     *
     * @return The recipe ID.
     */
    public int getRecipeId() {
        return this.recipeId;
    }

    /**
     * Sets the recipe ID.
     *
     * @param recipeId The recipe ID to set.
     */
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    /**
     * Retrieves the recipe name.
     *
     * @return The recipe name.
     */
    public String getRecipeName() {
        return this.recipeName;
    }

    /**
     * Sets the recipe name.
     *
     * @param recipeName The recipe name to set.
     */
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    /**
     * Retrieves the serving amount of the recipe.
     *
     * @return The serve amount.
     */
    public int getServeAmount() {
        return this.serveAmount;
    }

    /**
     * Sets the serving amount of the recipe.
     *
     * @param serveAmount The serve amount to set.
     */
    public void setServeAmount(int serveAmount) {
        this.serveAmount = serveAmount;
    }

    /**
     * Retrieves the preparation time of the recipe.
     *
     * @return The preparation time, in minutes.
     */
    public int getPreparationTime() {
        return this.preparationTime;
    }

    /**
     * Sets the preparation time of the recipe.
     *
     * @param preparationTime The preparation time to set, in minutes.
     */
    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    /**
     * Retrieves the cooking time of the recipe.
     *
     * @return The cooking time, in minutes.
     */
    public int getCookingTime() {
        return this.cookingTime;
    }

    /**
     * Sets the cooking time of the recipe.
     *
     * @param cookingTime The cooking time to set, in minutes.
     */
    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    /**
     * Retrieves the image URL associated with the recipe.
     *
     * @return The image URL.
     */
    public String getImageUrl() {
        return this.imageUrl;
    }

    /**
     * Sets the image URL associated with the recipe.
     *
     * @param imageUrl The image URL to set.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Retrieves the calories of the recipe.
     *
     * @return The calories.
     */
    public int getCalories() {
        return this.calories;
    }

    /**
     * Sets the calories of the recipe.
     *
     * @param calories The calories to set.
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * Retrieves the protein content of the recipe.
     *
     * @return The protein content in grams.
     */
    public double getProtein() {
        return this.protein;
    }

    /**
     * Sets the protein content of the recipe.
     *
     * @param protein The protein content to set, in grams.
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }

    /**
     * Retrieves the carbohydrates content of the recipe.
     *
     * @return The carbohydrates content in grams.
     */
    public double getCarbohydrates() {
        return this.carbohydrates;
    }

    /**
     * Sets the carbohydrates content of the recipe.
     *
     * @param carbohydrates The carbohydrates content to set, in grams.
     */
    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    /**
     * Retrieves the fat content of the recipe.
     *
     * @return The fat content in grams.
     */
    public double getFat() {
        return this.fat;
    }

    /**
     * Sets the fat content of the recipe.
     *
     * @param fat The fat content to set, in grams.
     */
    public void setFat(double fat) {
        this.fat = fat;
    }

    /**
     * Retrieves the fiber content of the recipe.
     *
     * @return The fiber content in grams.
     */
    public double getFiber() {
        return this.fiber;
    }

    /**
     * Sets the fiber content of the recipe.
     *
     * @param fiber The fiber content to set, in grams.
     */
    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    /**
     * Returns a string representation of the Recipe object.
     *
     * @return A string containing all attributes of the Recipe.
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
