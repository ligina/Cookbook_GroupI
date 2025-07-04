package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert; // 导入 Alert 类
import javafx.event.ActionEvent;
import model.Model; // 导入 Model 类
import view.RecipeSelectView; // 导入 RecipeSelectView 类
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
    public Button signupButton;
    private Model model;
    @FXML
    public void initialize() {
        model = new Model();
    }

    @FXML
    private void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // EC V3: Username cannot be empty
        if (username == null || username.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username cannot be empty!");
            return;
        }

        // EC V6: Password cannot be empty
        if (password == null || password.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password cannot be empty!");
            return;
        }

        boolean loginSuccessful = model.login(username, password);

        if (loginSuccessful) {
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
            RecipeSelectView recipeSelectView = new RecipeSelectView();
            recipeSelectView.show();
        } else {
            // Error messages are handled in model.login method
        }
    }

    @FXML
    private void handleSignupButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/trail_view_signuppage.fxml")); // FXML 文件名要记得对应
            Scene signupScene = new Scene(loader.load());
            Stage signupStage = new Stage();
            signupStage.setTitle("Sign Up");
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