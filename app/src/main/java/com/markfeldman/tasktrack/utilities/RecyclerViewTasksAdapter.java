package com.markfeldman.tasktrack.utilities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.database.DatabaseContract;

public class RecyclerViewTasksAdapter extends RecyclerView.Adapter<RecyclerViewTasksAdapter.TasksHolder> {
    private Cursor cursor;

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
        holder.tasksText.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.TasksContract.TABLE_NAME)));
    }

    @Override
    public int getItemCount() {
        if (cursor==null){
            return 0;
        }
        return cursor.getCount();
    }

    public void swap (Cursor c){
        this.cursor = c;
        notifyDataSetChanged();
    }


}

