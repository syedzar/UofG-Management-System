package com.manage.uofgmanagement;

import com.manage.uofgmanagement.CourseEnrollment;
import com.manage.uofgmanagement.StudentEnrollment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentManagementController {

    @FXML
    private TableView<StudentEnrollment> enrollmentTable, waitlistTable;
    @FXML
    private TableColumn<StudentEnrollment, String> studentNameColumn, waitlistNameColumn;
    @FXML
    private Button enrollButton, dropButton, assignFacultyButton;
    @FXML
    private ComboBox<CourseEnrollment> courseDropdown;

    private boolean isAdmin = false;
    private final EnrollmentService enrollmentService = new EnrollmentService();
    private CourseEnrollment selectedCourse;

    // Declare the ObservableList variables
    private ObservableList<StudentEnrollment> enrolledStudentsObservable;
    private ObservableList<StudentEnrollment> waitlistedStudentsObservable;

    @FXML
    public void initialize() {
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        waitlistNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadCourses();
        checkAdminAccess();
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

            // Initialize the ObservableLists
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
        assignFacultyButton.setDisable(!isAdmin);
        enrollButton.setDisable(isAdmin);
    }

    @FXML
    private void handleEnroll(ActionEvent event) {
        if (selectedCourse == null) {
            showAlert("Enrollment Error", "Please select a course first.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enroll Student");
        dialog.setHeaderText("Enter Student Name:");
        dialog.setContentText("Name:");

        dialog.showAndWait().ifPresent(name -> {
            if (!name.isEmpty()) {
                String result = enrollmentService.enrollStudent(name, selectedCourse.getId());
                showAlert("Enrollment Status", result);
                loadEnrollmentData();
            }
        });
    }

    @FXML
    private void handleDrop(ActionEvent event) {
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

    public static class EnrollmentService {

        private final List<StudentEnrollment> enrolledStudents = new ArrayList<>();
        private final List<StudentEnrollment> waitlistedStudents = new ArrayList<>();
        private final List<CourseEnrollment> courses = new ArrayList<>();

        public EnrollmentService() {
            courses.add(new CourseEnrollment(1, "MATH001", "Calculus I", "MATH", "Section 1", 30, "Mon/Wed 9-11 AM", "12/15/2025 9:00", "Room 101", "Dr. Alan Turing"));
            courses.add(new CourseEnrollment(2, "ENG101", "Literature Basics", "ENG", "Section 1", 25, "Tue/Thu 10-12 PM", "12/16/2025 10:00", "Room 102", "Prof. Emily Brontë"));
            courses.add(new CourseEnrollment(3, "CS201", "Introduction to Programming", "CS", "Section 1", 42, "Tue/Thu 12-2 PM", "12/16/2025 12:30", "Room 103", "Prof. Bahar Nozari"));
            courses.add(new CourseEnrollment(4, "CHEM200", "Introduction to Chemistry", "CHEM", "Section 1", 50, "Mon/Thu 3-4 PM", "12/14/2025 4:00", "Room 201", "Dr. Lucka Lucku"));
            courses.add(new CourseEnrollment(5, "ENG101", "Introduction to French", "ENG", "Section 1", 25, "Tue/Thu 4:30 - 5:30 PM", "12/13/2025 10:00", "Room 202", "Dr. Lakyn Copeland"));
            courses.add(new CourseEnrollment(6, "ENGG402", "Water Resources", "ENGG", "Section 1", 50, "Mon/Fri 9:00 - 10:30", "12/01/2025 9:00", "Room 203", "Dr. Albozr Gharabaghi"));
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

        public String enrollStudent(String studentName, int courseId) {
            CourseEnrollment course = getCourseById(courseId);
            if (course != null) {
                if (enrolledStudents.size() < course.getCapacity()) {
                    enrolledStudents.add(new StudentEnrollment(studentName));
                    return "Student enrolled successfully.";
                } else {
                    waitlistedStudents.add(new StudentEnrollment(studentName));
                    return "Course is full. Student added to waitlist.";
                }
            }
            return "Course not found.";
        }

        public String dropStudent(int studentId, int courseId) {
            StudentEnrollment studentToRemove = null;
            for (StudentEnrollment student : enrolledStudents) {
                if (student.getId() == studentId) {
                    studentToRemove = student;
                    break;
                }
            }

            if (studentToRemove != null) {
                enrolledStudents.remove(studentToRemove);
                return "Student dropped successfully.";
            }
            return "Student not found in enrolled list.";
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
            for (CourseEnrollment course : courses) {
                if (course.getId() == courseId) {
                    return course;
                }
            }
            return null;
        }
    }
}
