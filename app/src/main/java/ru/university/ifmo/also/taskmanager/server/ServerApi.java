package ru.university.ifmo.also.taskmanager.server;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.university.ifmo.also.taskmanager.model.*;

public interface ServerApi {
    @POST("account/login")
    Call<ValidateModel<UserLoginResponse>> login(@Body UserLoginRequest login);

    @GET("project")
    Call<ValidateModel<List<ProjectInfo>>> getAllProjects(@Header("Authorization") String access_token);

    @POST("project")
    Call<ValidateModel<ProjectInfo>> createProject(@Header("Authorization") String access_token, @Body CreateProject project);

    @GET("task/all/")
    Call<ValidateModel<List<TaskInfo>>> getAllTasks(@Header("Authorization") String access_token,
                                                    @Query("projectId") String projectId,
                                                    @Query("title") String title,
                                                    @Query("ownerLogin") String ownerLogin,
                                                    @Query("assignedLogin") String assignedLogin,
                                                    @Query("priority") Integer priority,
                                                    @Query("type") Integer type);

    @POST("account/register")
    Call<ValidateModel<UserRegisterResponse>> register(@Body UserRegisterRequest register);
}
