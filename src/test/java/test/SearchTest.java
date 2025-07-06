package test;

import control.RecipeSelectFXMLController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class SearchTest {

    private RecipeSelectFXMLController controller;
    private TextField searchField;
    private Button searchButton;
    private Model mockModel;

    @Start
    public void start(Stage stage) {
        // 创建控制器
        controller = new RecipeSelectFXMLController();

        // 创建模拟Model对象
        mockModel = mock(Model.class);

        // 创建UI组件
        searchField = new TextField();
        searchButton = new Button("Search");

        // 使用反射注入组件
        setField(controller, "searchField", searchField);
        setField(controller, "searchButton", searchButton);
        setField(controller, "model", mockModel);
    }

    @BeforeEach
    public void setUp() {
        // 清除输入字段
        searchField.clear();
        // 重置警告记录
        Model.resetLastDisplayedAlert();
    }

    @AfterEach
    public void tearDown() {
        // 重置警告记录
        Model.resetLastDisplayedAlert();
    }

    /**
     * 模拟搜索操作并检查是否弹窗
     *
     * @param searchTerm 要输入的搜索词
     * @return true表示没有弹窗，false表示有弹窗
     */
    private boolean checkNoAlertForInput(String searchTerm) {
        CompletableFuture<Boolean> noAlertFuture = new CompletableFuture<>();

        Platform.runLater(() -> {
            try {
                // 重置警告记录
                Model.resetLastDisplayedAlert();

                // 将文本键入搜索框
                searchField.setText(searchTerm);

                // 尝试执行搜索按钮点击
                try {
                    Method handleMethod = RecipeSelectFXMLController.class.getDeclaredMethod(
                            "handleSearchButton", ActionEvent.class);
                    handleMethod.setAccessible(true);
                    handleMethod.invoke(controller, (ActionEvent) null);
                } catch (Exception e) {
                    // 忽略执行过程中的任何异常
                }

                // 检查是否有警告弹窗
                Alert lastAlert = Model.getLastDisplayedAlert();
                noAlertFuture.complete(lastAlert == null);
            } catch (Exception e) {
                // 即使发生异常，也检查是否有警告
                Alert lastAlert = Model.getLastDisplayedAlert();
                noAlertFuture.complete(lastAlert == null);
            }
        });

        try {
            return noAlertFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            // 如果获取结果失败，默认认为有弹窗
            return false;
        }
    }

    /**
     * Test No.1: 有效搜索词输入 (EC V1)
     * 输入: "tomato"
     * 预期: 无弹窗
     */
    @Test
    public void testValidTextInput_Tomato() {
        // 检查是否有弹窗
        boolean noAlert = checkNoAlertForInput("tomato");

        // 验证无弹窗
        assertTrue(noAlert, "No alert");
    }

    /**
     * Test No.2: 超长搜索词输入 (EC V2)
     * 输入: "averylongIngredientNamewhichexceeds30characters"
     * 预期: 有弹窗
     */
    @Test
    public void testLongTextInput() {
        String longTerm = "averylongIngredientNamewhichexceeds30characters";
        boolean noAlert = checkNoAlertForInput(longTerm);

        // 验证有弹窗
        assertFalse(noAlert, "Should have alert");
    }

    /**
     * Test No.3: 空值输入 (EC V3)
     * 输入: "" (代表null)
     * 预期: 有弹窗
     */
    @Test
    public void testEmptyTextInput() {
        boolean noAlert = checkNoAlertForInput("");

        // 验证有弹窗
        assertFalse(noAlert, "Should have alert");
    }

    /**
     * 使用反射设置字段值
     */
    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("设置字段失败: " + fieldName, e);
        }
    }
}