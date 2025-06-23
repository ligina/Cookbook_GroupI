package dao.mappers;

/**
 * Represents an ingredient used in a recipe, containing details such as recipe ID,
 * ingredient name, quantity, unit of measurement, and description.
 *
 * @author 
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
     * Default constructor for the RecipeIngredient class.
     */
    public RecipeIngredient() {

    }

    /**
     * Constructs a RecipeIngredient object with specified attributes.
     *
     * @param recipeId    The unique identifier of the recipe this ingredient belongs to.
     * @param name        The name of the ingredient.
     * @param quantity    The quantity or amount of the ingredient.
     * @param unit        The unit of measurement for the quantity (e.g., grams, milliliters).
     * @param description A brief description or additional notes about the ingredient.
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
     * Constructs a RecipeIngredient object with all attributes including nutritional information.
     *
     * @param recipeId           The unique identifier of the recipe this ingredient belongs to.
     * @param name               The name of the ingredient.
     * @param quantity           The quantity or amount of the ingredient.
     * @param unit               The unit of measurement for the quantity.
     * @param description        A brief description or additional notes about the ingredient.
     * @param unitCalories       Calories per unit of this ingredient.
     * @param unitProtein        Protein per unit of this ingredient.
     * @param unitFat            Fat per unit of this ingredient.
     * @param unitCarbohydrates  Carbohydrates per unit of this ingredient.
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
     * Copy constructor for RecipeIngredient objects.
     * Creates a new RecipeIngredient object based on an existing one.
     *
     * @param ingredient The RecipeIngredient object to copy.
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

    /**
     * Retrieves the recipe ID associated with this ingredient.
     *
     * @return The recipe ID.
     */
    public int getRecipeId() {
        return recipeId;
    }

    /**
     * Sets the recipe ID associated with this ingredient.
     *
     * @param recipeId The recipe ID to set.
     */
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    /**
     * Retrieves the name of the ingredient.
     *
     * @return The name of the ingredient.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the ingredient.
     *
     * @param name The name of the ingredient to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the quantity or amount of the ingredient.
     *
     * @return The quantity or amount of the ingredient.
     */
    public Float getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity or amount of the ingredient.
     *
     * @param quantity The quantity or amount of the ingredient to set.
     */
    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the unit of measurement for the ingredient quantity.
     *
     * @return The unit of measurement.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the unit of measurement for the ingredient quantity.
     *
     * @param unit The unit of measurement to set.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Retrieves the description or additional notes about the ingredient.
     *
     * @return The description of the ingredient.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description or additional notes about the ingredient.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the calories per unit of this ingredient.
     *
     * @return The calories per unit.
     */
    public Float getUnitCalories() {
        return unitCalories;
    }

    /**
     * Sets the calories per unit of this ingredient.
     *
     * @param unitCalories The calories per unit to set.
     */
    public void setUnitCalories(Float unitCalories) {
        this.unitCalories = unitCalories;
    }

    /**
     * Retrieves the protein per unit of this ingredient.
     *
     * @return The protein per unit.
     */
    public Float getUnitProtein() {
        return unitProtein;
    }

    /**
     * Sets the protein per unit of this ingredient.
     *
     * @param unitProtein The protein per unit to set.
     */
    public void setUnitProtein(Float unitProtein) {
        this.unitProtein = unitProtein;
    }

    /**
     * Retrieves the fat per unit of this ingredient.
     *
     * @return The fat per unit.
     */
    public Float getUnitFat() {
        return unitFat;
    }

    /**
     * Sets the fat per unit of this ingredient.
     *
     * @param unitFat The fat per unit to set.
     */
    public void setUnitFat(Float unitFat) {
        this.unitFat = unitFat;
    }

    /**
     * Retrieves the carbohydrates per unit of this ingredient.
     *
     * @return The carbohydrates per unit.
     */
    public Float getUnitCarbohydrates() {
        return unitCarbohydrates;
    }

    /**
     * Sets the carbohydrates per unit of this ingredient.
     *
     * @param unitCarbohydrates The carbohydrates per unit to set.
     */
    public void setUnitCarbohydrates(Float unitCarbohydrates) {
        this.unitCarbohydrates = unitCarbohydrates;
    }

    /**
     * Calculates total calories for this ingredient based on quantity.
     *
     * @return Total calories for this ingredient.
     */
    public Float getTotalCalories() {
        return unitCalories != null && quantity != null ? unitCalories * quantity : 0.0f;
    }

    /**
     * Calculates total protein for this ingredient based on quantity.
     *
     * @return Total protein for this ingredient.
     */
    public Float getTotalProtein() {
        return unitProtein != null && quantity != null ? unitProtein * quantity : 0.0f;
    }

    /**
     * Calculates total fat for this ingredient based on quantity.
     *
     * @return Total fat for this ingredient.
     */
    public Float getTotalFat() {
        return unitFat != null && quantity != null ? unitFat * quantity : 0.0f;
    }

    /**
     * Calculates total carbohydrates for this ingredient based on quantity.
     *
     * @return Total carbohydrates for this ingredient.
     */
    public Float getTotalCarbohydrates() {
        return unitCarbohydrates != null && quantity != null ? unitCarbohydrates * quantity : 0.0f;
    }

    /**
     * Sets the total calories by calculating unit calories based on quantity.
     * This allows direct editing of total nutrition values.
     *
     * @param totalCalories The total calories to set.
     */
    public void setTotalCalories(Float totalCalories) {
        if (quantity != null && quantity > 0) {
            this.unitCalories = totalCalories / quantity;
        } else {
            this.unitCalories = totalCalories != null ? totalCalories : 0.0f;
        }
    }

    /**
     * Sets the total protein by calculating unit protein based on quantity.
     * This allows direct editing of total nutrition values.
     *
     * @param totalProtein The total protein to set.
     */
    public void setTotalProtein(Float totalProtein) {
        if (quantity != null && quantity > 0) {
            this.unitProtein = totalProtein / quantity;
        } else {
            this.unitProtein = totalProtein != null ? totalProtein : 0.0f;
        }
    }

    /**
     * Sets the total fat by calculating unit fat based on quantity.
     * This allows direct editing of total nutrition values.
     *
     * @param totalFat The total fat to set.
     */
    public void setTotalFat(Float totalFat) {
        if (quantity != null && quantity > 0) {
            this.unitFat = totalFat / quantity;
        } else {
            this.unitFat = totalFat != null ? totalFat : 0.0f;
        }
    }

    /**
     * Sets the total carbohydrates by calculating unit carbohydrates based on quantity.
     * This allows direct editing of total nutrition values.
     *
     * @param totalCarbohydrates The total carbohydrates to set.
     */
    public void setTotalCarbohydrates(Float totalCarbohydrates) {
        if (quantity != null && quantity > 0) {
            this.unitCarbohydrates = totalCarbohydrates / quantity;
        } else {
            this.unitCarbohydrates = totalCarbohydrates != null ? totalCarbohydrates : 0.0f;
        }
    }

    /**
     * Returns a string representation of the RecipeIngredient object.
     *
     * @return A string containing all attributes of the RecipeIngredient.
     */
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
