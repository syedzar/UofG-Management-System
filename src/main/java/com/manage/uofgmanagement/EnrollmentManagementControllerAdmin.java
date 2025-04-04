package com.manage.uofgmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing course enrollments in the admin view.
 * This includes enrolling students, dropping students, and assigning faculty.
 */
public class EnrollmentManagementControllerAdmin {

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

    private ObservableList<StudentEnrollment> enrolledStudentsObservable;
    private ObservableList<StudentEnrollment> waitlistedStudentsObservable;

    @FXML
    public void initialize() {
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        waitlistNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadCourses();
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

        if (!courses.isEmpty()) {
            courseDropdown.getSelectionModel().selectFirst();
            selectedCourse = courseDropdown.getSelectionModel().getSelectedItem();
            loadEnrollmentData();
        }
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
            loadEnrollmentData();
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

    /**
     * Service class with simulated student enrollments.
     */
    private class EnrollmentService {
            private final List<CourseEnrollment> courses = new ArrayList<>();
            private final List<StudentEnrollment> allStudents = new ArrayList<>();
            private final List<EnrollmentRecord> enrollmentRecords = new ArrayList<>();

            public EnrollmentService() {
                // Add courses
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

                // Add students
                allStudents.add(new StudentEnrollment("Alice Smith"));      // 0
                allStudents.add(new StudentEnrollment("Bob Johnson"));      // 1
                allStudents.add(new StudentEnrollment("Carol Williams"));   // 2
                allStudents.add(new StudentEnrollment("Lucka Racki"));      // 3
                allStudents.add(new StudentEnrollment("David Lee"));        // 4
                allStudents.add(new StudentEnrollment("Emily Brown"));      // 5
                allStudents.add(new StudentEnrollment("George Smith"));     // 6
                allStudents.add(new StudentEnrollment("Helen Jones"));      // 7
                allStudents.add(new StudentEnrollment("Isaac Clark"));      // 8
                allStudents.add(new StudentEnrollment("Jennifer Davis"));   // 9

                // Enrollment Records (mix of enrolled and waitlisted)
                enrollmentRecords.add(new EnrollmentRecord(1, allStudents.get(0), true));   // Alice Smith - enrolled in MATH001
                enrollmentRecords.add(new EnrollmentRecord(1, allStudents.get(1), false));  // Bob Johnson - waitlisted in MATH001

                enrollmentRecords.add(new EnrollmentRecord(2, allStudents.get(2), true));   // Carol Williams - enrolled in ENG101
                enrollmentRecords.add(new EnrollmentRecord(2, allStudents.get(3), false));  // Lucka Racki - waitlisted in ENG101

                enrollmentRecords.add(new EnrollmentRecord(3, allStudents.get(4), true));   // David Lee - enrolled in CS201
                enrollmentRecords.add(new EnrollmentRecord(3, allStudents.get(5), false));  // Emily Brown - waitlisted in CS201
                enrollmentRecords.add(new EnrollmentRecord(3, allStudents.get(6), false));  // George Smith - waitlisted in CS201

                enrollmentRecords.add(new EnrollmentRecord(4, allStudents.get(7), true));   // Helen Jones - enrolled in CHEM200
                enrollmentRecords.add(new EnrollmentRecord(4, allStudents.get(8), false));  // Isaac Clark - waitlisted in CHEM200

                enrollmentRecords.add(new EnrollmentRecord(5, allStudents.get(9), true));   // Jennifer Davis - enrolled in French
            }

            public List<CourseEnrollment> getAllCourses() {
                return courses;
            }

            public List<StudentEnrollment> getEnrolledStudents(int courseId) {
                List<StudentEnrollment> enrolled = new ArrayList<>();
                for (EnrollmentRecord record : enrollmentRecords) {
                    if (record.courseId == courseId && record.isEnrolled) {
                        enrolled.add(record.student);
                    }
                }
                return enrolled;
            }

            public List<StudentEnrollment> getWaitlistedStudents(int courseId) {
                List<StudentEnrollment> waitlisted = new ArrayList<>();
                for (EnrollmentRecord record : enrollmentRecords) {
                    if (record.courseId == courseId && !record.isEnrolled) {
                        waitlisted.add(record.student);
                    }
                }
                return waitlisted;
            }

            public String dropStudent(int studentId, int courseId) {
                for (EnrollmentRecord record : enrollmentRecords) {
                    if (record.courseId == courseId && record.student.getId() == studentId && record.isEnrolled) {
                        record.isEnrolled = false;
                        return "Student dropped successfully.";
                    }
                }
                return "Student not found in enrolled list.";
            }

            public String enrollStudent(int studentId, int courseId) {
                for (EnrollmentRecord record : enrollmentRecords) {
                    if (record.courseId == courseId && record.student.getId() == studentId && !record.isEnrolled) {
                        record.isEnrolled = true;
                        return "Student enrolled successfully.";
                    }
                }
                return "Student not found in waitlist.";
            }

            public String assignFaculty(int courseId, String facultyName) {
                for (CourseEnrollment course : courses) {
                    if (course.getId() == courseId) {
                        course.setTeacherName(facultyName);
                        return "Faculty assigned successfully.";
                    }
                }
                return "Course not found.";
            }

            private class EnrollmentRecord {
                int courseId;
                StudentEnrollment student;
                boolean isEnrolled;

                EnrollmentRecord(int courseId, StudentEnrollment student, boolean isEnrolled) {
                    this.courseId = courseId;
                    this.student = student;
                    this.isEnrolled = isEnrolled;
                }
            }
        }

    }
