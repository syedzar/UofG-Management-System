package com.manage.uofgmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.manage.uofgmanagement.ExcelReader.getCellValue;

public class EditProfile implements Initializable {
    // Preserve your original static email field
    private static String userEmail;

    @FXML private Label nameLabel;
    @FXML private Label idLabel;
    @FXML private Label addressLabel;
    @FXML private Label phoneLabel;
    @FXML private Label emailLabel;
    @FXML private ImageView profileImageView;
    @FXML private ListView<String> coursesListView;

    @FXML private Label academicLevelLabel;
    @FXML private Label currentSemesterLabel;
    @FXML private Label thesisTitleLabel;
    @FXML private Label progressLabel;

    // Keep your original static email methods
    public static void setEmail(String emailValue) {
        userEmail = emailValue;
    }

    public static String getEmail() {
        return userEmail;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUserDataFromExcel();
    }

    private void loadUserDataFromExcel() {
        System.out.println("flkjdsaflkjsdf\nfldsfj");
        try (FileInputStream file = new FileInputStream(new File("src/main/resources/UMS_Data.xlsx"));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet studentSheet = workbook.getSheet("Students "); // The sheet had a space afterward :/


            if (studentSheet != null) {

                for (Row row : studentSheet) {
                    if (row.getRowNum() == 0) continue;
                    String email = getCellValue(row.getCell(4)).trim();
                    System.out.println(userEmail);


                    if (email.equals(userEmail)) {
                        // Populate data
                        nameLabel.setText(getCellValue(row.getCell(1)));
                        idLabel.setText(getCellValue(row.getCell(0)));
                        addressLabel.setText(getCellValue(row.getCell(2)));
                        phoneLabel.setText(getCellValue(row.getCell(3)));
                        emailLabel.setText(email);

                        // Academic Information
                        academicLevelLabel.setText(getCellValue(row.getCell(5)));
                        currentSemesterLabel.setText(getCellValue(row.getCell(6)));

                        String thesisTitle = getCellValue(row.getCell(9));
                        thesisTitleLabel.setText(
                                thesisTitle.matches("[-_]+") ? "N/A" : thesisTitle
                        );

                        String progress = getCellValue(row.getCell(10));
                        progressLabel.setText(progress.isEmpty() ? "0%" : progress);

                        String coursesString = getCellValue(row.getCell(8));
                        if (coursesString != null && !coursesString.isEmpty()) {
                            ObservableList<String> courses = FXCollections.observableArrayList(
                                    Arrays.stream(coursesString.split(","))
                                            .map(String::trim)
                                            .filter(s -> !s.isEmpty())
                                            .collect(Collectors.toList())
                            );
                            coursesListView.setItems(courses);
                        } else {
                            coursesListView.setItems(FXCollections.observableArrayList("No courses enrolled"));
                        }

                        // Handle profile picture with default fallback
                        String imagePath = getCellValue(row.getCell(7));
                        if (imagePath == null || imagePath.isEmpty() || imagePath.equalsIgnoreCase("default")) {
                            loadDefaultImage();
                        } else {
                            profileImageView.setImage(new Image(new File(imagePath).toURI().toString()));
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDefaultImage() {
        try {
            InputStream stream = getClass().getResourceAsStream("/Default_pfp.svg.png");
            profileImageView.setImage(new Image(stream));
        } catch (Exception e) {
            System.out.println("Error loading default image: " + e.getMessage());
        }
    }

    // Keep your original constructors
    public EditProfile(String password, String email) {
        // Your original constructor logic
    }

    public EditProfile() {
        // Default constructor
    }
}