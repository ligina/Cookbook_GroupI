package Enter;
import javafx.stage.Stage;
import view.*;
import view.LoginView;
/**
 * The type Application entrance.
 * Load a JavaFX stage.
 */
public class ApplicationEntrance extends javafx.application.Application {
    // 在 ApplicationEntrance.java 或 App.java 中
    // 这个项目是通过enter.app调用enter.applicationentrance来实现登录界面的
    //这里我修改了原先的loginview,因此重新写了定位

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginView loginView = new LoginView();
        loginView.show();
        // 不需要 primaryStage.show(); 因为 LoginView 本身就是一个 Stage
    }
}
