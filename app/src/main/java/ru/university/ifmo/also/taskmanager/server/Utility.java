package ru.university.ifmo.also.taskmanager.server;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;

public class Utility {
    private  final static String appName = "TaskManager";
    private static String accessToken;
    private static boolean isLoginned;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        Utility.accessToken = accessToken;
    }

    public static boolean getIsLoginned() {
        return isLoginned;
    }

    public static void setIsLoginned(boolean value) {
        isLoginned = value;
    }

    public static String getAppName(){
        return appName;
    }
}
