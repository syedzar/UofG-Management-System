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
        // Loads the university logo from resources
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/university_logo.png")));
        universityLogo.setImage(image);
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validating login using ExcelReader (your custom logic)
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
        if ("ADMIN".equals(role)) { // load the admin FXML here
            System.out.println("Redirecting to Admin dashboard");
            try {
                // Load the Admin Dashboard FXML file.
                // Ensure AdminDashboard.fxml is in the resources root (or adjust the path accordingly).
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard.fxml"));
                Parent root = loader.load();

                // Optionally, get the controller to pass any data:
                AdminDashboardController controller = loader.getController();
                // controller.initializeData(...);

                // Create a new stage for the admin dashboard
                Stage adminStage = new Stage();
                adminStage.setScene(new Scene(root));
                adminStage.setTitle("Admin Dashboard");
                adminStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("USER".equals(role)) { // load the user FXML here
            System.out.println("Redirecting to User dashboard");
            // TODO: Implement user dashboard loading similarly
        }
        // Close login window
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }
}
