package dao.mappers;

/**
 * RecipeIngredient represents an ingredient used in a recipe with its nutritional information.
 * This class stores ingredient details including quantity, unit, and nutritional values
 * such as calories, protein, fat, and carbohydrates per unit.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class RecipeIngredient {
    private int recipeId;
    private String name;
    private Float quantity;
    private String unit;
    private String description;
    private Float unitCalories;
    private Float unitProtein;
    private Float unitFat;
    private Float unitCarbohydrates;

    
    /**
     * Default constructor for RecipeIngredient.
     */
    public RecipeIngredient() {

    }

    
    /**
     * Constructor for RecipeIngredient with basic information.
     * 
     * @param recipeId The ID of the recipe this ingredient belongs to
     * @param name The name of the ingredient
     * @param quantity The quantity of the ingredient
     * @param unit The unit of measurement
     * @param description Additional description of the ingredient
     */
    public RecipeIngredient(int recipeId, String name, Float quantity, String unit, String description) {
        this.recipeId = recipeId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.description = description;
        this.unitCalories = 0.0f;
        this.unitProtein = 0.0f;
        this.unitFat = 0.0f;
        this.unitCarbohydrates = 0.0f;
    }

    
    /**
     * Constructor for RecipeIngredient with complete nutritional information.
     * 
     * @param recipeId The ID of the recipe this ingredient belongs to
     * @param name The name of the ingredient
     * @param quantity The quantity of the ingredient
     * @param unit The unit of measurement
     * @param description Additional description of the ingredient
     * @param unitCalories Calories per unit of the ingredient
     * @param unitProtein Protein content per unit
     * @param unitFat Fat content per unit
     * @param unitCarbohydrates Carbohydrate content per unit
     */
    public RecipeIngredient(int recipeId, String name, Float quantity, String unit, String description,
                           Float unitCalories, Float unitProtein, Float unitFat, Float unitCarbohydrates) {
        this.recipeId = recipeId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.description = description;
        this.unitCalories = unitCalories;
        this.unitProtein = unitProtein;
        this.unitFat = unitFat;
        this.unitCarbohydrates = unitCarbohydrates;
    }

    
    /**
     * Copy constructor for RecipeIngredient.
     * 
     * @param ingredient The RecipeIngredient object to copy
     */
    public RecipeIngredient(RecipeIngredient ingredient) {
        this.name = ingredient.name;
        this.quantity = ingredient.quantity;
        this.unit = ingredient.unit;
        this.description = ingredient.description;
        this.recipeId = ingredient.recipeId;
        this.unitCalories = ingredient.unitCalories;
        this.unitProtein = ingredient.unitProtein;
        this.unitFat = ingredient.unitFat;
        this.unitCarbohydrates = ingredient.unitCarbohydrates;
    }

    
    public int getRecipeId() {
        return recipeId;
    }

    
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public Float getQuantity() {
        return quantity;
    }

    
    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    
    public String getUnit() {
        return unit;
    }

    
    public void setUnit(String unit) {
        this.unit = unit;
    }

    
    public String getDescription() {
        return description;
    }

    
    public void setDescription(String description) {
        this.description = description;
    }

    
    public Float getUnitCalories() {
        return unitCalories;
    }

    
    public void setUnitCalories(Float unitCalories) {
        this.unitCalories = unitCalories;
    }

    
    public Float getUnitProtein() {
        return unitProtein;
    }

    
    public void setUnitProtein(Float unitProtein) {
        this.unitProtein = unitProtein;
    }

    
    public Float getUnitFat() {
        return unitFat;
    }

    
    public void setUnitFat(Float unitFat) {
        this.unitFat = unitFat;
    }

    
    public Float getUnitCarbohydrates() {
        return unitCarbohydrates;
    }

    
    public void setUnitCarbohydrates(Float unitCarbohydrates) {
        this.unitCarbohydrates = unitCarbohydrates;
    }

    
    public Float getTotalCalories() {
        return unitCalories != null && quantity != null ? unitCalories * quantity : 0.0f;
    }

    
    public Float getTotalProtein() {
        return unitProtein != null && quantity != null ? unitProtein * quantity : 0.0f;
    }

    
    public Float getTotalFat() {
        return unitFat != null && quantity != null ? unitFat * quantity : 0.0f;
    }

    
    public Float getTotalCarbohydrates() {
        return unitCarbohydrates != null && quantity != null ? unitCarbohydrates * quantity : 0.0f;
    }

    
    public void setTotalCalories(Float totalCalories) {
        if (quantity != null && quantity > 0) {
            this.unitCalories = totalCalories / quantity;
        } else {
            this.unitCalories = totalCalories != null ? totalCalories : 0.0f;
        }
    }

    
    public void setTotalProtein(Float totalProtein) {
        if (quantity != null && quantity > 0) {
            this.unitProtein = totalProtein / quantity;
        } else {
            this.unitProtein = totalProtein != null ? totalProtein : 0.0f;
        }
    }

    
    public void setTotalFat(Float totalFat) {
        if (quantity != null && quantity > 0) {
            this.unitFat = totalFat / quantity;
        } else {
            this.unitFat = totalFat != null ? totalFat : 0.0f;
        }
    }

    
    public void setTotalCarbohydrates(Float totalCarbohydrates) {
        if (quantity != null && quantity > 0) {
            this.unitCarbohydrates = totalCarbohydrates / quantity;
        } else {
            this.unitCarbohydrates = totalCarbohydrates != null ? totalCarbohydrates : 0.0f;
        }
    }

    
    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "recipeId=" + recipeId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", description='" + description + '\'' +
                ", unitCalories=" + unitCalories +
                ", unitProtein=" + unitProtein +
                ", unitFat=" + unitFat +
                ", unitCarbohydrates=" + unitCarbohydrates +
                '}';
    }
}
