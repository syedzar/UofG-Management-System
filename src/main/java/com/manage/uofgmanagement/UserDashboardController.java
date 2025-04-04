package com.manage.uofgmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {


    @FXML
    private Label usernameLabel;

    @FXML
    private Label enrolledCoursesLabel;

    @FXML
    private Label completedCoursesLabel;

    @FXML
    private Label pendingAssignmentsLabel;

    @FXML
    private Label notificationsLabel;

    @FXML
    private Label subjectManagementLabel;

    @FXML
    private Label courseManagementLabel;

    @FXML
    private Button viewProfileButton;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button viewSubjectsButton;

    @FXML
    private Button viewCoursesButton;

    @FXML
    private Button viewAssignmentsButton;

    @FXML
    private Button submitAssignmentButton;

    @FXML
    private Button viewNotificationsButton;

    @FXML
    private Button logoutButton2;

    private boolean isAdmin = false; // Default: Regular user

    // Method to set admin status
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        System.out.println("User Dashboard - Admin status set to: " + isAdmin);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText("John Doe");
        enrolledCoursesLabel.setText("5");
        completedCoursesLabel.setText("3");
        pendingAssignmentsLabel.setText("2");
        notificationsLabel.setText("1");

        // Set up button event handlers
        viewProfileButton.setOnAction(this::handleViewProfile);
        editProfileButton.setOnAction(this::handleEditProfile);
        viewSubjectsButton.setOnAction(this::handleViewSubjects);
        viewCoursesButton.setOnAction(this::handleViewCourses);
        viewAssignmentsButton.setOnAction(this::handleViewAssignments);
        submitAssignmentButton.setOnAction(this::handleSubmitAssignment);
        viewNotificationsButton.setOnAction(this::handleViewNotifications);
        logoutButton2.setOnAction(this::handleLogout2);
    }

    private void handleViewProfile(ActionEvent event) {
        System.out.println("View Profile button clicked.");
    }

    private void handleEditProfile(ActionEvent event) {
        System.out.println("Edit Profile button clicked.");
    }

    private void handleViewSubjects(ActionEvent event) {
        System.out.println("View Subjects button clicked.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewSubjects.fxml"));
            Parent root = loader.load();

            Stage facultyStage = new Stage();
            facultyStage.setScene(new Scene(root));
            facultyStage.setTitle("Subject View");
            facultyStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleViewCourses(ActionEvent event) {
        System.out.println("View Courses button clicked.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseDashboard.fxml"));
            Parent root = loader.load();

            // Get the controller for CourseDashboard
            CourseDashboardController controller = loader.getController();

            // Pass admin status to the CourseDashboard
            controller.setAdmin(isAdmin);

            // Open Course Dashboard
            Stage courseViewStage = new Stage();
            courseViewStage.setTitle("Course Dashboard");
            courseViewStage.setScene(new Scene(root));
            courseViewStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleViewAssignments(ActionEvent event) {
        System.out.println("View Assignments button clicked.");
    }

    private void handleSubmitAssignment(ActionEvent event) {
        System.out.println("Submit Assignment button clicked.");
    }

    private void handleViewNotifications(ActionEvent event) {
        System.out.println("View Notifications button clicked.");
    }
    // Logout method
    @FXML
    private void handleLogout2(ActionEvent event) {
        try {
            // Get the current stage (user dashboard window) and close it
            Stage currentStage = (Stage) logoutButton2.getScene().getWindow();
            currentStage.close();

            // Load the login FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            // Create a new stage (login window) and show it
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.setTitle("Login");
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}