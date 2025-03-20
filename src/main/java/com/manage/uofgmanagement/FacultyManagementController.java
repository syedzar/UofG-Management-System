package com.manage.uofgmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import java.io.*;
import java.util.*;

public class FacultyManagementController {

    @FXML
    private ListView<String> facultyListView;

    private static final String FILE_PATH = "src/main/resources/UMS_Data.xlsx"; // Update this with actual file path
    private static final String SHEET_NAME = "Faculties ";

    private List<Faculty> facultyList = new ArrayList<>();

    public static class Faculty {
        String id;
        String name;
        String degree;
        String researchInterest;
        String email;
        String officeLocation;
        String coursesOffered;
        String password;

        Faculty(String id, String name, String degree, String researchInterest, String email, String officeLocation, String coursesOffered, String password) {
            this.id = id;
            this.name = name;
            this.degree = degree;
            this.researchInterest = researchInterest;
            this.email = email;
            this.officeLocation = officeLocation;
            this.coursesOffered = coursesOffered;
            this.password = password;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        @Override
        public String toString() { return name; }
    }

    @FXML
    public void initialize() {
        loadFacultyData();
    }

    private void loadFacultyData() {
        facultyList.clear();
        facultyListView.getItems().clear();
        try (FileInputStream fis = new FileInputStream("src/main/resources/UMS_Data.xlsx");
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Faculties ");
            if (sheet == null) return;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                Faculty faculty = new Faculty(
                        row.getCell(0).getStringCellValue(), // Faculty ID
                        row.getCell(1).getStringCellValue(), // Name
                        row.getCell(2).getStringCellValue(), // Degree
                        row.getCell(3).getStringCellValue(), // Research Interest
                        row.getCell(4).getStringCellValue(), // Email
                        row.getCell(5).getStringCellValue(), // Office Location
                        row.getCell(6).getStringCellValue(), // Courses Offered
                        row.getCell(7).getStringCellValue()  // Password
                );
                facultyList.add(faculty);
                facultyListView.getItems().add(faculty.toString()); // Display "FXXXX - Name"
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddFaculty() {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        TextField nameField = new TextField();
        TextField degreeField = new TextField();
        TextField researchField = new TextField();
        TextField emailField = new TextField();
        TextField officeField = new TextField();
        TextField coursesField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button submitButton = new Button("Add Faculty");

        submitButton.setOnAction(event -> {
            String id = generateUniqueFacultyID();
            Faculty newFaculty = new Faculty(id, nameField.getText(), degreeField.getText(), researchField.getText(),
                    emailField.getText(), officeField.getText(), coursesField.getText(), passwordField.getText());
            facultyList.add(newFaculty);
            saveFacultyData();
            loadFacultyData();
            stage.close();
        });

        vbox.getChildren().addAll(new Label("Name:"), nameField, new Label("Degree:"), degreeField,
                new Label("Research Interest:"), researchField, new Label("Email:"), emailField,
                new Label("Office Location:"), officeField, new Label("Courses Offered:"), coursesField,
                new Label("Password:"), passwordField, submitButton);
        stage.setScene(new Scene(vbox, 300, 400));
        stage.setTitle("Add Faculty");
        stage.show();
    }

    private void saveFacultyData() {
        try (FileInputStream fis = new FileInputStream(FILE_PATH); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) sheet = workbook.createSheet(SHEET_NAME);
            int rowNum = 1;
            for (Faculty faculty : facultyList) {
                Row row = sheet.getRow(rowNum);
                if (row == null) row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(faculty.getId());
                row.createCell(1).setCellValue(faculty.getName());
                row.createCell(2).setCellValue(faculty.degree);
                row.createCell(3).setCellValue(faculty.researchInterest);
                row.createCell(4).setCellValue(faculty.getEmail());
                row.createCell(5).setCellValue(faculty.officeLocation);
                row.createCell(6).setCellValue(faculty.coursesOffered);
                row.createCell(7).setCellValue(faculty.getPassword());
                rowNum++;
            }
            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private String generateUniqueFacultyID() {
        int maxNum = 0;
        for (Faculty faculty : facultyList) {
            String numPart = faculty.getId().substring(1);
            maxNum = Math.max(maxNum, Integer.parseInt(numPart));
        }
        return "F" + String.format("%04d", maxNum + 1);
    }

    @FXML
    private void handleEditFaculty() {
        String selectedFaculty = facultyListView.getSelectionModel().getSelectedItem();
        if (selectedFaculty == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a faculty to edit.");
            return;
        }

        String selectedID = selectedFaculty.split(" - ")[0]; // Extract ID (FXXXX format)
        Faculty faculty = facultyList.stream()
                .filter(f -> f.id.equals(selectedID))
                .findFirst().orElse(null);

        if (faculty == null) {
            showAlert(Alert.AlertType.ERROR, "Faculty not found.");
            return;
        }

        Stage editStage = new Stage();
        VBox vbox = new VBox(10);
        TextField nameField = new TextField(faculty.name);
        TextField degreeField = new TextField(faculty.degree);
        TextField researchField = new TextField(faculty.researchInterest);
        TextField emailField = new TextField(faculty.email);
        TextField officeField = new TextField(faculty.officeLocation);
        TextField coursesField = new TextField(faculty.coursesOffered);
        PasswordField passwordField = new PasswordField();
        passwordField.setText(faculty.password);

        Button saveButton = new Button("Save Changes");
        saveButton.setOnAction(event -> {
            faculty.name = nameField.getText();
            faculty.degree = degreeField.getText();
            faculty.researchInterest = researchField.getText();
            faculty.email = emailField.getText();
            faculty.officeLocation = officeField.getText();
            faculty.coursesOffered = coursesField.getText();
            faculty.password = passwordField.getText();

            updateFacultyInExcel(faculty);
            loadFacultyData();
            editStage.close();
        });

        vbox.getChildren().addAll(new Label("Name:"), nameField, new Label("Degree:"), degreeField,
                new Label("Research Interest:"), researchField, new Label("Email:"), emailField,
                new Label("Office Location:"), officeField, new Label("Courses Offered:"), coursesField,
                new Label("Password:"), passwordField, saveButton);

        editStage.setScene(new Scene(vbox, 350, 400));
        editStage.setTitle("Edit Faculty");
        editStage.show();
    }

    private void updateFacultyInExcel(Faculty updatedFaculty) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) return;

            for (Row row : sheet) {
                if (row.getCell(0).getStringCellValue().equals(updatedFaculty.id)) {
                    row.getCell(1).setCellValue(updatedFaculty.name);
                    row.getCell(2).setCellValue(updatedFaculty.degree);
                    row.getCell(3).setCellValue(updatedFaculty.researchInterest);
                    row.getCell(4).setCellValue(updatedFaculty.email);
                    row.getCell(5).setCellValue(updatedFaculty.officeLocation);
                    row.getCell(6).setCellValue(updatedFaculty.coursesOffered);
                    row.getCell(7).setCellValue(updatedFaculty.password);
                    break;
                }
            }

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteFaculty() {
        String selectedFaculty = facultyListView.getSelectionModel().getSelectedItem();
        if (selectedFaculty == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a faculty to delete.");
            return;
        }

        String selectedID = selectedFaculty.split(" - ")[0]; // Extract Faculty ID
        facultyList.removeIf(faculty -> faculty.id.equals(selectedID));
        deleteFacultyFromExcel(selectedID);
        loadFacultyData();
    }

    private void deleteFacultyFromExcel(String facultyID) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) return;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(facultyID)) {
                    sheet.removeRow(row);
                    break;
                }
            }

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewProfile() {
        String selectedFaculty = facultyListView.getSelectionModel().getSelectedItem();
        if (selectedFaculty == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a faculty.");
            return;
        }

        String selectedID = selectedFaculty.split(" - ")[0]; // Extract Faculty ID
        Faculty faculty = facultyList.stream()
                .filter(f -> f.id.equals(selectedID))
                .findFirst().orElse(null);

        if (faculty != null) {
            showAlert(Alert.AlertType.INFORMATION,
                    "ID: " + faculty.id + "\nName: " + faculty.name + "\nEmail: " + faculty.email +
                            "\nDegree: " + faculty.degree + "\nResearch: " + faculty.researchInterest);
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Faculty Management");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
