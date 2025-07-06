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


public class Model implements ModelMethod {
    private DatabaseManager dbManager;
    private UserService userService;
    private RecipeService recipeService;
    private ValidationService validationService;
    private ImageService imageService;

    
    public Model() {
        this.dbManager = new DatabaseManager();
        this.userService = new UserService(dbManager);
        this.recipeService = new RecipeService(dbManager);
        this.validationService = new ValidationService();
        this.imageService = new ImageService();
    }

    
    @Override
    public boolean sign(String name, String password) {
        return userService.sign(name, password);
    }

    @Override
    public boolean login(String name, String password) {
        return userService.login(name, password);
    }

    
    @Override
    public LinkedHashMap<Integer, String> updateImageUrls(String recipeName) {
        return recipeService.updateImageUrls(recipeName);
    }

    @Override
    public ArrayList<String> updateImageNames(String recipeName) {
        return recipeService.updateImageNames(recipeName);
    }

    @Override
    public Recipe getRecipeByID(Integer id) {
        return recipeService.getRecipeByID(id);
    }

    @Override
    public List<RecipeIngredient> getIngredientByID(Integer id) {
        return recipeService.getIngredientByID(id);
    }

    @Override
    public List<PreparationStep> getRecipePreparationSteps(Integer id) {
        return recipeService.getRecipePreparationSteps(id);
    }

    @Override
    public List<RecipeIngredient> updateIngredientByServeNumber(Integer id, String serveNumber) {
        return recipeService.updateIngredientByServeNumber(id, serveNumber);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        recipeService.updateRecipe(recipe);
    }

    @Override
    public Integer addRecipe(Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    @Override
    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeService.addRecipeIngredient(recipeIngredient);
    }

    @Override
    public void updateRecipeIngredient(Integer recipeID, List<RecipeIngredient> recipeIngredients) {
        recipeService.updateRecipeIngredient(recipeID, recipeIngredients);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @Override
    public void addRecipePreparationStep(PreparationStep preparationStep) {
        recipeService.addRecipePreparationStep(preparationStep);
    }

    @Override
    public void updateRecipePreparationStep(Integer recipeID, List<PreparationStep> preparationSteps) {
        recipeService.updateRecipePreparationStep(recipeID, preparationSteps);
    }

    @Override
    public void deleteRecipe(Integer recipeID) {
        recipeService.deleteRecipe(recipeID);
    }

    
    @Override
    public Path duplicateImage(String imageURL) {
        return imageService.duplicateImage(imageURL);
    }

    
    @Override
    public boolean serveNumberIsInteger(String str) {
        return validationService.serveNumberIsInteger(str);
    }

    @Override
    public boolean validateRecipeIngredient(String recipeName, Float quantity, String unit) {
        return validationService.validateRecipeIngredient(recipeName, quantity, unit);
    }

    @Override
    public boolean validateRecipe(String recipeName, String cookingTime, String preparationTime, String recipeImage) {
        return validationService.validateRecipe(recipeName, cookingTime, preparationTime, recipeImage);
    }

    
    public boolean validateServingNumber(String serveNumber) {
        return validationService.validateServingNumber(serveNumber);
    }

    
    public boolean validateNutritionValue(String value, String fieldName) {
        return validationService.validateNutritionValue(value, fieldName);
    }

    
    public static void displayAlert(Alert.AlertType alertType, String title, String content) {
        UIUtils.displayAlert(alertType, title, content);
    }

    public static TextFormatter<String> textFieldFormatter(int maxLength) {
        return UIUtils.textFieldFormatter(maxLength);
    }

    
    public void getRecipes() {
        recipeService.getRecipes();
    }

    public void getRecipeBasicData(Integer recipeId) {
        recipeService.getRecipeBasicData(recipeId);
    }

    public String getRecipeName() {
        return recipeService.getRecipeName();
    }

    public String getCookingTime() {
        return recipeService.getCookingTime();
    }

    public String getPreparationTime() {
        return recipeService.getPreparationTime();
    }

    public String getImagePath() {
        return recipeService.getImagePath();
    }

    public List<RecipeIngredient> getIngredients(Integer recipeId) {
        return recipeService.getIngredients(recipeId);
    }

    public String getInstructions(Integer recipeId) {
        return recipeService.getInstructions(recipeId);
    }
}