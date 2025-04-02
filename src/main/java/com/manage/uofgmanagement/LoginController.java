package com.manage.uofgmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField usernameField; // Field to enter username/email

    @FXML
    private PasswordField passwordField; // Field to enter password

    @FXML
    private Button loginButton; // The interactable login button

    @FXML
    private Label errorLabel; // Error message for invalid credentials

    @FXML
    private ImageView universityLogo; // Image for university logo

    @FXML
    public void initialize() {
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/university_logo.png")));
            universityLogo.setImage(image);
        } catch (NullPointerException e) {
            System.out.println("Warning: University logo not found.");
        }

        // Check if the Login.fxml file is accessible
        checkLoginFXML();
    }

    // Method to check if the Login.fxml is accessible and debug the path
    private void checkLoginFXML() {
        // Print out the path that the class loader is using to find the FXML file
        System.out.println("FXML path: " + getClass().getClassLoader().getResource("login.fxml"));  // Updated path

        InputStream input = getClass().getClassLoader().getResourceAsStream("login.fxml");  // Updated path
        if (input == null) {
            System.out.println("Login.fxml file not found!");
            errorLabel.setText("Login.fxml file not found! Please contact support.");
        } else {
            System.out.println("Login.fxml file loaded successfully!");
        }
    }


    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validating login using ExcelReader (your custom logic)
        String role = ExcelReader.validateUser(username, password);

        if (role != null) {
            System.out.println("Login successful! Role: " + role);
            navigateToDashboard(role);
        } else {
            errorLabel.setText("Invalid credentials. Please try again.");
        }
    }

    private void navigateToDashboard(String role) {
        String fxmlFile = null;
        boolean isAdmin = false;

        if ("ADMIN".equalsIgnoreCase(role)) {
            fxmlFile = "/AdminDashboard.fxml";
            isAdmin = true;
        } else if ("USER".equalsIgnoreCase(role)) {
            fxmlFile = "/UserDashboard.fxml";
        }

        if (fxmlFile != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                // Get the controller and pass admin status
                Object controller = loader.getController();
                if (controller instanceof AdminDashboardController) {
                    ((AdminDashboardController) controller).setAdmin(isAdmin);
                } else if (controller instanceof UserDashboardController) {
                    ((UserDashboardController) controller).setAdmin(isAdmin);
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle(isAdmin ? "Admin Dashboard" : "User Dashboard");
                stage.show();

                // Close login window
                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
                errorLabel.setText("Error loading dashboard. Please contact support.");
            }
        } else {
            errorLabel.setText("Error: Unknown user role.");
        }
    }
}
