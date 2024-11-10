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
import com.example.unversalyogajavaapplication.Apdater.InstructorAdater;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class InstructorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton Add_Instructor_Button;
    InstructorAdater instructorAdater;
    DatabaseHelper DB;
    ArrayList<String> TeacherID, Name, Specialty, ContactInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instructors);

        // Handling window insets for edge-to-edge design
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        Add_Instructor_Button = findViewById(R.id.Add_Instructor_Button);

        Add_Instructor_Button.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddInstructorActivity.class);
            startActivity(intent);
        });

        DB = new DatabaseHelper(InstructorActivity.this);
        TeacherID = new ArrayList<>();
        Name = new ArrayList<>();
        Specialty = new ArrayList<>();
        ContactInfo = new ArrayList<>();

        // Fetch data and display it in RecyclerView
        displayInstructorData();

        // Initialize the adapter and set it on the RecyclerView
        instructorAdater = new InstructorAdater(this, TeacherID, Name, Specialty, ContactInfo);
        recyclerView.setAdapter(instructorAdater);
        recyclerView.setLayoutManager(new LinearLayoutManager(InstructorActivity.this));
    }

    // Method to fetch data from the database
    void displayInstructorData() {
        Cursor cursor = DB.readAllInstructor(); // This should be a method to fetch teachers from the database
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                // Add data from the database into the corresponding lists
                TeacherID.add(cursor.getString(0));
                Name.add(cursor.getString(1));
                Specialty.add(cursor.getString(2));
                ContactInfo.add(cursor.getString(3));
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        TeacherID.clear();
        Name.clear();
        Specialty.clear();
        ContactInfo.clear();
        displayInstructorData();
        instructorAdater.notifyDataSetChanged(); // Thông báo cho adapter cập nhật lại dữ liệu
    }

}
