package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Model;
import static org.junit.jupiter.api.Assertions.*;
import javafx.application.Platform;


// TODO:没找到 model中对Calories/Protein/Fat/Carbs的限制要求，所以V13-16没有写
/**
 * Tests for create/edit ingredient input validation, covering Equivalence Classes V1–V12.
 */
class CreateOrEditIngredientTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model();
        // 如果 validateRecipeIngredient 需要依赖 Mapper，也可以在这里注入 mock：
        // model.setRecipeIngredientMapper(mock(RecipeIngredientMapper.class));
    }

    /**
     * EC V1: IngredientName ≤ 30 characters, Quantity valid, Unit valid → should pass.
     */
    @Test
    void testValidAllFields() {
        boolean result = model.validateRecipeIngredient(
                "Tomato",    // 6 chars ≤ 30
                1234.5f,     // 纯数字且长度合理
                "grams"      // 包含字母且长度 ≤ 10
        );
        assertTrue(result);
    } // 测试通过

    /**
     * EC V2: IngredientName length > 30 → should fail.
     */
    @Test
    void testNameTooLong() {
        String longName = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDE"; // 31 chars
        boolean result = model.validateRecipeIngredient(
                longName,
                10f,
                "grams"
        );
        assertFalse(result);
    } // 测试失败，也许model中没有对ingredientName的字数限制

    /**
     * EC V3: IngredientName duplicates an existing one → 应在数据库层检测，此处先占位。
     * <p>
     * （如果日后在 Model 中加入对重复名称的校验，可在这里用 mock 模拟 mapper 返回已有同名配料）
     */
    @Test
    void testNameDuplicate() {
        // TODO: 在 Model 增加对重复名称的检查后再补充此测试
    }

    /**
     * EC V4: IngredientName is null → should fail.
     */
    @Test
    void testNameNull() {
        boolean result = model.validateRecipeIngredient(
                null,
                10f,
                "grams"
        );
        assertFalse(result);
    } // 报错：Cannot invoke "String.isEmpty()" because "recipeName" is null

    /**
     * EC V5: Quantity is valid pure number, length ≤ 10 → should pass.
     */
    @Test
    void testQuantityValid() {
        boolean result = model.validateRecipeIngredient(
                "Sugar",
                999999999f,  // 9 digits ≤ 10
                "grams"
        );
        assertTrue(result);
    } // 报错：This operation is permitted on the event thread only; currentThread = main

    /**
     * EC V6: Quantity contains non-digit characters → should fail.
     */
    @Test
    void testQuantityNonNumeric() {
        // parse 时会抛出 NumberFormatException，或者在前端层被拦截，此处直接模拟为失败
        boolean result = model.validateRecipeIngredient(
                "Sugar",
                Float.NaN,   // NaN 用作此处替代非数字情形
                "grams"
        );
        assertFalse(result);
    }

    /**
     * EC V7: Quantity length > 10 digits → should fail.
     */
    @Test
    void testQuantityTooLong() {
        // 11 位数字：10000000000f
        boolean result = model.validateRecipeIngredient(
                "Flour",
                10000000000f,
                "grams"
        );
        assertFalse(result);
    } // 报错：This operation is permitted on the event thread only; currentThread = main

    /**
     * EC V8: Quantity is null → should fail.
     */
    @Test
    void testQuantityNull() {
        boolean result = model.validateRecipeIngredient(
                "Flour",
                null,
                "grams"
        );
        assertFalse(result);
    } //报错：This operation is permitted on the event thread only; currentThread = main

    /**
     * EC V9: Unit contains letters, length ≤ 10 → should pass.
     */
    @Test
    void testUnitValid() {
        boolean result = model.validateRecipeIngredient(
                "Salt",
                5f,
                "teaspoon"
        );
        assertTrue(result);
    } // 测试通过

    /**
     * EC V10: Unit has no letters (pure numbers) → should fail.
     */
    @Test
    void testUnitNoLetters() {
        boolean result = model.validateRecipeIngredient(
                "Salt",
                5f,
                "12345"
        );
        assertFalse(result);
    } // 报错：This operation is permitted on the event thread only; currentThread = main

    /**
     * EC V11: Unit length > 10 → should fail.
     */
    @Test
    void testUnitTooLong() {
        boolean result = model.validateRecipeIngredient(
                "Pepper",
                2f,
                "verylongunit"  // 12 chars
        );
        assertFalse(result);
    } // 测试失败，或许没有对unit的长度进行限制

    /**
     * EC V12: Unit is null → should fail.
     */
    @Test
    void testUnitNull() {
        boolean result = model.validateRecipeIngredient(
                "Pepper",
                2f,
                null
        );
        assertFalse(result);
    } // 报错：Cannot invoke "String.isEmpty()" because "unit" is null
}
