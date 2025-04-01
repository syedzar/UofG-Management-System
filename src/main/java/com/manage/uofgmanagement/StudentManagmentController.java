package com.manage.uofgmanagement;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class StudentManagmentController {

    @FXML
    private TableView<Student> studentTable;  // TableView to hold student data
    @FXML
    private TableColumn<Student, String> studentColumn;  // Column for student name
    @FXML
    private TableColumn<Student, String> studentIdColumn;  // Column for student ID
    @FXML
    private TableColumn<Student, String> addressColumn;  // Column for address
    @FXML
    private TableColumn<Student, String> phoneColumn;  // Column for phone number
    @FXML
    private TableColumn<Student, String> passwordColumn;  // Column for password
    @FXML
    private TableColumn<Student, String> emailColumn;  // Column for email

    // Initialize ObservableList to hold student data
    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    // Student class to represent student data
    public static class Student {
        private String studentName;
        private String studentId;
        private String address;
        private String phone;
        private String password;
        private String email;
        private String tuitionFee;
        private String profilePicturePath;
        private ObservableList<Course> enrolledCourses;
        private ObservableList<Grade> grades;

        // Constructor
        public Student(String studentName, String studentId, String address, String phone, String password, String email, String tuitionFee) {
            this.studentName = studentName;
            this.studentId = studentId;
            this.address = address;
            this.phone = phone;
            this.password = password;
            this.email = email;
            this.tuitionFee = tuitionFee;
            this.profilePicturePath = "resources/Default_pfp.svg.png";
            this.enrolledCourses = FXCollections.observableArrayList();
            this.grades = FXCollections.observableArrayList();

        }

        // Getters
        public String getStudentName() {
            return studentName;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public String getTuitionFee() {
            return tuitionFee;
        }
        public String getProfilePicturePath() {
            return profilePicturePath;
        }

        public ObservableList<Course> getEnrolledCourses() {
            return enrolledCourses;
        }
        public ObservableList<Grade> getGrades() {
            return grades;
        }

        // Setters
        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public void setTuitionFee(String tuitionFee) {
            this.tuitionFee = tuitionFee;
        }
        public void setProfilePicturePath(String profilePicturePath) {
            this.profilePicturePath = profilePicturePath;
        }
        public void addCourse(Course course) {
            enrolledCourses.add(course);
        }
        public void removeCourse(Course course) {
            enrolledCourses.remove(course);
        }
        public void addGrade(Grade grade) {
            grades.add(grade);
        }
        public void enrollInCourse(Course course) {
            enrolledCourses.add(course);
            // Generate a random grade (between 30 and 100) when enrolling
            double randomGrade = 30 + (Math.random() * 70); // Grade between 30 and 100
            Grade newGrade = new Grade(course, randomGrade);
            grades.add(newGrade);
        }
    }

    public static class Course {
        private String courseName;
        private String courseCode;

        public Course(String courseName, String courseCode) {
            this.courseName = courseName;
            this.courseCode = courseCode;
        }

        // Getters and Setters
        public String getCourseName() {
            return courseName;
        }

        public String getCourseCode() {
            return courseCode;
        }

        @Override
        public String toString() {
            return courseName + " (" + courseCode + ")";
        }
    }

    public static class Grade {
        private Course course;
        private double gradeValue; // Numeric grade (e.g., 90.0 for an A)

        public Grade(Course course, double gradeValue) {
            this.course = course;
            this.gradeValue = gradeValue;
        }

        // Getters and Setters
        public Course getCourse() {
            return course;
        }

        public double getGradeValue() {
            return gradeValue;
        }

        public void setGradeValue(double gradeValue) {
            this.gradeValue = gradeValue;
        }

        @Override
        public String toString() {
            return course.getCourseName() + " (" + course.getCourseCode() + ") - Grade: " + gradeValue;
        }
    }



    @FXML
    private void initialize() {
        // Bind columns to the respective fields of the Student class
        studentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudentName()));
        studentIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudentId()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        passwordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        // Set the TableView items to the ObservableList
        studentTable.setItems(studentList);
    }

    @FXML
    private void handleAddStudent() {
        // Create a dialog to add a new student
        Stage addStudentStage = new Stage();
        VBox vBox = new VBox(10);

        // Create labels and text fields for the new student attributes
        Label nameLabel = new Label(" Name:");
        TextField nameField = new TextField();

        Label idLabel = new Label(" Student ID:");
        TextField idField = new TextField();

        Label addressLabel = new Label(" Address:");
        TextField addressField = new TextField();

        Label phoneLabel = new Label(" Phone:");
        TextField phoneField = new TextField();

        Label passwordLabel = new Label(" Password:");
        TextField passwordField = new TextField();

        Label emailLabel = new Label(" Email:");
        TextField emailField = new TextField();

        // Create the submit button
        Button submitButton = new Button(" Add Student");

        // Handle form submission
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String studentId = idField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();

            if (name.isEmpty() || studentId.isEmpty() || address.isEmpty() || phone.isEmpty() || password.isEmpty() || email.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "All fields are required!");
            } else {
                // Create a new Student object and add it to the ObservableList
                Student newStudent = new Student(name, studentId, address, phone, password, email, "0.0");
                studentList.add(newStudent);  // Add new student to the ObservableList

                // Close the dialog
                addStudentStage.close();
            }
        });

        // Set up the VBox and add all fields to the form
        vBox.getChildren().addAll(nameLabel, nameField, idLabel, idField, addressLabel, addressField, phoneLabel, phoneField,
                passwordLabel, passwordField, emailLabel, emailField, submitButton);

        // Set up the scene for the dialog
        Scene scene = new Scene(vBox, 400, 500);
        addStudentStage.setScene(scene);
        addStudentStage.show();
    }

    @FXML
    private void handleEditStudent() {
        // Get the selected student from the TableView
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        // Check if a student is selected
        if (selectedStudent != null) {
            // Create a dialog to edit the selected student's details
            Stage editStudentStage = new Stage();
            VBox vBox = new VBox(10);

            // Pre-fill the fields with the selected student's current details
            TextField nameField = new TextField(selectedStudent.getStudentName());
            TextField idField = new TextField(selectedStudent.getStudentId());
            TextField addressField = new TextField(selectedStudent.getAddress());
            TextField phoneField = new TextField(selectedStudent.getPhone());
            TextField passwordField = new TextField(selectedStudent.getPassword());
            TextField emailField = new TextField(selectedStudent.getEmail());

            // Create the submit button for updating
            Button submitButton = new Button("Update Student");

            // Handle form submission
            submitButton.setOnAction(event -> {
                String name = nameField.getText();
                String studentId = idField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();
                String password = passwordField.getText();
                String email = emailField.getText();

                // Ensure that no fields are empty before submitting
                if (name.isEmpty() || studentId.isEmpty() || address.isEmpty() || phone.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "All fields are required!");
                } else {
                    // Update the selected student's details
                    selectedStudent.setStudentName(name);
                    selectedStudent.setStudentId(studentId);
                    selectedStudent.setAddress(address);
                    selectedStudent.setPhone(phone);
                    selectedStudent.setPassword(password);
                    selectedStudent.setEmail(email);

                    // Close the dialog
                    editStudentStage.close();

                    // Refresh the TableView to reflect the updated data
                    studentTable.refresh();
                }
            });

            // Set up the VBox and add all fields to the form
            vBox.getChildren().addAll(new Label("Name:"), nameField, new Label("Student ID:"), idField,
                    new Label("Address:"), addressField, new Label("Phone:"), phoneField,
                    new Label("Password:"), passwordField, new Label("Email:"), emailField, submitButton);

            // Set up the scene for the dialog
            Scene scene = new Scene(vBox, 400, 500);
            editStudentStage.setScene(scene);
            editStudentStage.show();
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a student to edit.");
        }
    }

    @FXML
    private void handleDeleteStudent() {
        // Get the selected student from the TableView
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        // Check if a student is selected
        if (selectedStudent != null) {
            // Create a confirmation dialog
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Student");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete this student?");

            // Show the dialog and wait for the user's response
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // If the user confirms, delete the student from the ObservableList
                    studentList.remove(selectedStudent);

                    // Optionally, show a success message
                    showAlert(Alert.AlertType.INFORMATION, "Student deleted successfully.");
                }
            });
        } else {
            // If no student is selected, show an error message
            showAlert(Alert.AlertType.ERROR, "Please select a student to delete.");
        }
    }

    @FXML
    private void handleTuitionManagement() {
        // Get the selected student from the TableView
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        // Check if a student is selected
        if (selectedStudent != null) {
            // Create a new window (Stage) to manage tuition fee
            Stage tuitionStage = new Stage();
            VBox vBox = new VBox(10);

            // Label and TextField for tuition fee
            Label tuitionFeeLabel = new Label("Tuition Fee:");
            TextField tuitionFeeField = new TextField(selectedStudent.getTuitionFee());

            // Create the submit button to update tuition fee
            Button submitButton = new Button("Update Tuition Fee");

            // Handle form submission (updating tuition fee)
            submitButton.setOnAction(event -> {
                String tuitionFee = tuitionFeeField.getText();

                // Ensure the tuition fee is valid (simple validation for numeric input)
                if (tuitionFee.isEmpty() || !tuitionFee.matches("[0-9]+(\\.[0-9]{1,2})?")) {
                    showAlert(Alert.AlertType.ERROR, "Please enter a valid tuition fee.");
                } else {
                    // Update the tuition fee of the selected student
                    selectedStudent.setTuitionFee(tuitionFee);

                    // Close the tuition management window
                    tuitionStage.close();

                    // Refresh the table to reflect the updated data (if necessary)
                    studentTable.refresh();
                }
            });

            // Set up the VBox and add the fields to the form
            vBox.getChildren().addAll(tuitionFeeLabel, tuitionFeeField, submitButton);

            // Set up the scene for the tuition management window
            Scene scene = new Scene(vBox, 300, 150);
            tuitionStage.setScene(scene);
            tuitionStage.setTitle("Tuition Management");
            tuitionStage.show();
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a student to manage tuition fee.");
        }
    }

    @FXML
    private void handleViewStudentProfile() {
        // Get the selected student from the TableView
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // Create a dialog to show the student's profile
            Stage profileStage = new Stage();
            VBox profileVBox = new VBox(10);
            profileVBox.setStyle("-fx-padding: 10;");

            // Create labels to show student information
            Label nameLabel = new Label("Name: " + selectedStudent.getStudentName());
            Label idLabel = new Label("Student ID: " + selectedStudent.getStudentId());
            Label addressLabel = new Label("Address: " + selectedStudent.getAddress());
            Label phoneLabel = new Label("Phone: " + selectedStudent.getPhone());
            Label passwordLabel = new Label("Password: " + selectedStudent.getPassword());
            Label emailLabel = new Label("Email: " + selectedStudent.getEmail());

            // Default profile picture (replace with your default image file path if needed)
            // Ensure the image is inside the 'resources/images' folder and adjust the path accordingly
            String profilePicPath = "/Default_pfp.svg.png"; // Path relative to the resources folder
            URL imageUrl = getClass().getResource(profilePicPath);

            if (imageUrl != null) {
                Image profileImage = new Image(imageUrl.toString());  // Load the image from the resources folder
                ImageView profileImageView = new ImageView(profileImage);  // Create an ImageView for displaying the image
                profileImageView.setFitHeight(100);  // Set image size
                profileImageView.setFitWidth(100);

                // Add the student's information and profile picture to the VBox
                profileVBox.getChildren().addAll(nameLabel, idLabel, addressLabel, phoneLabel, passwordLabel, emailLabel, profileImageView);
            } else {
                System.out.println("Error: Profile picture not found.");
            }

            // Create and show the scene
            Scene profileScene = new Scene(profileVBox, 300, 400);
            profileStage.setScene(profileScene);
            profileStage.setTitle("Student Profile");
            profileStage.show();
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a student to view.");
        }
    }

    private void refreshEnrollments(Student student) {
        // This method will update the table of students with the new enrollments
        studentTable.refresh();
    }

    @FXML
    private void handleManageEnrollments() {
        // Get the selected student from the TableView
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // Create a new window to manage enrollments
            Stage enrollStage = new Stage();
            VBox enrollVBox = new VBox(10);
            enrollVBox.setStyle("-fx-padding: 10;");

            // Define a list of available courses
            ObservableList<Course> availableCourses = FXCollections.observableArrayList(
                    new Course("Math 101", "MATH101"),
                    new Course("History 201", "HIST201"),
                    new Course("Computer Science 301", "CS301"),
                    new Course("Biology 102", "BIO102")
            );

            // ComboBox to select a course for enrollment
            ComboBox<Course> courseComboBox = new ComboBox<>(availableCourses);
            courseComboBox.setPromptText("Select a course");

            // Button to enroll the student in the selected course
            Button enrollButton = new Button("Enroll Student");
            enrollButton.setOnAction(e -> {
                Course selectedCourse = courseComboBox.getValue();
                if (selectedCourse != null) {
                    selectedStudent.enrollInCourse(selectedCourse); // Enroll student and generate grade
                    showAlert(Alert.AlertType.INFORMATION, "Student enrolled in " + selectedCourse.getCourseName());
                    enrollStage.close(); // Close the enrollment window after enrollment
                    refreshEnrollments(selectedStudent); // Refresh the table view to show updated enrollments
                } else {
                    showAlert(Alert.AlertType.ERROR, "Please select a course.");
                }
            });

            // TableView to display the student's enrolled courses and their grades
            TableView<Grade> gradeTableView = new TableView<>();
            gradeTableView.setItems(selectedStudent.getGrades());

            // Create columns for Course and Grade
            TableColumn<Grade, String> courseColumn = new TableColumn<>("Course");
            courseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourse().getCourseName()));

            TableColumn<Grade, Double> gradeColumn = new TableColumn<>("Grade");
            gradeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getGradeValue()).asObject());

            gradeTableView.getColumns().add(courseColumn);
            gradeTableView.getColumns().add(gradeColumn);

            // Add the ComboBox, Button, and TableView to the VBox
            enrollVBox.getChildren().addAll(
                    new Label("Select a Course to Enroll:"),
                    courseComboBox,
                    enrollButton,
                    new Label("Enrolled Courses & Grades:"),
                    gradeTableView
            );

            // Create and show the scene
            Scene enrollScene = new Scene(enrollVBox, 400, 400);
            enrollStage.setScene(enrollScene);
            enrollStage.setTitle("Manage Enrollments");
            enrollStage.show();
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a student to enroll.");
        }
    }



    @FXML
    private void handleAcademicProgressTracking() {
        // Get the selected student from the TableView
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // Create a new window to show academic progress
            Stage progressStage = new Stage();
            VBox progressVBox = new VBox(10);
            progressVBox.setStyle("-fx-padding: 10;");

            // ListView to display enrolled courses and grades
            ListView<Grade> gradesListView = new ListView<>(selectedStudent.getGrades());
            gradesListView.setPrefHeight(150);

            // Calculate GPA
            double totalGradePoints = 0;
            int totalCourses = 0;
            for (Grade grade : selectedStudent.getGrades()) {
                totalGradePoints += grade.getGradeValue();
                totalCourses++;
            }
            double gpa = (totalCourses > 0) ? totalGradePoints / totalCourses : 0;

            // Display GPA
            Label gpaLabel = new Label("GPA: " + String.format("%.2f", gpa));

            // Calculate graduation progress (assuming 120 credit hours required)
            int totalCreditHours = selectedStudent.getGrades().size() * 3;  // Each course is 3 credit hours
            double graduationProgress = (totalCreditHours / 120.0) * 100;  // Graduation progress in percentage
            Label progressLabel = new Label("Graduation Progress: " + String.format("%.2f", graduationProgress) + "%");

            // Add everything to the VBox
            progressVBox.getChildren().addAll(
                    new Label("Enrolled Courses and Grades:"),
                    gradesListView,
                    gpaLabel,
                    progressLabel
            );

            // Create and show the scene
            Scene progressScene = new Scene(progressVBox, 400, 400);
            progressStage.setScene(progressScene);
            progressStage.setTitle("Academic Progress Tracking");
            progressStage.show();
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a student to view academic progress.");
        }
    }

    // Method to show alert messages
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

