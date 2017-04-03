package com.markfeldman.tasktrack.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.markfeldman.tasktrack.R;

public class SharedPreferencesUtility {

    public static boolean areNotificationsEnabled(Context context){
        final String displayNotificationsKey = context.getString(R.string.pref_enable_notifications_key);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(displayNotificationsKey, true);
    }
}
