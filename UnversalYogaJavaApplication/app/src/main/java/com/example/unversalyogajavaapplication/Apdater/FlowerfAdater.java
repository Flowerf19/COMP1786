package com.example.unversalyogajavaapplication.Apdater;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unversalyogajavaapplication.DatabaseHelper;
import com.example.unversalyogajavaapplication.R;
import com.example.unversalyogajavaapplication.UpdateCourseActivity;

import java.util.ArrayList;

public class FlowerfAdater extends RecyclerView.Adapter<FlowerfAdater.FlowerfViewHolder> {
    private Context context;
    private ArrayList<String> CourseID, DayOfWeek, Capacity, TimeOfCourse, Duration, Price, Type, Location;
    private DatabaseHelper db;

    public FlowerfAdater(Context context, ArrayList<String> CourseID, ArrayList<String> DayOfWeek, ArrayList<String> Capacity, ArrayList<String> TimeOfCourse, ArrayList<String> Duration, ArrayList<String> Price, ArrayList<String> Type, ArrayList<String> Location) {
        this.context = context;
        this.CourseID = CourseID;
        this.DayOfWeek = DayOfWeek;
        this.Capacity = Capacity;
        this.TimeOfCourse = TimeOfCourse;
        this.Duration = Duration;
        this.Price = Price;
        this.Type = Type;
        this.Location = Location;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public FlowerfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.f_row, parent, false);
        return new FlowerfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerfViewHolder holder, int position) {
        holder.CourseID.setText("Course: " + CourseID.get(position));
        holder.DayOfWeek.setText("Day: " + DayOfWeek.get(position));
        holder.Capacity.setText("Capacity: " + Capacity.get(position));
        holder.TimeOfCourse.setText("Time: " + TimeOfCourse.get(position));
        holder.Duration.setText("Duration: " + Duration.get(position) + " hours");
        holder.Price.setText("Price: " + Price.get(position) + "$");
        holder.Type.setText("Type: " + Type.get(position));
        holder.Location.setText("Location: " + Location.get(position));

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateCourseActivity.class);
            intent.putExtra("CourseID", CourseID.get(position));
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Course")
                    .setMessage("Are you sure you want to delete this course?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.deleteCourse(CourseID.get(position));

                        CourseID.remove(position);
                        DayOfWeek.remove(position);
                        Capacity.remove(position);
                        TimeOfCourse.remove(position);
                        Duration.remove(position);
                        Price.remove(position);
                        Type.remove(position);
                        Location.remove(position);

                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, CourseID.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return CourseID.size();
    }

    public class FlowerfViewHolder extends RecyclerView.ViewHolder {
        TextView CourseID, DayOfWeek, Capacity, TimeOfCourse, Duration, Price, Type, Location;
        Button editButton, deleteButton;

        public FlowerfViewHolder(@NonNull View itemView) {
            super(itemView);
            CourseID = itemView.findViewById(R.id.CourseID);
            DayOfWeek = itemView.findViewById(R.id.DayOfWeek);
            Capacity = itemView.findViewById(R.id.Capacity);
            TimeOfCourse = itemView.findViewById(R.id.TimeOfCourse);
            Duration = itemView.findViewById(R.id.Duration);
            Price = itemView.findViewById(R.id.Price);
            Type = itemView.findViewById(R.id.Type);
            Location = itemView.findViewById(R.id.Location);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
