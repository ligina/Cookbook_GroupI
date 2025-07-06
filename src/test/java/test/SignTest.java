package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import javafx.application.Platform;
import model.Model;
import dao.mappers.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationExtension;
import dao.mappers.UserMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ExtendWith(ApplicationExtension.class)
public class SignTest {

    private UserMapper userMapper;
    private Model model;

    @BeforeEach
    public void setUp() throws Exception {
        // 创建模拟Mapper
        userMapper = mock(UserMapper.class);

        // 创建真实Model实例
        model = new Model();

        // 使用反射注入模拟UserMapper
        Field userMapperField = Model.class.getDeclaredField("userMapper");
        userMapperField.setAccessible(true);
        userMapperField.set(model, userMapper);

        // 设置数据库行为
        when(userMapper.getUserByName("qwe")).thenReturn(new User());
        when(userMapper.getUserByName("ezra123")).thenReturn(null);
    }

    // Test Case 1: 有效注册 (EC V1, V6, V11)
    @Test
    public void testSignUpValid() throws Exception {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() ->
                future.complete(model.sign("ezra123", "pass1234")));
        assertTrue(future.get());
    }

    // Test Case 3: 用户名已存在 (EC V3)
    @Test
    public void testSignUpUsernameExists() throws Exception {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Platform.runLater(() ->
                future.complete(model.sign("qwe", "pass1234")));
        assertFalse(future.get());
    }

}