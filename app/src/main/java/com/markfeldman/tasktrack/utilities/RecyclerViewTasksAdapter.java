package com.markfeldman.tasktrack.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.markfeldman.tasktrack.R;

public class RecyclerViewTasksAdapter extends RecyclerView.Adapter<RecyclerViewTasksAdapter.TasksHolder> {

    class TasksHolder extends RecyclerView.ViewHolder{
        TextView tasksText;

        public TasksHolder(View itemView) {
            super(itemView);
            tasksText = (TextView)itemView.findViewById(R.id.task_text);
        }
    }

    @Override
    public TasksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.individual_tasks;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new TasksHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksHolder holder, int position) {
        holder.tasksText.setText("Test");

    }

    @Override
    public int getItemCount() {
        return 10;
    }


}

