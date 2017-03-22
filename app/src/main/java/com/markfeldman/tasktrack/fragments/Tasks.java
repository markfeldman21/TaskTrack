package com.markfeldman.tasktrack.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.database.DatabaseContract;
import com.markfeldman.tasktrack.utilities.PopUpUtilities;
import com.markfeldman.tasktrack.utilities.RecyclerViewTasksAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tasks extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private RecyclerViewTasksAdapter recyclerViewTasksAdapter;
    public final int SEARCH_TASK_LOADER_ID = 33;
    private String[] projection = {DatabaseContract.TasksContract._ID,DatabaseContract.TasksContract.TASK};
    private Uri tasksQueryUri = DatabaseContract.TasksContract.CONTENT_URI_TASKS;
    Cursor cursor;

    public Tasks() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewTasks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerViewTasksAdapter = new RecyclerViewTasksAdapter();
        recyclerView.setAdapter(recyclerViewTasksAdapter);
        final FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fabTasks);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpUtilities.addTaskPopUpWindow(getActivity());
            }
        });

        swipeToDelete();

        getActivity().getSupportLoaderManager().initLoader(SEARCH_TASK_LOADER_ID,null,this);

        return view;
    }

    public void swipeToDelete(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                getActivity().getContentResolver().delete(tasksQueryUri, DatabaseContract.TasksContract._ID + "=" + id, null);
                Log.v("TAG","INSIDE ON SWIPED CURSOR SIZE" + cursor.getCount());
            }
        }).attachToRecyclerView(recyclerView);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case SEARCH_TASK_LOADER_ID:{
                return new CursorLoader(getActivity(),tasksQueryUri,projection,null,null,null);
            }
            default:
                throw new RuntimeException("CURSOR LOADER NOT IMPLEMENTED " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursor = data;
        recyclerViewTasksAdapter.swap(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recyclerViewTasksAdapter.swap(null);
    }
}
