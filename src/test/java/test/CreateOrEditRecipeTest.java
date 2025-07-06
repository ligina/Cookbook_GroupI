package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.Model;
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
 * 食谱创建/编辑功能的测试类，严格遵循EC_BVA表格中的测试用例设计。
 * <p>
 * 覆盖Create/Edit Recipe部分的所有13个测试场景（Test No.1-13），
 * 每个测试方法对应表格中的一个测试编号，并明确标注覆盖的等价类组合。
 *
 * <p>测试使用JavaFX的Platform.runLater()确保UI操作在正确的线程上执行，
 * 并使用CompletableFuture处理异步操作和结果验证。</p>
 */
@ExtendWith(ApplicationExtension.class)
public class CreateOrEditRecipeTest {

    private RecipeMapper recipeMapper;
    private SqlSession sqlSession;
    private Model model;

    /**
     * 初始化测试环境，设置模拟数据库和模型实例。
     * <p>
     * 在每个测试方法执行前：
     * <ol>
     *   <li>创建RecipeMapper和SqlSession的模拟对象</li>
     *   <li>创建Model实例</li>
     *   <li>使用反射将模拟的SqlSession注入Model</li>
     *   <li>配置SqlSession返回RecipeMapper模拟对象</li>
     *   <li>设置RecipeMapper的默认模拟行为</li>
     *   <li>重置警告记录</li>
     * </ol>
     *
     * @throws Exception 如果反射注入失败
     */
    @BeforeEach
    public void setUp() throws Exception {
        // 创建模拟对象
        recipeMapper = mock(RecipeMapper.class);
        sqlSession = mock(SqlSession.class);
        model = new Model();

        // 使用反射注入模拟的sqlSession
        injectMockSqlSession();

        // 配置sqlSession返回recipeMapper模拟对象
        when(sqlSession.getMapper(RecipeMapper.class)).thenReturn(recipeMapper);

        // 配置默认的模拟行为
        when(recipeMapper.addRecipe(any(Recipe.class))).thenReturn(true);
        when(recipeMapper.updateRecipe(any(Recipe.class))).thenReturn(true);

        // 重置警告记录
        Model.resetLastDisplayedAlert();
    }

    /**
     * 使用反射将模拟的SqlSession注入Model实例。
     * <p>
     * 通过反射访问Model的私有字段"sqlSession"并设置其值为模拟对象。
     *
     * @throws Exception 如果字段访问或设置失败
     */
    private void injectMockSqlSession() throws Exception {
        Field sqlSessionField = Model.class.getDeclaredField("sqlSession");
        sqlSessionField.setAccessible(true);
        sqlSessionField.set(model, sqlSession);
    }

    /**
     * 在JavaFX应用线程中执行食谱验证。
     * <p>
     * 此方法异步执行验证逻辑并返回CompletableFuture用于获取结果。
     * 在执行验证前会重置警告记录。
     *
     * @param recipeName 菜谱名称
     * @param prepTime 准备时间
     * @param cookTime 烹饪时间
     * @param imageURL 图片URL
     * @return CompletableFuture 包含验证结果的Future对象
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
     * 在JavaFX应用线程中执行份数验证。
     * <p>
     * 此方法异步执行份数验证逻辑并返回CompletableFuture用于获取结果。
     * 在执行验证前会重置警告记录。
     *
     * @param serveNumber 份数
     * @return CompletableFuture 包含验证结果的Future对象
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
     * 验证最后显示的警告内容是否符合预期。
     * <p>
     * 在JavaFX应用线程中获取最后显示的警告内容，并与预期消息进行比较。
     * 如果未显示警告或警告内容不匹配，则测试失败。
     *
     * @param expectedMessage 预期的警告内容
     * @throws Exception 如果操作超时或发生错误
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

    // ===================== 测试用例实现 =====================
    // 严格遵循EC_BVA表格中的13个测试场景

    /**
     * 测试编号：1
     * 覆盖等价类：V1, V4, V12, V16
     * 输入：所有字段有效
     * 预期：验证通过且无警告
     *
     * @throws Exception 如果测试执行失败
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
     * 测试编号：2
     * 覆盖等价类：V2, V4, V12, V16
     * 输入：菜谱名称超长（>70字符）
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
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
     * 测试编号：3
     * 覆盖等价类：V3, V4, V12, V16
     * 输入：菜谱名称为空
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
     */
    @Test
    public void testCreateOrEditRecipe_TC3() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                null, "30", "30", "src/images/dishes/scrambled_eggs_with_tomatoes.png");

        assertFalse(future.get());
        verifyAlertContent("Recipe name cannot be empty!");
    }

    /**
     * 测试编号：4
     * 覆盖等价类：V1, V5, V12, V16
     * 输入：准备时间和烹饪时间包含非数字字符
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
     */
    @Test
    public void testCreateOrEditRecipe_TC4() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                "Scrambled eggs with tomatoes", "30m", "30m", "src/images/dishes/scrambled_eggs_with_tomatoes.png");

        assertFalse(future.get());
        verifyAlertContent("Preparation time must contain only numbers and cannot be negative!");
    }

    /**
     * 测试编号：5
     * 覆盖等价类：V1, V6, V12, V16
     * 输入：准备时间和烹饪时间过长（>5位数字）
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
     */
    @Test
    public void testCreateOrEditRecipe_TC5() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                "Scrambled eggs with tomatoes", "123456", "123456", "src/images/dishes/scrambled_eggs_with_tomatoes.png");

        assertFalse(future.get());
        verifyAlertContent("Preparation time cannot exceed 5 digits!");
    }

    /**
     * 测试编号：6
     * 覆盖等价类：V1, V7, V12, V16
     * 输入：准备时间和烹饪时间为空
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
     */
    @Test
    public void testCreateOrEditRecipe_TC6() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                "Scrambled eggs with tomatoes", null, null, "src/images/dishes/scrambled_eggs_with_tomatoes.png");

        assertFalse(future.get());
        verifyAlertContent("Preparation time cannot be empty!");
    }

    /**
     * 测试编号：7
     * 覆盖等价类：V1, V4, V13, V16
     * 输入：图片URL格式无效
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
     */
    @Test
    public void testCreateOrEditRecipe_TC7() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                "Scrambled eggs with tomatoes", "30", "30", "not a URL");

        assertFalse(future.get());
        verifyAlertContent("Image URL is not in valid format!");
    }

    /**
     * 测试编号：8
     * 覆盖等价类：V1, V4, V14, V16
     * 输入：图片URL为空
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
     */
    @Test
    public void testCreateOrEditRecipe_TC8() throws Exception {
        CompletableFuture<Boolean> future = executeValidation(
                "Scrambled eggs with tomatoes", "30", "30", null);

        assertFalse(future.get());
        verifyAlertContent("Recipe image cannot be empty!");
    }

    /**
     * 测试编号：9
     * 覆盖等价类：V1, V4, V15, V16
     * 输入：份数包含非数字字符
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
     */
    @Test
    public void testCreateOrEditRecipe_TC9() throws Exception {
        CompletableFuture<Boolean> future = executeServingValidation("a");

        assertFalse(future.get());
        verifyAlertContent("Serving number must contain only numbers and cannot be negative!");
    }

    /**
     * 测试编号：10
     * 覆盖等价类：V1, V4, V12, V17
     * 输入：份数为0
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
     */
    @Test
    public void testCreateOrEditRecipe_TC10() throws Exception {
        CompletableFuture<Boolean> future = executeServingValidation("0");

        assertFalse(future.get());
        verifyAlertContent("Serving number must be a positive number!");
    }

    /**
     * 测试编号：11
     * 覆盖等价类：V1, V4, V12, V18
     * 输入：份数超限（>10）
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
     */
    @Test
    public void testCreateOrEditRecipe_TC11() throws Exception {
        CompletableFuture<Boolean> future = executeServingValidation("20");

        assertFalse(future.get());
        verifyAlertContent("Serving number cannot exceed 10!");
    }

    /**
     * 测试编号：12
     * 覆盖等价类：V1, V4, V12, V19
     * 输入：份数为空
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
     */
    @Test
    public void testCreateOrEditRecipe_TC12() throws Exception {
        CompletableFuture<Boolean> future = executeServingValidation(null);

        assertFalse(future.get());
        verifyAlertContent("Serving number cannot be empty!");
    }

    /**
     * 测试编号：13
     * 覆盖等价类：V1, V4, V20, V21, V22
     * 输入：准备时间、烹饪时间和份数为负数
     * 预期：验证失败并显示正确警告
     *
     * @throws Exception 如果测试执行失败
     */
    @Test
    public void testCreateOrEditRecipe_TC13() throws Exception {
        // 准备时间和烹饪时间为负数
        CompletableFuture<Boolean> recipeFuture = executeValidation(
                "Scrambled eggs with tomatoes", "-30", "-30", "https://example.com/images/scrambled_eggs.png");

        assertFalse(recipeFuture.get());
        verifyAlertContent("Preparation time must contain only numbers and cannot be negative!");

        // 重置警告记录
        Model.resetLastDisplayedAlert();

        // 份数为负数
        CompletableFuture<Boolean> servingFuture = executeServingValidation("-3");

        assertFalse(servingFuture.get());
        verifyAlertContent("Serving number must contain only numbers and cannot be negative!");
    }
}


