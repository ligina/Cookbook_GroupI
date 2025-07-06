package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;


public class ApplicationLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginView loginView = new LoginView();
        loginView.show();
    }
}