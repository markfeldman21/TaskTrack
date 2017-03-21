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


        getActivity().getSupportLoaderManager().initLoader(SEARCH_TASK_LOADER_ID,null,this);

        return view;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case SEARCH_TASK_LOADER_ID:{
                Uri tasksQueryUri = DatabaseContract.TasksContract.CONTENT_URI_TASKS;


                return new CursorLoader(getActivity(),tasksQueryUri,projection,null,null,null);
            }
            default:
                throw new RuntimeException("CURSOR LOADER NOT IMPLEMENTED " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() != 0){
            Log.v("TAG","LARGER THAN 0!!!!!" + data.getCount());
            recyclerViewTasksAdapter.swap(data);
        }else if (data.getCount() == 0) {
            Toast.makeText(getActivity(),"Nothing In there", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recyclerViewTasksAdapter.swap(null);
    }
}
