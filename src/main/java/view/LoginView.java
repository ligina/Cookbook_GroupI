package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class LoginView extends Stage {

    public LoginView() {
        try {
            URL fxmlLocation = getClass().getResource("/trial_view_loginpage.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            Pane root = loader.load();

            Scene scene = new Scene(root);
            this.setScene(scene);
            this.setTitle("login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}