package com.manage.uofgmanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static com.manage.uofgmanagement.ExcelReader.getCellValue;


/**
 * Controller class for managing the Course Dashboard.
 * This class handles displaying, adding, editing, and deleting courses,
 * as well as handling enrollments for students and admins.
 */

public class CourseDashboardController {

    @FXML
    private Button enrollStudentButton, enrollAdminButton, addButton, editButton, deleteButton;
    @FXML
    private ButtonBar buttonBar;  // Holds admin-related buttons.
    @FXML
    private TableView<CourseEnrollment> courseTable; // Table to display courses

    // Columns for the courseTable, each mapping to a property in CourseEnrollment.
    @FXML
    private TableColumn<CourseEnrollment, String> courseCodeColumn, courseNameColumn, subjectCodeColumn, sectionNumberColumn, lectureTimeColumn, finalDateColumn, locationColumn, teacherNameColumn;
    @FXML
    private TableColumn<CourseEnrollment, Integer> capacityColumn; // Capacity column (integer type).
    @FXML
    private Label title; // Label to display the title of the dashboard.

    private boolean isAdmin = false; // Tracks whether the current user is an admin.

    /**
     * Sets whether the user is an admin and updates UI accordingly.
     * @param isAdmin true if user is an admin, false otherwise.
     */
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        checkAdminAccess(); // Enable/disable admin buttons based on role.
    }


    /**
     * Handles student enrollment button click.
     * Loads the appropriate Enrollment Management UI based on user role.
     */
    @FXML
    void enrollStudentButtonPressed(ActionEvent event) {
        CourseEnrollment selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            try {
                // Choose the appropriate FXML file based on whether the user is an admin.
                String resourcePath = isAdmin ? "/EnrollmentManagementAdmin.fxml" : "/EnrollmentManagementUser.fxml";

                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
                Parent root = loader.load();

                // Pass the selected course to the corresponding controller
                if (isAdmin) {
                    EnrollmentManagementControllerAdmin controller = loader.getController();
                    controller.setSelectedCourse(selectedCourse);
                } else {
                    EnrollmentManagementControllerUser controller = loader.getController();
                    controller.setSelectedCourse(selectedCourse);
                }

                // Create and show the enrollment management window
                Stage enrollStage = new Stage();
                enrollStage.setScene(new Scene(root));
                enrollStage.setTitle("Enrollment Management");
                enrollStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error loading Enrollment Management FXML: " + e.getMessage());
            }
        } else {
            System.out.println("No course selected for enrollment.");
        }
    }


    /**
     * Handles admin enrollment button click.
     * Opens the admin-specific Enrollment Management UI.
     */
    @FXML
    void enrollAdminButtonPressed(ActionEvent event) {
        loadAdminEnrollmentDashboard(event);
    }

    /**
     * Loads the Admin Enrollment Dashboard.
     */
    private void loadAdminEnrollmentDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnrollmentManagementAdmin.fxml"));
            Parent root = loader.load();

            Stage adminStage = new Stage();
            adminStage.setScene(new Scene(root));
            adminStage.setTitle("Admin Enrollment Management");
            adminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading EnrollmentManagementAdmin.fxml");
        }
    }

    /**
     * Handles the 'Add Course' button click.
     * Opens the Add/Edit Course window in 'Add' mode.
     */
    @FXML
    void addButtonPressed(ActionEvent event) {
        openAddEditWindow(null); // Passing null means we're adding a new course.
    }

    /**
     * Handles the 'Edit Course' button click.
     * Opens the Add/Edit Course window in 'Edit' mode with selected course data.
     */
    @FXML
    void editButtonPressed(ActionEvent event) {
        CourseEnrollment selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            openAddEditWindow(selectedCourse);
        }
    }

    /**
     * Handles the 'Delete Course' button click.
     * Removes the selected course from the table.
     */
    @FXML
    void deleteButtonPressed(ActionEvent event) {
        CourseEnrollment selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            courseTable.getItems().remove(selectedCourse);
        }
    }

    /**
     * Opens the Add/Edit Course window.
     * @param course If null, opens in 'Add' mode; otherwise, opens in 'Edit' mode with course details.
     */
    private void openAddEditWindow(CourseEnrollment course) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEditCourse.fxml"));
            Parent root = loader.load();

            AddEditCourseController controller = loader.getController();
            if (course != null) {
                controller.initData(course);
            }

            Stage stage = new Stage();
            stage.setTitle(course == null ? "Add Course" : "Edit Course");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            courseTable.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the Course Dashboard.
     * This method is automatically called when the FXML file is loaded.
     */
    @FXML
    void initialize() {
        // Bind table columns to CourseEnrollment properties.
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        subjectCodeColumn.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
        sectionNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        lectureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureTime"));
        finalDateColumn.setCellValueFactory(new PropertyValueFactory<>("finalDate"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

        loadDataFromExcel(); // Load course data from an Excel file.
        checkAdminAccess();  // Adjust UI based on admin privileges.
    }

    /**
     * Enables/disables admin-only buttons based on user role.
     */
    private void checkAdminAccess() {
        addButton.setDisable(!isAdmin);
        editButton.setDisable(!isAdmin);
        deleteButton.setDisable(!isAdmin);
        enrollAdminButton.setDisable(!isAdmin);
    }

    /**
     * Loads course data from an Excel file and populates the table.
     */
    private void loadDataFromExcel() {
        ObservableList<CourseEnrollment> data = FXCollections.observableArrayList();
        try (FileInputStream file = new FileInputStream(new File("src/main/resources/UMS_Data.xlsx"));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet coursesSheet = workbook.getSheet("Courses");
            if (coursesSheet != null) {
                int idCounter = 1;
                for (Row row : coursesSheet) {
                    if (row.getRowNum() == 0) continue; // Skip header row

                    // Read cell values safely
                    String courseCode = getCellValue(row.getCell(0));
                    String courseName = getCellValue(row.getCell(1));
                    String subjectCode = getCellValue(row.getCell(2));
                    String sectionNumber = getCellValue(row.getCell(3));
                    double capacityDouble;
                    try {
                        capacityDouble = Double.parseDouble(getCellValue(row.getCell(4)));
                    } catch (NumberFormatException e) {
                        capacityDouble = 0.0;
                        System.err.println("Error parsing capacity: " + getCellValue(row.getCell(4)));
                    }
                    int capacity = (int) capacityDouble;

                    String lectureTime = getCellValue(row.getCell(5));
                    String finalDate = getCellValue(row.getCell(6));
                    String location = getCellValue(row.getCell(7));
                    String teacherName = getCellValue(row.getCell(8));

                    CourseEnrollment course = new CourseEnrollment(idCounter++, courseCode, courseName, subjectCode, sectionNumber, capacity, lectureTime, finalDate, location, teacherName);
                    data.add(course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        courseTable.setItems(data);
    }
}