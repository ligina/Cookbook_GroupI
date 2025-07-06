package service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for handling nutrition-related calculations and data management.
 * This class provides functionality for retrieving nutritional information about ingredients
 * and calculating nutritional values based on quantities and units.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class NutritionService {
    
    /** Static database containing nutritional information for various ingredients */
    private static final Map<String, NutritionData> NUTRITION_DATABASE = new HashMap<>();
    
    static {
        
        NUTRITION_DATABASE.put("chicken breast", new NutritionData(165, 31.0f, 3.6f, 0));
        NUTRITION_DATABASE.put("chicken wings", new NutritionData(203, 30.5f, 8.1f, 0));
        NUTRITION_DATABASE.put("pork", new NutritionData(250, 26.0f, 17.0f, 0));
        NUTRITION_DATABASE.put("pork belly", new NutritionData(518, 9.3f, 53.0f, 0));
        NUTRITION_DATABASE.put("beef", new NutritionData(250, 26.0f, 17.0f, 0));
        NUTRITION_DATABASE.put("eggs", new NutritionData(155, 13.0f, 11.0f, 1.1f));
        NUTRITION_DATABASE.put("tomatoes", new NutritionData(18, 0.9f, 0.2f, 3.9f));
        NUTRITION_DATABASE.put("onions", new NutritionData(40, 1.1f, 0.1f, 9.3f));
        NUTRITION_DATABASE.put("garlic", new NutritionData(149, 6.4f, 0.5f, 33.1f));
        NUTRITION_DATABASE.put("potato", new NutritionData(77, 2.0f, 0.1f, 17.5f));
        NUTRITION_DATABASE.put("rice", new NutritionData(130, 2.7f, 0.3f, 28.2f));
        NUTRITION_DATABASE.put("noodles", new NutritionData(138, 4.5f, 0.9f, 27.4f));
        NUTRITION_DATABASE.put("flour", new NutritionData(364, 10.3f, 1.0f, 76.3f));
        NUTRITION_DATABASE.put("sugar", new NutritionData(387, 0, 0, 99.8f));
        NUTRITION_DATABASE.put("salt", new NutritionData(0, 0, 0, 0));
        NUTRITION_DATABASE.put("soy sauce", new NutritionData(8, 1.3f, 0.01f, 0.8f));
        NUTRITION_DATABASE.put("oyster sauce", new NutritionData(51, 2.4f, 0.3f, 11.9f));
        NUTRITION_DATABASE.put("ketchup", new NutritionData(112, 1.7f, 0.5f, 25.8f));
        NUTRITION_DATABASE.put("olive oil", new NutritionData(884, 0, 100.0f, 0));
        NUTRITION_DATABASE.put("vegetable oil", new NutritionData(884, 0, 100.0f, 0));
        NUTRITION_DATABASE.put("butter", new NutritionData(717, 0.9f, 81.1f, 0.1f));
        NUTRITION_DATABASE.put("milk", new NutritionData(42, 3.4f, 1.0f, 5.0f));
        NUTRITION_DATABASE.put("cheese", new NutritionData(113, 7.0f, 9.0f, 1.0f));
        NUTRITION_DATABASE.put("bread", new NutritionData(265, 9.0f, 3.2f, 49.0f));
        NUTRITION_DATABASE.put("pasta", new NutritionData(131, 5.0f, 1.1f, 25.0f));
        NUTRITION_DATABASE.put("carrots", new NutritionData(41, 0.9f, 0.2f, 9.6f));
        NUTRITION_DATABASE.put("broccoli", new NutritionData(34, 2.8f, 0.4f, 7.0f));
        NUTRITION_DATABASE.put("spinach", new NutritionData(23, 2.9f, 0.4f, 3.6f));
        NUTRITION_DATABASE.put("cabbage", new NutritionData(25, 1.3f, 0.1f, 5.8f));
        NUTRITION_DATABASE.put("pepper", new NutritionData(251, 10.4f, 3.3f, 64.0f));
        NUTRITION_DATABASE.put("ginger", new NutritionData(80, 1.8f, 0.8f, 18.0f));
        NUTRITION_DATABASE.put("mushrooms", new NutritionData(22, 3.1f, 0.3f, 3.3f));
        NUTRITION_DATABASE.put("corn", new NutritionData(86, 3.3f, 1.4f, 19.0f));
        NUTRITION_DATABASE.put("apple", new NutritionData(52, 0.3f, 0.2f, 14.0f));
        NUTRITION_DATABASE.put("banana", new NutritionData(89, 1.1f, 0.3f, 23.0f));
        NUTRITION_DATABASE.put("orange", new NutritionData(47, 0.9f, 0.1f, 12.0f));
        NUTRITION_DATABASE.put("lemon", new NutritionData(29, 1.1f, 0.3f, 9.3f));
        NUTRITION_DATABASE.put("coca-cola", new NutritionData(42, 0, 0, 10.6f));
        NUTRITION_DATABASE.put("bay leaves", new NutritionData(313, 7.6f, 8.4f, 75.0f));
    }
    
    /**
     * Retrieves nutritional data for a specific ingredient.
     * Performs case-insensitive matching and partial name matching.
     * 
     * @param ingredientName the name of the ingredient to look up
     * @return NutritionData object containing nutritional information, or null if not found
     */
    public static NutritionData getNutritionData(String ingredientName) {
        if (ingredientName == null) return null;
        
        String cleanName = ingredientName.toLowerCase().trim();
        
        // Try exact match first
        if (NUTRITION_DATABASE.containsKey(cleanName)) {
            return NUTRITION_DATABASE.get(cleanName);
        }
        
        // Try partial matching
        for (Map.Entry<String, NutritionData> entry : NUTRITION_DATABASE.entrySet()) {
            if (entry.getKey().contains(cleanName) || cleanName.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        
        return null;
    }
    
    /**
     * Calculates nutritional values for a specific quantity and unit of an ingredient.
     * The base nutritional data is per 100 grams, and this method scales it appropriately.
     * 
     * @param ingredientName the name of the ingredient
     * @param quantity the quantity of the ingredient
     * @param unit the unit of measurement (g, kg, oz, lb, etc.)
     * @return NutritionData object with calculated values, or null if calculation fails
     */
    public static NutritionData calculateNutritionForQuantity(String ingredientName, Float quantity, String unit) {
        NutritionData baseNutrition = getNutritionData(ingredientName);
        if (baseNutrition == null || quantity == null || quantity <= 0) {
            return null;
        }
        
        // Convert quantity to grams
        float quantityInGrams = convertToGrams(quantity, unit);
        if (quantityInGrams <= 0) {
            return null;
        }
        
        // Calculate multiplier based on 100g base values
        float multiplier = quantityInGrams / 100.0f;
        
        return new NutritionData(
            Math.round(baseNutrition.calories * multiplier),
            baseNutrition.protein * multiplier,
            baseNutrition.fat * multiplier,
            baseNutrition.carbohydrates * multiplier
        );
    }
    
    /**
     * Converts various units of measurement to grams.
     * Supports weight units (g, kg, oz, lb) and volume units (ml, l, cups, tbsp, tsp).
     * 
     * @param quantity the numeric quantity to convert
     * @param unit the unit of measurement
     * @return the equivalent weight in grams
     */
    private static float convertToGrams(Float quantity, String unit) {
        if (unit == null) return quantity; 
        
        String unitLower = unit.toLowerCase().trim();
        
        switch (unitLower) {
            case "g":
            case "gram":
            case "grams":
                return quantity;
            case "kg":
            case "kilogram":
            case "kilograms":
                return quantity * 1000;
            case "oz":
            case "ounce":
            case "ounces":
                return quantity * 28.35f;
            case "lb":
            case "pound":
            case "pounds":
                return quantity * 453.59f;
            case "ml":
            case "milliliter":
            case "milliliters":
                
                return quantity;
            case "l":
            case "liter":
            case "liters":
                return quantity * 1000;
            case "cup":
            case "cups":
                return quantity * 240; 
            case "tbsp":
            case "tablespoon":
            case "tablespoons":
                return quantity * 15;
            case "tsp":
            case "teaspoon":
            case "teaspoons":
                return quantity * 5;
            case "piece":
            case "pieces":
            case "个":
                return quantity * 50; 
            case "slice":
            case "slices":
                return quantity * 25; 
            case "spoon":
            case "spoons":
                return quantity * 10; 
            default:
                return quantity;
        }
    }
    
    /**
     * Checks if nutritional data is available for a specific ingredient.
     * 
     * @param ingredientName the name of the ingredient to check
     * @return true if nutritional data exists, false otherwise
     */
    public static boolean hasNutritionData(String ingredientName) {
        return getNutritionData(ingredientName) != null;
    }
    
    /**
     * Inner class representing nutritional data for an ingredient.
     * Contains calorie, protein, fat, and carbohydrate information per 100 grams.
     * 
     * @author Ziang Liu
     * @version 1.0
     * @since 1.0
     */
    public static class NutritionData {
        /** Calories per 100 grams */
        public final int calories;
        
        /** Protein content in grams per 100 grams */
        public final float protein;
        
        /** Fat content in grams per 100 grams */
        public final float fat;
        
        /** Carbohydrate content in grams per 100 grams */
        public final float carbohydrates;
        
        /**
         * Constructor for NutritionData.
         * 
         * @param calories the calorie content per 100 grams
         * @param protein the protein content in grams per 100 grams
         * @param fat the fat content in grams per 100 grams
         * @param carbohydrates the carbohydrate content in grams per 100 grams
         */
        public NutritionData(int calories, float protein, float fat, float carbohydrates) {
            this.calories = calories;
            this.protein = protein;
            this.fat = fat;
            this.carbohydrates = carbohydrates;
        }
        
        /**
         * Returns a formatted string representation of the nutritional data.
         * 
         * @return a string containing formatted nutritional information
         */
        @Override
        public String toString() {
            return String.format("Calories: %d, Protein: %.1fg, Fat: %.1fg, Carbs: %.1fg", 
                               calories, protein, fat, carbohydrates);
        }
    }
}