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


    static void loadUserData() {
        try (FileInputStream file = new FileInputStream(new File(FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {

            // Load Students
            Sheet studentSheet = workbook.getSheet("Students ");
            if (studentSheet != null) {
                for (Row row : studentSheet) {
                    Cell emailCell = row.getCell(4);
                    Cell passwordCell = row.getCell(10);
                    if (emailCell != null && passwordCell != null) {
                        // Ensure the email and password are read correctly based on their type
                        String email = getCellValue(emailCell);
                        String password = getCellValue(passwordCell);
                        if (!email.isEmpty() && !password.isEmpty()) {
                            users.put(email, password);
                            roles.put(email, "USER");
                        }
                    }
                }
            }

            // Load Faculties
            Sheet facultySheet = workbook.getSheet("Faculties ");
            if (facultySheet != null) {
                for (Row row : facultySheet) {
                    Cell emailCell = row.getCell(5);
                    Cell passwordCell = row.getCell(6);
                    if (emailCell != null && passwordCell != null) {
                        // Ensure the email and password are read correctly based on their type
                        String email = getCellValue(emailCell);
                        String password = getCellValue(passwordCell);
                        if (!email.isEmpty() && !password.isEmpty()) {
                            users.put(email, password);
                            roles.put(email, "ADMIN");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get the value of a cell safely
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }


    public static String validateUser(String username, String password) {
        try (FileInputStream fis = new FileInputStream(new File(FILE_PATH));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell userCell = row.getCell(0); // Assuming username is in column A
                Cell passCell = row.getCell(11); // Assuming password is in column B
                Cell roleCell = row.getCell(2); // Assuming role is in column C

                if (userCell != null && passCell != null && roleCell != null) {
                    String storedUser = userCell.getStringCellValue().trim();
                    String storedPass = passCell.getStringCellValue().trim();
                    String storedRole = roleCell.getStringCellValue().trim();

                    if (storedUser.equals(username) && storedPass.equals(password)) {
                        return storedRole; // Return role if credentials match
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if credentials are invalid
    }
}
