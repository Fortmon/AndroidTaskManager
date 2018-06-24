package ru.university.ifmo.also.taskmanager.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.university.ifmo.also.taskmanager.R;
import ru.university.ifmo.also.taskmanager.TasksActivity;
import ru.university.ifmo.also.taskmanager.adapter.ProjectArrayAdapter;
import ru.university.ifmo.also.taskmanager.adapter.TaskArrayAdapter;
import ru.university.ifmo.also.taskmanager.model.ProjectInfo;
import ru.university.ifmo.also.taskmanager.model.TaskInfo;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;

public class TaskListView extends Fragment {
    private final String TAG = "TASK_LIST_VIEW";
    ListView lvTasks;
    String projectId;

    Callback<ValidateModel<List<TaskInfo>>> onGetTasks = new Callback<ValidateModel<List<TaskInfo>>>() {
        @Override
        public void onResponse(Call<ValidateModel<List<TaskInfo>>> call, Response<ValidateModel<List<TaskInfo>>> response) {
            if(response.isSuccessful()){
                List<TaskInfo> items = response.body().getEntity();
                if (items != null && items.size() > 0) {
                    TaskArrayAdapter adapter = new TaskArrayAdapter(TaskListView.this.getContext(), items);
                    lvTasks.setAdapter(adapter);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.d(TAG, "Fragment1 onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Fragment1 onCreate");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Fragment1 onCreateView");

        View view =inflater.inflate(R.layout.task_list_view, null);
        lvTasks = view.findViewById(R.id.lvTasks);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Fragment onActivityCreated");
    }

    public void onStart() {
        super.onStart();
        Log.d(TAG, "Fragment onStart");
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "Fragment onResume");
        ApiManager.getAllTasks(projectId, onGetTasks);
    }
}
