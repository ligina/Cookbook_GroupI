package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import control.SignupFXMLController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ExtendWith(ApplicationExtension.class)
public class SignupFXMLControllerTest {

    private SignupFXMLController controller;
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField;
    private Model mockModel;

    @Start
    public void start(Stage stage) throws Exception {
        // 创建控制器
        controller = new SignupFXMLController();

        // 创建模拟Model
        mockModel = mock(Model.class);

        // 使用反射替换控制器中的Model实例
        Field modelField = SignupFXMLController.class.getDeclaredField("model");
        modelField.setAccessible(true);
        modelField.set(controller, mockModel);

        // 创建UI组件
        usernameField = new TextField();
        passwordField = new PasswordField();
        repeatPasswordField = new PasswordField();

        // 使用反射将UI组件注入控制器
        Field usernameFieldField = SignupFXMLController.class.getDeclaredField("usernameField");
        usernameFieldField.setAccessible(true);
        usernameFieldField.set(controller, this.usernameField);

        Field passwordFieldField = SignupFXMLController.class.getDeclaredField("passwordField");
        passwordFieldField.setAccessible(true);
        passwordFieldField.set(controller, this.passwordField);

        Field repeatPasswordFieldField = SignupFXMLController.class.getDeclaredField("repeatPasswordField");
        repeatPasswordFieldField.setAccessible(true);
        repeatPasswordFieldField.set(controller, this.repeatPasswordField);

        // 重置警告记录
        Model.resetLastDisplayedAlert();
    }

    @AfterEach
    public void tearDown() {
        // 每个测试后重置警告记录
        Model.resetLastDisplayedAlert();
    }

    // 辅助方法：模拟注册操作并返回警告消息
    private String simulateSignup(String username, String password, String repeatPassword) {
        CompletableFuture<String> futureAlertMessage = new CompletableFuture<>();

        Platform.runLater(() -> {
            try {
                // 重置警告记录
                Model.resetLastDisplayedAlert();

                // 设置输入字段值
                usernameField.setText(username);
                passwordField.setText(password);
                repeatPasswordField.setText(repeatPassword);

                // 使用反射调用私有方法
                Method handleMethod = SignupFXMLController.class.getDeclaredMethod("handleCreateButton", ActionEvent.class);
                handleMethod.setAccessible(true); // 设置为可访问
                handleMethod.invoke(controller, (ActionEvent) null); // 传入null作为事件

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

    // Test Case 2: 用户名为纯数字 (EC V2)
    @Test
    public void testUsernameNumeric() {
        String alertMessage = simulateSignup("123456", "pass1234", "pass1234");
        assertEquals("Username cannot be pure numbers! Must contain at least one letter.", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    // Test Case 4: 用户名过长 (EC V4)
    @Test
    public void testUsernameTooLong() {
        String alertMessage = simulateSignup("averylongUserName", "pass1234", "pass1234");
        assertEquals("Username length cannot exceed 15 characters!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    // Test Case 5: 用户名为空 (EC V5)
    @Test
    public void testUsernameEmpty() {
        String alertMessage = simulateSignup("", "pass1234", "pass1234");
        assertEquals("Username cannot be empty!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    // Test Case 6: 密码为纯数字 (EC V7)
    @Test
    public void testPasswordNumeric() {
        String alertMessage = simulateSignup("ezra123", "123456", "123456");
        assertEquals("Password cannot contain only numbers! Must contain both letters and numbers.", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    // Test Case 7: 密码为纯字母 (EC V8)
    @Test
    public void testPasswordAlpha() {
        String alertMessage = simulateSignup("ezra123", "abcd", "abcd");
        assertEquals("Password cannot contain only letters! Must contain both letters and numbers.", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    // Test Case 8: 密码过长 (EC V9)
    @Test
    public void testPasswordTooLong() {
        String alertMessage = simulateSignup("ezra123", "averylongPassword", "averylongPassword");
        assertEquals("Password length cannot exceed 15 characters!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    // Test Case 9: 密码为空 (EC V10)
    @Test
    public void testPasswordEmpty() {
        String alertMessage = simulateSignup("ezra123", "", "");
        assertEquals("Password cannot be empty!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    // Test Case 10: 确认密码不匹配 (EC V12)
    @Test
    public void testPasswordMismatch() {
        String alertMessage = simulateSignup("ezra123", "pass1234", "word5678");
        assertEquals("The passwords entered twice do not match!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }

    // Test Case 11: 确认密码为空 (EC V13)
    @Test
    public void testConfirmPasswordEmpty() {
        String alertMessage = simulateSignup("ezra123", "pass1234", "");
        assertEquals("Confirm password cannot be empty!", alertMessage);
        verify(mockModel, never()).sign(any(), any());
    }
}