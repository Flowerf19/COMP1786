package com.example.unversalyogajavaapplication.Apdater;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unversalyogajavaapplication.DatabaseHelper;
import com.example.unversalyogajavaapplication.R;
import com.example.unversalyogajavaapplication.UpdateClassActivity;

import java.util.ArrayList;

public class ClassAdater extends RecyclerView.Adapter<ClassAdater.ViewHolder> {

    private Context context;
    private ArrayList<String> classID, courseID, date, teacherID, dayOfWeek; // Add dayOfWeek
    private DatabaseHelper DB;

    public ClassAdater(Context context, ArrayList<String> classID, ArrayList<String> courseID, ArrayList<String> date, ArrayList<String> teacherID, ArrayList<String> dayOfWeek) {
        this.context = context;
        this.classID = classID;
        this.courseID = courseID;
        this.date = date;
        this.teacherID = teacherID;
        this.dayOfWeek = dayOfWeek; // Add this line
        this.DB = new DatabaseHelper(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.c_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.classID.setText("Class ID: " + classID.get(position));
        holder.courseID.setText("Course ID: " + courseID.get(position));
        holder.date.setText("Date: " + date.get(position));
        holder.teacherID.setText("Teacher Name: " + teacherID.get(position));
        holder.dayOfWeek.setText("Day of Week: " + dayOfWeek.get(position)); // Set dayOfWeek text

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateClassActivity.class);
            intent.putExtra("ClassID", classID.get(position));
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Class")
                    .setMessage("Are you sure you want to delete this class?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        String classIDToDelete = classID.get(position);
                        DB.deleteClass(classIDToDelete);
                        classID.remove(position);
                        courseID.remove(position);
                        date.remove(position);
                        teacherID.remove(position);
                        dayOfWeek.remove(position); // Remove dayOfWeek
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, classID.size());
                        Toast.makeText(context, "Class deleted successfully", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return classID.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView classID, courseID, date, teacherID, dayOfWeek; // Add dayOfWeek
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            classID = itemView.findViewById(R.id.ClassID);
            courseID = itemView.findViewById(R.id.CourseID);
            date = itemView.findViewById(R.id.Date);
            teacherID = itemView.findViewById(R.id.TeacherName);
            dayOfWeek = itemView.findViewById(R.id.DayOfWeek); // Initialize dayOfWeek
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
