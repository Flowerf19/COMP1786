package com.example.unversalyogajavaapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unversalyogajavaapplication.Entity.Teacher;

public class UpdateInstructorActivity extends AppCompatActivity {

    private EditText editTextName, editTextSpecialty, editTextContactInfo;
    private Button buttonUpdateInstructor;
    private DatabaseHelper DB;
    private String teacherID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_instructor);

        editTextName = findViewById(R.id.editTextName);
        editTextSpecialty = findViewById(R.id.editTextSpecialty);
        editTextContactInfo = findViewById(R.id.editTextContactInfo);
        buttonUpdateInstructor = findViewById(R.id.buttonUpdateInstructor);

        DB = new DatabaseHelper(UpdateInstructorActivity.this);

        // Get the teacher ID from the intent
        Intent intent = getIntent();
        teacherID = intent.getStringExtra("TeacherID");

        // Load existing instructor data
        loadInstructorData();

        // Update instructor on button click
        buttonUpdateInstructor.setOnClickListener(view -> updateInstructor());
    }

    private void loadInstructorData() {
        // Query the database to get the instructor details
        Cursor cursor = DB.getTeacherByID(teacherID);
        if (cursor != null && cursor.moveToFirst()) {
            // Get the column indices
            int nameIndex = cursor.getColumnIndex("Name");
            int specialtyIndex = cursor.getColumnIndex("Specialty");
            int contactInfoIndex = cursor.getColumnIndex("ContactInfo");

            // Check if all column indices are valid
            if (nameIndex != -1 && specialtyIndex != -1 && contactInfoIndex != -1) {
                // Get the instructor details
                String name = cursor.getString(nameIndex);
                String specialty = cursor.getString(specialtyIndex);
                String contactInfo = cursor.getString(contactInfoIndex);

                // Set the instructor details in the UI
                editTextName.setText(name);
                editTextSpecialty.setText(specialty);
                editTextContactInfo.setText(contactInfo);
            } else {
                Log.e("Database", "One or more columns not found");
            }

            cursor.close();
        }
    }

    private void updateInstructor() {
        // Get input for name, specialty, and contact info
        String name = editTextName.getText().toString().trim();
        String specialty = editTextSpecialty.getText().toString().trim();
        String contactInfo = editTextContactInfo.getText().toString().trim();

        // Ensure that all fields are filled
        if (name.isEmpty() || specialty.isEmpty() || contactInfo.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Teacher object
        Teacher teacher = new Teacher(teacherID, name, specialty, contactInfo);

        // Update the instructor in the database
        DB.updateTeacher(teacher);

        // Display success message
        Toast.makeText(this, "Instructor updated successfully", Toast.LENGTH_SHORT).show();

        // Optionally, return to the previous screen
        Intent returnIntent = new Intent(UpdateInstructorActivity.this, InstructorActivity.class);
        startActivity(returnIntent);
        finish();
    }
}
