package com.example.flowerftodoapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.flowerftodoapplication.db.entity.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flowerf_todo.db";
    private static final int DATABASE_VERSION = 1;

    // Task table
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE_CREATED = "date_created";
    private static final String COLUMN_DATE_FINISHED = "date_finished";
    private static final String COLUMN_DATE_REMAINING = "date_remaining";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DATE_CREATED + " INTEGER,"
                + COLUMN_DATE_FINISHED + " INTEGER,"
                + COLUMN_DATE_REMAINING + " INTEGER" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // Create a new task
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, task.getTitle());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_DATE_CREATED, task.getDateCreated().getTime());
        values.put(COLUMN_DATE_FINISHED, task.getDateFinished() != null ? task.getDateFinished().getTime() : null);
        values.put(COLUMN_DATE_REMAINING, task.getDateRemaining().getTime());

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // Read a task by ID
    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS, new String[]{
                        COLUMN_ID,
                        COLUMN_TITLE,
                        COLUMN_DESCRIPTION,
                        COLUMN_DATE_CREATED,
                        COLUMN_DATE_FINISHED,
                        COLUMN_DATE_REMAINING},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Task task = new Task(
                    cursor.getInt(0), // Assuming you have an ID in your Task entity
                    cursor.getString(1),
                    cursor.getString(2),
                    new Date(cursor.getLong(3)),
                    cursor.getLong(4) != 0 ? new Date(cursor.getLong(4)) : null,
                    new Date(cursor.getLong(5))
            );
            cursor.close();
            return task;
        }
        return null; // Return null if the task does not exist
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(
                        cursor.getInt(0), // ID
                        cursor.getString(1),
                        cursor.getString(2),
                        new Date(cursor.getLong(3)),
                        cursor.getLong(4) != 0 ? new Date(cursor.getLong(4)) : null,
                        new Date(cursor.getLong(5))
                );
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }

    // Update a task
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, task.getTitle());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_DATE_CREATED, task.getDateCreated().getTime());
        values.put(COLUMN_DATE_FINISHED, task.getDateFinished() != null ? task.getDateFinished().getTime() : null);
        values.put(COLUMN_DATE_REMAINING, task.getDateRemaining().getTime());

        return db.update(TABLE_TASKS, values, COLUMN_ID + "=?", new String[]{String.valueOf(task.getId())});
    }

    // Delete a task
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
