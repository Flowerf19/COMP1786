package com.example.flowerftodoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowerftodoapplication.db.DatabaseHelper;
import com.example.flowerftodoapplication.db.entity.Task;

import java.util.Date;
import java.util.List;

import Adapter.TaskAdapter;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskEditListener, TaskAdapter.OnTaskDeleteListener {

    private DatabaseHelper dbHelper;
    private List<Task> taskList;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerViewTasks;
    private Button buttonAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // UI Elements
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        buttonAddTask = findViewById(R.id.addTask);

        // Set up button click listener to launch AddTaskActivity
        buttonAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        // Initial setup of the RecyclerView
        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the task list when the activity resumes
        refreshTaskList();
    }

    private void setupRecyclerView() {
        taskList = dbHelper.getAllTasks();
        taskAdapter = new TaskAdapter(taskList, this, this);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);
    }

    private void refreshTaskList() {
        taskList.clear();
        taskList.addAll(dbHelper.getAllTasks());
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTaskEdit(Task task) {
        // Handle the edit task action here
        Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
        intent.putExtra("task_id", task.getId());
        startActivity(intent);
    }

    @Override
    public void onTaskDelete(Task task) {
        // Handle the delete task action here
        dbHelper.deleteTask(task.getId());
        taskList.remove(task);
        taskAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Task deleted successfully!", Toast.LENGTH_SHORT).show();
    }
}
