package com.example.unversalyogajavaapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unversalyogajavaapplication.Entity.Course;

public class AddCourseActivity extends AppCompatActivity {
    EditText CourseID, Capacity, TimeOfCourse, Duration, Price, Location;
    Button button_save;
    Spinner Type, DayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        // Initialize EditText fields
        CourseID = findViewById(R.id.CourseID);
        Capacity = findViewById(R.id.Capacity);
        TimeOfCourse = findViewById(R.id.TimeOfCourse);
        Duration = findViewById(R.id.Duration);
        Price = findViewById(R.id.Price);
        Type = findViewById(R.id.Type);
        DayOfWeek = findViewById(R.id.DayOfWeek);
        Location = findViewById(R.id.Location);

        // Set up Spinners
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.yoga_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Type.setAdapter(typeAdapter);

        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DayOfWeek.setAdapter(dayAdapter);

        // Initialize the save button
        button_save = findViewById(R.id.button_save);

        // Set OnClickListener for the save button
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Create an instance of DatabaseHelper
                    DatabaseHelper db = new DatabaseHelper(AddCourseActivity.this);

                    // Get the input data from EditText fields and Spinners
                    String courseId = CourseID.getText().toString().trim();
                    String dayOfWeek = DayOfWeek.getSelectedItem().toString();
                    int capacity = Integer.parseInt(Capacity.getText().toString().trim());
                    String timeOfCourse = TimeOfCourse.getText().toString().trim();
                    int duration = Integer.parseInt(Duration.getText().toString().trim());
                    double price = Double.parseDouble(Price.getText().toString().trim());
                    String type = Type.getSelectedItem().toString();
                    String location = Location.getText().toString().trim();
                    long createAt = System.currentTimeMillis();

                    // Create a Course object
                    Course course = new Course(courseId, dayOfWeek, capacity, timeOfCourse, duration, price, type, location, createAt);

                    // Add the course to the database
                    db.addCourse(course);

                    // Notify the user of success
                    Toast.makeText(AddCourseActivity.this, "Course added successfully", Toast.LENGTH_SHORT).show();

                    // Optionally, clear the input fields or finish the activity
                    CourseID.setText("");
                    DayOfWeek.setSelection(0);
                    Capacity.setText("");
                    TimeOfCourse.setText("");
                    Duration.setText("");
                    Price.setText("");
                    Type.setSelection(0);
                    Location.setText("");
                } catch (IllegalArgumentException e) {
                    // Display the error message to the user
                    Toast.makeText(AddCourseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
