package model;

import dao.mappers.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Database manager class responsible for managing MyBatis configuration and mapper instances.
 * This class initializes the database connection and provides access to all mapper interfaces
 * for database operations throughout the application.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class DatabaseManager {
    /** Mapper for user-related database operations */
    private UserMapper userMapper;
    
    /** Mapper for recipe-related database operations */
    private RecipeMapper recipeMapper;
    
    /** Mapper for recipe ingredient-related database operations */
    private RecipeIngredientMapper recipeIngredientMapper;
    
    /** Mapper for preparation step-related database operations */
    private PreparationStepMapper preparationStepMapper;
    
    /** MyBatis SQL session for database transactions */
    private SqlSession sqlSession;

    /**
     * Constructor that initializes the database connection and all mapper instances.
     * Loads the MyBatis configuration from mybatis-config.xml and creates mapper instances.
     */
    public DatabaseManager() {
        String resource = "mybatis-config.xml";
        try {
            // Load MyBatis configuration
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            
            // Initialize all mapper instances
            userMapper = sqlSession.getMapper(UserMapper.class);
            recipeMapper = sqlSession.getMapper(RecipeMapper.class);
            recipeIngredientMapper = sqlSession.getMapper(RecipeIngredientMapper.class);
            preparationStepMapper = sqlSession.getMapper(PreparationStepMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the UserMapper instance for user-related database operations.
     * 
     * @return the UserMapper instance
     */
    public UserMapper getUserMapper() { return userMapper; }
    
    /**
     * Gets the RecipeMapper instance for recipe-related database operations.
     * 
     * @return the RecipeMapper instance
     */
    public RecipeMapper getRecipeMapper() { return recipeMapper; }
    
    /**
     * Gets the RecipeIngredientMapper instance for ingredient-related database operations.
     * 
     * @return the RecipeIngredientMapper instance
     */
    public RecipeIngredientMapper getRecipeIngredientMapper() { return recipeIngredientMapper; }
    
    /**
     * Gets the PreparationStepMapper instance for preparation step-related database operations.
     * 
     * @return the PreparationStepMapper instance
     */
    public PreparationStepMapper getPreparationStepMapper() { return preparationStepMapper; }
    
    /**
     * Gets the SqlSession instance for manual transaction management.
     * 
     * @return the SqlSession instance
     */
    public SqlSession getSqlSession() { return sqlSession; }
}