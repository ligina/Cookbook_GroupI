package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane; // 将这里的 AnchorPane 改为 Pane
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class LoginView extends Stage {

    public LoginView() {
        try {
            URL fxmlLocation = getClass().getResource("/trial_view_loginpage.fxml"); // 确认路径是否正确
            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            Pane root = loader.load(); // 将这里的 AnchorPane 改为 Pane

            Scene scene = new Scene(root);
            this.setScene(scene);
            this.setTitle("login"); // 设置窗口标题

        } catch (IOException e) {
            e.printStackTrace();
            // 处理加载 FXML 文件的错误
        }
    }

    // 添加其他方法来访问 FXML 中定义的 UI 组件或与 Controller 交互
}