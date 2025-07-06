package dao.mappers;
import org.apache.ibatis.annotations.Param;
import java.util.List;


public interface RecipeIngredientMapper {

    
    boolean addRecipeIngredient(@Param("recipeIngredient") RecipeIngredient recipeIngredient);

    
    boolean deleteRecipeIngredient(@Param("recipeID") Integer recipeID);

    
    boolean updateRecipeIngredient(@Param("recipeIngredient") RecipeIngredient recipeIngredient);

    
    List<RecipeIngredient> getRecipeIngredientsByRecipeId(@Param("recipeId") int recipeId);
}
