package com.example.unversalyogajavaapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

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

        // Set up the BottomNavigationView and item selection listener
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            // Use if-else instead of switch to check for selected item
            if (item.getItemId() == R.id.nav_course) {
                // Handle navigation to the Course screen
                startActivity(new Intent(MainActivity.this, CourseActivity.class));
                return true;
            } else if (item.getItemId() == R.id.nav_class) {
                // Handle navigation to the Class screen
                startActivity(new Intent(MainActivity.this, ClassActivity.class));
                return true;
            } else if (item.getItemId() == R.id.nav_teacher) {
                // Handle navigation to the Teacher screen
                startActivity(new Intent(MainActivity.this, InstructorActivity.class));
                return true;
            }
            return false;
        });
    }
}
