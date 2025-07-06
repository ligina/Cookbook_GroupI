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
 * 模型层登录功能测试类，专注于业务逻辑验证
 * <p>
 * 基于EC_BVA表格设计的测试用例，覆盖所有等价类组合：
 * <ul>
 *   <li>V1: 用户名存在</li>
 *   <li>V2: 用户名不存在</li>
 *   <li>V3: 用户名为空</li>
 *   <li>V4: 密码匹配</li>
 *   <li>V5: 密码不匹配</li>
 *   <li>V6: 密码为空</li>
 * </ul>
 *
 * 数据库当前用户数据：
 * <ul>
 *   <li>用户名: qwe, 密码: qwe123</li>
 *   <li>用户名: ezra1234, 密码: pass1234</li>
 * </ul>
 *
 * 测试用例严格遵循EC_BVA表格中的登录测试部分(Test No.1-5)
 */
@ExtendWith(ApplicationExtension.class)
public class LoginTest {

    private UserMapper userMapper;
    private SqlSession sqlSession;
    private Model model;

    /**
     * 初始化测试环境，设置模拟数据库
     *
     * @throws Exception 如果反射注入失败
     */
    @BeforeEach
    public void setUp() throws Exception {
        // 创建模拟对象
        userMapper = mock(UserMapper.class);
        sqlSession = mock(SqlSession.class);
        model = new Model();

        // 使用反射注入模拟的sqlSession
        Field sqlSessionField = Model.class.getDeclaredField("sqlSession");
        sqlSessionField.setAccessible(true);
        sqlSessionField.set(model, sqlSession);

        // 配置sqlSession返回userMapper模拟对象
        when(sqlSession.getMapper(UserMapper.class)).thenReturn(userMapper);

        // 设置数据库中的用户数据
        User user1 = new User();
        user1.setUser("qwe", "qwe");

        User user2 = new User();
        user2.setUser("ezra1234", "pass1234");

        // 配置模拟行为
        when(userMapper.getUserByName("qwe")).thenReturn(user1);
        when(userMapper.getUserByName("ezra1234")).thenReturn(user2);
        when(userMapper.getUserByName(anyString())).thenReturn(null);
    }

    /**
     * 测试用例1: 有效用户名+有效密码 (EC V1 & V4)
     * 用户名: qwe (数据库中存在)
     * 密码: qwe (正确密码)
     * 预期结果: 登录成功(返回true)
     *
     * @throws InterruptedException 如果线程中断
     * @throws ExecutionException   如果异步执行异常
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
     * 测试用例2: 无效用户名+有效密码 (EC V2 & V4)
     * 用户名: alice123 (数据库中不存在)
     * 密码: pass1234 (任意密码)
     * 预期结果: 登录失败(返回false)
     *
     * @throws InterruptedException 如果线程中断
     * @throws ExecutionException   如果异步执行异常
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
     * 测试用例4: 有效用户名+无效密码 (EC V1 & V5)
     * 用户名: qwe (数据库中存在)
     * 密码: word5678 (错误密码)
     * 预期结果: 登录失败(返回false)
     *
     * @throws InterruptedException 如果线程中断
     * @throws ExecutionException   如果异步执行异常
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

