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
    // ... other student management buttons

    // Subject Management Button (new consolidated button)
    @FXML private Button manageSubjectsButton; // Make sure this fx:id is referenced in your updated FXML

    // Course Management Buttons
    @FXML private Button addCourseButton;
    @FXML private Button editCourseButton;
    @FXML private Button deleteCourseButton;
    @FXML private Button viewCoursesButton;

    // Faculty Management Buttons
    @FXML private Button addFacultyButton;

    // Event Management Buttons
    // Note: For consolidated event management, we now use handleManageEvents(), not the individual add/edit/delete buttons.
    // If you no longer need addEventButton, editEventButton, deleteEventButton, they should be removed from your FXML and code.
    // Otherwise, you can leave them if they're still used.
    // For this example, we assume they're no longer needed.
    // @FXML private Button addEventButton;
    // @FXML private Button editEventButton;
    // @FXML private Button deleteEventButton;

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
            Stage studentStage = new Stage();
            studentStage.setScene(new Scene(root));
            studentStage.setTitle("Student Management");
            studentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // ... other student management methods

    // New Subject Management Action
    @FXML
    private void handleManageSubjects() {
        System.out.println("Manage Subjects button clicked!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SubjectManagement.fxml"));
            Parent root = loader.load();
            Stage subjectStage = new Stage();
            subjectStage.setScene(new Scene(root));
            subjectStage.setTitle("Subject Management");
            subjectStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    // Faculty Management Action
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

    // Consolidated Event Management Action
    @FXML
    private void handleManageEvents() {
        System.out.println("Manage Events button clicked!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventManagement.fxml"));
            Parent root = loader.load();
            Stage eventStage = new Stage();
            eventStage.setScene(new Scene(root));
            eventStage.setTitle("Event Management");
            eventStage.show();
            eventStage.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Initialize method should be inside the class body.
    @FXML
    public void initialize() {
        System.out.println("Admin Dashboard initialized.");
    }

    // Helper method to open a window (optional, not used in the code above)
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
