package model;

import dao.mappers.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Database connection and session management
 */
public class DatabaseManager {
    private UserMapper userMapper;
    private RecipeMapper recipeMapper;
    private RecipeIngredientMapper recipeIngredientMapper;
    private PreparationStepMapper preparationStepMapper;
    private SqlSession sqlSession;

    /**
     * Constructor that initializes MyBatis and connects to the database.
     */
    public DatabaseManager() {
        String resource = "mybatis-config.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            userMapper = sqlSession.getMapper(UserMapper.class);
            recipeMapper = sqlSession.getMapper(RecipeMapper.class);
            recipeIngredientMapper = sqlSession.getMapper(RecipeIngredientMapper.class);
            preparationStepMapper = sqlSession.getMapper(PreparationStepMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public UserMapper getUserMapper() { return userMapper; }
    public RecipeMapper getRecipeMapper() { return recipeMapper; }
    public RecipeIngredientMapper getRecipeIngredientMapper() { return recipeIngredientMapper; }
    public PreparationStepMapper getPreparationStepMapper() { return preparationStepMapper; }
    public SqlSession getSqlSession() { return sqlSession; }
}