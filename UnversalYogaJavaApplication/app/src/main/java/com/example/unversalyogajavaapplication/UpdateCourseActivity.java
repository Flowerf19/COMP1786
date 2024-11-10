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

import com.example.unversalyogajavaapplication.Entity.Course;

public class UpdateCourseActivity extends AppCompatActivity {
    EditText CourseID, Capacity, TimeOfCourse, Duration, Price, Location;
    Button button_update;
    Spinner Type, DayOfWeek;
    DatabaseHelper DB;
    String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        // Initialize EditText fields
        CourseID = findViewById(R.id.CourseID);
        Capacity = findViewById(R.id.Capacity);
        TimeOfCourse = findViewById(R.id.TimeOfCourse);
        Duration = findViewById(R.id.Duration);
        Price = findViewById(R.id.Price);
        Type = findViewById(R.id.Type);
        DayOfWeek = findViewById(R.id.DayOfWeek);
        Location = findViewById(R.id.Location);
        button_update = findViewById(R.id.buttonUpdateCourse);

        DB = new DatabaseHelper(UpdateCourseActivity.this);

        // Get the course ID from the intent
        Intent intent = getIntent();
        courseID = intent.getStringExtra("CourseID");

        // Set up Spinners
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.yoga_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Type.setAdapter(typeAdapter);

        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DayOfWeek.setAdapter(dayAdapter);

        // Load existing course data
        loadCourseData();

        // Update course on button click
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
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

                    // Update the course in the database
                    DB.updateCourse(course);

                    // Notify the user of success
                    Toast.makeText(UpdateCourseActivity.this, "Course updated successfully", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(UpdateCourseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadCourseData() {
        // Query the database to get the course details
        Cursor cursor = DB.getCourseByID(courseID);
        if (cursor != null && cursor.moveToFirst()) {
            // Get the column indices
            int dayOfWeekIndex = cursor.getColumnIndex("DayOfWeek");
            int capacityIndex = cursor.getColumnIndex("Capacity");
            int timeOfCourseIndex = cursor.getColumnIndex("TimeOfCourse");
            int durationIndex = cursor.getColumnIndex("Duration");
            int priceIndex = cursor.getColumnIndex("Price");
            int typeIndex = cursor.getColumnIndex("Type");
            int locationIndex = cursor.getColumnIndex("Location");

            // Check if all column indices are valid
            if (dayOfWeekIndex != -1 && capacityIndex != -1 && timeOfCourseIndex != -1 && durationIndex != -1 && priceIndex != -1 && typeIndex != -1 && locationIndex != -1) {
                // Get the course details
                String dayOfWeek = cursor.getString(dayOfWeekIndex);
                int capacity = cursor.getInt(capacityIndex);
                String timeOfCourse = cursor.getString(timeOfCourseIndex);
                int duration = cursor.getInt(durationIndex);
                double price = cursor.getDouble(priceIndex);
                String type = cursor.getString(typeIndex);
                String location = cursor.getString(locationIndex);

                // Set the course details in the UI
                CourseID.setText(courseID); // Displaying the existing course ID
                DayOfWeek.setSelection(((ArrayAdapter)DayOfWeek.getAdapter()).getPosition(dayOfWeek));
                Capacity.setText(String.valueOf(capacity));
                TimeOfCourse.setText(timeOfCourse);
                Duration.setText(String.valueOf(duration));
                Price.setText(String.valueOf(price));
                Location.setText(location);

                // Set the selected type in the spinner
                if (type != null) {
                    int spinnerPosition = ((ArrayAdapter)Type.getAdapter()).getPosition(type);
                    Type.setSelection(spinnerPosition);
                }
            } else {
                Log.e("Database", "One or more columns not found");
            }

            cursor.close();
        }
    }
}
