package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.Model;
import model.DatabaseManager;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import dao.mappers.Recipe;
import dao.mappers.RecipeMapper;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * Test class for recipe creation/editing functionality, strictly following the test case design from the EC_BVA table.
 * <p>
 * Covers all 13 test scenarios (Test No.1-13) for the Create/Edit Recipe section.
 * Each test method corresponds to a test number in the table and clearly indicates the covered equivalence class combination.
 *
 * <p>Tests use JavaFX's Platform.runLater() to ensure UI operations execute on the correct thread,
 * and use CompletableFuture for handling asynchronous operations and result verification.</p>
 */
@ExtendWith(ApplicationExtension.class)
public class CreateOrEditRecipeTest {

    private RecipeMapper recipeMapper;
    private SqlSession sqlSession;
    private Model model;

    /**
     * Initializes the test environment and sets up mock database and model instance.
     * <p>
     * Before each test method:
     * <ol>
     *   <li>Creates mock objects for RecipeMapper and SqlSession</li>
     *   <li>Instantiates the Model</li>
     *   <li>Injects mock SqlSession into Model using reflection</li>
     *   <li>Configures SqlSession to return mock RecipeMapper</li>
     *   <li>Sets up default mock behaviors for RecipeMapper</li>
     *   <li>Resets warning tracking</li>
     * </ol>
     *
     * @throws Exception if reflection injection fails
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Create mock objects
        recipeMapper = mock(RecipeMapper.class);
        sqlSession = mock(SqlSession.class);
        model = new Model();

        // Inject mock sqlSession using reflection
        injectMockSqlSession();

        // Configure sqlSession to return mock recipeMapper
        when(sqlSession.getMapper(RecipeMapper.class)).thenReturn(recipeMapper);

        // Set up default mock behaviors
        when(recipeMapper.addRecipe(any(Recipe.class))).thenReturn(true);
        when(recipeMapper.updateRecipe(any(Recipe.class))).thenReturn(true);

        // Reset warning tracking
        Model.resetLastDisplayedAlert();
    }

    /**
     * Injects mock SqlSession into the Model instance using reflection.
     * <p>
     * Accesses the private field "sqlSession" in Model class and sets its value to the mock object.
     *
     * @throws Exception if field access or setting fails
     */
    private void injectMockSqlSession() throws Exception {
        Field sqlSessionField = Model.class.getDeclaredField("sqlSession");
        sqlSessionField.setAccessible(true);
        sqlSessionField.set(model, sqlSession);
    }

    /**
     * Executes recipe validation on the JavaFX application thread.
     * <p>
     * Performs validation logic asynchronously and returns CompletableFuture for result retrieval.
     * Resets warning tracking before executing validation.
     *
     * @param recipeName recipe name
     * @param prepTime preparation time
     * @param cookTime cooking time
     * @param imageURL image URL
     * @return CompletableFuture containing validation result
     */
    private CompletableFuture<Boolean> executeValidation(
            String recipeName, String prepTime, String cookTime, String imageURL) {

        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Platform.runLater(() -> {
            try {
                Model.resetLastDisplayedAlert();
                boolean result = model.validateRecipe(recipeName, cookTime, prepTime, imageURL);
                future.complete(result);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    /**
     * Executes serving number validation on the JavaFX application thread.
     * <p>
     * Performs serving validation asynchronously and returns CompletableFuture for result retrieval.
     * Resets warning tracking before executing validation.
     *
     * @param serveNumber serving number
     * @return CompletableFuture containing validation result
     */
    private CompletableFuture<Boolean> executeServingValidation(String serveNumber) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        Platform.runLater(() -> {
            try {
                Model.resetLastDisplayedAlert();
                boolean result = model.validateServingNumber(serveNumber);
                future.complete(result);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    /**
     * Verifies the content of the last displayed alert matches the expected message.
     * <p>
     * Retrieves the last displayed alert content on JavaFX thread and compares with expected message.
     * Fails the test if no alert was shown or content doesn't match.
     *
     * @param expectedMessage expected alert content
     * @throws Exception if operation times out or error occurs
     */
    private void verifyAlertContent(String expectedMessage) throws Exception {
        CompletableFuture<String> future = new CompletableFuture<>();

        Platform.runLater(() -> {
            Alert alert = Model.getLastDisplayedAlert();
            future.complete(alert != null ? alert.getContentText() : null);
        });

        String actualMessage = future.get();
        assertNotNull(actualMessage, "Alert was not displayed");
        assertEquals(expectedMessage, actualMessage);
    }

    // ===================== Test Case Implementations =====================
    // Strictly follows 13 test scenarios from EC_BVA table

    /**
     * Test Number: 1
     * Covered Equivalence Classes: V1, V4, V12, V16
     * Input: All fields valid
     * Expected: Validation passes with no warnings
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC1() throws Exception {
        CompletableFuture<Boolean> recipeFuture = executeValidation(
                "Scrambled eggs with tomatoes", "30", "30", "https://example.com/images/scrambled_eggs.png");
        CompletableFuture<Boolean> servingFuture = executeServingValidation("3");

        assertTrue(recipeFuture.get());
        assertTrue(servingFuture.get());
    }

    /**
     * Test Number: 2
     * Covered Equivalence Classes: V2, V4, V12, V16
     * Input: Recipe name exceeds length limit (>70 characters)
     * Expected: Validation fails with correct warning
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC2() throws Exception {
        String longName = "averylongRecipeNamewhichexceeds70charactersIsNotAllowedBecauseOfTheLengthIsTooLongToShow";
        CompletableFuture<Boolean> future = executeValidation(
                longName, "30", "30", "src/images/dishes/scrambled_eggs_with_tomatoes.png");

        assertFalse(future.get());
        verifyAlertContent("Recipe name length cannot exceed 70 characters!");
    }

    /**
     * Test Number: 3
     * Covered Equivalence Classes: V3, V4, V12, V16
     * Input: Recipe name is empty
     * Expected: Validation fails with correct warning
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC3() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                null, "30", "30", "src/images/dishes/scrambled_eggs_with_tomatoes.png");

        assertFalse(future.get());
        verifyAlertContent("Recipe name cannot be empty!");
    }

    /**
     * Test Number: 4
     * Covered Equivalence Classes: V1, V5, V12, V16
     * Input: Preparation/cooking time contains non-numeric characters
     * Expected: Validation fails with correct warning
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC4() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                "Scrambled eggs with tomatoes", "30m", "30m", "src/images/dishes/scrambled_eggs_with_tomatoes.png");

        assertFalse(future.get());
        verifyAlertContent("Preparation time must contain only numbers and cannot be negative!");
    }

    /**
     * Test Number: 5
     * Covered Equivalence Classes: V1, V6, V12, V16
     * Input: Preparation/cooking time exceeds digit limit (>5 digits)
     * Expected: Validation fails with correct warning
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC5() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                "Scrambled eggs with tomatoes", "123456", "123456", "src/images/dishes/scrambled_eggs_with_tomatoes.png");

        assertFalse(future.get());
        verifyAlertContent("Preparation time cannot exceed 5 digits!");
    }

    /**
     * Test Number: 6
     * Covered Equivalence Classes: V1, V7, V12, V16
     * Input: Preparation/cooking time is empty
     * Expected: Validation fails with correct warning
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC6() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                "Scrambled eggs with tomatoes", null, null, "src/images/dishes/scrambled_eggs_with_tomatoes.png");

        assertFalse(future.get());
        verifyAlertContent("Preparation time cannot be empty!");
    }

    /**
     * Test Number: 7
     * Covered Equivalence Classes: V1, V4, V13, V16
     * Input: Image URL format is invalid
     * Expected: Validation fails with correct warning
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC7() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                "Scrambled eggs with tomatoes", "30", "30", "not a URL");

        assertFalse(future.get());
        verifyAlertContent("Image URL is not in valid format!");
    }

    /**
     * Test Number: 8
     * Covered Equivalence Classes: V1, V4, V14, V16
     * Input: Image URL is empty
     * Expected: Validation fails with correct warning
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC8() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                "Scrambled eggs with tomatoes", "30", "30", null);

        assertFalse(future.get());
        verifyAlertContent("Recipe image cannot be empty!");
    }

    /**
     * Test Number: 9
     * Covered Equivalence Classes: V1, V4, V15, V16
     * Input: Serving number contains non-numeric characters
     * Expected: Validation fails with correct warning
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC9() throws Exception {
        CompletableFuture<Boolean> future = executeServingValidation("a");

        assertFalse(future.get());
        verifyAlertContent("Serving number must contain only numbers and cannot be negative!");
    }

    /**
     * Test Number: 10
     * Covered Equivalence Classes: V1, V4, V12, V17
     * Input: Serving number is 0
     * Expected: Validation fails with correct warning
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC10() throws Exception {
        CompletableFuture<Boolean> future = executeServingValidation("0");

        assertFalse(future.get());
        verifyAlertContent("Serving number must be a positive number!");
    }

    /**
     * Test Number: 11
     * Covered Equivalence Classes: V1, V4, V12, V18
     * Input: Serving number exceeds upper limit (>10)
     * Expected: Validation fails with correct warning
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC11() throws Exception {
        CompletableFuture<Boolean> future = executeServingValidation("20");

        assertFalse(future.get());
        verifyAlertContent("Serving number cannot exceed 10!");
    }

    /**
     * Test Number: 12
     * Covered Equivalence Classes: V1, V4, V12, V19
     * Input: Serving number is empty
     * Expected: Validation fails with correct warning
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC12() throws Exception {
        CompletableFuture<Boolean> future = executeServingValidation(null);

        assertFalse(future.get());
        verifyAlertContent("Serving number cannot be empty!");
    }

    /**
     * Test Number: 13
     * Covered Equivalence Classes: V1, V4, V20, V21, V22
     * Input: Negative values for preparation time, cooking time, and serving number
     * Expected: Validation fails with correct warnings
     *
     * @throws Exception if test execution fails
     */
    @Test
    public void testCreateOrEditRecipe_TC13() throws Exception {
        // Negative preparation and cooking times
        CompletableFuture<Boolean> recipeFuture = executeValidation(
                "Scrambled eggs with tomatoes", "-30", "-30", "https://example.com/images/scrambled_eggs.png");

        assertFalse(recipeFuture.get());
        verifyAlertContent("Preparation time must contain only numbers and cannot be negative!");

        // Reset warning tracking
        Model.resetLastDisplayedAlert();

        // Negative serving number
        CompletableFuture<Boolean> servingFuture = executeServingValidation("-3");

        assertFalse(servingFuture.get());
        verifyAlertContent("Serving number must contain only numbers and cannot be negative!");
    }
}


