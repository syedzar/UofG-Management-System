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

public class CourseDashboardController {

    @FXML
    private Button enrollStudentButton, enrollAdminButton, addButton, editButton, deleteButton;
    @FXML
    private ButtonBar buttonBar;
    @FXML
    private TableView<CourseEnrollment> courseTable;
    @FXML
    private TableColumn<CourseEnrollment, String> courseCodeColumn, courseNameColumn, subjectCodeColumn, sectionNumberColumn, lectureTimeColumn, finalDateColumn, locationColumn, teacherNameColumn;
    @FXML
    private TableColumn<CourseEnrollment, Integer> capacityColumn;
    @FXML
    private Label title;

    private boolean isAdmin = false;

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        checkAdminAccess();
    }

    @FXML
    void enrollStudentButtonPressed(ActionEvent event) {
        CourseEnrollment selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            try {
                String resourcePath = isAdmin ? "/EnrollmentManagementAdmin.fxml" : "/EnrollmentManagementUser.fxml";

                FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
                Parent root = loader.load();

                if (isAdmin) {
                    EnrollmentManagementControllerAdmin controller = loader.getController();
                    controller.setSelectedCourse(selectedCourse);
                } else {
                    EnrollmentManagementControllerUser controller = loader.getController();
                    controller.setSelectedCourse(selectedCourse);
                }

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

    @FXML
    void enrollAdminButtonPressed(ActionEvent event) {
        loadAdminEnrollmentDashboard(event);
    }

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

    @FXML
    void addButtonPressed(ActionEvent event) {
        openAddEditWindow(null);
    }

    @FXML
    void editButtonPressed(ActionEvent event) {
        CourseEnrollment selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            openAddEditWindow(selectedCourse);
        }
    }

    @FXML
    void deleteButtonPressed(ActionEvent event) {
        CourseEnrollment selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            courseTable.getItems().remove(selectedCourse);
        }
    }

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

    @FXML
    void initialize() {
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        subjectCodeColumn.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
        sectionNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        lectureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureTime"));
        finalDateColumn.setCellValueFactory(new PropertyValueFactory<>("finalDate"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

        loadDataFromExcel();
        checkAdminAccess();
    }

    private void checkAdminAccess() {
        addButton.setDisable(!isAdmin);
        editButton.setDisable(!isAdmin);
        deleteButton.setDisable(!isAdmin);
        enrollAdminButton.setDisable(!isAdmin);
    }

    private void loadDataFromExcel() {
        ObservableList<CourseEnrollment> data = FXCollections.observableArrayList();
        try (FileInputStream file = new FileInputStream(new File("src/main/resources/UMS_Data.xlsx"));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet coursesSheet = workbook.getSheet("Courses");
            if (coursesSheet != null) {
                int idCounter = 1;
                for (Row row : coursesSheet) {
                    if (row.getRowNum() == 0) continue;

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