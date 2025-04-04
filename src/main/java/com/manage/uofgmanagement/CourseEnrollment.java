package com.manage.uofgmanagement;

/**
 * Represents a course enrollment entry.
 * This class holds details about a course, such as its code, name, section,
 * capacity, schedule, and assigned teacher.
 */
public class CourseEnrollment {


    private int id;               // Unique identifier for the course enrollment
    private String courseCode;    // Course code (e.g., "CS101")
    private String courseName;    // Name of the course (e.g., "Introduction to Computer Science")
    private String subjectCode;   // Subject code or faculty identifier
    private String sectionNumber; // Section number of the course (e.g., "A1")
    private int capacity;         // Maximum number of students allowed in the course
    private String lectureTime;   // Scheduled lecture time (e.g., "Mon/Wed 10:00 AM - 11:30 AM")
    private String finalDate;     // Date of the final exam or final assessment
    private String location;      // Physical or virtual location of the course
    private String teacherName;   // Name of the assigned professor or instructor


    /**
     * Constructor to initialize a CourseEnrollment object with all fields.
     *
     * @param id            Unique ID for the course
     * @param courseCode    Course code (e.g., "CS101")
     * @param courseName    Course name (e.g., "Data Structures")
     * @param subjectCode   Subject code/faculty identifier
     * @param sectionNumber Section number (e.g., "B2")
     * @param capacity      Maximum number of students allowed
     * @param lectureTime   Schedule of lectures
     * @param finalDate     Final exam date
     * @param location      Location where the course takes place
     * @param teacherName   Name of the professor
     */
    public CourseEnrollment(int id, String courseCode, String courseName, String subjectCode, String sectionNumber,
                            int capacity, String lectureTime, String finalDate, String location, String teacherName) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.subjectCode = subjectCode;
        this.sectionNumber = sectionNumber;
        this.capacity = capacity;
        this.lectureTime = lectureTime;
        this.finalDate = finalDate;
        this.location = location;
        this.teacherName = teacherName;
    }


    /**
     * Gets the unique course ID.
     *
     * @return Course ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique course ID.
     *
     * @param id New course ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the course code.
     *
     * @return Course code (e.g., "CS101")
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Sets the course code.
     *
     * @param courseCode New course code
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Gets the course name.
     *
     * @return Course name (e.g., "Data Structures")
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the course name.
     *
     * @param courseName New course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the subject code.
     *
     * @return Subject code or faculty identifier
     */
    public String getSubjectCode() {
        return subjectCode;
    }

    /**
     * Sets the subject code.
     *
     * @param subjectCode New subject code
     */
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    /**
     * Gets the section number.
     *
     * @return Section number (e.g., "A1")
     */
    public String getSectionNumber() {
        return sectionNumber;
    }

    /**
     * Sets the section number.
     *
     * @param sectionNumber New section number
     */
    public void setSectionNumber(String sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    /**
     * Gets the course capacity.
     *
     * @return Maximum number of students allowed
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the course capacity.
     *
     * @param capacity New capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the lecture time schedule.
     *
     * @return Lecture time (e.g., "Mon/Wed 10:00 AM - 11:30 AM")
     */
    public String getLectureTime() {
        return lectureTime;
    }

    /**
     * Sets the lecture time schedule.
     *
     * @param lectureTime New lecture time
     */
    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }

    /**
     * Gets the final exam date.
     *
     * @return Final exam date
     */
    public String getFinalDate() {
        return finalDate;
    }

    /**
     * Sets the final exam date.
     *
     * @param finalDate New final exam date
     */
    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    /**
     * Gets the course location.
     *
     * @return Location of the course
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the course location.
     *
     * @param location New location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the teacher's name.
     *
     * @return Name of the assigned professor
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * Sets the teacher's name.
     *
     * @param teacherName New professor's name
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


    /**
     * Returns a string representation of the course enrollment object.
     * The format is: "Course Name (Course Code - Section Number)".
     *
     * @return Readable course summary
     */
    @Override
    public String toString() {
        return courseName + " (" + courseCode + " - " + sectionNumber + ")";
    }
}
