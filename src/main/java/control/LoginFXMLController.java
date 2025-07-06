package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import model.Model;
import view.RecipeSelectView;
import view.SignupView;
import javafx.stage.Stage;
import javafx.scene.Node;


public class LoginFXMLController {

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

        if (username == null || username.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username cannot be empty!");
            return;
        }

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
        }
    }

    @FXML
    private void handleSignupButton(ActionEvent event) {
        SignupView signupView = new SignupView();
        signupView.show();
    }

}