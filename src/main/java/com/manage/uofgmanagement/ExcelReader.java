package com.manage.uofgmanagement;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {
    private static final String FILE_PATH = "src/main/resources/UMS_Data.xlsx"; // File path to the excel data
    private static final Map<String, String> users = new HashMap<>(); // HashMap to store all users
    private static final Map<String, String> roles = new HashMap<>(); // HashMap to store all roles

    // Load user data from Excel
    public static void loadUserData() {
        try (FileInputStream file = new FileInputStream(new File(FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {

            // Load Students
            Sheet studentSheet = workbook.getSheet("Students "); // Access for student sheet
            if (studentSheet != null) {
                for (Row row : studentSheet) { // For loop to load all available students in the student sheet
                    Cell emailCell = row.getCell(4); // Column E to retrieve emails
                    Cell passwordCell = row.getCell(11); // Column H to retrieve passwords
                    if (emailCell != null && passwordCell != null) {
                        String email = getCellValue(emailCell);
                        String password = getCellValue(passwordCell);
                        if (!email.isEmpty() && !password.isEmpty()) {
                            users.put(email, password);
                            roles.put(email, "USER"); // Assigned as user
                            System.out.println("Loaded users: " + users);
                        }
                    }
                }
            }

            // Load Faculties
            Sheet facultySheet = workbook.getSheet("Faculties "); // Access for faculty sheet
            if (facultySheet != null) {
                for (Row row : facultySheet) { // For loop to load all available faculty members in faculty sheet
                    Cell emailCell = row.getCell(4); // Column E to retrieve emails
                    Cell passwordCell = row.getCell(7); // Column H to retrieve passwords
                    if (emailCell != null && passwordCell != null) {
                        String email = getCellValue(emailCell);
                        String password = getCellValue(passwordCell);
                        if (!email.isEmpty() && !password.isEmpty()) {
                            users.put(email, password);
                            roles.put(email, "ADMIN"); // Assigned as admin
                            System.out.println("Loaded users: " + users);
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Authenticate the user login
    public static String validateUser(String email, String password) {
        if (users.containsKey(email) && users.get(email).equals(password)) {
            return roles.get(email); // Returns the role "USER" or "ADMIN"
        }
        return null; // Incorrect credentials
    }

    // Method to read cell values as correct type
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return ""; // Handle null cells
        }
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue().trim(); // If cell is a string
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()); // If cell is a boolean
            case BLANK:
                return ""; // Handle blank cells
            case FORMULA:
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    return cell.getStringCellValue();
                }
            default:
                return "";
        }
    }
}