package com.markfeldman.tasktrack.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.markfeldman.tasktrack.R;


public class Home extends Fragment implements View.OnClickListener {
    public onFragmentClicked onFragmentClickedListener;

    @Override
    public void onClick(View v) {

    }

    static public interface onFragmentClicked{
        public void buttonClicked(View v);
    }


    public Home() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }
}
