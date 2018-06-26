package ru.university.ifmo.also.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.university.ifmo.also.taskmanager.model.CreateProject;
import ru.university.ifmo.also.taskmanager.model.ProjectInfo;
import ru.university.ifmo.also.taskmanager.model.UpdateProject;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;

public class CreateProjectActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG="CREATE PROJECT ACTIVITY";
    String projectId;
    Button btnCreateProject;
    EditText txtProjectName;
    EditText txtProjectDescription;

    Callback<ValidateModel<ProjectInfo>> onCreateProject = new Callback<ValidateModel<ProjectInfo>>() {
        @Override
        public void onResponse(Call<ValidateModel<ProjectInfo>> call, Response<ValidateModel<ProjectInfo>> response) {
            if (response.isSuccessful()){
                Log.d(TAG,"project was create successful");
                finish();
                Toast.makeText(CreateProjectActivity.this, "Проект создан", Toast.LENGTH_LONG).show();
            }
            else {
                Log.d(TAG,"project create failure: " + response.body());
            }
        }

        @Override
        public void onFailure(Call<ValidateModel<ProjectInfo>> call, Throwable t) {
            Log.d(TAG,"project create failure: " + t);
        }
    };

    Callback<Void> onUpdateProject = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            Toast.makeText(CreateProjectActivity.this, "Проект обновлен", Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<ValidateModel<ProjectInfo>> onGetProject = new Callback<ValidateModel<ProjectInfo>>() {
        @Override
        public void onResponse(Call<ValidateModel<ProjectInfo>> call, Response<ValidateModel<ProjectInfo>> response) {
            if (response.isSuccessful()){
                txtProjectName.setText(response.body().getEntity().getTitle());
                txtProjectDescription.setText(response.body().getEntity().getDescription());
            }
            else {
                Toast.makeText(CreateProjectActivity.this, "Не удалось загрузить проект", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<ValidateModel<ProjectInfo>> call, Throwable t) {
            Toast.makeText(CreateProjectActivity.this, "Не удалось загрузить проект", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project);
        btnCreateProject = findViewById(R.id.btnCreateProject);
        btnCreateProject.setOnClickListener(this);
        txtProjectName = findViewById((R.id.txtProjectName));
        txtProjectDescription = findViewById(R.id.txtProjectDescription);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
          projectId = extras.getString("projectId");
          if (projectId != null && !projectId.isEmpty()){
              ApiManager.getProject(projectId, onGetProject);
              btnCreateProject.setText("Обновить");
          }
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "View was pressed");

        switch (v.getId()) {
            case R.id.btnCreateProject: {
                if (projectId != null && !projectId.isEmpty()){
                    UpdateProject projectInfo = new UpdateProject();
                    projectInfo.setTitle(txtProjectName.getText().toString());
                    projectInfo.setDescription(txtProjectDescription.getText().toString());
                    projectInfo.setId (projectId);
                    ApiManager.updateProject(projectInfo, onUpdateProject);
                }
                else {
                    CreateProject project = new CreateProject(txtProjectName.getText().toString(), txtProjectDescription.getText().toString());
                    ApiManager.createProject(project, onCreateProject);
                }
                break;
            }
            default:{
                break;
            }
        }
    }
}
