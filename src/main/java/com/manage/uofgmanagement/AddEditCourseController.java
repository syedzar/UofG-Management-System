package com.manage.uofgmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the "Add/Edit Course" window.
 * This class handles adding a new course or editing an existing one.
 */
public class AddEditCourseController {


    @FXML private TextField courseField;    // Text field for course name
    @FXML private TextField locationField;  // Text field for course location
    @FXML private TextField codeField;      // Text field for course code
    @FXML private TextField capacityField;  // Text field for course capacity (number of students)
    @FXML private TextField sectionField;   // Text field for section number
    @FXML private TextField examField;      // Text field for exam/final date
    @FXML private TextField professorField; // Text field for professor's name
    @FXML private TextField facultyField;   // Text field for faculty/subject code
    @FXML private TextField lectureField;   // Text field for lecture time

    private CourseEnrollment courseData; // Stores course information if editing
    private boolean isEditMode = false;  // Tracks whether the user is editing or adding a course

    /**
     * Initializes the form with an existing course's data for editing.
     * If null is passed, the form remains empty for adding a new course.
     *
     * @param course The course object to edit (null if adding a new course)
     */
    public void initData(CourseEnrollment course) {
        this.courseData = course;

        // If a course is provided, switch to edit mode and populate fields with existing data
        if (course != null) {
            isEditMode = true;
            courseField.setText(course.getCourseName());
            locationField.setText(course.getLocation());
            codeField.setText(course.getCourseCode());
            capacityField.setText(String.valueOf(course.getCapacity()));
            sectionField.setText(course.getSectionNumber());
            examField.setText(course.getFinalDate());
            professorField.setText(course.getTeacherName());
            facultyField.setText(course.getSubjectCode());
            lectureField.setText(course.getLectureTime());
        }
    }

    /**
     * Handles the 'Save' button click.
     * Saves the course data if valid and closes the window.
     */
    @FXML
    void saveCourse(ActionEvent event) {
        // If editing an existing course, update its details
        if (courseData != null && isEditMode) {
            updateExistingCourse();
        } else {
            createNewCourse();
        }

        closeWindow(); // Close the form after saving
    }

    /**
     * Updates an existing course's details with the values from the text fields.
     */
    private void updateExistingCourse() {
        courseData.setCourseName(courseField.getText());
        courseData.setLocation(locationField.getText());
        courseData.setCourseCode(codeField.getText());

        // Validate and set capacity
        try {
            courseData.setCapacity(Integer.parseInt(capacityField.getText()));
        } catch (NumberFormatException e) {
            System.err.println("Invalid capacity entered. Must be a number.");
            return;
        }

        courseData.setSectionNumber(sectionField.getText());
        courseData.setFinalDate(examField.getText());
        courseData.setTeacherName(professorField.getText());
        courseData.setSubjectCode(facultyField.getText());
        courseData.setLectureTime(lectureField.getText());
    }

    /**
     * Creates a new course using the provided form values and assigns it a unique ID.
     */
    private void createNewCourse() {
        try {
            int capacity = Integer.parseInt(capacityField.getText()); // Convert capacity to an integer
            int newId = generateNewId(); // Generate a unique ID for the new course

            CourseEnrollment newCourse = new CourseEnrollment(
                    newId,                      // Unique course ID
                    codeField.getText(),        // Course code
                    courseField.getText(),      // Course name
                    facultyField.getText(),     // Faculty/subject code
                    sectionField.getText(),     // Section number
                    capacity,                   // Course capacity
                    lectureField.getText(),     // Lecture time
                    examField.getText(),        // Exam/final date
                    locationField.getText(),    // Course location
                    professorField.getText()    // Professor's name
            );

            // TODO: Add logic to save newCourse to the database or an ObservableList

        } catch (NumberFormatException e) {
            System.err.println("Invalid capacity entered. Must be a number.");
            return;
        }
    }

    /**
     * Generates a new unique course ID.
     * Currently, this is a placeholder using random values.
     * In a real system, this should ensure uniqueness based on database records.
     *
     * @return A randomly generated course ID
     */
    private int generateNewId() {
        return (int) (Math.random() * 10000); // Simple random ID generator (not ideal for real use)
    }

    /**
     * Handles the 'Cancel' button click.
     * Closes the Add/Edit Course window without saving any changes.
     */
    @FXML
    void cancel(ActionEvent event) {
        closeWindow();
    }

    /**
     * Closes the Add/Edit Course window.
     */
    private void closeWindow() {
        Stage stage = (Stage) courseField.getScene().getWindow();
        stage.close();
    }
}
