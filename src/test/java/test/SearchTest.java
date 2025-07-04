package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import dao.mappers.RecipeMapper;
import model.Model;
import dao.mappers.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;

/**
 * Tests the recipe‐search functionality in Model using Equivalence Classes:
 * <ul>
 *   <li>V1: term ≤ 30 chars → valid</li>
 *   <li>V2: term > 30 chars → invalid</li>
 *   <li>V3: term is null → invalid</li>
 * </ul>
 */
class SearchTest {

    private Model model;
    private RecipeMapper recipeMapper;

    @BeforeEach
    void setUp() {
        recipeMapper = mock(RecipeMapper.class);
        model = new Model();
        model.setRecipeMapper(recipeMapper);
    }

    /**
     * EC V1: SearchTerm ≤ 30 chars.
     * Should return the mapped IDs→URLs and names lists as provided by the mapper.
     */
    @Test
    void testSearchValidTerm() {
        String term = "Chocolate Cake"; // 14 chars

        // prepare fake recipes
        Recipe r1 = new Recipe(); r1.setRecipeId(1); r1.setImageUrl("url1"); r1.setRecipeName("ChocoCake");
        Recipe r2 = new Recipe(); r2.setRecipeId(2); r2.setImageUrl("url2"); r2.setRecipeName("DarkChoco");

        List<Recipe> fakeList = new ArrayList<>();
        fakeList.add(r1);
        fakeList.add(r2);

        // when mapper is called with this term, return our fake list
        doReturn(fakeList).when(recipeMapper).getRecipeByName(term);

        // call updateImageUrls
        LinkedHashMap<Integer,String> urls = model.updateImageUrls(term);
        assertEquals(2, urls.size());
        assertEquals("url1", urls.get(1));
        assertEquals("url2", urls.get(2));

        // call updateImageNames
        List<String> names = model.updateImageNames(term);
        assertEquals(2, names.size());
        assertTrue(names.contains("ChocoCake"));
        assertTrue(names.contains("DarkChoco"));
    } // 测试通过

    /**
     * EC V2: SearchTerm length > 30.
     * Our Model does no length‐check, so the mapper will still be called;
     * here we simulate it returning an empty list.
     */
    @Test
    void testSearchTermTooLong() {
        String longTerm = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDE"; // 31 chars
        doReturn(new ArrayList<>()).when(recipeMapper).getRecipeByName(longTerm);

        // Expect empty results
        assertTrue(model.updateImageUrls(longTerm).isEmpty());
        assertTrue(model.updateImageNames(longTerm).isEmpty());
    } // TODO: 没有限制搜索长度

    /**
     * EC V3: SearchTerm is null.
     * Passing null typically triggers a NullPointerException in the mapper call.
     */
    @Test
    public void testSearchRecipeNullPointer() throws InterruptedException, ExecutionException {
        // Intentionally do not set RecipeMapper to trigger NullPointerException
        model.setRecipeMapper(null);
    } // TODO:没有限制不能填空
}
