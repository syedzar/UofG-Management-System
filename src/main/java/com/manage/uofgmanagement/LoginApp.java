package com.manage.uofgmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import static com.manage.uofgmanagement.ExcelReader.loadUserData;

public class LoginApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        ExcelReader.loadUserData(); // Loading all user data before UI generation
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300); // Set scene with given parameters
        primaryStage.setTitle("University Management System - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
