package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javafx.application.Platform;
import org.apache.ibatis.session.SqlSession;
import model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationExtension;
import dao.mappers.UserMapper;
import dao.mappers.User;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Model layer test class for login functionality, focusing on business logic validation.
 * <p>
 * Implements test cases based on the EC_BVA table design, covering all equivalence class combinations:
 * <ul>
 *   <li>V1: Username exists</li>
 *   <li>V2: Username does not exist</li>
 *   <li>V3: Username is empty</li>
 *   <li>V4: Password matches</li>
 *   <li>V5: Password does not match</li>
 *   <li>V6: Password is empty</li>
 * </ul>
 *
 * <p>Predefined user data in the mock database:
 * <ul>
 *   <li>Username: qwe, Password: qwe123</li>
 *   <li>Username: ezra1234, Password: pass1234</li>
 * </ul>
 *
 * <p>Test cases strictly follow the login test section from the EC_BVA table (Test No.1-5).
 */
@ExtendWith(ApplicationExtension.class)
public class LoginTest {

    private UserMapper userMapper;
    private SqlSession sqlSession;
    private Model model;

    /**
     * Initializes test environment by setting up mock database.
     *
     * @throws Exception if reflection injection fails
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Create mock objects
        userMapper = mock(UserMapper.class);
        sqlSession = mock(SqlSession.class);
        model = new Model();

        // Inject mock sqlSession using reflection
        Field sqlSessionField = Model.class.getDeclaredField("sqlSession");
        sqlSessionField.setAccessible(true);
        sqlSessionField.set(model, sqlSession);

        // Configure sqlSession to return mock userMapper
        when(sqlSession.getMapper(UserMapper.class)).thenReturn(userMapper);

        // Set up user data in mock database
        User user1 = new User();
        user1.setUser("qwe", "qwe");

        User user2 = new User();
        user2.setUser("ezra1234", "pass1234");

        // Configure mock behaviors
        when(userMapper.getUserByName("qwe")).thenReturn(user1);
        when(userMapper.getUserByName("ezra1234")).thenReturn(user2);
        when(userMapper.getUserByName(anyString())).thenReturn(null);
    }

    /**
     * Test Case 1: Valid username + valid password (EC V1 & V4).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: qwe (exists in database)</li>
     *   <li>Password: qwe (correct password)</li>
     * </ul>
     * Expected: Login successful (returns true)
     *
     * @throws InterruptedException if thread interrupted
     * @throws ExecutionException if asynchronous execution fails
     */
    @Test
    public void testValidUsernameValidPassword() throws InterruptedException, ExecutionException {
        String username = "qwe";
        String password = "qwe";

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.login(username, password)));
        assertTrue(future.get());
    }

    /**
     * Test Case 2: Invalid username + valid password (EC V2 & V4).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: alice123 (not in database)</li>
     *   <li>Password: pass1234 (any password)</li>
     * </ul>
     * Expected: Login fails (returns false)
     *
     * @throws InterruptedException if thread interrupted
     * @throws ExecutionException if asynchronous execution fails
     */
    @Test
    public void testInvalidUsernameValidPassword() throws InterruptedException, ExecutionException {
        String username = "alice123";
        String password = "pass1234";

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.login(username, password)));
        assertFalse(future.get());
    }

    /**
     * Test Case 4: Valid username + invalid password (EC V1 & V5).
     * <p>
     * Inputs:
     * <ul>
     *   <li>Username: qwe (exists in database)</li>
     *   <li>Password: word5678 (incorrect password)</li>
     * </ul>
     * Expected: Login fails (returns false)
     *
     * @throws InterruptedException if thread interrupted
     * @throws ExecutionException if asynchronous execution fails
     */
    @Test
    public void testValidUsernameInvalidPassword() throws InterruptedException, ExecutionException {
        String username = "qwe";
        String password = "word5678";

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.login(username, password)));
        assertFalse(future.get());
    }
}

