package ru.university.ifmo.also.taskmanager.server;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.university.ifmo.also.taskmanager.model.*;

public interface ServerApi {
    @POST("account/login")
    Call<ValidateModel<UserLoginResponse>> login(@Body UserLoginRequest login);

    @GET("project")
    Call<ValidateModel<List<ProjectInfo>>> getAllProjects(@Header("Authorization") String access_token);

    @GET("project/{id}")
    Call<ValidateModel<ProjectInfo>> getProject(@Header("Authorization") String access_token,
                                                @Path("id") String projectId);

    @POST("project")
    Call<ValidateModel<ProjectInfo>> createProject(@Header("Authorization") String access_token, @Body CreateProject project);

    @DELETE("project/{id}")
    Call<Void> deleteProject(@Header("Authorization") String access_token,
                             @Path("id") String projectId);

    @PUT("project")
    Call<Void> updateProject(@Header("Authorization") String access_token,
                             @Body UpdateProject project);

    @GET("project/invite")
    Call<Void> inviteUser(@Header("Authorization") String access_token,
                          @Query("projectId") String projectId,
                          @Query("userLogin") String userLogin,
                          @Query("role") int role);

    @GET("task/all/")
    Call<ValidateModel<List<TaskInfo>>> getAllTasks(@Header("Authorization") String access_token,
                                                    @Query("projectId") String projectId,
                                                    @Query("title") String title,
                                                    @Query("ownerLogin") String ownerLogin,
                                                    @Query("assignedLogin") String assignedLogin,
                                                    @Query("priority") Integer priority,
                                                    @Query("type") Integer type);

    @GET("user/project/{projectId}")
    Call<ValidateModel<List<UserInfo>>> getUsers (@Header("Authorization") String access_token,
                                                  @Path("projectId") String projectId);

    @POST("account/register")
    Call<ValidateModel<UserInfo>> register(@Body UserRegisterRequest register);

}
