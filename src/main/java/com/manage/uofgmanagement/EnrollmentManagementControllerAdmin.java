package com.manage.uofgmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentManagementControllerAdmin {

    @FXML
    private TableView<StudentEnrollment> enrollmentTable, waitlistTable;
    @FXML
    private TableColumn<StudentEnrollment, String> studentNameColumn, waitlistNameColumn;
    @FXML
    private Button enrollButton, dropButton, assignFacultyButton; // Removed deleteButton
    @FXML
    private ComboBox<CourseEnrollment> courseDropdown;

    private boolean isAdmin = false;
    private final EnrollmentService enrollmentService = new EnrollmentService();
    private CourseEnrollment selectedCourse;

    private ObservableList<StudentEnrollment> enrolledStudentsObservable;
    private ObservableList<StudentEnrollment> waitlistedStudentsObservable;

    @FXML
    public void initialize() {
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        waitlistNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadCourses();

        // Set admin mode to true to ensure buttons are enabled
        setAdmin(true);
    }

    public void setSelectedCourse(CourseEnrollment course) {
        this.selectedCourse = course;
        loadEnrollmentData();
    }

    private void loadCourses() {
        List<CourseEnrollment> courses = enrollmentService.getAllCourses();
        courseDropdown.setItems(FXCollections.observableArrayList(courses));

        courseDropdown.setOnAction(event -> {
            selectedCourse = courseDropdown.getSelectionModel().getSelectedItem();
            loadEnrollmentData();
        });
    }

    private void loadEnrollmentData() {
        if (selectedCourse != null) {
            List<StudentEnrollment> students = enrollmentService.getEnrolledStudents(selectedCourse.getId());
            List<StudentEnrollment> waitlist = enrollmentService.getWaitlistedStudents(selectedCourse.getId());

            enrolledStudentsObservable = FXCollections.observableArrayList(students);
            waitlistedStudentsObservable = FXCollections.observableArrayList(waitlist);

            enrollmentTable.setItems(enrolledStudentsObservable);
            waitlistTable.setItems(waitlistedStudentsObservable);
        }
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        checkAdminAccess();
    }

    private void checkAdminAccess() {
        System.out.println("Admin Mode: " + isAdmin); // Debug log

        enrollButton.setDisable(!isAdmin);
        assignFacultyButton.setDisable(!isAdmin);
        dropButton.setDisable(!isAdmin);
    }

    @FXML
    private void handleEnroll(ActionEvent event) {
        if (!isAdmin) {
            showAlert("Access Denied", "Only admins can enroll students.");
            return;
        }

        if (selectedCourse == null) {
            showAlert("Enrollment Error", "Please select a course first.");
            return;
        }

        StudentEnrollment selectedStudent = waitlistTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            String result = enrollmentService.enrollStudent(selectedStudent.getId(), selectedCourse.getId());
            showAlert("Enrollment Status", result);
            loadEnrollmentData(); // Update the tables after enrolling the student
        } else {
            showAlert("Select Student", "Please select a student from the waitlist.");
        }
    }

    @FXML
    private void handleDrop(ActionEvent event) {
        if (!isAdmin) {
            showAlert("Access Denied", "Only admins can drop students.");
            return;
        }

        StudentEnrollment selectedStudent = enrollmentTable.getSelectionModel().getSelectedItem();

        if (selectedCourse == null) {
            showAlert("Drop Error", "Please select a course first.");
            return;
        }

        if (selectedStudent == null) {
            showAlert("Drop Error", "Please select a student to drop.");
            return;
        }

        String result = enrollmentService.dropStudent(selectedStudent.getId(), selectedCourse.getId());
        showAlert("Drop Status", result);
        loadEnrollmentData();
    }

    @FXML
    private void handleAssignFaculty(ActionEvent event) {
        if (!isAdmin) {
            showAlert("Access Denied", "Only admins can assign faculty.");
            return;
        }

        if (selectedCourse == null) {
            showAlert("Faculty Assignment Error", "Please select a course first.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Assign Faculty");
        dialog.setHeaderText("Enter Faculty Name:");
        dialog.setContentText("Name:");

        dialog.showAndWait().ifPresent(name -> {
            if (!name.isEmpty()) {
                String result = enrollmentService.assignFaculty(selectedCourse.getId(), name);
                showAlert("Faculty Assignment", result);
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // =========================== INNER CLASS: EnrollmentService =========================== //
    private static class EnrollmentService {
        private final List<StudentEnrollment> enrolledStudents = new ArrayList<>();
        private final List<StudentEnrollment> waitlistedStudents = new ArrayList<>();
        private final List<CourseEnrollment> courses = new ArrayList<>();

        public EnrollmentService() {
            // Adding all the courses as provided
            courses.add(new CourseEnrollment(1, "MATH001", "Calculus I", "MATH", "Section 1", 30, "Mon/Wed 9-11 AM", "12/15/2025 9:00", "Room 101", "Dr. Alan Turing"));
            courses.add(new CourseEnrollment(2, "ENG101", "Literature Basics", "ENG", "Section 1", 25, "Tue/Thu 10-12 PM", "12/16/2025 10:00", "Room 102", "Prof. Emily Brontë"));
            courses.add(new CourseEnrollment(2, "ENG101", "Literature Basics", "ENG", "Section 2", 25, "Mon/Wed 10-12 PM", "12/16/2025 10:00", "Room 102", "Prof. Emily Brontë"));
            courses.add(new CourseEnrollment(3, "CS201", "Introduction to Programming", "CS", "Section 1", 42, "Tue/Thu 12-2 PM", "12/16/2025 12:30", "Room 103", "Prof. Bahar Nozari"));
            courses.add(new CourseEnrollment(4, "CHEM200", "Introduction to Chemistry", "CHEM", "Section 1", 50, "Mon/Thu 3-4 PM", "12/14/2025 4:00", "Room 201", "Dr. Lucka Lucku"));
            courses.add(new CourseEnrollment(4, "CHEM200", "Introduction to Chemistry", "CHEM", "Section 2", 50, "Mon/Tue 5-6 PM", "12/14/2025 4:00", "Room 201", "Dr. Lucka Lucku"));
            courses.add(new CourseEnrollment(4, "CHEM200", "Introduction to Chemistry", "CHEM", "Section 3", 50, "Fri/Thu 2-3 PM", "12/14/2025 4:00", "Room 201", "Dr. Lucka Lucku"));
            courses.add(new CourseEnrollment(5, "ENG101", "Introduction to French", "ENG", "Section 1", 25, "Tue/Thu 4:30 - 5:30 PM", "12/13/2025 10:00", "Room 202", "Dr. Lakyn Copeland"));
            courses.add(new CourseEnrollment(5, "ENG101", "Introduction to French", "ENG", "Section 2", 25, "Tue/Thu 5:30 - 6:30 PM", "12/13/2025 10:00", "Room 202", "Dr. Lakyn Copeland"));
            courses.add(new CourseEnrollment(6, "ENGG402", "Water Resources", "ENGG", "Section 1", 50, "Mon/Fri 9:00 - 10:30 AM", "12/01/2025 9:00", "Room 203", "Dr. Albozr Gharabaghi"));
        }

        public List<CourseEnrollment> getAllCourses() {
            return courses;
        }

        public List<StudentEnrollment> getEnrolledStudents(int courseId) {
            return enrolledStudents;
        }

        public List<StudentEnrollment> getWaitlistedStudents(int courseId) {
            return waitlistedStudents;
        }

        public String dropStudent(int studentId, int courseId) {
            return enrolledStudents.removeIf(student -> student.getId() == studentId) ?
                    "Student dropped successfully." : "Student not found in enrolled list.";
        }

        public String enrollStudent(int studentId, int courseId) {
            StudentEnrollment student = getWaitlistedStudentById(studentId);
            if (student != null) {
                enrolledStudents.add(student);
                waitlistedStudents.remove(student);
                return "Student enrolled successfully.";
            }
            return "Student not found in waitlist.";
        }

        public String assignFaculty(int courseId, String facultyName) {
            CourseEnrollment course = getCourseById(courseId);
            if (course != null) {
                course.setTeacherName(facultyName);
                return "Faculty assigned successfully.";
            }
            return "Course not found.";
        }

        private CourseEnrollment getCourseById(int courseId) {
            return courses.stream().filter(course -> course.getId() == courseId).findFirst().orElse(null);
        }

        private StudentEnrollment getWaitlistedStudentById(int studentId) {
            return waitlistedStudents.stream().filter(student -> student.getId() == studentId).findFirst().orElse(null);
        }
    }
}
