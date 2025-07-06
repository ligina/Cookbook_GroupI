package dao.mappers;
import org.apache.ibatis.annotations.Param;
import java.util.ArrayList;

/**
 * MyBatis mapper interface for Recipe entity operations.
 * This interface defines database operations for managing recipes including
 * CRUD operations, search functionality, and recipe retrieval methods.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public interface RecipeMapper {
    
    /**
     * Adds a new recipe to the database.
     * 
     * @param recipe the Recipe object to be added
     * @return true if the addition was successful, false otherwise
     */
    boolean addRecipe(@Param("recipe") Recipe recipe);

    /**
     * Deletes a recipe from the database.
     * This will also cascade delete related ingredients and preparation steps.
     * 
     * @param recipeID the ID of the recipe to be deleted
     * @return true if the deletion was successful, false otherwise
     */
    boolean deleteRecipe(@Param("recipeID") Integer recipeID);

    /**
     * Updates an existing recipe in the database.
     * 
     * @param recipe the Recipe object with updated information
     * @return true if the update was successful, false otherwise
     */
    boolean updateRecipe(@Param("recipe") Recipe recipe);

    /**
     * Retrieves a recipe by its unique ID.
     * 
     * @param recipeId the unique identifier of the recipe
     * @return the Recipe object if found, null otherwise
     */
    Recipe getRecipeById(@Param("recipeId") int recipeId);

    /**
     * Retrieves the most recently added recipe.
     * This method is typically used after adding a new recipe to get its generated ID.
     * 
     * @return the most recently added Recipe object
     */
    Recipe getNewRecipe();

    /**
     * Searches for recipes by name.
     * This method performs a search that may include partial name matches.
     * 
     * @param recipeName the name or partial name to search for
     * @return a list of Recipe objects matching the search criteria
     */
    ArrayList<Recipe> getRecipeByName(@Param("recipeName") String recipeName);

    /**
     * Retrieves all recipes from the database.
     * 
     * @return a list of all Recipe objects in the database
     */
    ArrayList<Recipe> getAllRecipes();

    /**
     * Retrieves recipes by category.
     * 
     * @param category the category to filter recipes by
     * @return a list of Recipe objects in the specified category
     */
    ArrayList<Recipe> getRecipeByCategory(@Param("category") String category);

}
