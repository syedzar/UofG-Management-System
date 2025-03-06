package com.manage.uofgmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FacultyManagementAdminController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Button addFacultyButton;
    @FXML private TableView<FacultyMemberManagementModel> facultyTable;
    @FXML private TableColumn<FacultyMemberManagementModel, String> facultyColumn;

    private final ObservableList<FacultyMemberManagementModel> facultyList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        facultyTable.setItems(facultyList);

        addFacultyButton.setOnAction(event -> addFacultyMember());
    }

    private void addFacultyMember() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and Password cannot be empty.");
            return;
        }

        FacultyMemberManagementModel faculty = new FacultyMemberManagementModel(username, password); // Fixed class name
        facultyList.add(faculty);
        usernameField.clear();
        passwordField.clear();
    }
}
