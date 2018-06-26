package ru.university.ifmo.also.taskmanager.fragment;

import android.app.Activity;
//import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.university.ifmo.also.taskmanager.CreateProjectActivity;
import ru.university.ifmo.also.taskmanager.R;
import ru.university.ifmo.also.taskmanager.TasksActivity;
import ru.university.ifmo.also.taskmanager.adapter.ProjectArrayAdapter;
import ru.university.ifmo.also.taskmanager.model.ProjectInfo;
import ru.university.ifmo.also.taskmanager.model.TaskFilter;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;
import ru.university.ifmo.also.taskmanager.server.Utility;

public class ProjectListView extends Fragment {
    private final String TAG = "PROJECT_LIST_VIEW";
    ListView lvProjects;
    Button btnCreateProject;
    Context context;

    AdapterView.OnItemClickListener onRowClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View rowView, int position, long id) {

            TextView lblProjectId = rowView.findViewById(R.id.lblProjectId);

            TaskFilter projectTaskFilter = new TaskFilter();
            projectTaskFilter.setProjectId(lblProjectId.getText().toString());
            Utility.setTaskFilter(projectTaskFilter);

            Fragment newFragment  = new TaskListView();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, newFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            Utility.setProjectId(lblProjectId.getText().toString());

        }
    };

    Callback<Void> onDeleteProject = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            Toast.makeText(ProjectListView.this.getContext(), "Проект был удалён", Toast.LENGTH_LONG).show();
            ApiManager.getAllProjects(onGetProject);
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Toast.makeText(ProjectListView.this.getContext(), "Проект не был удалён. Попробуйте позже", Toast.LENGTH_LONG).show();
        }
    };

    Callback<Void> onUpdateProject = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {

        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<ValidateModel<List<ProjectInfo>>> onGetProject = new Callback<ValidateModel<List<ProjectInfo>>>() {

        @Override
        public void onResponse(Call<ValidateModel<List<ProjectInfo>>> call, Response<ValidateModel<List<ProjectInfo>>> response) {

            if (response.isSuccessful()){
                List<ProjectInfo> items = response.body().getEntity();
                if (items != null && items.size() > 0) {
                    ProjectArrayAdapter adapter = new ProjectArrayAdapter(ProjectListView.this.getContext(), items);
                    lvProjects.setAdapter(adapter);

                    //lvProjects.setOnClickListener(onRowClick);
                    lvProjects.setOnItemClickListener(onRowClick);
                    registerForContextMenu(lvProjects);
                    Log.d(TAG,"projects fetched: ");
                }
            }
            else{
                Log.d(TAG,"get projects failure: " + response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<ValidateModel<List<ProjectInfo>>> call, Throwable t) {
            Log.d(TAG,"get projects failure: " + t);
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Fragment1 onCreateView");

        View view =inflater.inflate(R.layout.project_list_view, null);
        context = view.getContext();
        lvProjects = view.findViewById(R.id.lvProjects);
        btnCreateProject = view.findViewById(R.id.btnCreateProject);
        btnCreateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectListView.this.context , CreateProjectActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "Fragment1 onResume");
        ApiManager.getAllProjects(onGetProject);
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "Fragment1 onPause");
    }

    public void onStop() {
        super.onStop();
        Log.d(TAG, "Fragment1 onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "Fragment1 onDestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Fragment1 onDestroy");
    }

    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Fragment1 onDetach");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        ApiManager.getAllProjects(onGetProject);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "Задачи");//groupId, itemId, order, title
        menu.add(0, 1, 0, "Пользователи");
        menu.add(0, 2, 0, "Редактировать");
        menu.add(0, 3, 0, "Удалить");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        View view = info.targetView;

        switch (item.getItemId()){
            case 0:{
                TextView lblProjectId = view.findViewById(R.id.lblProjectId);

                TaskFilter projectTaskFilter = new TaskFilter();
                projectTaskFilter.setProjectId(lblProjectId.getText().toString());
                Utility.setTaskFilter(projectTaskFilter);
                Utility.setProjectId(lblProjectId.getText().toString());

                Fragment newFragment  = new TaskListView();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            }
            case 1:{
                TextView lblProjectId = view.findViewById(R.id.lblProjectId);


               // projectTaskFilter.setProjectId(lblProjectId.getText().toString());
                //Utility.setTaskFilter(projectTaskFilter);

                Fragment newFragment  = new UserListView();
                ((UserListView)newFragment).setProjectId(lblProjectId.getText().toString());
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Utility.setProjectId(lblProjectId.getText().toString());
                fragmentTransaction.replace(R.id.content_frame, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            }
            case 2:{
                TextView lblProjectId = view.findViewById(R.id.lblProjectId);
                String projectId = lblProjectId.getText().toString();
                Intent intent = new Intent(ProjectListView.this.context , CreateProjectActivity.class);
                intent.putExtra("projectId", projectId );
                startActivityForResult(intent, 1);
                break;
            }
            case 3:{
                TextView lblProjectId = view.findViewById(R.id.lblProjectId);
                String projectId = lblProjectId.getText().toString();
                ApiManager.deleteProject(projectId, onDeleteProject);
                break;
            }
        }

        /*if(item.getTitle()=="Call"){
            Toast.makeText(getApplicationContext(),"calling code",Toast.LENGTH_LONG).show();
        }
        else if(item.getTitle()=="SMS"){
            Toast.makeText(getApplicationContext(),"sending sms code",Toast.LENGTH_LONG).show();
        }else{
            return false;
        }*/
        return true;
    }
}
