package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import javafx.application.Platform;
import model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Tests for the {@link Model#validateRecipe(String, String, String, String)} method using equivalence class testing.
 * <p>
 * Equivalence Classes:
 * <ul>
 *   <li>EC V1: RecipeName is a string of no more than 50 characters</li>
 *   <li>EC V2: RecipeName length exceeds 50</li>
 *   <li>EC V3: RecipeName is null</li>
 *   <li>EC V4: PrepTime is a pure number, length ≤ 5</li>
 *   <li>EC V5: PrepTime contains non-numeric characters</li>
 *   <li>EC V6: PrepTime length exceeds 5</li>
 *   <li>EC V7: PrepTime is null</li>
 *   <li>EC V8: CookTime is a pure number, length ≤ 5</li>
 *   <li>EC V9: CookTime contains non-numeric characters</li>
 *   <li>EC V10: CookTime length exceeds 5</li>
 *   <li>EC V11: CookTime is null</li>
 *   <li>EC V12: ImageURL is a valid URL string (not validated by model)</li>
 *   <li>EC V13: ImageURL is not a URL (not validated by model)</li>
 *   <li>EC V14: ImageURL is null (not validated by model)</li>
 * </ul>
 */
@ExtendWith(ApplicationExtension.class)
public class CreateOrEditRecipeTest {

    private Model model;

    /**
     * Sets up the test environment by instantiating the Model.
     */
    @BeforeEach
    public void setUp() {
        model = spy(new Model());
    }

    /**
     * Tests EC V1, V4, V8, V12: All valid inputs, expect true.
     */
    @Test
    public void testValidRecipe() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.validateRecipe("ValidName", "123", "456", "http://image.com")));
        assertTrue(future.get());
    } // 测试通过

    /**
     * Tests EC V2: RecipeName length exceeds 50, expect false.
     */
    @Test
    public void testRecipeNameTooLong() throws InterruptedException, ExecutionException {
        String longName = "a".repeat(51);
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.validateRecipe(longName, "123", "456", "http://image.com")));
        assertFalse(future.get());
    } // 弹窗 recipe name is too long ，测试通过

    /**
     * Tests EC V3: RecipeName is null, expect false.
     */
    @Test
    public void testRecipeNameNull() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.validateRecipe(null, "123", "456", "http://image.com")));
        assertFalse(future.get());
    } // 报错：Cannot invoke "String.isEmpty()" because "recipeName" is null

    /**
     * Tests EC V5: PrepTime contains non-numeric characters, expect false.
     */
    @Test
    public void testPrepTimeNonNumeric() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.validateRecipe("Name", "12a3", "456", "http://image.com")));
        assertFalse(future.get());
    } // 弹窗 please input number ，测试通过

    /**
     * Tests EC V6: PrepTime length exceeds 5, expect false.
     */
    @Test
    public void testPrepTimeTooLong() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.validateRecipe("Name", "123456", "456", "http://image.com")));
        assertFalse(future.get());
    } // TODO：测试失败， 或许 prepTime 长度限制不对

    /**
     * Tests EC V7: PrepTime is null, expect false.
     */
    @Test
    public void testPrepTimeNull() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.validateRecipe("Name", "456", null, "http://image.com")));
        assertFalse(future.get());
    } // 报错：Cannot invoke "String.isEmpty()" because "preparationTime" is null

    /**
     * Tests EC V9: CookTime contains non-numeric characters, expect false.
     */
    @Test
    public void testCookTimeNonNumeric() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.validateRecipe("Name", "123", "45a6", "http://image.com")));
        assertFalse(future.get());
    } // 弹窗 please input number ，测试通过

    /**
     * Tests EC V10: CookTime length exceeds 5, expect false.
     */
    @Test
    public void testCookTimeTooLong() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.validateRecipe("Name", "123456", "123", "http://image.com")));
        assertFalse(future.get());
    } // TODO：测试失败， 或许 cookTime 长度限制不对

    /**
     * Tests EC V11: CookTime is null, expect false.
     */
    @Test
    public void testCookTimeNull() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.validateRecipe("Name", null, "123", "http://image.com")));
        assertFalse(future.get());
    } // 报错：Cannot invoke "String.isEmpty()" because "cookingTime" is null

    /**
     * Tests EC V13: ImageURL is not a URL (no validation in model, expect true).
     */
    @Test
    public void testImageUrlInvalidFormat() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.validateRecipe("Name", "123", "456", "not_a_url")));
        assertFalse(future.get());
    } // TODO: 测试失败，因为ImageURL还未校验

    /**
     * Tests EC V14: ImageURL is null (no validation in model, expect true).
     */
    @Test
    public void testImageUrlNull() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.validateRecipe("Name", "123", "456", null)));
        assertFalse(future.get());
    } // TODO: 测试失败，因为ImageURL还未校验
}
