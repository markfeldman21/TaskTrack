package com.markfeldman.tasktrack.utilities;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateUtility {
    public static boolean isItToday(Date dateClicked){
        Date date = new Date();
        long time = date.getTime();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy", Locale.US);
        String dateClickedText = formatDate.format(dateClicked);
        String dateCurrentText = formatDate.format(time);
        return dateCurrentText.equals(dateClickedText);
    }

    public static String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        Date date = new Date();
        long time = date.getTime();
        return dateFormat.format(time);
    }

    public static String getCurrentDate(){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy", Locale.US);
        Date date = new Date();
        long time = date.getTime();
        return formatDate.format(time);
    }

    public static String formatToDDMMYY(Date date){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy", Locale.US);
        return formatDate.format(date);
    }

}
