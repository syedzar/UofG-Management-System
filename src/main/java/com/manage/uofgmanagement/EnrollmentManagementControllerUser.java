package com.manage.uofgmanagement;

// Import required JavaFX components
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing student enrollments in the user view.
 * This allows students to view available courses, check their enrollment status,
 * and attempt to enroll in a course.
 */
public class EnrollmentManagementControllerUser {

    @FXML
    private TableView<StudentEnrollment> enrollmentTable, waitlistTable;   // Tables displaying enrolled and waitlisted students

    @FXML
    private TableColumn<StudentEnrollment, String> studentNameColumn, waitlistNameColumn; // Columns displaying student names
    @FXML
    private Button enrollButton; // Removed dropButton, assignFacultyButton, deleteButton
    @FXML
    private ComboBox<CourseEnrollment> courseDropdown; // Dropdown to select a course

    private final EnrollmentService enrollmentService = new EnrollmentService(); // Handles enrollment logic
    private CourseEnrollment selectedCourse; // Stores the currently selected course

    // Observable lists to dynamically update the UI tables
    private ObservableList<StudentEnrollment> enrolledStudentsObservable;
    private ObservableList<StudentEnrollment> waitlistedStudentsObservable;


    /**
     * Initializes the controller when the UI loads.
     * Sets up table columns and loads available courses.
     */
    @FXML
    public void initialize() {
        // Link table columns to the "name" property in StudentEnrollment objects
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        waitlistNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
// Load courses into the dropdown menu
        loadCourses();
    }

    /**
     * Sets the currently selected course and refreshes enrollment data.
     * @param course The selected course
     */
    public void setSelectedCourse(CourseEnrollment course) {
        this.selectedCourse = course;
        loadEnrollmentData();
    }


    /**
     * Fetches all available courses from the enrollment service and populates the dropdown.
     */
    private void loadCourses() {
        List<CourseEnrollment> courses = enrollmentService.getAllCourses(); // Fetch all courses
        courseDropdown.setItems(FXCollections.observableArrayList(courses)); // Populate dropdown

        // Handle course selection event
        courseDropdown.setOnAction(event -> {
            selectedCourse = courseDropdown.getSelectionModel().getSelectedItem();// Update selected course
            loadEnrollmentData(); // Refresh enrollment data for the selected course
        });
    }

    /**
     * Loads student enrollment and waitlist data for the selected course.
     */
    private void loadEnrollmentData() {
        if (selectedCourse != null) {
            // Fetch enrolled and waitlisted students from the backend service
            List<StudentEnrollment> students = enrollmentService.getEnrolledStudents(selectedCourse.getId());
            List<StudentEnrollment> waitlist = enrollmentService.getWaitlistedStudents(selectedCourse.getId());

            // Convert lists to observable lists for dynamic UI updates
            enrolledStudentsObservable = FXCollections.observableArrayList(students);
            waitlistedStudentsObservable = FXCollections.observableArrayList(waitlist);

            // Bind data to UI tables
            enrollmentTable.setItems(enrolledStudentsObservable);
            waitlistTable.setItems(waitlistedStudentsObservable);
        }
    }


    /**
     * Handles the enrollment button click event.
     * Prompts the user for their name and attempts to enroll them in the selected course.
     * @param event Button click event
     */
    @FXML
    private void handleEnroll(ActionEvent event) {
        if (selectedCourse == null) {
            showAlert("Enrollment Error", "Please select a course first.");
            return;
        }

        // Open a text input dialog to get the student's name
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enroll Student");
        dialog.setHeaderText("Enter Student Name:");
        dialog.setContentText("Name:");

        // Process user input and attempt enrollment
        dialog.showAndWait().ifPresent(name -> {
            if (!name.isEmpty()) {
                String result = enrollmentService.enrollStudent(name, selectedCourse.getId());
                showAlert("Enrollment Status", result); // Show the result in an alert
                loadEnrollmentData(); // Refresh the UI with updated enrollment data

            }
        });
    }

    /**
     * Displays an informational alert dialog.
     * @param title The title of the alert
     * @param message The message content
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     * Simulates a backend service for managing course enrollments.
     * Stores mock data for courses and student enrollments.
     */
    private static class EnrollmentService {
        private final List<StudentEnrollment> enrolledStudents = new ArrayList<>();
        private final List<StudentEnrollment> waitlistedStudents = new ArrayList<>();
        private final List<CourseEnrollment> courses = new ArrayList<>();


        /**
         * Initializes the service with a set of predefined courses.
         */

        public EnrollmentService() {
            // Adding courses to the system
            courses.add(new CourseEnrollment(1, "MATH001", "Calculus I", "MATH", "Section 1", 30, "Mon/Wed 9-11 AM", "12/15/2025 9:00", "Room 101", "Dr. Alan Turing"));
            courses.add(new CourseEnrollment(2, "ENG101", "Literature Basics", "ENG", "Section 1", 25, "Tue/Thu 10-12 PM", "12/16/2025 10:00", "Room 102", "Prof. Emily Brontë"));
            courses.add(new CourseEnrollment(2, "ENG101", "Literature Basics", "ENG", "Section 2", 25, "Mon/Wed 10-12 PM", "12/16/2025 10:00", "Room 102", "Prof. Emily Brontë"));
            courses.add(new CourseEnrollment(3, "CS201", "Introduction to Programming", "CS", "Section 1", 42, "Tue/Thu 12-2 PM", "12/16/2025 12:30", "Room 103", "Prof. Bahar Nozari"));
            courses.add(new CourseEnrollment(4, "CHEM200", "Introduction to Chemistry", "CHEM", "Section 1", 50, "Mon/Thu 3-4 PM", "12/14/2025 4:00", "Room 201", "Dr. Lucka Lucku"));
            courses.add(new CourseEnrollment(4, "CHEM200", "Introduction to Chemistry", "CHEM", "Section 2", 50, "Mon/Tue 5-6 PM", "12/14/2025 4:00", "Room 201", "Dr. Lucka Lucku"));
            courses.add(new CourseEnrollment(4, "CHEM200", "Introduction to Chemistry", "CHEM", "Section 3", 50, "Fri/Thu 2-3 PM", "12/14/2025 4:00", "Room 201", "Dr. Lucka Lucku"));
            courses.add(new CourseEnrollment(5, "ENG101", "Introduction to French", "ENG", "Section 1", 25, "Tue/Thu 4:30-5:30 PM", "12/13/2025 10:00", "Room 202", "Dr. Lakyn Copeland"));
            courses.add(new CourseEnrollment(5, "ENG101", "Introduction to French", "ENG", "Section 2", 25, "Tue/Thu 5:30-6:30 PM", "12/13/2025 10:00", "Room 202", "Dr. Lakyn Copeland"));
            courses.add(new CourseEnrollment(6, "ENGG402", "Water Resources", "ENGG", "Section 1", 50, "Mon/Fri 9:00-10:30 AM", "12/01/2025 9:00", "Room 203", "Dr. Albozr Gharabaghi"));
        }


        /**
         * Returns a list of all available courses.
         */
        public List<CourseEnrollment> getAllCourses() {
            return courses;
        }

        /**
         * Returns a list of enrolled students for a given course.
         * @param courseId The ID of the course
         */
        public List<StudentEnrollment> getEnrolledStudents(int courseId) {
            return enrolledStudents;
        }

        /**
         * Returns a list of waitlisted students for a given course.
         * @param courseId The ID of the course
         */
        public List<StudentEnrollment> getWaitlistedStudents(int courseId) {
            return waitlistedStudents;
        }


        /**
         * Attempts to enroll a student in a course.
         * If the course is full, the student is placed on a waitlist.
         * @param studentName The name of the student
         * @param courseId The ID of the course
         */
        public String enrollStudent(String studentName, int courseId) {
            CourseEnrollment course = getCourseById(courseId);
            if (course != null) {
                // Check if the course has available seats
                if (enrolledStudents.size() < course.getCapacity()) {
                    enrolledStudents.add(new StudentEnrollment(studentName));  // Add student to enrolled list
                    return "Student enrolled successfully.";
                } else {
                    waitlistedStudents.add(new StudentEnrollment(studentName)); // Add student to waitlist
                    return "Course is full. Student added to waitlist.";
                }
            }
            return "Course not found.";
        }

        /**
         * Finds a course by its ID.
         * @param courseId The ID of the course
         * @return The corresponding CourseEnrollment object, or null if not found
         */
        private CourseEnrollment getCourseById(int courseId) {
            return courses.stream().filter(course -> course.getId() == courseId).findFirst().orElse(null);
        }
    }
}
