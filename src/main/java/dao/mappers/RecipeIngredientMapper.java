package dao.mappers;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * MyBatis mapper interface for RecipeIngredient entity operations.
 * This interface defines database operations for managing recipe ingredients
 * including adding, updating, deleting, and retrieving ingredients for recipes.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public interface RecipeIngredientMapper {

    /**
     * Adds a new recipe ingredient to the database.
     * 
     * @param recipeIngredient the RecipeIngredient object to be added
     * @return true if the addition was successful, false otherwise
     */
    boolean addRecipeIngredient(@Param("recipeIngredient") RecipeIngredient recipeIngredient);

    /**
     * Deletes all ingredients for a specific recipe.
     * This method is typically used when updating recipe ingredients.
     * 
     * @param recipeID the ID of the recipe whose ingredients should be deleted
     * @return true if the deletion was successful, false otherwise
     */
    boolean deleteRecipeIngredient(@Param("recipeID") Integer recipeID);

    /**
     * Updates an existing recipe ingredient in the database.
     * 
     * @param recipeIngredient the RecipeIngredient object with updated information
     * @return true if the update was successful, false otherwise
     */
    boolean updateRecipeIngredient(@Param("recipeIngredient") RecipeIngredient recipeIngredient);

    /**
     * Retrieves all ingredients for a specific recipe.
     * 
     * @param recipeId the ID of the recipe to get ingredients for
     * @return a list of RecipeIngredient objects for the specified recipe
     */
    List<RecipeIngredient> getRecipeIngredientsByRecipeId(@Param("recipeId") int recipeId);
}
