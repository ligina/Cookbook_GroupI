package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import model.Model;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;


public class SignupFXMLController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private Button createButton;
    @FXML
    private Button backButton;

    private Model model;

    @FXML
    public void initialize() {
        model = new Model();
    }

    @FXML
    private void handleCreateButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();


        if (username == null || username.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username cannot be empty!");
            return;
        }


        if (password == null || password.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password cannot be empty!");
            return;
        }


        if (repeatPassword == null || repeatPassword.isEmpty()) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Confirm password cannot be empty!");
            return;
        }


        if (username.length() > 15) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username length cannot exceed 15 characters!");
            return;
        }


        if (username.matches("^\\d+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username cannot be pure numbers! Must contain at least one letter.");
            return;
        }


        if (!username.matches("^[a-zA-Z0-9]+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Username can only contain letters and numbers!");
            return;
        }


        if (password.length() > 15) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password length cannot exceed 15 characters!");
            return;
        }


        if (password.matches("^\\d+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password cannot contain only numbers! Must contain both letters and numbers.");
            return;
        }


        if (password.matches("^[a-zA-Z]+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password cannot contain only letters! Must contain both letters and numbers.");
            return;
        }


        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$")) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "Password must contain both letters and numbers!");
            return;
        }


        if (!password.equals(repeatPassword)) {
            Model.displayAlert(Alert.AlertType.WARNING, "Warning", "The passwords entered twice do not match!");
            passwordField.clear();
            repeatPasswordField.clear();
            return;
        }


        boolean signupSuccessful = model.sign(username, password);


        if (signupSuccessful) {
            Model.displayAlert(Alert.AlertType.INFORMATION, "Success", "Registration successful!");

            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
        } else {



        }
    }

    @FXML
    private void handleBackButton(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

    }
}