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
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * The class is used to test the sign-up function
 *
 * @author
 */
// use ApplicationExtension to manage JavaFX environment
@ExtendWith(ApplicationExtension.class)
public class SignTest {

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
    }

    /**
     * EC V2: Username is pure numbers. Expected to fail.
     *
     * @throws InterruptedException if thread is interrupted
     * @throws ExecutionException   if computation threw an exception
     */
    @Test
    public void testSignUserNameIsPureInteger() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.sign("123456", "password123")));
        assertFalse(future.get());
    } // 弹窗正常，测试通过

    /**
     * EC V3: Attempt to register a username that already exists.
     *
     * @throws InterruptedException if thread is interrupted
     * @throws ExecutionException   if computation threw an exception
     */
    @Test
    public void testSignUserAlreadyExists() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> {
            model.sign("existingUser", "password123");
            boolean result = model.sign("existingUser", "password123");
            future.complete(result);
        });
        assertFalse(future.get());
    } // 弹窗正常，测试通过

    /**
     * EC V4: Username length exceeds 15 characters. Expected to fail.
     *
     * @throws InterruptedException if thread is interrupted
     * @throws ExecutionException   if computation threw an exception
     */
    @Test
    public void testSignUserNameTooLong() throws InterruptedException, ExecutionException {
        String longUsername = "abcdefghijklmnop"; // 16 chars
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.sign(longUsername, "password123")));
        assertFalse(future.get());
    } // 弹窗为user already exists 不符合要求

    /**
     * EC V5: Username is null. Expected to fail.
     *
     * @throws InterruptedException if thread is interrupted
     * @throws ExecutionException   if computation threw an exception
     */
    @Test
    public void testSignUserNameNull() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.sign(null, "password123")));
        assertFalse(future.get());
    } // 报错：Cannot invoke "String.matches(String)" because "name" is null

    /**
     * EC V7: Password is pure numbers. Expected to fail.
     *
     * @throws InterruptedException if thread is interrupted
     * @throws ExecutionException   if computation threw an exception
     */
    @Test
    public void testSignPasswordIsPureInteger() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.sign("validUser01", "123456")));
        assertFalse(future.get());
    } // 弹窗正常 测试通过

    /**
     * EC V8: Password is pure letters. Expected to fail.
     *
     * @throws InterruptedException if thread is interrupted
     * @throws ExecutionException   if computation threw an exception
     */
    @Test
    public void testSignPasswordIsPureLetters() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.sign("validUser02", "abcdef")));
        assertFalse(future.get());
    } // 似乎没有限制密码不能为纯字母，测试不过

    /**
     * EC V9: Password length exceeds 15 characters. Expected to fail.
     *
     * @throws InterruptedException if thread is interrupted
     * @throws ExecutionException   if computation threw an exception
     */
    @Test
    public void testSignPasswordTooLong() throws InterruptedException, ExecutionException {
        String longPassword = "pass123pass12345"; // 16 chars
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.sign("validUser03", longPassword)));
        assertFalse(future.get());
    } // 似乎没有限制密码不能过长，测试不过

    /**
     * EC V10: Password is null. Expected to fail.
     *
     * @throws InterruptedException if thread is interrupted
     * @throws ExecutionException   if computation threw an exception
     */
    @Test
    public void testSignPasswordNull() throws InterruptedException, ExecutionException {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() -> future.complete(model.sign("validUser04", null)));
        assertFalse(future.get());
    } // 报错：Cannot invoke "String.matches(String)" because "password" is null

    //以下先进行临时注释以免影响运行（以下测试的问题：model.sign中没有定义确认密码？）
    /**
     * EC V12: ConfirmPassword does not match Password. Expected to fail.
     */

    //@Test
    //public void testSignConfirmPasswordMismatch() throws InterruptedException, ExecutionException {
        //CompletableFuture<Boolean> future = new CompletableFuture<>();
        //Platform.runLater(() -> future.complete(model.sign("validUser", "password123", "password124")));
        //assertFalse(future.get());
    //}

    /**
     * EC V13: ConfirmPassword is null. Expected to fail.
     */
    //@Test
    //public void testSignConfirmPasswordNull() throws InterruptedException, ExecutionException {
        //CompletableFuture<Boolean> future = new CompletableFuture<>();
        //Platform.runLater(() -> future.complete(model.sign("validUser", "password123", null)));
        //assertFalse(future.get());
    //}

    /**
     * EC V6 & V11: Valid sign-up with matching ConfirmPassword. Expected to succeed.
     */
    //@Test
    //public void testSignSuccess() throws InterruptedException, ExecutionException {
        //CompletableFuture<Boolean> future = new CompletableFuture<>();
        //Platform.runLater(() -> future.complete(model.sign("validUser", "pass123word", "pass123word")));
        //assertTrue(future.get());
    //}

}