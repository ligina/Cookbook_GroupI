package Enter;
import javafx.stage.Stage;
import view.*;

/**
 * The type Application entrance.
 * Load a JavaFX stage.
 */
public class ApplicationEntrance extends javafx.application.Application {
<<<<<<< Updated upstream
=======
    // 在您的 ApplicationEntrance.java 或 App.java 中

>>>>>>> Stashed changes
    @Override
    public void start(Stage stage) throws Exception {
        SignInView signinView = new SignInView();
        signinView.show();
    }
}
