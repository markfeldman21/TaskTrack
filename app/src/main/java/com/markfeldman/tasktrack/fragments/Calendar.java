package com.markfeldman.tasktrack.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.markfeldman.tasktrack.R;
import com.markfeldman.tasktrack.utilities.DateUtility;
import com.markfeldman.tasktrack.utilities.PopUpUtilities;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Calendar extends Fragment {
    private Date date = new Date();
    private SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("MMMM yyyy",Locale.getDefault());

    public Calendar() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        CompactCalendarView calendarView = (CompactCalendarView)view.findViewById(R.id.compactCalendar);
        final TextView monthDisplay = (TextView)view.findViewById(R.id.displayMonth);

        //ADDFAKEDATA
        //PopUpUtilities.addFakaData(getActivity());
        Date firstDayofMonth = calendarView.getFirstDayOfCurrentMonth();
        monthDisplay.setText(simpleDateFormatMonth.format(firstDayofMonth));

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
                monthDisplay.setText(simpleDateFormatMonth.format(firstDayOfNewMonth));
            }
        });

        return view;
    }



}
