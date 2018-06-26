package ru.university.ifmo.also.taskmanager.server;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ru.university.ifmo.also.taskmanager.model.TaskFilter;

import static android.content.Context.MODE_PRIVATE;

public class Utility {
    private  final static String appName = "TaskManager";
    private static String accessToken;
    private static boolean isLoginned;
    private static TaskFilter taskFilter;
    private static String projectId;

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

    public static TaskFilter getTaskFilter() {
        return taskFilter;
    }

    public static void setTaskFilter(TaskFilter taskFilter) {
        Utility.taskFilter = taskFilter;
    }

    public static String getProjectId() {
        return projectId;
    }

    public static void setProjectId(String projectId) {
        Utility.projectId = projectId;
    }
}
