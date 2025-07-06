package dao.mappers;

import org.apache.ibatis.annotations.Param;
import java.util.List;


public interface PreparationStepMapper {

    
    boolean addPreparationStep(@Param("preparationStep") PreparationStep preparationStep);

    
    boolean deletePreparationStep(@Param("recipeID") Integer recipeID);;

    
    boolean updatePreparationStep(@Param("preparationStep") PreparationStep preparationStep);

    
    List<PreparationStep> getPreparationStepsByRecipeId(@Param("recipeId") Integer recipeId);
}
