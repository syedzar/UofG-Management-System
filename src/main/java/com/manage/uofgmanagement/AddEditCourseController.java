package com.manage.uofgmanagement;

import com.manage.uofgmanagement.CourseEnrollment; // Import the correct class
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEditCourseController {

    @FXML private TextField courseField;
    @FXML private TextField locationField;
    @FXML private TextField codeField;
    @FXML private TextField capacityField;
    @FXML private TextField sectionField;
    @FXML private TextField examField;
    @FXML private TextField professorField;
    @FXML private TextField facultyField;
    @FXML private TextField lectureField;

    private CourseEnrollment courseData; // Use CourseEnrollment
    private boolean isEditMode = false;

    public void initData(CourseEnrollment course) {
        this.courseData = course;
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

    @FXML
    void saveCourse(ActionEvent event) {
        if (courseData != null && isEditMode) {
            courseData.setCourseName(courseField.getText());
            courseData.setLocation(locationField.getText());
            courseData.setCourseCode(codeField.getText());
            try {
                courseData.setCapacity(Integer.parseInt(capacityField.getText()));
            } catch (NumberFormatException e) {
                System.err.println("Invalid capacity entered");
                return;
            }
            courseData.setSectionNumber(sectionField.getText());
            courseData.setFinalDate(examField.getText());
            courseData.setTeacherName(professorField.getText());
            courseData.setSubjectCode(facultyField.getText());
            courseData.setLectureTime(lectureField.getText());
        } else {
            try {
                int capacity = Integer.parseInt(capacityField.getText());
                int newId = generateNewId(); // Implemented ID generation

                CourseEnrollment newCourse = new CourseEnrollment(
                        newId, // Assign a new unique ID
                        codeField.getText(),
                        courseField.getText(),
                        facultyField.getText(),
                        sectionField.getText(),
                        capacity,
                        lectureField.getText(),
                        examField.getText(),
                        locationField.getText(),
                        professorField.getText()
                );

                // Add the newCourse to your ObservableList or database here
            } catch (NumberFormatException e) {
                System.err.println("Invalid capacity entered");
                return;
            }
        }
        closeWindow();
    }

    private int generateNewId() {
        // Implement logic to generate a new unique ID (Example: increment last used ID)
        return (int) (Math.random() * 10000); // Placeholder logic
    }

    @FXML
    void cancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) courseField.getScene().getWindow();
        stage.close();
    }
}
