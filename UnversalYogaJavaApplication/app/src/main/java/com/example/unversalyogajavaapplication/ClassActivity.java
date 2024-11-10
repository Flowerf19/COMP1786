package com.example.unversalyogajavaapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unversalyogajavaapplication.Apdater.ClassAdater;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.widget.SearchView;

public class ClassActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton Add_Class_Button;
    SearchView searchView;
    Spinner dayOfWeekSpinner;
    ClassAdater classAdater;
    DatabaseHelper DB;
    ArrayList<String> ClassID, CourseID, Date, TeacherID, DayOfWeek; // Ensure they are ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_class);

        recyclerView = findViewById(R.id.recyclerView);
        Add_Class_Button = findViewById(R.id.Add_Class_Button);
        searchView = findViewById(R.id.searchView);
        dayOfWeekSpinner = findViewById(R.id.dayOfWeekSpinner);  // Initialize Spinner

        Add_Class_Button.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddClassActivity.class);
            startActivity(intent);
        });

        DB = new DatabaseHelper(ClassActivity.this);
        ClassID = new ArrayList<>();
        CourseID = new ArrayList<>();
        Date = new ArrayList<>();
        TeacherID = new ArrayList<>();
        DayOfWeek = new ArrayList<>(); // Use ArrayList

        classAdater = new ClassAdater(this, ClassID, CourseID, Date, TeacherID, DayOfWeek);
        recyclerView.setAdapter(classAdater);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClassActivity.this));

        displayClassData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchClasses(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchClasses(newText);
                return false;
            }
        });

        dayOfWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDay = parent.getItemAtPosition(position).toString();
                if (!selectedDay.equals("All")) {
                    searchClassesByDay(selectedDay);
                } else {
                    displayClassData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ClassID.isEmpty()) {
            displayClassData();
        } else {
            classAdater.notifyDataSetChanged();
        }
    }

    void displayClassData() {
        Cursor cursor = DB.readAllClass();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No class data", Toast.LENGTH_SHORT).show();
        } else {
            ClassID.clear();
            CourseID.clear();
            Date.clear();
            TeacherID.clear();
            DayOfWeek.clear();
            while (cursor.moveToNext()) {
                ClassID.add(cursor.getString(0));
                CourseID.add(cursor.getString(2));
                Date.add(cursor.getString(1));
                DayOfWeek.add(cursor.getString(3)); // Ensure order matches query
                TeacherID.add(cursor.getString(4)); // Ensure order matches query
            }
            cursor.close();
            classAdater.notifyDataSetChanged();
        }
    }

    void searchClassesByDay(String dayOfWeek) {
        Cursor cursor = DB.searchClassesByDay(dayOfWeek);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No classes found", Toast.LENGTH_SHORT).show();
        } else {
            ClassID.clear();
            CourseID.clear();
            Date.clear();
            TeacherID.clear();
            DayOfWeek.clear();
            while (cursor.moveToNext()) {
                ClassID.add(cursor.getString(0));
                CourseID.add(cursor.getString(2));
                Date.add(cursor.getString(1));
                DayOfWeek.add(cursor.getString(3)); // Ensure order matches query
                TeacherID.add(cursor.getString(4)); // Ensure order matches query
            }
            cursor.close();
            classAdater.notifyDataSetChanged();
        }
    }

    void searchClasses(String teacherName) {
        Cursor cursor = DB.searchClassesByTeacherName(teacherName);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No classes found", Toast.LENGTH_SHORT).show();
        } else {
            ClassID.clear();
            CourseID.clear();
            Date.clear();
            TeacherID.clear();
            DayOfWeek.clear();
            while (cursor.moveToNext()) {
                ClassID.add(cursor.getString(0));
                CourseID.add(cursor.getString(2));
                Date.add(cursor.getString(1));
                DayOfWeek.add(cursor.getString(3)); // Ensure order matches query
                TeacherID.add(cursor.getString(4)); // Ensure order matches query
            }
            cursor.close();
            classAdater.notifyDataSetChanged();
        }
    }
}
