package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import model.Model; // 导入 Model 类
import view.MainPageView; // 导入 MainPageView 类
import javafx.stage.Stage; // 导入 Stage 类
import javafx.scene.Node; // 导入 Node 类
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;


public class TrialViewLoginpage {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    public Button signuoButton;

    private Model model; // 声明 Model 类的成员变量

    @FXML
    public void initialize() {
        model = new Model(); // 实例化 Model 类
    }

    // 处理登录按钮点击事件的方法
    // 登录按钮的 On Action 设置是 #handleLoginButton
    @FXML
    private void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        //System.out.println("尝试登录 用户名: " + username + ", 密码: " + password);

        // 调用 Model 的 login 方法进行用户验证
        boolean loginSuccessful = model.login(username, password); // 确保 model 变量已声明并初始化

        if (loginSuccessful) {
            //System.out.println("登录成功!");
            // 登录成功后，切换到主界面

            // 获取当前窗口 Stage
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close(); // 关闭当前登录窗口

            // 打开新的主界面窗口
            MainPageView mainPage = new MainPageView();
            mainPage.show();

        } else {
            //System.out.println("登录失败。");
            // Model.login 方法中已经包含了显示错误消息的逻辑
        }
    }

    // 原来的示例 validateUser 方法已被移除，因为我们现在使用 model.login 进行验证
    @FXML
    private void handleSignupButton(ActionEvent event) {
        try {
            // 加载新的注册界面 FXML 文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/trail_view_signuppage.fxml")); // FXML 文件名要记得对应
            Scene signupScene = new Scene(loader.load());

            // 创建一个新的 Stage 来显示注册界面
            Stage signupStage = new Stage();
            signupStage.setTitle("Sign Up"); // 设置窗口标题
            signupStage.setScene(signupScene);
            signupStage.show();

            // 关闭当前的登录窗口（如果希望打开注册界面时关闭登录界面）
            // Node source = (Node) event.getSource();
            // Stage currentStage = (Stage) source.getScene().getWindow();
            // currentStage.close();
            // 测试下来感觉不是很必要
        } catch (IOException e) {
            e.printStackTrace();
            // 处理加载 FXML 文件的错误
        }
    }

    // 如果在 FXML 中设置了其他组件的事件处理方法，也在这里实现
    // 后续再添加

}