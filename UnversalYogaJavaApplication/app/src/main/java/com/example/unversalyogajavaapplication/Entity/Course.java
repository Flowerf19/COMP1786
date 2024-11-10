package com.example.unversalyogajavaapplication.Entity;

public class Course {
    private String courseID;
    private String dayOfWeek;
    private int capacity;
    private String timeOfCourse;
    private int duration;
    private double price;
    private String type;
    private String location;
    private long createAt;

    public Course(String courseID, String dayOfWeek, int capacity, String timeOfCourse, int duration, double price, String type, String location, long createAt) {
        if (courseID == null || courseID.isEmpty()) {
            throw new IllegalArgumentException("Course ID is required.");
        }
        if (dayOfWeek == null || dayOfWeek.isEmpty() || !isValidDayOfWeek(dayOfWeek)) {
            throw new IllegalArgumentException("Day of the week is required and must be a valid day.");
        }
        if (timeOfCourse == null || timeOfCourse.isEmpty() || !isValidTimeOfCourse(timeOfCourse)) {
            throw new IllegalArgumentException("Time of course is required and must be in the format HH:MM.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive integer.");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be a positive integer.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must be a non-negative number.");
        }
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type of class is required.");
        }
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Location is required.");
        }

        this.courseID = courseID;
        this.dayOfWeek = dayOfWeek;
        this.capacity = capacity;
        this.timeOfCourse = timeOfCourse;
        this.duration = duration;
        this.price = price;
        this.type = type;
        this.location = location;
        this.createAt = createAt;
    }

    // Helper method to validate day of the week
    private boolean isValidDayOfWeek(String dayOfWeek) {
        String[] validDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : validDays) {
            if (day.equalsIgnoreCase(dayOfWeek)) {
                return true;
            }
        }
        return false;
    }

    // Helper method to validate time of course
    private boolean isValidTimeOfCourse(String timeOfCourse) {
        return timeOfCourse.matches("^(?:[01]\\d|2[0-3]):[0-5]\\d$");
    }

    // Getters and setters
    public String getCourseID() { return courseID; }
    public void setCourseID(String courseID) { this.courseID = courseID; }
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getTimeOfCourse() { return timeOfCourse; }
    public void setTimeOfCourse(String timeOfCourse) { this.timeOfCourse = timeOfCourse; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public long getCreateAt() { return createAt; }
    public void setCreateAt(long createAt) { this.createAt = createAt; }
}
