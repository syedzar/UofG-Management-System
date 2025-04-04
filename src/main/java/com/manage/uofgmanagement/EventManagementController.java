package com.manage.uofgmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EventManagementController implements Initializable {

    // FXML fields for the TableView and its columns
    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, String> eventNameColumn;

    @FXML
    private TableColumn<Event, String> eventCodeColumn;

    @FXML
    private TableColumn<Event, String> descriptionColumn;

    @FXML
    private TableColumn<Event, String> dateTimeColumn;

    @FXML
    private TableColumn<Event, String> locationColumn;

    @FXML
    private TableColumn<Event, Integer> capacityColumn;

    // Observable list for event data
    private ObservableList<Event> eventList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind the TableView columns to the Event class properties
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        eventCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        // Add sample event data
        eventList.add(new Event("Tech Conference", "EV001", "Annual tech conference", "2025-05-10 09:00", "Auditorium", 200));
        eventList.add(new Event("AI Workshop", "EV002", "Hands-on workshop on AI", "2025-06-15 13:00", "Lab 1", 50));
        eventList.add(new Event("Networking Night", "EV003", "Evening networking event", "2025-07-20 18:00", "Lobby", 150));

        // Set the event list to the TableView
        eventTable.setItems(eventList);
    }

    @FXML
    private void handleAddEvent(ActionEvent event) {
        System.out.println("Add Event button clicked!");
        // TODO: Implement logic to open a form/dialog to add a new event.
    }

    @FXML
    private void handleEditEvent(ActionEvent event) {
        System.out.println("Edit Event button clicked!");
        // TODO: Implement logic to open a form/dialog to edit the selected event.
    }

    @FXML
    private void handleDeleteEvent(ActionEvent event) {
        System.out.println("Delete Event button clicked!");
        // Remove the selected event from the list, if any.
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            eventList.remove(selectedEvent);
        } else {
            System.out.println("No event selected for deletion.");
        }
    }

    @FXML
    private void handleViewEventDetails(ActionEvent event) {
        System.out.println("View Event Details button clicked!");
        // TODO: Implement logic to display details of the selected event.
    }

    @FXML
    private void handleManageRegistrations(ActionEvent event) {
        System.out.println("Manage Registrations button clicked!");
        // TODO: Implement logic to manage registrations for the selected event.
    }

    @FXML
    private void handleEventReports(ActionEvent event) {
        System.out.println("Event Reports button clicked!");
        // TODO: Implement logic to generate and display event reports.
    }

    @FXML
    private void handleAssignOrganizers(ActionEvent event) {
        System.out.println("Assign Organizers button clicked!");
        // TODO: Implement logic to assign organizers to the event.
    }

    // Inner model class for Event. Alternatively, this class can be placed in its own file.
    public static class Event {
        private String name;
        private String code;
        private String description;
        private String dateTime;
        private String location;
        private int capacity;

        public Event(String name, String code, String description, String dateTime, String location, int capacity) {
            this.name = name;
            this.code = code;
            this.description = description;
            this.dateTime = dateTime;
            this.location = location;
            this.capacity = capacity;
        }

        // Getters and setters for all properties
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getDateTime() {
            return dateTime;
        }
        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }
        public String getLocation() {
            return location;
        }
        public void setLocation(String location) {
            this.location = location;
        }
        public int getCapacity() {
            return capacity;
        }
        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }
    }
}
