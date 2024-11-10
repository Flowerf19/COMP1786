package com.example.unversalyogajavaapplication.Entity;

public class Teacher {
    private String teacherID;
    private String name;
    private String specialty;
    private String contactInfo;

    public Teacher(String teacherID, String name, String specialty, String contactInfo) {
        setTeacherID(teacherID);
        setName(name);
        setSpecialty(specialty);
        setContactInfo(contactInfo);
    }

    // Getters and setters with validation

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        if (teacherID == null || teacherID.trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher ID is required and cannot be empty.");
        }
        this.teacherID = teacherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required and cannot be empty.");
        }
        if (!name.matches("[a-zA-Z\\s-]+")) {
            throw new IllegalArgumentException("Name should only contain letters, spaces, or hyphens.");
        }
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        if (specialty == null || specialty.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialty is required and cannot be empty.");
        }
        // Optionally, validate if the specialty is in a predefined list
        this.specialty = specialty;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        if (contactInfo == null || contactInfo.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact information is required and cannot be empty.");
        }
        if (!contactInfo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$") &&
                !contactInfo.matches("^\\+?[0-9]{10,15}$")) {
            throw new IllegalArgumentException("Contact information must be a valid email or phone number.");
        }
        this.contactInfo = contactInfo;
    }
}
