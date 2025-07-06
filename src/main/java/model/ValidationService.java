package model;

import javafx.scene.control.Alert;


public class ValidationService {
    private ImageService imageService;

    public ValidationService() {
        this.imageService = new ImageService();
    }

    
    public boolean validateServingNumber(String serveNumber) {
        
        if (serveNumber == null || serveNumber.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Serving number cannot be empty!");
            return false;
        }
        
        
        if (!serveNumber.matches("^\\d+$")) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Serving number must contain only numbers and cannot be negative!");
            return false;
        }
        
        try {
            int servings = Integer.parseInt(serveNumber);
            
            
            if (servings <= 0) {
                UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Serving number must be a positive number!");
                return false;
            }
            
            
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

    
    public boolean validateNutritionValue(String value, String fieldName) {
        
        if (value == null || value.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", fieldName + " cannot be empty!");
            return false;
        }
        
        
        if (!value.matches("^\\d*\\.?\\d+$")) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", fieldName + " must contain only numbers and cannot be negative!");
            return false;
        }
        
        
        if (value.length() > 10) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", fieldName + " cannot exceed 10 digits!");
            return false;
        }
        
        
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

    
    public boolean validateRecipeIngredient(String recipeName, Float quantity, String unit) {
        
        if(recipeName == null || recipeName.isEmpty()){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Ingredient name cannot be empty!");
            return false;
        }
        
        
        if (recipeName.length() > 30) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Ingredient name length cannot exceed 30 characters!");
            return false;
        }
        
        
        if(quantity == null || quantity == 0.0){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Ingredient quantity cannot be empty!");
            return false;
        }
        
        
        if(quantity < 0.0){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Ingredient quantity cannot be negative!");
            return false;
        }
        
        
        if(String.valueOf(quantity).length() > 10){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Quantity cannot exceed 10 digits!");
            return false;
        }

        
        if(unit == null || unit.isEmpty()){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Unit cannot be empty!");
            return false;
        }
        
        
        if(unit.length() > 10){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Unit length cannot exceed 10 characters!");
            return false;
        }
        
        
        if (!unit.matches("^[a-zA-Z]+$")) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Unit must contain only letters!");
            return false;
        }
        
        return true;
    }

    
    public boolean validateRecipe(String recipeName, String cookingTime, String preparationTime, String recipeImage) {
        
        if (recipeName == null || recipeName.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Recipe name cannot be empty!");
            return false;
        }
        
        
        if(recipeName.length() > 70){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Recipe name length cannot exceed 70 characters!");
            return false;
        }
        
        
        if (preparationTime == null || preparationTime.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Preparation time cannot be empty!");
            return false;
        }
        
        
        if (cookingTime == null || cookingTime.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Cooking time cannot be empty!");
            return false;
        }
        
        
        if (!preparationTime.matches("^\\d+$")) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Preparation time must contain only numbers and cannot be negative!");
            return false;
        }
        
        
        if (!cookingTime.matches("^\\d+$")) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Cooking time must contain only numbers and cannot be negative!");
            return false;
        }
        
        
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
        
        
        if(preparationTime.length() > 5){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Preparation time cannot exceed 5 digits!");
            return false;
        }
        
        
        if(cookingTime.length() > 5){
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Cooking time cannot exceed 5 digits!");
            return false;
        }
        
        
        if (recipeImage == null || recipeImage.isEmpty()) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Recipe image cannot be empty!");
            return false;
        }
        
        
        if (!imageService.isValidURL(recipeImage)) {
            UIUtils.displayAlert(Alert.AlertType.WARNING, "Warning", "Image URL is not in valid format!");
            return false;
        }

        return true;
    }

    
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