package com.example.flowerftodoapplication.db.entity;

import java.util.Date;

public class Task {
    private int id; // Add an ID field
    private String title;
    private String description;
    private Date dateCreated;
    private Date dateFinished;
    private Date dateRemaining;

    // Constructor
    public Task(int id, String title, String description, Date dateCreated, Date dateFinished, Date dateRemaining) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateFinished = dateFinished;
        this.dateRemaining = dateRemaining;
    }

    // Overloaded constructor for creating a new task without an ID
    public Task(String title, String description, Date dateCreated, Date dateFinished, Date dateRemaining) {
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateFinished = dateFinished;
        this.dateRemaining = dateRemaining;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(Date dateFinished) {
        this.dateFinished = dateFinished;
    }

    public Date getDateRemaining() {
        return dateRemaining;
    }

    public void setDateRemaining(Date dateRemaining) {
        this.dateRemaining = dateRemaining;
    }
}
