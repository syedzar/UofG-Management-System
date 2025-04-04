package com.manage.uofgmanagement;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class FacultyManagementController {
    @FXML private Button editFacultyButton;
    @FXML private Button deleteFacultyButton;
    @FXML private Button viewProfileButton;
    @FXML private Button addFacultyButton;

    @FXML
    private ListView<String> facultyListView;

    private static final String DB_URL = "jdbc:sqlite:src/main/resources/faculty.db"; // SQLite database file

    @FXML
    public void initialize() {
        loadFacultyData();
        loadFacultyList();
    }

    private Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createTableIfNotExists() { // Creates the faculties table in case doesn't exist
        String sql = "CREATE TABLE IF NOT EXISTS Faculties (" +
                "faculty_id TEXT PRIMARY KEY, " +
                "name TEXT, " +
                "degree TEXT, " +
                "research_interest TEXT, " +
                "email TEXT, " +
                "office_location TEXT, " +
                "courses_offered TEXT, " +
                "password TEXT)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFacultyData() { // Loading all exist faculty members in the database
        facultyListView.getItems().clear();
        String sql = "SELECT faculty_id, name FROM Faculties"; // Format of "ID(FXXXX) - Name"

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String facultyInfo = rs.getString("faculty_id") + " - " + rs.getString("name");
                facultyListView.getItems().add(facultyInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFacultyList() { // Displays all the previously loaded faculty member data
        facultyListView.getItems().clear();
        try (Connection conn = connect()) {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT name FROM Faculties")) {
                while (rs.next()) {
                    facultyListView.getItems().add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddFaculty() { // Function to add a faculty member
        Stage addFacultyStage = new Stage();
        VBox vbox = new VBox(10);

        // Corresponding text fields for the fields of a faculty member
        TextField nameField = new TextField();
        TextField degreeField = new TextField();
        TextField researchField = new TextField();
        TextField emailField = new TextField();
        TextField officeField = new TextField();
        TextField coursesField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button submitButton = new Button("Add Faculty");

        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String degree = degreeField.getText();
            String research = researchField.getText();
            String email = emailField.getText();
            String office = officeField.getText();
            String courses = coursesField.getText();
            String password = passwordField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) { // Ensures all fields are filled
                showAlert(Alert.AlertType.ERROR, "All fields are required!");
            } else {
                addFacultyToDatabase(name, degree, research, email, office, courses, password);
                loadFacultyList();
                addFacultyStage.close();
            }
        });

        vbox.getChildren().addAll(new Label("Name:"), nameField, new Label("Degree:"), degreeField,
                new Label("Research Interest:"), researchField, new Label("Email:"), emailField,
                new Label("Office Location:"), officeField, new Label("Courses Offered:"), coursesField,
                new Label("Password:"), passwordField, submitButton);
        addFacultyStage.setScene(new Scene(vbox, 300, 500));
        addFacultyStage.setTitle("Add Faculty");
        addFacultyStage.show();
    }

    // Function that saves the newly created faculty member into the actual SQLite database for future access
    private void addFacultyToDatabase(String name, String degree, String research, String email, String office, String courses, String password) {
        try (Connection conn = connect()) {
            try (Statement stmt = conn.createStatement()) {

                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM Faculties");
                rs.next();
                int count = rs.getInt("count") + 1;
                String facultyId = String.format("F%04d", count);

                //Inserts the member accordingly to the fields
                String sql = "INSERT INTO Faculties (faculty_id, name, degree, research_interest, email, office_location, courses_offered, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, facultyId);
                    pstmt.setString(2, name);
                    pstmt.setString(3, degree);
                    pstmt.setString(4, research);
                    pstmt.setString(5, email);
                    pstmt.setString(6, office);
                    pstmt.setString(7, courses);
                    pstmt.setString(8, password);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditFaculty() { // Function that allows you to select a faculty member and edit their info
        String selectedFaculty = facultyListView.getSelectionModel().getSelectedItem();
        if (selectedFaculty == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a faculty to edit.");
            return;
        }

        String selectedID = selectedFaculty.split(" - ")[0]; // Extract Faculty ID

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Faculties WHERE faculty_id = ?")) {
            stmt.setString(1, selectedID);
            ResultSet rs = stmt.executeQuery();
            loadFacultyData();

            if (rs.next()) {
                Stage editStage = new Stage();
                VBox vbox = new VBox(10);

                // All the necessary text fields to fill out
                TextField nameField = new TextField(rs.getString("name"));
                TextField degreeField = new TextField(rs.getString("degree"));
                TextField researchField = new TextField(rs.getString("research_interest"));
                TextField emailField = new TextField(rs.getString("email"));
                TextField officeField = new TextField(rs.getString("office_location"));
                TextField coursesField = new TextField(rs.getString("courses_offered"));
                PasswordField passwordField = new PasswordField();
                passwordField.setText(rs.getString("password"));

                Button saveButton = new Button("Save Changes"); // Updates the new info into the faculty member
                saveButton.setOnAction(event -> {
                    updateFaculty(selectedID, nameField.getText(), degreeField.getText(), researchField.getText(),
                            emailField.getText(), officeField.getText(), coursesField.getText(), passwordField.getText());
                    loadFacultyData();
                    editStage.close();
                });

                vbox.getChildren().addAll(new Label("Name:"), nameField, new Label("Degree:"), degreeField,
                        new Label("Research Interest:"), researchField, new Label("Email:"), emailField,
                        new Label("Office Location:"), officeField, new Label("Courses Offered:"), coursesField,
                        new Label("Password:"), passwordField, saveButton);

                editStage.setScene(new Scene(vbox, 350, 500));
                editStage.setTitle("Edit Faculty");
                editStage.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that updates the newly edited faculty data into the selected faculty member
    @FXML
    private void updateFaculty(String id, String name, String degree, String research, String email, String office, String courses, String password) {
        String sql = "UPDATE Faculties SET name = ?, degree = ?, research_interest = ?, email = ?, office_location = ?, courses_offered = ?, password = ? WHERE faculty_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(2, name);
            stmt.setString(3, degree);
            stmt.setString(4, research);
            stmt.setString(5, email);
            stmt.setString(6, office);
            stmt.setString(7, courses);
            stmt.setString(8, password);
            stmt.setString(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteFaculty() { // Function that allows you to select and delete a faculty member
        String selectedFaculty = facultyListView.getSelectionModel().getSelectedItem();
        if (selectedFaculty == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a faculty to delete.");
            return;
        }

        String selectedID = selectedFaculty.split(" - ")[0];
        String sql = "DELETE FROM Faculties WHERE faculty_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, selectedID);
            stmt.executeUpdate();
            loadFacultyData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewProfile() { // Function that allows you to select and view the info of a faculty member
        String selectedFaculty = facultyListView.getSelectionModel().getSelectedItem();
        if (selectedFaculty == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a faculty.");
            return;
        }

        String selectedID = selectedFaculty.split(" - ")[0];
        String sql = "SELECT * FROM Faculties WHERE faculty_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, selectedID);
            ResultSet rs = stmt.executeQuery();
            loadFacultyData();

            if (rs.next()) { // Displaying the selected information of the faculty member
                showAlert(Alert.AlertType.INFORMATION,
                        "ID: " + rs.getString("faculty_id") +
                                "\nName: " + rs.getString("name") +
                                "\nEmail: " + rs.getString("email") +
                                "\nDegree: " + rs.getString("degree") +
                                "\nResearch Interest: " + rs.getString("research_interest"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Faculty Management");
        alert.setContentText(message);
        alert.showAndWait();
    }

}