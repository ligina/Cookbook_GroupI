package model;

import javafx.scene.control.Alert;

/**
 * Data validation service for recipes, ingredients and other inputs
 */
public class ValidationService {
    private ImageService imageService;

    public ValidationService() {
        this.imageService = new ImageService();
    }

    /**
     * Validates serving number according to requirements
     */
    public boolean validateServingNumber(String serveNumber) {
        // Cannot be empty
        if (serveNumber == null || serveNumber.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Serving number cannot be empty!");
            return false;
        }
        
        // Must be pure numbers (no negative signs allowed)
        if (!serveNumber.matches("^\\d+$")) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Serving number must contain only numbers and cannot be negative!");
            return false;
        }
        
        try {
            int servings = Integer.parseInt(serveNumber);
            
            // Cannot be 0 or negative
            if (servings <= 0) {
                UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Serving number must be a positive number!");
                return false;
            }
            
            // Cannot exceed 10
            if (servings > 10) {
                UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Serving number cannot exceed 10!");
                return false;
            }
            
            return true;
        } catch (NumberFormatException e) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Invalid serving number format!");
            return false;
        }
    }

    /**
     * Validates nutrition values (calories, protein, fat, carbs)
     */
    public boolean validateNutritionValue(String value, String fieldName) {
        // Cannot be empty
        if (value == null || value.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", fieldName + " cannot be empty!");
            return false;
        }
        
        // Must be pure numbers (no negative signs allowed)
        if (!value.matches("^\\d*\\.?\\d+$")) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", fieldName + " must contain only numbers and cannot be negative!");
            return false;
        }
        
        // String length cannot exceed 10 digits
        if (value.length() > 10) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", fieldName + " cannot exceed 10 digits!");
            return false;
        }
        
        // Additional check: ensure the numeric value is not negative
        try {
            double numericValue = Double.parseDouble(value);
            if (numericValue < 0) {
                UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", fieldName + " cannot be negative!");
                return false;
            }
        } catch (NumberFormatException e) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", fieldName + " has invalid number format!");
            return false;
        }
        
        return true;
    }

    /**
     * Validates recipe ingredient details
     */
    public boolean validateRecipeIngredient(String recipeName, Float quantity, String unit) {
        // Ingredient name cannot be empty
        if(recipeName == null || recipeName.isEmpty()){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Ingredient name cannot be empty!");
            return false;
        }
        
        // Ingredient name length cannot exceed 30 characters
        if (recipeName.length() > 30) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Ingredient name length cannot exceed 30 characters!");
            return false;
        }
        
        // Quantity cannot be empty or zero
        if(quantity == null || quantity == 0.0){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Ingredient quantity cannot be empty!");
            return false;
        }
        
        // Quantity cannot be negative
        if(quantity < 0.0){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Ingredient quantity cannot be negative!");
            return false;
        }
        
        // Quantity string length cannot exceed 10 digits
        if(String.valueOf(quantity).length() > 10){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Quantity cannot exceed 10 digits!");
            return false;
        }

        // Unit cannot be empty
        if(unit == null || unit.isEmpty()){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Unit cannot be empty!");
            return false;
        }
        
        // Unit length cannot exceed 10 characters
        if(unit.length() > 10){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Unit length cannot exceed 10 characters!");
            return false;
        }
        
        // Unit must contain only letters
        if (!unit.matches("^[a-zA-Z]+$")) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Unit must contain only letters!");
            return false;
        }
        
        return true;
    }

    /**
     * Validates recipe details
     */
    public boolean validateRecipe(String recipeName, String cookingTime, String preparationTime, String recipeImage) {
        // Recipe name cannot be empty
        if (recipeName == null || recipeName.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Recipe name cannot be empty!");
            return false;
        }
        
        // Recipe name length cannot exceed 70 characters
        if(recipeName.length() > 70){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Recipe name length cannot exceed 70 characters!");
            return false;
        }
        
        // Preparation time cannot be empty
        if (preparationTime == null || preparationTime.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Preparation time cannot be empty!");
            return false;
        }
        
        // Cooking time cannot be empty
        if (cookingTime == null || cookingTime.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Cooking time cannot be empty!");
            return false;
        }
        
        // Preparation time must be pure numbers (no negative signs allowed)
        if (!preparationTime.matches("^\\d+$")) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Preparation time must contain only numbers and cannot be negative!");
            return false;
        }
        
        // Cooking time must be pure numbers (no negative signs allowed)
        if (!cookingTime.matches("^\\d+$")) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Cooking time must contain only numbers and cannot be negative!");
            return false;
        }
        
        // Additional validation: ensure times are positive
        try {
            int prepTime = Integer.parseInt(preparationTime);
            int cookTime = Integer.parseInt(cookingTime);
            
            if (prepTime <= 0) {
                UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Preparation time must be a positive number!");
                return false;
            }
            
            if (cookTime <= 0) {
                UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Cooking time must be a positive number!");
                return false;
            }
        } catch (NumberFormatException e) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Invalid time format!");
            return false;
        }
        
        // Preparation time string length cannot exceed 5 digits
        if(preparationTime.length() > 5){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Preparation time cannot exceed 5 digits!");
            return false;
        }
        
        // Cooking time string length cannot exceed 5 digits
        if(cookingTime.length() > 5){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Cooking time cannot exceed 5 digits!");
            return false;
        }
        
        // Image URL cannot be empty
        if (recipeImage == null || recipeImage.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Recipe image cannot be empty!");
            return false;
        }
        
        // Image URL must be valid URL format
        if (!imageService.isValidURL(recipeImage)) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Image URL is not in valid format!");
            return false;
        }

        return true;
    }

    /**
     * Checks if a string represents an integer
     */
    public boolean serveNumberIsInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            return Integer.parseInt(str) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}