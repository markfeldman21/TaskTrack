package com.markfeldman.tasktrack.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.markfeldman.tasktrack.R;


public class Home extends Fragment implements View.OnClickListener {
    public onFragmentClicked onFragmentClickedListener;

    static public interface onFragmentClicked{
        public void buttonClicked(View v);
    }

    public Home() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.onFragmentClickedListener = (onFragmentClicked)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button cal = (Button)view.findViewById(R.id.cal_button);
        Button tasks = (Button) view.findViewById(R.id.tasks_button);
        Button contacts = (Button)view.findViewById(R.id.contacts_button);

        cal.setOnClickListener(this);
        tasks.setOnClickListener(this);
        contacts.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case (R.id.cal_button):{
                onFragmentClickedListener.buttonClicked(v);
                break;
            }
            case (R.id.tasks_button):{
                onFragmentClickedListener.buttonClicked(v);
                break;
            }
            case (R.id.contacts_button):{
                onFragmentClickedListener.buttonClicked(v);
                break;
            }
        }

    }


}
