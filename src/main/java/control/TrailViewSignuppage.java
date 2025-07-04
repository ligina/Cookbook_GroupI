package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import model.Model; // 导入 Model 类
import javafx.stage.Stage; // 导入 Stage 类
import javafx.scene.Node; // 导入 Node 类
import javafx.scene.control.Alert; // 导入 Alert


public class TrailViewSignuppage {

    @FXML
    private TextField usernameField; // 对应 FXML 中的 fx:id="usernameField"
    @FXML
    private PasswordField passwordField; // 对应 FXML 中的 fx:id="passwordField"
    @FXML
    private PasswordField repeatPasswordField; // 对应 FXML 中的 fx:id="repeatPasswordField"
    @FXML
    private Button createButton; // 对应 FXML 中的 fx:id="createButton"
    @FXML
    private Button backButton; // FXML 中有一个 fx:id="backButton" 的按钮

    private Model model; // Model 类的成员变量

    @FXML
    public void initialize() {
        model = new Model(); // 实例化 Model 类
    }

    // 处理注册按钮点击事件的方法
    @FXML
    private void handleCreateButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();

        // EC V5: Username cannot be empty
        if (username == null || username.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username cannot be empty!");
            return;
        }

        // EC V10: Password cannot be empty
        if (password == null || password.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password cannot be empty!");
            return;
        }

        // EC V13: Confirm password cannot be empty
        if (repeatPassword == null || repeatPassword.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Confirm password cannot be empty!");
            return;
        }

        // EC V4: Username length cannot exceed 15 characters
        if (username.length() > 15) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username length cannot exceed 15 characters!");
            return;
        }

        // EC V2: Username cannot be pure numbers (must contain at least one letter)
        if (username.matches("^\\d+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username cannot be pure numbers! Must contain at least one letter.");
            return;
        }

        // EC V1: Username can only contain letters and numbers
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username can only contain letters and numbers!");
            return;
        }

        // EC V9: Password length cannot exceed 15 characters
        if (password.length() > 15) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password length cannot exceed 15 characters!");
            return;
        }

        // EC V7: Password cannot contain only numbers
        if (password.matches("^\\d+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password cannot contain only numbers! Must contain both letters and numbers.");
            return;
        }

        // EC V8: Password cannot contain only letters
        if (password.matches("^[a-zA-Z]+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password cannot contain only letters! Must contain both letters and numbers.");
            return;
        }

        // EC V6: Password must contain both letters and numbers
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password must contain both letters and numbers!");
            return;
        }

        // EC V12: Confirm password must match password
        if (!password.equals(repeatPassword)) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "The passwords entered twice do not match!");
            passwordField.clear();
            repeatPasswordField.clear();
            return;
        }

        // 调用 Model 的 sign 方法进行注册
        boolean signupSuccessful = model.sign(username, password);

        // 根据注册结果给出提示
        if (signupSuccessful) {
            Model.displayAlert(Alert.AlertType.INFORMATION, "Success", "Registration successful!");
            // 注册成功后，关闭注册窗口
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
        } else {
            // Model.sign 方法中已经包含了用户已存在的提示，这里可以根据需要添加其他提示
            // 例如：Model.displayAlert(Alert.AlertType.ERROR, "失败", "注册失败，请稍后再试或联系管理员。");
            // 暂时待定，后面看情况进行测试再编辑
        }
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        // 获取当前舞台并关闭
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        // 如果需要在这里打开登录页面，可以添加相应的代码：
        // try {
        //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/your/login_view.fxml"));
        //     Parent root = loader.load();
        //     Stage loginStage = new Stage();
        //     loginStage.setScene(new Scene(root));
        //     loginStage.show();
        //     currentStage.close(); // 关闭注册窗口
        // } catch (IOException e) {
        //     e.printStackTrace();
    }
}