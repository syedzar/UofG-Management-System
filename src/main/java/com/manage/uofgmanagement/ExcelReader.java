package com.manage.uofgmanagement;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {
    private static final String FILE_PATH = "src/main/resources/UMS_Data.xlsx";
    private static final Map<String, String> users = new HashMap<>();
    private static final Map<String, String> roles = new HashMap<>();

    // Load user data from Excel
    public static void loadUserData() {
        try (FileInputStream file = new FileInputStream(new File(FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {

            // Load Students
            Sheet studentSheet = workbook.getSheet("Students ");
            if (studentSheet != null) {
                for (Row row : studentSheet) {
                    Cell emailCell = row.getCell(4); // Column A
                    Cell passwordCell = row.getCell(11); // Column L
                    if (emailCell != null && passwordCell != null) {
                        String email = getCellValue(emailCell);
                        String password = getCellValue(passwordCell);
                        if (!email.isEmpty() && !password.isEmpty()) {
                            users.put(email, password);
                            roles.put(email, "USER");
                            System.out.println("Loaded users: " + users);
                        }
                    }
                }
            }

            // Load Faculties
            Sheet facultySheet = workbook.getSheet("Faculties ");
            if (facultySheet != null) {
                for (Row row : facultySheet) {
                    Cell emailCell = row.getCell(4); // Column E
                    Cell passwordCell = row.getCell(7); // Column H
                    if (emailCell != null && passwordCell != null) {
                        String email = getCellValue(emailCell);
                        String password = getCellValue(passwordCell);
                        if (!email.isEmpty() && !password.isEmpty()) {
                            users.put(email, password);
                            roles.put(email, "ADMIN");
                            System.out.println("Loaded users: " + users);
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Authenticate user login
    public static String validateUser(String email, String password) {
        if (users.containsKey(email) && users.get(email).equals(password)) {
            return roles.get(email); // Return "USER" or "ADMIN"
        }
        return null; // Invalid credentials
    }

    // Helper method to read cell values properly
    private static String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue().trim();
            case NUMERIC: return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            default: return "";
        }
    }
}
