package com.example.unversalyogajavaapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unversalyogajavaapplication.Apdater.FlowerfAdater;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton Add_Course_Button;
    FlowerfAdater flowerfAdater;
    DatabaseHelper DB;
    ArrayList<String> CourseID, DayOfWeek, Duration,TimeOfCourse, Price, Type, Capacity, Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        Add_Course_Button = findViewById(R.id.Add_Course_Button);

        Add_Course_Button.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddCourseActivity.class);
            startActivity(intent);
        });

        DB = new DatabaseHelper(CourseActivity.this);
        CourseID = new ArrayList<>();
        DayOfWeek = new ArrayList<>();
        Capacity = new ArrayList<>();
        TimeOfCourse = new ArrayList<>();
        Duration = new ArrayList<>();
        Price = new ArrayList<>();
        Type = new ArrayList<>();
        Location = new ArrayList<>();

        displayCourseData();

        flowerfAdater = new FlowerfAdater(this, CourseID, DayOfWeek,Capacity, TimeOfCourse, Duration, Price, Type, Location);
        recyclerView.setAdapter(flowerfAdater);
        recyclerView.setLayoutManager(new LinearLayoutManager(CourseActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        CourseID.clear();
        DayOfWeek.clear();
        Capacity.clear();
        TimeOfCourse.clear();
        Duration.clear();
        Price.clear();
        Type.clear();
        Location.clear();
        displayCourseData();
        flowerfAdater.notifyDataSetChanged();
    }

    void displayCourseData() {
        Cursor cursor = DB.readAllCourse();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                CourseID.add(cursor.getString(0));
                DayOfWeek.add(cursor.getString(1));
                Capacity.add(cursor.getString(2));
                TimeOfCourse.add(cursor.getString(3));
                Duration.add(cursor.getString(4));
                Price.add(cursor.getString(5));
                Type.add(cursor.getString(6));
                Location.add(cursor.getString(7));
            }
        }
    }
}
