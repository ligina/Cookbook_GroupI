package dao.mappers;

/**
 * PreparationStep entity class representing a single step in recipe preparation.
 * This class contains information about individual cooking instructions for a recipe.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class PreparationStep {

    /** The ID of the recipe this step belongs to */
    private int recipeId;
    
    /** The step number in the preparation sequence */
    private int step;
    
    /** The detailed description of this preparation step */
    private String description;

    /**
     * Default constructor for PreparationStep.
     * Creates an empty PreparationStep object.
     */
    public PreparationStep() {

    }

    /**
     * Parameterized constructor for PreparationStep.
     * 
     * @param recipeId the ID of the recipe this step belongs to
     * @param step the step number in the preparation sequence
     * @param description the detailed description of this preparation step
     */
    public PreparationStep(int recipeId, int step, String description) {
        this.recipeId = recipeId;
        this.step = step;
        this.description = description;
    }

    /**
     * Gets the recipe ID.
     * 
     * @return the ID of the recipe this step belongs to
     */
    public int getRecipeId() {
        return recipeId;
    }

    /**
     * Sets the recipe ID.
     * 
     * @param recipeId the ID of the recipe this step belongs to
     */
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    /**
     * Gets the step number.
     * 
     * @return the step number in the preparation sequence
     */
    public int getStep() {
        return step;
    }

    /**
     * Sets the step number.
     * 
     * @param step the step number in the preparation sequence
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * Gets the step description.
     * 
     * @return the detailed description of this preparation step
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the step description.
     * 
     * @param description the detailed description of this preparation step
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the PreparationStep object.
     * 
     * @return a string containing recipe ID, step number, and description
     */
    @Override
    public String toString() {
        return "PreparationStep{" +
                "recipeId=" + recipeId +
                ", step=" + step +
                ", description='" + description + '\'' +
                '}';
    }
}
