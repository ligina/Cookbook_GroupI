package dao.mappers;

import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * MyBatis mapper interface for PreparationStep entity operations.
 * This interface defines database operations for managing recipe preparation steps
 * including adding, updating, deleting, and retrieving preparation steps.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public interface PreparationStepMapper {

    /**
     * Adds a new preparation step to the database.
     * 
     * @param preparationStep the PreparationStep object to be added
     * @return true if the addition was successful, false otherwise
     */
    boolean addPreparationStep(@Param("preparationStep") PreparationStep preparationStep);

    /**
     * Deletes all preparation steps for a specific recipe.
     * This method is typically used when updating recipe instructions.
     * 
     * @param recipeID the ID of the recipe whose preparation steps should be deleted
     * @return true if the deletion was successful, false otherwise
     */
    boolean deletePreparationStep(@Param("recipeID") Integer recipeID);

    /**
     * Updates an existing preparation step in the database.
     * 
     * @param preparationStep the PreparationStep object with updated information
     * @return true if the update was successful, false otherwise
     */
    boolean updatePreparationStep(@Param("preparationStep") PreparationStep preparationStep);

    /**
     * Retrieves all preparation steps for a specific recipe.
     * The steps are typically ordered by step number.
     * 
     * @param recipeId the ID of the recipe to get preparation steps for
     * @return a list of PreparationStep objects for the specified recipe
     */
    List<PreparationStep> getPreparationStepsByRecipeId(@Param("recipeId") Integer recipeId);
}
