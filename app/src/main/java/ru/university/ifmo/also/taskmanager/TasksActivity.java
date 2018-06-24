package ru.university.ifmo.also.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;

import junit.framework.TestCase;

import java.util.List;

import retrofit2.Response;
import ru.university.ifmo.also.taskmanager.adapter.TaskArrayAdapter;
import ru.university.ifmo.also.taskmanager.model.ProjectInfo;
import ru.university.ifmo.also.taskmanager.model.TaskFilter;
import ru.university.ifmo.also.taskmanager.model.TaskInfo;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;

public class TasksActivity extends AppCompatActivity {
    private final String TAG = "Task Activity";

    ListView lvTasks;
    TextView lblProjectName;
    TextView lblProjectDescription;

    Callback<ValidateModel<List<TaskInfo>>> onGetTasks = new Callback<ValidateModel<List<TaskInfo>>>() {
        @Override
        public void onResponse(Call<ValidateModel<List<TaskInfo>>> call, Response<ValidateModel<List<TaskInfo>>> response) {
            if(response.isSuccessful()){
                if (response.body() != null) {
                    List<TaskInfo> items = response.body().getEntity();
                    if (items != null && items.size() > 0) {
                        TaskArrayAdapter adapter = new TaskArrayAdapter(TasksActivity.this, items);
                        lvTasks.setAdapter(adapter);
                    }
                }
            }
            else{
                Log.d(TAG, "get task failure: " + response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<ValidateModel<List<TaskInfo>>> call, Throwable t) {
            Log.d(TAG, "get task failure: " + t );
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);

        Intent intent = getIntent();

        String projectName = intent.getStringExtra("title");
        String projectDescription = intent.getStringExtra("description");
        String projectId = intent.getStringExtra("projectId");

        lblProjectName = findViewById(R.id.lblProjectName);
        lblProjectDescription = findViewById(R.id.lblProjectDescription);
        lvTasks = findViewById(R.id.lvTasks);

        lblProjectName.setText(projectName);
        lblProjectDescription.setText(projectDescription);

        //lvTasks = findViewById(R.id.lvTasks);
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setProjectId(projectId);
        ApiManager.getAllTasks( taskFilter , onGetTasks);
    }

}
