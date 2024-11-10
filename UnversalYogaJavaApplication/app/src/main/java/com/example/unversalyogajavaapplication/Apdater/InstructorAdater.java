package com.example.unversalyogajavaapplication.Apdater;

import android.content.Context;
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
import com.example.unversalyogajavaapplication.UpdateInstructorActivity;

import java.util.ArrayList;

public class InstructorAdater extends RecyclerView.Adapter<InstructorAdater.InstructorViewHolder> {
    private Context context;
    private ArrayList<String> TeacherID, Name, Specialty, ContactInfo;
    private DatabaseHelper db;

    public InstructorAdater(Context context, ArrayList<String> TeacherID, ArrayList<String> Name, ArrayList<String> Specialty, ArrayList<String> ContactInfo) {
        this.context = context;
        this.TeacherID = TeacherID;
        this.Name = Name;
        this.Specialty = Specialty;
        this.ContactInfo = ContactInfo;

        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.i_row, parent, false);
        return new InstructorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorViewHolder holder, int position) {
        holder.TeacherID.setText("Teacher ID: " + TeacherID.get(position));
        holder.Name.setText("Name: " + Name.get(position));
        holder.Specialty.setText("Specialty: " + Specialty.get(position));
        holder.ContactInfo.setText("Contact Info: " + ContactInfo.get(position));

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateInstructorActivity.class);
            intent.putExtra("TeacherID", TeacherID.get(position));
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Instructor")
                    .setMessage("Are you sure you want to delete this instructor?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.deleteInstructor(TeacherID.get(position));
                        TeacherID.remove(position);
                        Name.remove(position);
                        Specialty.remove(position);
                        ContactInfo.remove(position);

                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, TeacherID.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return TeacherID.size();
    }

    public class InstructorViewHolder extends RecyclerView.ViewHolder {
        TextView TeacherID, Name, Specialty, ContactInfo;
        Button editButton, deleteButton;

        public InstructorViewHolder(@NonNull View itemView) {
            super(itemView);
            TeacherID = itemView.findViewById(R.id.TeacherID);
            Name = itemView.findViewById(R.id.Name);
            Specialty = itemView.findViewById(R.id.Specialty);
            ContactInfo = itemView.findViewById(R.id.ContactInfo);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
