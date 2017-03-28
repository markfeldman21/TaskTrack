package com.markfeldman.tasktrack.utilities;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


class DateUtility {
    static boolean areDatesEqual(Date dateClicked){
        Date date = new Date();
        long time = date.getTime();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy", Locale.US);
        String dateClickedText = formatDate.format(dateClicked);
        String dateCurrentText = formatDate.format(time);
        return dateCurrentText.equals(dateClickedText);
    }

    static String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        Date date = new Date();
        long time = date.getTime();
        return dateFormat.format(time);
    }

}
