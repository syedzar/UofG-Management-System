package com.manage.uofgmanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ViewSubjectsDisplay implements Initializable {

    @FXML
    private ListView<String> myListView;

    @FXML
    private Label myLabel;

    // Declare HashMap to store subjects
    private final Map<String, String> subjects = new HashMap<>();

    private static final String FILE_PATH = "src/main/resources/UMS_Data.xlsx"; // Path to Excel file
    private static final String SHEET_NAME = "Subjects"; // Sheet name (with space)

    // Load subjects from Excel file
    private void loadSubjectsFromExcel() {
        try (FileInputStream fis = new FileInputStream(new File(FILE_PATH));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) {
                System.out.println("Sheet '" + SHEET_NAME + "' not found.");
                return;
            }

            for (Row row : sheet) {
                Cell codeCell = row.getCell(0);
                Cell nameCell = row.getCell(1);

                if (codeCell != null && nameCell != null) {
                    String code = codeCell.getStringCellValue().trim();
                    String name = nameCell.getStringCellValue().trim();
                    subjects.put(code, name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get key (subject code) by value (subject name)
    private String getKeyByValue(String value) {
        for (Map.Entry<String, String> entry : subjects.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null; // Return null if no match is found
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Loading subjects...");
        loadSubjectsFromExcel(); // Load subjects from Excel file

        myListView.getItems().addAll(subjects.values()); // Populate ListView with subject names

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String subjectCode = getKeyByValue(newValue);
                myLabel.setText(subjectCode != null ? subjectCode : "N/A");
            }
        });
    }
}
