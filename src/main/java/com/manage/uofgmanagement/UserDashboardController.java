package com.manage.uofgmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
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
    private Button viewProfileButton;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button viewCoursesButton;

    @FXML
    private Button viewCourses2Button;  // Button for View Courses 2

    @FXML
    private Button enrollCourseButton;

    @FXML
    private Button viewAssignmentsButton;

    @FXML
    private Button submitAssignmentButton;

    @FXML
    private Button viewNotificationsButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize with sample data or bind to your model/data source.
        usernameLabel.setText("John Doe");
        enrolledCoursesLabel.setText("5");
        completedCoursesLabel.setText("3");
        pendingAssignmentsLabel.setText("2");
        notificationsLabel.setText("1");

        // Set up button event handlers
        viewProfileButton.setOnAction(this::handleViewProfile);
        editProfileButton.setOnAction(this::handleEditProfile);
        viewCoursesButton.setOnAction(this::handleViewCourses);
        viewCourses2Button.setOnAction(this::handleViewCourses2);  // Event for View Courses 2
        enrollCourseButton.setOnAction(this::handleEnrollCourse);
        viewAssignmentsButton.setOnAction(this::handleViewAssignments);
        submitAssignmentButton.setOnAction(this::handleSubmitAssignment);
        viewNotificationsButton.setOnAction(this::handleViewNotifications);
    }

    private void handleViewProfile(ActionEvent event) {
        // Add code to handle viewing the user profile.
        System.out.println("View Profile button clicked.");
    }

    private void handleEditProfile(ActionEvent event) {
        // Add code to handle editing the user profile.
        System.out.println("Edit Profile button clicked.");
    }

    private void handleViewCourses(ActionEvent event) {
        // Add code to handle viewing courses.
        System.out.println("View Courses button clicked.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewSubjects.fxml"));
            Parent root = loader.load();

            Stage facultyStage = new Stage();
            facultyStage.setScene(new Scene(root));
            facultyStage.setTitle("Course View");
            facultyStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleViewCourses2(ActionEvent event) {
        // Code to open Course Dashboard for View Courses 2
        System.out.println("View Courses 2 button clicked.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseDashboard.fxml"));
            Parent root = loader.load();

            // Stage for Course Dashboard
            Stage courseViewStage = new Stage();
            courseViewStage.setTitle("Course Dashboard");
            courseViewStage.setScene(new Scene(root));
            courseViewStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEnrollCourse(ActionEvent event) {
        // Add code to handle course enrollment.
        System.out.println("Enroll in Course button clicked.");
    }

    private void handleViewAssignments(ActionEvent event) {
        // Add code to handle viewing assignments.
        System.out.println("View Assignments button clicked.");
    }

    private void handleSubmitAssignment(ActionEvent event) {
        // Add code to handle assignment submission.
        System.out.println("Submit Assignment button clicked.");
    }

    private void handleViewNotifications(ActionEvent event) {
        // Add code to handle viewing notifications.
        System.out.println("View Notifications button clicked.");
    }
}
