package test;

import control.LoginFXMLController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * 控制层登录页面测试类，专注于Log In表格中指定的两种场景
 * <p>
 * 严格遵循EC_BVA表格中的登录测试部分：
 * <ul>
 *   <li>Test No.3: 用户名为空 (EC V3)</li>
 *   <li>Test No.5: 密码为空 (EC V6)</li>
 * </ul>
 *
 * 测试验证：
 * <ul>
 *   <li>当用户名为空时，显示正确的警告信息且不调用模型层</li>
 *   <li>当密码为空时，显示正确的警告信息且不调用模型层</li>
 * </ul>
 */
@ExtendWith(ApplicationExtension.class)
public class LoginFXMLControllerTest {

    private LoginFXMLController controller;
    private Model mockModel;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;

    /**
     * 初始化测试环境，创建UI组件和模拟对象
     *
     * @param stage 测试舞台
     */
    @Start
    public void start(Stage stage) {
        // 创建模拟Model
        mockModel = mock(Model.class);

        // 创建控制器
        controller = new LoginFXMLController();

        // 创建UI组件
        usernameField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("Login");

        // 使用反射注入组件
        setField(controller, "usernameField", usernameField);
        setField(controller, "passwordField", passwordField);
        setField(controller, "loginButton", loginButton);
        setField(controller, "model", mockModel);

        // 初始化控制器
        controller.initialize();

        // 重置警告记录
        Model.resetLastDisplayedAlert();
    }

    @BeforeEach
    public void setUp() {
        // 重置模拟对象
        reset(mockModel);
        // 清除输入字段
        usernameField.clear();
        passwordField.clear();
        // 重置警告记录
        Model.resetLastDisplayedAlert();
    }

    @AfterEach
    public void tearDown() {
        // 重置警告记录
        Model.resetLastDisplayedAlert();
    }

    /**
     * 模拟登录操作并返回警告消息
     *
     * @param username 用户名
     * @param password 密码
     * @return 显示的警告消息内容，如果没有警告则为null
     */
    private String simulateLogin(String username, String password) {
        CompletableFuture<String> futureAlertMessage = new CompletableFuture<>();

        Platform.runLater(() -> {
            try {
                // 重置警告记录
                Model.resetLastDisplayedAlert();

                // 设置输入字段值
                usernameField.setText(username);
                passwordField.setText(password);

                // 使用反射调用私有方法
                Method handleMethod = LoginFXMLController.class.getDeclaredMethod("handleLoginButton", ActionEvent.class);
                handleMethod.setAccessible(true);
                handleMethod.invoke(controller, (ActionEvent) null);

                // 获取警告消息
                Alert lastAlert = Model.getLastDisplayedAlert();
                if (lastAlert != null) {
                    futureAlertMessage.complete(lastAlert.getContentText());
                } else {
                    futureAlertMessage.complete(null);
                }
            } catch (Exception e) {
                futureAlertMessage.completeExceptionally(e);
            }
        });

        try {
            return futureAlertMessage.get();
        } catch (InterruptedException | ExecutionException e) {
            fail("Test interrupted: " + e.getMessage());
            return null;
        }
    }

    /**
     * 测试Log In表格中的Test No.3: 用户名为空 (EC V3)
     * 用户名: null
     * 密码: pass1234
     * 预期警告: "Username cannot be empty!"
     */
    @Test
    public void testLoginWithEmptyUsername() {
        String alertMessage = simulateLogin(null, "pass1234");
        assertEquals("Username cannot be empty!", alertMessage);
        verify(mockModel, never()).login(anyString(), anyString());
    }

    /**
     * 测试Log In表格中的Test No.5: 密码为空 (EC V6)
     * 用户名: ezra1234
     * 密码: null
     * 预期警告: "Password cannot be empty!"
     */
    @Test
    public void testLoginWithEmptyPassword() {
        String alertMessage = simulateLogin("ezra1234", null);
        assertEquals("Password cannot be empty!", alertMessage);
        verify(mockModel, never()).login(anyString(), anyString());
    }

    /**
     * 使用反射设置字段值
     *
     * @param target 目标对象
     * @param fieldName 字段名称
     * @param value 要设置的值
     */
    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}