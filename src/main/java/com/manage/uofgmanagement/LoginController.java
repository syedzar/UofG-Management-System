package com.manage.uofgmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.manage.uofgmanagement.ExcelReader;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        // Loads the university logo
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/university_logo.png")));
        universityLogo.setImage(image);
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validating login
        String role = ExcelReader.validateUser(username, password);

        if (role != null) {
            errorLabel.setText("Login successful! Role: " + role);
            navigateToDashboard(role);
        } else {
            errorLabel.setText("Invalid credentials. Please try again.");
        }
    }

    private void navigateToDashboard(String role) {
        // Function to load the correct dashboard based on role
        if ("ADMIN".equals(role)) { // load the admin fxml here
            System.out.println("Redirecting to Admin dashboard");
        } else if ("USER".equals(role)) { // load the user fxml here
            System.out.println("Redirecting to User dashboard");
        }
        // Close login window
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }
}
