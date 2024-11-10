package com.example.unversalyogajavaapplication.Entity;

public class ClassInstance {
    private String classID;
    private String courseID;
    private String date;
    private String teacherID;
    private String comment;
    private long createAt;

    public ClassInstance(String classID, String courseID, String date, String teacherID, String comment, long createAt) {
        this.classID = classID;
        this.courseID = courseID;
        this.date = date;
        this.teacherID = teacherID;
        this.comment = comment;
        this.createAt = createAt;
    }

    // Getters and setters
    public String getClassID() { return classID; }
    public void setClassID(String classID) { this.classID = classID; }
    public String getCourseID() { return courseID; }
    public void setCourseID(String courseID) { this.courseID = courseID; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTeacherID() { return teacherID; }
    public void setTeacherID(String teacherID) { this.teacherID = teacherID; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public long getCreateAt() { return createAt; }
    public void setCreateAt(long createAt) { this.createAt = createAt; }
}
