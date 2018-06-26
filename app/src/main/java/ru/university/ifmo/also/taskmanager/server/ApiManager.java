package ru.university.ifmo.also.taskmanager.server;


import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.university.ifmo.also.taskmanager.LoginActivity;
import ru.university.ifmo.also.taskmanager.model.CreateProject;
import ru.university.ifmo.also.taskmanager.model.ProjectInfo;
import ru.university.ifmo.also.taskmanager.model.TaskFilter;
import ru.university.ifmo.also.taskmanager.model.TaskInfo;
import ru.university.ifmo.also.taskmanager.model.UpdateProject;
import ru.university.ifmo.also.taskmanager.model.UserInfo;
import ru.university.ifmo.also.taskmanager.model.UserLoginRequest;
import ru.university.ifmo.also.taskmanager.model.UserLoginResponse;
import ru.university.ifmo.also.taskmanager.model.UserRegisterRequest;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;

public class ApiManager {
    private static final String BASE_URL="http://192.168.1.43:5000/api/";
    //private static final String BASE_URL="https://also-server.azurewebsites.net/api/";


    static Retrofit retrofit;
    static ServerApi serverApi;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverApi = retrofit.create(ServerApi.class);
    }

    public static void login (String login, String password, Callback<ValidateModel<UserLoginResponse>> callback){
        Call<ValidateModel<UserLoginResponse>> validateUserLogin = serverApi.login( new UserLoginRequest( login, password));
        validateUserLogin.enqueue(callback);
    }

    public static void register(UserRegisterRequest userRegisterRequest, Callback<ValidateModel<UserInfo>> callback){
        Call<ValidateModel<UserInfo>> validateUserRegister = serverApi.register(userRegisterRequest);
        validateUserRegister.enqueue(callback);
    }

    public static void getAllProjects(Callback<ValidateModel<List<ProjectInfo>>> callback){
        Call<ValidateModel<List<ProjectInfo>>> projectsCall = serverApi.getAllProjects(Utility.getAccessToken());
        projectsCall.enqueue(callback);
    }

    public static void getAllTasks(TaskFilter taskFilter,
                                   Callback<ValidateModel<List<TaskInfo>>> callback){
        Call<ValidateModel<List<TaskInfo>>> getTasks = serverApi.getAllTasks(Utility.getAccessToken(),
                taskFilter.getProjectId(),
                taskFilter.getTitle(),
                taskFilter.getOwnerLogin(),
                taskFilter.getAssignedLogin(),
                taskFilter.getPriority(),
                taskFilter.getType() );
        getTasks.enqueue(callback);
    }

    public static void getUsers(String projectId, Callback<ValidateModel<List<UserInfo>>> callback){
        Call<ValidateModel<List<UserInfo>>> getUsers = serverApi.getUsers(Utility.getAccessToken(), projectId);
        getUsers.enqueue(callback);
    }

    public static void createProject(CreateProject project, Callback<ValidateModel<ProjectInfo>> callback){
        Call<ValidateModel<ProjectInfo>> createProjectCall = serverApi.createProject(Utility.getAccessToken(), project);
        createProjectCall.enqueue(callback);
    }

    public static void inviteUser(String projectId, String userLogin, int role, Callback<Void> callback){
        Call<Void> inviteUser = serverApi.inviteUser(Utility.getAccessToken(), projectId, userLogin, role );
        inviteUser.enqueue(callback);
    }

    public static void deleteProject(String projectId,Callback<Void> callback){
        Call<Void> deleteProject = serverApi.deleteProject(Utility.getAccessToken(), projectId);
        deleteProject.enqueue(callback);
    }

    public static void updateProject(UpdateProject project, Callback<Void> callback){
        Call<Void> updateProject = serverApi.updateProject(Utility.getAccessToken(), project);
        updateProject.enqueue(callback);
    }

    public static void getProject(String projectId, Callback<ValidateModel<ProjectInfo>> callback){
        Call<ValidateModel<ProjectInfo>> getProject = serverApi.getProject(Utility.getAccessToken(), projectId);
        getProject.enqueue(callback);
    }
}
