package ru.university.ifmo.also.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.university.ifmo.also.taskmanager.model.CreateTask;
import ru.university.ifmo.also.taskmanager.model.TaskInfo;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;
import ru.university.ifmo.also.taskmanager.server.Utility;

public class TaskDetail extends AppCompatActivity {
    private String taskId;
    private String projectId;
    private TextView txtTaskTitle;
    private TextView txtTaskDescription;
    private TextView txtTaskOwner;
    private TextView txtTaskAssigned;
    private TextView txtTaskCreationDate;
    private Spinner spPriority;
    private Spinner spTaskType;
    private Spinner spTaskStatus;
    private Button btnCreateTask;
    /*private TextView txtOwner;
    private TextView txtCreationDate;*/


    Callback<ValidateModel<TaskInfo>> onGetTask = new Callback<ValidateModel<TaskInfo>>() {
        @Override
        public void onResponse(Call<ValidateModel<TaskInfo>> call, Response<ValidateModel<TaskInfo>> response) {
            TaskInfo entity =response.body().getEntity();
            txtTaskTitle.setText(entity.getTitle());
            txtTaskDescription.setText(entity.getDescription());
            spPriority.setSelection(entity.getPriority());
            spTaskType.setSelection(entity.getType());
            spTaskStatus.setSelection(entity.getStatus());
        }

        @Override
        public void onFailure(Call<ValidateModel<TaskInfo>> call, Throwable t) {

        }
    };

    Callback<ValidateModel<TaskInfo>> onCreateTask = new Callback<ValidateModel<TaskInfo>>() {
        @Override
        public void onResponse(Call<ValidateModel<TaskInfo>> call, Response<ValidateModel<TaskInfo>> response) {
            if (response.isSuccessful()){

                Toast.makeText(TaskDetail.this, "Задача создана", Toast.LENGTH_LONG).show();
                finish();
            }
            else {
                Toast.makeText(TaskDetail.this, "Задача не создана", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<ValidateModel<TaskInfo>> call, Throwable t) {

        }
    };

    Callback<Void> onUpdateTask = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            Toast.makeText(TaskDetail.this, "Обновлено", Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
    /*    btnCreateProject = findViewById(R.id.btnCreateProject);
        btnCreateProject.setOnClickListener(this);*/
        txtTaskTitle = findViewById((R.id.txtTaskTitle));
        txtTaskDescription = findViewById(R.id.txtTaskDescription);
        spPriority = findViewById(R.id.spPriority);
        spTaskType = findViewById(R.id.spTaskType);
        spTaskStatus = findViewById(R.id.spTaskStatus);


        btnCreateTask =findViewById(R.id.btnCreateTask);


        List<String> priorities = new ArrayList<>();
        priorities.add("Низкий");
        priorities.add("Средний");
        priorities.add("Высокий");
        priorities.add("Блокирующий");

        ArrayAdapter<String> adPriority = new ArrayAdapter(this, R.layout.spinner_list_item, priorities);
        spPriority.setAdapter(adPriority);

        List<String> types = new ArrayList<>();
        types.add("Новая функциональность");
        types.add("Ошибка");

        ArrayAdapter<String> adTypes = new ArrayAdapter<>(this, R.layout.spinner_list_item, types);
        spTaskType.setAdapter(adTypes);

        List<String> statuses = new ArrayList<>();
        statuses.add("Новый");
        statuses.add("Разработка");
        statuses.add("Тестирование");
        statuses.add("Закрыт");
        statuses.add("Не ошибка");

        ArrayAdapter<String> adStatuses = new ArrayAdapter<>(this, R.layout.spinner_list_item, statuses);
        spTaskStatus.setAdapter(adStatuses);

        btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskId != null && !taskId.isEmpty()){
                    TaskInfo taskInfo = new TaskInfo();
                    taskInfo.setDescription(txtTaskDescription.getText().toString());
                    taskInfo.setTitle(txtTaskTitle.getText().toString());
                    taskInfo.setId(UUID.fromString(taskId));
                    taskInfo.setPriority((int)spPriority.getSelectedItemId());
                    taskInfo.setType((int)spTaskType.getSelectedItemId());
                    taskInfo.setStatus((int)spTaskStatus.getSelectedItemId());
                    ApiManager.updateTask(taskInfo, onUpdateTask);
                }
                else {
                    CreateTask createTask = new CreateTask();
                    createTask.setDescription(txtTaskDescription.getText().toString());
                    createTask.setTitle(txtTaskTitle.getText().toString());
                    createTask.setProjectId(UUID.fromString(projectId));
                    createTask.setPriority((int)spPriority.getSelectedItemId());
                    createTask.setType((int)spTaskType.getSelectedItemId());

                    ApiManager.createTask(createTask, onCreateTask);
                }
            }
        });

        projectId = Utility.getProjectId(); //extras.getString("projectId");
        if (projectId == null || projectId.isEmpty()){
            btnCreateTask.setVisibility(View.GONE);
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            taskId = extras.getString("taskId");
            if (taskId != null && !taskId.isEmpty()){
                ApiManager.getTask(taskId, onGetTask);
                btnCreateTask.setText("Обновить");
                spTaskStatus.setVisibility(View.VISIBLE);
            }
        }
    }
}
