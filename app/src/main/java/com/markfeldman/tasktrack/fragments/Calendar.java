package com.markfeldman.tasktrack.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.utilities.DateUtility;
import com.markfeldman.tasktrack.utilities.PopUpUtilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Calendar extends Fragment {
    private Date date = new Date();

    public Calendar() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        CompactCalendarView calendarView = (CompactCalendarView)view.findViewById(R.id.compactCalendar);

        //ADDFAKEDATA
        PopUpUtilities.addFakaData(getActivity());

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if(DateUtility.isItToday(dateClicked)){
                    PopUpUtilities.listTodaysTasks(getActivity(),dateClicked);
                }else {
                    PopUpUtilities.listOtherDaysTasks(getActivity(),dateClicked);
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });

        return view;
    }

}
