# 🎓 U of G Student Management System

A JavaFX-based student management application developed as a university project to simulate an academic management system for students and administrators.

The application provides separate functionality depending on whether the user logs in as a regular user or an administrator. Users can access information such as their courses and enrolment details, while administrators have additional tools for managing students, courses, faculty, grades, and other academic information.

---

## 📖 Overview

The U of G Student Management System was created to provide a centralized graphical interface for viewing and managing university-related information.

The application uses **Java and JavaFX**, with **FXML** files defining the different interfaces and Java controller classes handling the application's functionality. Academic information can be loaded from external data sources and displayed or modified through the appropriate management screens.

The system includes separate dashboards and functionality for regular users and administrators.

---

## ✨ Features

### 👨‍🎓 Student / User

- User login
- Student dashboard
- View enrolled courses
- View course and subject information
- View enrolment information
- View and edit profile information
- Navigate between different student services

### 🛠️ Administrator

- Administrator login and dashboard
- Student management
- Course management
- Faculty management
- Enrolment management
- Subject management
- Add and edit course information
- Modify student academic information and grades
- Manage university-related records

### 💻 Additional Functionality

- Separate user and administrator interfaces
- JavaFX graphical user interface
- FXML-based application views
- External academic data handling
- Local data storage
- Event management functionality

---

## 🧰 Technologies Used

- **Java**
- **JavaFX**
- **FXML**
- **Maven**
- **IntelliJ IDEA**
- **Git & GitHub**

---

## 📂 Project Structure

The application uses a controller-based JavaFX structure. FXML files define the user interfaces while Java controller classes contain the logic for each part of the application.

```text
src/main/
│
├── java/
│   └── com/manage/uofgmanagement/
│       ├── LoginApp.java
│       ├── LoginController.java
│       ├── AdminDashboardController.java
│       ├── UserDashboardController.java
│       ├── StudentManagementController.java
│       ├── FacultyManagementController.java
│       ├── EnrollmentManagementControllerAdmin.java
│       ├── EnrollmentManagementControllerUser.java
│       ├── AddEditCourseController.java
│       ├── CourseDashboardController.java
│       ├── EventManagementController.java
│       ├── SubjectManagementController.java
│       └── ...
│
└── resources/
    ├── Login.fxml
    ├── AdminDashboard.fxml
    ├── UserDashboard.fxml
    ├── StudentManagementAdmin.fxml
    ├── StudentManagementUser.fxml
    ├── FacultyManagement.fxml
    ├── EnrollmentManagementAdmin.fxml
    ├── EnrollmentManagementUser.fxml
    ├── AddEditCourse.fxml
    ├── CourseDashboard.fxml
    ├── EventManagement.fxml
    ├── SubjectManagement.fxml
    └── ...
```

---

## ⚙️ How It Works

When the application starts, the user is presented with a login screen. Depending on the account type, the application directs the user to either the regular user dashboard or the administrator dashboard.

```text
                       Login
                         │
                 ┌───────┴───────┐
                 │               │
              Student          Admin
                 │               │
          User Dashboard    Admin Dashboard
                 │               │
          ┌──────┼──────┐   ┌────┼────────┐
          │      │      │   │    │        │
       Courses Profile  │ Students Courses Faculty
                        │
                    Enrolment
```

Each dashboard provides access to the features appropriate for that type of user. This keeps administrator management functionality separate from the functionality available to regular users.

---

## 🚀 Running the Project

### Requirements

Before running the application, make sure you have:

- Java / JDK installed
- JavaFX configured
- Maven installed, or use the included Maven wrapper
- An IDE such as IntelliJ IDEA

### 1. Clone the Repository

```bash
git clone https://github.com/syedzar/UofG-Management-System.git
cd UofG-Management-System
```

### 2. Build with Maven

On Windows using the included Maven wrapper:

```bash
mvnw.cmd clean install
```

Or, if Maven is installed globally:

```bash
mvn clean install
```

The project can also be opened directly in **IntelliJ IDEA**. Allow Maven to load the required dependencies before running the application.

---

## 🎯 Project Purpose

The purpose of this project was to gain experience developing a larger object-oriented application rather than an isolated programming assignment.

Through the development of the system, the project provided experience with:

- Object-oriented programming in Java
- Building graphical interfaces with JavaFX
- Connecting FXML views to Java controllers
- Managing multiple application screens
- Separating administrator and regular user functionality
- Working with academic records and application data
- Organizing a larger multi-file software project
- Collaborating on software development
- Using Git and GitHub for version control

---

## 🔮 Future Improvements

The project is still under development. Some potential improvements include:

- Improved authentication and account security
- More robust database integration
- Additional input validation and error handling
- Improved UI design and navigation
- Additional student and administrator functionality
- Improved data persistence
- Automated testing

---

## 📸 Screenshots

Screenshots of the application interface will be added as development continues.

<!--
Example for adding screenshots later:

### Login

![Login Screen](screenshots/login.png)

### Student Dashboard

![Student Dashboard](screenshots/student-dashboard.png)

### Admin Dashboard

![Admin Dashboard](screenshots/admin-dashboard.png)
-->

---

## 📝 Disclaimer

This is a student software project created for educational purposes. It is **not an official University of Guelph application** and is not affiliated with or endorsed by the University of Guelph.
