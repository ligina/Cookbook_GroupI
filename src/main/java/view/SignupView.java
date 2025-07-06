package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class SignupView extends Stage {

    public SignupView() {
        try {
            URL fxmlLocation = getClass().getResource("/trail_view_signuppage.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            Pane root = loader.load();

            Scene scene = new Scene(root);
            this.setScene(scene);
            this.setTitle("Sign Up");
            this.setResizable(false);
            this.setWidth(700);
            this.setHeight(650);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}