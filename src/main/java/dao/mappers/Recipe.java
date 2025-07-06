package dao.mappers;

import java.io.Serializable;


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

    
    public Recipe() {
    }

    
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

    
    public int getRecipeId() {
        return this.recipeId;
    }

    
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    
    public String getRecipeName() {
        return this.recipeName;
    }

    
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    
    public int getServeAmount() {
        return this.serveAmount;
    }

    
    public void setServeAmount(int serveAmount) {
        this.serveAmount = serveAmount;
    }

    
    public int getPreparationTime() {
        return this.preparationTime;
    }

    
    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    
    public int getCookingTime() {
        return this.cookingTime;
    }

    
    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    
    public String getImageUrl() {
        return this.imageUrl;
    }

    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    
    public int getCalories() {
        return this.calories;
    }

    
    public void setCalories(int calories) {
        this.calories = calories;
    }

    
    public double getProtein() {
        return this.protein;
    }

    
    public void setProtein(double protein) {
        this.protein = protein;
    }

    
    public double getCarbohydrates() {
        return this.carbohydrates;
    }

    
    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    
    public double getFat() {
        return this.fat;
    }

    
    public void setFat(double fat) {
        this.fat = fat;
    }

    
    public double getFiber() {
        return this.fiber;
    }

    
    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    
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
