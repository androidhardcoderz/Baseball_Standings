package com.baseballstandings.logify;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Scott on 11/23/2015.
 */
public class SavedData {

    public static final String JSON_DATA = "json_data";
    public static final String NOTIFICATION = "notification";
    public static final String FIRST_RUN = "first_run";
    public static final String SHOW_NOTIFICATIONS = "show_notifications";
    public static final String NOTIFICATION_ISSUED = "notification_issued";

    public static void saveJSONData(Context context,String string){

        PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).edit().putString(JSON_DATA,string).apply();
    }

    public static String getJSONData(Context context){

        return PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).getString(JSON_DATA,"");
    }

    public static boolean getNotificationState(Context context){
        return PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).getBoolean(NOTIFICATION, true);
    }

    public static void setNotificationState(Context context,boolean state){
        PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).edit().putBoolean(NOTIFICATION,state).apply();
    }
    public static boolean isFirstRun(Context context){
        return PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).getBoolean(FIRST_RUN, true);
    }

    public static void setFirstRun(Context context,boolean state){
        PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).edit().putBoolean(FIRST_RUN,state).apply();
    }

    public static void setNotifyIssueDay(Context context,String string){
        PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).edit().putString(NOTIFICATION_ISSUED, string);
    }

    public static String getNotifyIssueDay(Context context){
        return PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).getString(NOTIFICATION_ISSUED,"");
    }

    public static boolean getShowNotifications(Context context){
        return PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).getBoolean(SHOW_NOTIFICATIONS, true);
    }

    public static void setShowNotifications(Context context,boolean state){
        PreferenceManager.getDefaultSharedPreferences(
                context.getApplicationContext()).edit().putBoolean(SHOW_NOTIFICATIONS,state).apply();
    }


}
