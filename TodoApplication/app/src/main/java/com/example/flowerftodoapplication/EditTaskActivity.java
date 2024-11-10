package com.example.flowerftodoapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flowerftodoapplication.db.DatabaseHelper;
import com.example.flowerftodoapplication.db.entity.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextDateCreated;
    private EditText editTextDateRemaining;
    private Button buttonSaveTask;
    private DatabaseHelper dbHelper;
    private Calendar calendarCreated;
    private Calendar calendarRemaining;
    private Task taskToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Initialize UI elements
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDateCreated = findViewById(R.id.editTextDateCreated);
        editTextDateRemaining = findViewById(R.id.editTextDateRemaining);
        buttonSaveTask = findViewById(R.id.buttonSaveTask);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);
        calendarCreated = Calendar.getInstance();
        calendarRemaining = Calendar.getInstance();

        // Load task details if task_id is passed in the intent
        int taskId = getIntent().getIntExtra("task_id", -1);
        if (taskId != -1) {
            loadTaskDetails(taskId);
        }

        // Set up DatePicker for Date Created
        editTextDateCreated.setOnClickListener(v -> showDatePickerDialog(calendarCreated, editTextDateCreated));

        // Set up DatePicker for Date Remaining
        editTextDateRemaining.setOnClickListener(v -> showDatePickerDialog(calendarRemaining, editTextDateRemaining));

        // Set up button click listener
        buttonSaveTask.setOnClickListener(v -> saveTask());
    }

    private void loadTaskDetails(int taskId) {
        taskToEdit = dbHelper.getTask(taskId);
        if (taskToEdit != null) {
            editTextTitle.setText(taskToEdit.getTitle());
            editTextDescription.setText(taskToEdit.getDescription());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            editTextDateCreated.setText(sdf.format(taskToEdit.getDateCreated()));
            editTextDateRemaining.setText(sdf.format(taskToEdit.getDateRemaining()));
        }
    }

    private void showDatePickerDialog(Calendar calendar, EditText editText) {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            editText.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dateCreatedStr = editTextDateCreated.getText().toString().trim();
        String dateRemainingStr = editTextDateRemaining.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || dateCreatedStr.isEmpty() || dateRemainingStr.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert date strings back to Date objects
        Date dateCreated = calendarCreated.getTime();
        Date dateRemaining = calendarRemaining.getTime();

        if (taskToEdit != null) {
            // Update the existing task
            taskToEdit.setTitle(title);
            taskToEdit.setDescription(description);
            taskToEdit.setDateCreated(dateCreated);
            taskToEdit.setDateRemaining(dateRemaining);
            dbHelper.updateTask(taskToEdit);
            Toast.makeText(this, "Task updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            // Create a new Task object
            Task newTask = new Task(title, description, dateCreated, null, dateRemaining);
            dbHelper.addTask(newTask);
            Toast.makeText(this, "Task added successfully!", Toast.LENGTH_SHORT).show();
        }

        finish(); // Close the EditTaskActivity and return to MainActivity
    }
}
