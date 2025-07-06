package dao.mappers;
import org.apache.ibatis.annotations.Param;
import java.util.ArrayList;


public interface RecipeMapper {
    
    boolean addRecipe(@Param("recipe") Recipe recipe);

    
    boolean deleteRecipe(@Param("recipeID") Integer recipeID);

    
    boolean updateRecipe(@Param("recipe") Recipe recipe);

    
    Recipe getRecipeById(@Param("recipeId") int recipeId);

    
    Recipe getNewRecipe();

    
    ArrayList<Recipe> getRecipeByName(@Param("recipeName") String recipeName);

    
    ArrayList<Recipe> getAllRecipes();

    
    ArrayList<Recipe> getRecipeByCategory(@Param("category") String category);


}
