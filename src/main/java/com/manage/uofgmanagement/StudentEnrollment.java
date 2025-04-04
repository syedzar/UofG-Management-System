package com.manage.uofgmanagement;

/**
 * Represents a student enrolled in the system.
 * Each student has a unique ID and a name.
 */
public class StudentEnrollment {


    /**
     * Static counter for generating unique student IDs.
     * This ensures that each new student gets a distinct ID automatically.
     */
    private static int idCounter = 1;


    private int id;      // Unique student ID
    private String name; // Name of the student


    /**
     * Constructor that only requires the student's name.
     * The student ID is auto-generated using the static counter.
     *
     * @param name Name of the student
     */
    public StudentEnrollment(String name) {
        this.id = idCounter++; // Assign the current counter value as the ID, then increment
        this.name = name;
    }

    /**
     * Constructor that allows both an ID and a name to be set manually.
     * This can be useful when retrieving students from a database where IDs are already assigned.
     *
     * @param id   Manually assigned student ID
     * @param name Name of the student
     */
    public StudentEnrollment(int id, String name) {
        this.id = id;
        this.name = name;
    }


    /**
     * Gets the student's unique ID.
     *
     * @return Student ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the student's unique ID.
     * Should be used cautiously, as IDs are typically unique.
     *
     * @param id New student ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the student's name.
     *
     * @return Student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the student's name.
     *
     * @param name New student name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the student enrollment.
     * This method overrides toString() to return only the student's name.
     *
     * @return Student's name
     */
    @Override
    public String toString() {
        return name;
    }
}
