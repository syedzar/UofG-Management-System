package com.manage.uofgmanagement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

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

