package com.manage.uofgmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FacultyManagementController {

    @FXML
    private ListView<String> facultyListView;

    @FXML
    private Button addFacultyButton, editFacultyButton, deleteFacultyButton, viewProfileButton, assignCoursesButton;

    private List<Faculty> facultyList = new ArrayList<>();

    // Sample Faculty class to hold the faculty data
    public static class Faculty {
        String name;
        String email;
        String password;

        Faculty(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public FacultyManagementController() {
        // Sample Data - You can replace this with actual loading from a file or database
        facultyList.add(new Faculty("John Doe", "john.doe@university.com", "password123"));
        facultyList.add(new Faculty("Jane Smith", "jane.smith@university.com", "password123"));
    }

    @FXML
    private void handleAddFaculty() {
        // Create a dialog to add a new faculty
        Stage addFacultyStage = new Stage();
        VBox vbox = new VBox(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button submitButton = new Button("Add Faculty");

        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert(AlertType.ERROR, "All fields are required!");
            } else {
                // Add new faculty to the list
                Faculty newFaculty = new Faculty(name, email, password);
                facultyList.add(newFaculty);
                facultyListView.getItems().add(name); // Add faculty name to the ListView
                addFacultyStage.close();
            }
        });

        vbox.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField, submitButton);
        Scene scene = new Scene(vbox, 300, 250);
        addFacultyStage.setScene(scene);
        addFacultyStage.setTitle("Add Faculty");
        addFacultyStage.show();
    }

    @FXML
    private void handleEditFaculty() {
        String selectedFacultyName = facultyListView.getSelectionModel().getSelectedItem();
        if (selectedFacultyName == null) {
            showAlert(AlertType.WARNING, "Please select a faculty to edit.");
            return;
        }

        // Find the faculty object based on the selected name
        Faculty selectedFaculty = null;
        for (Faculty faculty : facultyList) {
            if (faculty.getName().equals(selectedFacultyName)) {
                selectedFaculty = faculty;
                break;
            }
        }

        if (selectedFaculty == null) {
            showAlert(AlertType.ERROR, "Selected faculty not found.");
            return;
        }

        // Create a new dialog to edit faculty
        Stage editFacultyStage = new Stage();
        VBox vbox = new VBox(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField(selectedFaculty.getName());
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField(selectedFaculty.getEmail());
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setText(selectedFaculty.getPassword());

        Button submitButton = new Button("Update Faculty");

        Faculty finalSelectedFaculty = selectedFaculty;
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert(AlertType.ERROR, "All fields are required!");
            } else {
                // Update the Faculty object in the model
                finalSelectedFaculty.name = name;
                finalSelectedFaculty.email = email;
                finalSelectedFaculty.password = password;

                // Now update the ListView
                // Ensure facultyListView is synchronized with facultyList
                facultyListView.getItems().clear();  // Clear current items
                facultyListView.getItems().addAll(getFacultyNames());  // Add updated items (faculty names)

                editFacultyStage.close();
            }
        });

        vbox.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField, submitButton);
        Scene scene = new Scene(vbox, 300, 250);
        editFacultyStage.setScene(scene);
        editFacultyStage.setTitle("Edit Faculty");
        editFacultyStage.show();
    }

    // Utility method to get faculty names for the ListView
    private List<String> getFacultyNames() {
        List<String> names = new ArrayList<>();
        for (Faculty faculty : facultyList) {
            names.add(faculty.getName());
        }
        return names;
    }

    @FXML
    private void handleDeleteFaculty() {
        String selectedFacultyName = facultyListView.getSelectionModel().getSelectedItem();
        if (selectedFacultyName == null) {
            showAlert(AlertType.WARNING, "Please select a faculty to delete.");
            return;
        }

        // Confirm deletion
        Alert confirmDeletionAlert = new Alert(AlertType.CONFIRMATION);
        confirmDeletionAlert.setTitle("Delete Faculty");
        confirmDeletionAlert.setHeaderText("Are you sure you want to delete the selected faculty?");
        Optional<ButtonType> result = confirmDeletionAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Remove faculty from the list
            Faculty selectedFaculty = null;
            for (Faculty faculty : facultyList) {
                if (faculty.getName().equals(selectedFacultyName)) {
                    selectedFaculty = faculty;
                    break;
                }
            }

            if (selectedFaculty != null) {
                facultyList.remove(selectedFaculty);
                facultyListView.getItems().remove(selectedFacultyName);
            }
        }
    }

    @FXML
    private void handleViewProfile() {
        String selectedFacultyName = facultyListView.getSelectionModel().getSelectedItem();
        if (selectedFacultyName == null) {
            showAlert(AlertType.WARNING, "Please select a faculty to view.");
            return;
        }

        Faculty selectedFaculty = null;
        for (Faculty faculty : facultyList) {
            if (faculty.getName().equals(selectedFacultyName)) {
                selectedFaculty = faculty;
                break;
            }
        }

        if (selectedFaculty == null) {
            showAlert(AlertType.ERROR, "Selected faculty not found.");
        } else {
            // Show the profile information in an alert
            showAlert(AlertType.INFORMATION, "Faculty Profile:\nName: " + selectedFaculty.getName() +
                    "\nEmail: " + selectedFaculty.getEmail());
        }
    }

    @FXML
    private void handleAssignCourses() {
        String selectedFacultyName = facultyListView.getSelectionModel().getSelectedItem();
        if (selectedFacultyName == null) {
            showAlert(AlertType.WARNING, "Please select a faculty to assign courses.");
            return;
        }

        Faculty selectedFaculty = null;
        for (Faculty faculty : facultyList) {
            if (faculty.getName().equals(selectedFacultyName)) {
                selectedFaculty = faculty;
                break;
            }
        }

        if (selectedFaculty == null) {
            showAlert(AlertType.ERROR, "Selected faculty not found.");
        } else {
            // Logic to assign courses
            showAlert(AlertType.INFORMATION, "Assign courses to: " + selectedFaculty.getName());
        }
    }

    // Utility method to show alerts
    private void showAlert(AlertType alertType, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Faculty Management");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
