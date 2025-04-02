package com.manage.uofgmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;

public class AdminDashboardController {

    // Student Management Buttons
    @FXML private Button addStudentButton;
    @FXML private Button editStudentButton;
    @FXML private Button deleteStudentButton;

    // Subject Management Buttons
    @FXML private Button addSubjectButton;
    @FXML private Button editSubjectButton;
    @FXML private Button deleteSubjectButton;

    // Course Management Buttons
    @FXML private Button addCourseButton;
    @FXML private Button editCourseButton;
    @FXML private Button deleteCourseButton;
    @FXML private Button viewCoursesButton;

    // Faculty Management Buttons
    @FXML private Button addFacultyButton;

    // Event Management Buttons
    @FXML private Button addEventButton;
    @FXML private Button editEventButton;
    @FXML private Button deleteEventButton;

    // Admin flag
    private boolean isAdmin = true;

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        System.out.println("Admin status set: " + isAdmin);
    }

    // Student Management Actions
    @FXML
    private void handleAddStudentAction() {
        System.out.println("Add Student button clicked!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentManagmentAdmin.fxml"));
            Parent root = loader.load();
            Stage facultyStage = new Stage();
            facultyStage.setScene(new Scene(root));
            facultyStage.setTitle("Student Management");
            facultyStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleEditStudent() {
        openWindow("/EditStudent.fxml", "Edit Student");
    }

    @FXML
    private void handleDeleteStudent() {
        System.out.println("Delete Student button clicked!");
    }

    // Subject Management Actions
    @FXML
    private void handleAddSubject() {
        System.out.println("Add Subject button clicked!");
    }

    @FXML
    private void handleEditSubject() {
        System.out.println("Edit Subject button clicked!");
    }

    @FXML
    private void handleDeleteSubject() {
        System.out.println("Delete Subject button clicked!");
    }

    // Course Management Actions
    @FXML
    private void handleAddCourseAction() {
        System.out.println("Add Course button clicked!");
    }

    @FXML
    private void handleEditCourseAction() {
        System.out.println("Edit Course button clicked!");
    }

    @FXML
    private void handleDeleteCourseAction() {
        System.out.println("Delete Course button clicked!");
    }

    @FXML
    private void handleViewCoursesAction() {
        try {
            URL fxmlLocation = getClass().getResource("/coursedashboard.fxml");
            if (fxmlLocation == null) {
                throw new IllegalStateException("FXML file not found at /coursedashboard.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            CourseDashboardController controller = loader.getController();
            controller.setAdmin(isAdmin);

            Stage courseStage = new Stage();
            courseStage.setScene(new Scene(root));
            courseStage.setTitle("Course Dashboard");
            courseStage.show();

            // Removed the line that closes the current window

        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    // Faculty Management Actions
    @FXML
    private void handleAddFacultyAction() {
        System.out.println("Add Faculty button clicked!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FacultyManagement.fxml"));
            Parent root = loader.load();
            Stage facultyStage = new Stage();
            facultyStage.setScene(new Scene(root));
            facultyStage.setTitle("Faculty Management");
            facultyStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Event Management Actions
    @FXML
    private void handleAddEvent() {
        System.out.println("Add Event button clicked!");
    }

    @FXML
    private void handleEditEvent() {
        System.out.println("Edit Event button clicked!");
    }

    @FXML
    private void handleDeleteEvent() {
        System.out.println("Delete Event button clicked!");
    }

    @FXML
    public void initialize() {
        System.out.println("Admin Dashboard initialized.");
    }

    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}