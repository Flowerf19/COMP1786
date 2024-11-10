package com.example.unversalyogajavaapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unversalyogajavaapplication.Entity.Teacher;

public class AddInstructorActivity extends AppCompatActivity {
    EditText TeacherID, Name, ContactInfo;
    Spinner Specialty;
    Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);

        // Initialize EditText fields
        TeacherID = findViewById(R.id.TeacherID);
        Name = findViewById(R.id.Name);
        ContactInfo = findViewById(R.id.ContactInfo);

        // Initialize Spinner
        Specialty = findViewById(R.id.Specialty); // Ensure this ID is correct and matches the XML

        ArrayAdapter<CharSequence> specialtyAdapter = ArrayAdapter.createFromResource(this,
                R.array.specialty_types, android.R.layout.simple_spinner_item);
        specialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Specialty.setAdapter(specialtyAdapter);

        // Initialize the save button
        button_save = findViewById(R.id.button_save);

        // Set OnClickListener for the save button
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Create an instance of DatabaseHelper
                    DatabaseHelper db = new DatabaseHelper(AddInstructorActivity.this);

                    // Get the input data from EditText fields and Spinner
                    String teacherID = TeacherID.getText().toString().trim();
                    String name = Name.getText().toString().trim();
                    String specialty = Specialty.getSelectedItem().toString();
                    String contactInfo = ContactInfo.getText().toString().trim();

                    // Create a Teacher object
                    Teacher teacher = new Teacher(teacherID, name, specialty, contactInfo);

                    // Add the instructor to the database
                    db.addTeacher(teacher);

                    // Notify the user of success
                    Toast.makeText(AddInstructorActivity.this, "Instructor added successfully", Toast.LENGTH_SHORT).show();

                    // Clear the input fields
                    TeacherID.setText("");
                    Name.setText("");
                    Specialty.setSelection(0);
                    ContactInfo.setText("");
                } catch (IllegalArgumentException e) {
                    // Display the error message to the user
                    Toast.makeText(AddInstructorActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
