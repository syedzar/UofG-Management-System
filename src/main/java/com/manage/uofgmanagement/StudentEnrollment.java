package com.manage.uofgmanagement;

public class StudentEnrollment {
    private static int idCounter = 1; // Static counter for generating unique IDs
    private int id;
    private String name;

    // Constructor that only requires name; ID is auto-generated
    public StudentEnrollment(String name) {
        this.id = idCounter++; // Increment the counter and assign it as the ID
        this.name = name;
    }

    // Constructor with both ID and name (if needed for manual assignments)
    public StudentEnrollment(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
