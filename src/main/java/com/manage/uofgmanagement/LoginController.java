package com.manage.uofgmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.manage.uofgmanagement.ExcelReader;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Use ExcelReader to validate login
        String role = ExcelReader.validateUser(username, password);

        if (role != null) {
            errorLabel.setText("Login successful! Role: " + role);
            navigateToDashboard(role);
        } else {
            errorLabel.setText("Invalid credentials. Please try again.");
        }
    }

    private void navigateToDashboard(String role) {
        // Logic to load the correct dashboard based on role (ADMIN or USER)
        if ("ADMIN".equals(role)) {
            System.out.println("Redirecting to Admin Dashboard...");
            // Load Admin Dashboard
        } else if ("USER".equals(role)) {
            System.out.println("Redirecting to User Dashboard...");
            // Load User Dashboard
        }

        // Close login window (optional)
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }
}
