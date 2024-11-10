package com.example.unversalyogajavaapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unversalyogajavaapplication.Entity.ClassInstance;

import java.util.ArrayList;

public class AddClassActivity extends AppCompatActivity {

    private Spinner spinnerCourse, spinnerTeacher;
    private EditText editTextDate, editTextComment;
    private Button buttonSaveClass;
    private DatabaseHelper DB;

    private ArrayList<String> courseList, teacherList;
    private ArrayList<String> courseIDs, teacherIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        spinnerCourse = findViewById(R.id.spinnerCourse);
        spinnerTeacher = findViewById(R.id.spinnerTeacher);
        editTextDate = findViewById(R.id.editTextDate);
        editTextComment = findViewById(R.id.editTextComment);
        buttonSaveClass = findViewById(R.id.buttonSaveClass);

        DB = new DatabaseHelper(AddClassActivity.this);
        courseList = new ArrayList<>();
        teacherList = new ArrayList<>();
        courseIDs = new ArrayList<>();
        teacherIDs = new ArrayList<>();

        // Populate course and teacher spinners
        loadCourseData();
        loadTeacherData();

        // Save class on button click
        buttonSaveClass.setOnClickListener(view -> saveClass());
    }

    private void loadCourseData() {
        // Query the database to get all courses
        Cursor cursor = DB.readAllCourse();
        if (cursor != null && cursor.moveToFirst()) {
            // Log all column names for debugging
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Database", "Column Name: " + columnName);
            }

            int courseIDIndex = cursor.getColumnIndex("CourseID");
            if (courseIDIndex != -1) {
                do {
                    String courseID = cursor.getString(courseIDIndex);
                    // Use CourseID as the course name in the spinner
                    courseIDs.add(courseID);
                    courseList.add(courseID); // Adding CourseID as the name to display
                } while (cursor.moveToNext());
            } else {
                Log.e("Database", "'CourseID' column not found");
            }
            cursor.close();

            // Set up the adapter for the course spinner
            ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseList);
            courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCourse.setAdapter(courseAdapter);
        }
    }

    private void loadTeacherData() {
        // Query the database to get all teachers
        Cursor cursor = DB.readAllInstructor();
        if (cursor != null && cursor.moveToFirst()) {
            // Log all column names for debugging
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Database", "Column Name: " + columnName);
            }

            int teacherIDIndex = cursor.getColumnIndex("TeacherID");
            int teacherNameIndex = cursor.getColumnIndex("Name");

            if (teacherIDIndex != -1 && teacherNameIndex != -1) {
                do {
                    String teacherID = cursor.getString(teacherIDIndex);
                    String teacherName = cursor.getString(teacherNameIndex);  // Assume Name is the teacher's name
                    teacherIDs.add(teacherID);
                    teacherList.add(teacherName);
                } while (cursor.moveToNext());
            } else {
                Log.e("Database", "'TeacherID' or 'Name' column not found");
            }
            cursor.close();

            // Set up the adapter for the teacher spinner
            ArrayAdapter<String> teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teacherList);
            teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTeacher.setAdapter(teacherAdapter);
        }
    }

    private void saveClass() {
        // Get selected course and teacher IDs
        String selectedCourseID = courseIDs.get(spinnerCourse.getSelectedItemPosition());
        String selectedTeacherID = teacherIDs.get(spinnerTeacher.getSelectedItemPosition());

        // Get input for date and comment
        String date = editTextDate.getText().toString().trim();
        String comment = editTextComment.getText().toString().trim();

        // Ensure that all fields are filled
        if (date.isEmpty() || comment.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate a new ClassID (this can be more sophisticated)
        String classID = "Class_" + System.currentTimeMillis();

        // Create a ClassInstance object
        ClassInstance classInstance = new ClassInstance(classID, selectedCourseID, date, selectedTeacherID, comment, System.currentTimeMillis());

        // Add the new class to the database
        DB.addClassInstance(classInstance);

        // Display success message
        Toast.makeText(this, "Class added successfully", Toast.LENGTH_SHORT).show();

        // Optionally, return to the previous screen
        Intent intent = new Intent(AddClassActivity.this, ClassActivity.class);
        startActivity(intent);
        finish();
    }
}
