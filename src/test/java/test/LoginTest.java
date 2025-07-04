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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Tests the log-in functionality using Equivalence Class (EC) testing.
 * <p>
 * EC Definitions:
 * <ul>
 *   <li>V1: Username exists in database</li>
 *   <li>V2: Username does not exist in database</li>
 *   <li>V3: Username is null or empty</li>
 *   <li>V4: Password matches the username record in database</li>
 *   <li>V5: Password does not match the username</li>
 *   <li>V6: Password is null</li>
 * </ul>
 */
@ExtendWith(ApplicationExtension.class)
public class LoginTest {

    private UserMapper userMapper;
    private SqlSession sqlSession;
    private Model model;

    /**
     * Sets up mocks and model before each test.
     */
    @BeforeEach
    public void setUp() {
        userMapper = mock(UserMapper.class);
        sqlSession = mock(SqlSession.class);
        model = new Model();
        model.setUserMapper(userMapper);
    }

    /**
     * EC V1 & V4: Valid login with existing username and correct password. Expected to succeed.
     *
     * @throws InterruptedException  if thread is interrupted
     * @throws ExecutionException    if computation threw an exception
     */
    @Test
    public void testLoginSuccess() throws InterruptedException, ExecutionException {
        String username = "existingUser";
        String password = "correctPass123";
        User user = new User();
        user.setUser(username, password);
        doReturn(user).when(userMapper).getUserByName(username);

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.login(username, password)));
        assertTrue(future.get());
    } // 无弹窗，测试通过

    /**
     * EC V2: Username does not exist. Expected to fail.
     *
     * @throws InterruptedException  if thread is interrupted
     * @throws ExecutionException    if computation threw an exception
     */
    @Test
    public void testLoginUsernameNotExists() throws InterruptedException, ExecutionException {
        String username = "nonexistent";
        String password = "anyPass";
        doReturn(null).when(userMapper).getUserByName(username);

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.login(username, password)));
        assertFalse(future.get());
    } // 弹窗userName error，测试通过

    /**
     * EC V3: Username is null or empty. Expected to fail.
     *
     * @throws InterruptedException  if thread is interrupted
     * @throws ExecutionException    if computation threw an exception
     */
    @Test
    public void testLoginUsernameNullOrEmpty() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future1 = new CompletableFuture<>();
        Platform.runLater(() -> future1.complete(model.login(null, "password")));
        assertFalse(future1.get());

        CompletableFuture<Boolean> future2 = new CompletableFuture<>();
        Platform.runLater(() -> future2.complete(model.login("", "password")));
        assertFalse(future2.get());
    } // 弹窗please input userName，测试通过

    /**
     * EC V5: Password does not match the username. Expected to fail.
     *
     * @throws InterruptedException  if thread is interrupted
     * @throws ExecutionException    if computation threw an exception
     */
    @Test
    public void testLoginPasswordMismatch() throws InterruptedException, ExecutionException {
        String username = "existingUser";
        String actualPassword = "correctPass123";
        String wrongPassword = "wrongPass";
        User user = new User();
        user.setUser(username, actualPassword);
        doReturn(user).when(userMapper).getUserByName(username);

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.login(username, wrongPassword)));
        assertFalse(future.get());
    } // 弹窗 password error，测试通过

    /**
     * EC V6: Password is null. Expected to fail.
     *
     * @throws InterruptedException  if thread is interrupted
     * @throws ExecutionException    if computation threw an exception
     */
    @Test
    public void testLoginPasswordNull() throws InterruptedException, ExecutionException {
        String username = "existingUser";
        User user = new User();
        user.setUser(username, "correctPass123");
        doReturn(user).when(userMapper).getUserByName(username);

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.login(username, null)));
        assertFalse(future.get());
    } // 弹窗password error，测试通过
}

