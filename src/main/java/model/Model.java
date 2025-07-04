package model;
import config.SessionManager;
import dao.mappers.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * The Model Class is used to implement ModelMethod Interface
 *
 * @author
 */
public class Model implements ModelMethod{
    private UserMapper userMapper;
    private RecipeMapper recipeMapper;
    private RecipeIngredientMapper recipeIngredientMapper;
    private PreparationStepMapper preparationStepMapper;
    private SqlSession sqlSession;
    private static final float MAX_QUANTITY = 99999.0f;
    private static final int MAX_LENGTH = 50;
    /**
     * Constructor that initializes MyBatis and connects to the database.
     */
    public Model(){
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

    @Override
    public boolean sign(String name, String password) {
        // EC V3: Check if username already exists
        if (userMapper.getUserByName(name) != null) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username already exists!");
            return false;
        }
        
        try {
            User user = new User();
            user.setUser(name, password);
            userMapper.addUser(user);
            sqlSession.commit();
            return true;
        } catch (Exception e) {
            sqlSession.rollback();
            return false;
        }
    }

    @Override
    public boolean login(String name, String password) {
        try {
            User user = userMapper.getUserByName(name);
            // EC V2: Username does not exist in database
            if (user == null) {
                displayAlert(Alert.AlertType.ERROR, "Error", "Username does not exist!");
                return false;
            } 
            // EC V5: Password does not match username
            else if (!user.getPassword().equals(password)) {
                displayAlert(Alert.AlertType.ERROR, "Error", "Password is incorrect!");
                return false;
            } else {
                SessionManager.setCurrentUserName(name);
                return true;
            }
        } catch (Exception e) {
            sqlSession.rollback();
            return false;
        }
    }



    @Override
    public  LinkedHashMap< Integer,String>   updateImageUrls(String recipeName){
        ArrayList<Recipe> recipes = recipeMapper.getRecipeByName(recipeName);
        LinkedHashMap< Integer,String> imageHashMap = new LinkedHashMap<>();
        for(Recipe recipe : recipes){

            imageHashMap.put(recipe.getRecipeId(),recipe.getImageUrl());
        }

        return imageHashMap;
    }
    @Override
    public  ArrayList<String>  updateImageNames(String recipeName){
        ArrayList<Recipe> recipes = recipeMapper.getRecipeByName(recipeName);
        ArrayList<String> imageNames = new ArrayList<>();
        for(Recipe recipe : recipes){

            imageNames.add(recipe.getRecipeName());
        }

        return imageNames;
    }

    public static void displayAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();

    }

    @Override
    public Recipe getRecipeByID(Integer id){
        return recipeMapper.getRecipeById(id);
    }

    @Override
    public List<RecipeIngredient> getIngredientByID(Integer id){
        return recipeIngredientMapper.getRecipeIngredientsByRecipeId(id);
    }

    @Override
    public List<PreparationStep> getRecipePreparationSteps(Integer id){
        return preparationStepMapper.getPreparationStepsByRecipeId(id);
    }

    @Override
    public List<RecipeIngredient> updateIngredientByServeNumber(Integer id,String serveNumber){
        Float serveNumberInt = Float.parseFloat(serveNumber);
        List<RecipeIngredient> updatedIngredients = new ArrayList<>();
        List<RecipeIngredient> ingredients = recipeIngredientMapper.getRecipeIngredientsByRecipeId(id);
        for(RecipeIngredient ingredient : ingredients){
            updatedIngredients.add(new RecipeIngredient(ingredient));
        }
        for(RecipeIngredient ingredient : updatedIngredients){
            ingredient.setQuantity(serveNumberInt * ingredient.getQuantity());
        }
        return updatedIngredients;
    }


    @Override
    public void updateRecipe(Recipe recipe) {
        try {
            recipeMapper.updateRecipe(recipe);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        }
    }

    @Override
    public Integer addRecipe(Recipe recipe){

        try {
            recipeMapper.addRecipe(recipe);
            sqlSession.commit();
            Recipe newRecipe = recipeMapper.getNewRecipe();
            return newRecipe.getRecipeId();
        } catch (Exception e) {
            sqlSession.rollback();
            return 0;
        }

   }

    @Override
    public void addRecipeIngredient(RecipeIngredient recipeIngredient){
        try {
            recipeIngredientMapper.addRecipeIngredient(recipeIngredient);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        }

    }

    @Override
    public void updateRecipeIngredient(Integer recipeID,List<RecipeIngredient> recipeIngredients){
        try {
            recipeIngredientMapper.deleteRecipeIngredient(recipeID);
            for (RecipeIngredient recipeIngredient : recipeIngredients) {
                recipeIngredientMapper.addRecipeIngredient(recipeIngredient);
            }
            sqlSession.commit();
        } catch (Exception e) {
           sqlSession.rollback();
        }
    }

    @Override
    public List<Recipe> getAllRecipes(){
        return recipeMapper.getAllRecipes();
    }

    @Override
    public void addRecipePreparationStep(PreparationStep preparationStep) {
        try {
            preparationStepMapper.addPreparationStep(preparationStep);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        }
    }

    @Override
    public void updateRecipePreparationStep(Integer recipeID, List<PreparationStep> preparationSteps){
        try {
            preparationStepMapper.deletePreparationStep(recipeID);
            for (PreparationStep preparationStep : preparationSteps) {
                preparationStepMapper.addPreparationStep(preparationStep);
            }
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        }
    }

    private static String getFileExtension(String filename) {
        if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0) {
            return filename.substring(filename.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    @Override
    public Path duplicateImage(String imageUrl){
        String projectPath = System.getProperty("user.dir");
        String targetPath = projectPath + "/src/images/dishes";
        String fileExtension = getFileExtension(imageUrl);
        long timestamp = System.currentTimeMillis();
        String newFileName = timestamp + "." + fileExtension;
        Path fullPath = Paths.get(targetPath).resolve(newFileName);
        Path imagePath = Paths.get(imageUrl);
        try {
            Files.copy(imagePath,fullPath);
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
        return  fullPath;

    }

    @Override
    public void deleteRecipe(Integer recipeID){
        try {
            recipeMapper.deleteRecipe(recipeID);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        }   }

    @Override
    public boolean serveNumberIsInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            return Integer.parseInt(str)> 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates serving number according to requirements
     * @param serveNumber the serving number string to validate
     * @return true if valid, false otherwise
     */
    public boolean validateServingNumber(String serveNumber) {
        // Cannot be empty
        if (serveNumber == null || serveNumber.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Serving number cannot be empty!");
            return false;
        }
        
        // Must be pure numbers
        if (!serveNumber.matches("^\\d+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Serving number must contain only numbers!");
            return false;
        }
        
        try {
            int servings = Integer.parseInt(serveNumber);
            
            // Cannot be 0
            if (servings == 0) {
                Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Serving number cannot be 0!");
                return false;
            }
            
            // Cannot exceed 10
            if (servings > 10) {
                Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Serving number cannot exceed 10!");
                return false;
            }
            
            return true;
        } catch (NumberFormatException e) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Invalid serving number format!");
            return false;
        }
    }
    
    /**
     * Validates if a string is a valid URL format
     * @param url the URL string to validate
     * @return true if valid URL format, false otherwise
     */
    private boolean isValidURL(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        
        // Basic URL pattern check
        String urlPattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return url.matches(urlPattern) || url.startsWith("file:");
    }
    
    /**
     * Validates nutrition values (calories, protein, fat, carbs)
     * @param value the nutrition value to validate
     * @param fieldName the name of the field for error messages
     * @return true if valid, false otherwise
     */
    public boolean validateNutritionValue(String value, String fieldName) {
        // EC V16: Cannot be empty
        if (value == null || value.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", fieldName + " cannot be empty!");
            return false;
        }
        
        // EC V14: Must be pure numbers
        if (!value.matches("^\\d*\\.?\\d+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", fieldName + " must contain only numbers!");
            return false;
        }
        
        // EC V15: String length cannot exceed 10 digits
        if (value.length() > 10) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", fieldName + " cannot exceed 10 digits!");
            return false;
        }
        
        return true;
    }

    public static  TextFormatter<String> textFieldFormatter(int maxLength) {
        TextFormatter<String> temp;
        temp = new TextFormatter<>(change -> {
            if(change.isAdded()){
                if(change.getControlText().length() >= maxLength) {
                    change.setText("");
                }

            }
            return change;
        });
        return temp;
    }


    @Override
    public boolean validateRecipeIngredient(String recipeName, Float quantity, String unit){
        // EC V4: Ingredient name cannot be empty
        if(recipeName == null || recipeName.isEmpty()){
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Ingredient name cannot be empty!");
            return false;
        }
        
        // EC V2: Ingredient name length cannot exceed 30 characters
        if (recipeName.length() > 30) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Ingredient name length cannot exceed 30 characters!");
            return false;
        }
        
        // EC V8: Quantity cannot be empty
        if(quantity == null || quantity == 0.0){
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Ingredient quantity cannot be empty!");
            return false;
        }
        
        // EC V7: Quantity string length cannot exceed 10 digits
        if(String.valueOf(quantity).length() > 10){
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Quantity cannot exceed 10 digits!");
            return false;
        }

        // EC V12: Unit cannot be empty
        if(unit == null || unit.isEmpty()){
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Unit cannot be empty!");
            return false;
        }
        
        // EC V11: Unit length cannot exceed 10 characters
        if(unit.length() > 10){
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Unit length cannot exceed 10 characters!");
            return false;
        }
        
        // EC V10: Unit must contain only letters
        if (!unit.matches("^[a-zA-Z]+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Unit must contain only letters!");
            return false;
        }
        
        return true;
    }
    @Override
    public boolean validateRecipe(String recipeName, String cookingTime, String preparationTime, String recipeImage){
        // EC V3: Recipe name cannot be empty
        if (recipeName == null || recipeName.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Recipe name cannot be empty!");
            return false;
        }
        
        // EC V2: Recipe name length cannot exceed 70 characters
        if(recipeName.length() > 70){
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Recipe name length cannot exceed 70 characters!");
            return false;
        }
        
        // EC V7: Preparation time cannot be empty
        if (preparationTime == null || preparationTime.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Preparation time cannot be empty!");
            return false;
        }
        
        // EC V11: Cooking time cannot be empty
        if (cookingTime == null || cookingTime.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Cooking time cannot be empty!");
            return false;
        }
        
        // EC V5: Preparation time must be pure numbers
        if (!preparationTime.matches("^\\d+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Preparation time must contain only numbers!");
            return false;
        }
        
        // EC V9: Cooking time must be pure numbers
        if (!cookingTime.matches("^\\d+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Cooking time must contain only numbers!");
            return false;
        }
        
        // EC V6: Preparation time string length cannot exceed 5 digits
        if(preparationTime.length() > 5){
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Preparation time cannot exceed 5 digits!");
            return false;
        }
        
        // EC V10: Cooking time string length cannot exceed 5 digits
        if(cookingTime.length() > 5){
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Cooking time cannot exceed 5 digits!");
            return false;
        }
        
        // EC V14: Image URL cannot be empty
        if (recipeImage == null || recipeImage.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Recipe image cannot be empty!");
            return false;
        }
        
        // EC V13: Image URL must be valid URL format
        if (!isValidURL(recipeImage)) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Image URL is not in valid format!");
            return false;
        }

        return true;
    }

    // Additional methods for RecipeDisplayFXMLController
    private Recipe currentRecipe;
    private List<RecipeIngredient> currentIngredients;
    private String currentInstructions;

    public void getRecipes() {
        // This method is called to initialize recipe loading
        // Implementation can be empty if not needed
    }

    public void getRecipeBasicData(Integer recipeId) {
        this.currentRecipe = recipeMapper.getRecipeById(recipeId);
    }

    public String getRecipeName() {
        return currentRecipe != null ? currentRecipe.getRecipeName() : "";
    }

    public String getCookingTime() {
        return currentRecipe != null ? String.valueOf(currentRecipe.getCookingTime()) : "0";
    }

    public String getPreparationTime() {
        return currentRecipe != null ? String.valueOf(currentRecipe.getPreparationTime()) : "0";
    }

    public String getImagePath() {
        return currentRecipe != null ? currentRecipe.getImageUrl() : "";
    }

    public List<RecipeIngredient> getIngredients(Integer recipeId) {
        this.currentIngredients = recipeIngredientMapper.getRecipeIngredientsByRecipeId(recipeId);
        return currentIngredients;
    }

    public String getInstructions(Integer recipeId) {
        List<PreparationStep> steps = preparationStepMapper.getPreparationStepsByRecipeId(recipeId);
        StringBuilder instructions = new StringBuilder();
        for (PreparationStep step : steps) {
            instructions.append("Step ").append(step.getStep()).append(": ")
                       .append(step.getDescription()).append("\n\n");
        }
        this.currentInstructions = instructions.toString();
        return currentInstructions;
    }

}

