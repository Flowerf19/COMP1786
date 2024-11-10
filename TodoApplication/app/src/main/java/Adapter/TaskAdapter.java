
package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowerftodoapplication.R;
import com.example.flowerftodoapplication.db.entity.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnTaskEditListener onTaskEditListener;
    private OnTaskDeleteListener onTaskDeleteListener;

    public TaskAdapter(List<Task> taskList, OnTaskEditListener onTaskEditListener, OnTaskDeleteListener onTaskDeleteListener) {
        this.taskList = taskList;
        this.onTaskEditListener = onTaskEditListener;
        this.onTaskDeleteListener = onTaskDeleteListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTaskTitle;
        private TextView textViewTaskDescription;
        private Button buttonEditTask;
        private Button buttonDeleteTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTaskTitle = itemView.findViewById(R.id.textViewTaskTitle);
            textViewTaskDescription = itemView.findViewById(R.id.textViewTaskDescription);
            buttonEditTask = itemView.findViewById(R.id.buttonEditTask);
            buttonDeleteTask = itemView.findViewById(R.id.buttonDeleteTask);
        }

        public void bind(Task task) {
            textViewTaskTitle.setText(task.getTitle());
            textViewTaskDescription.setText(task.getDescription());

            buttonEditTask.setOnClickListener(v -> onTaskEditListener.onTaskEdit(task));
            buttonDeleteTask.setOnClickListener(v -> onTaskDeleteListener.onTaskDelete(task));
        }
    }

    public interface OnTaskEditListener {
        void onTaskEdit(Task task);
    }

    public interface OnTaskDeleteListener {
        void onTaskDelete(Task task);
    }
}
