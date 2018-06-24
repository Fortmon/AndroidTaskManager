package ru.university.ifmo.also.taskmanager.fragment;

import android.app.Activity;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
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
import ru.university.ifmo.also.taskmanager.adapter.ProjectArrayAdapter;
import ru.university.ifmo.also.taskmanager.model.ProjectInfo;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;

public class ProjectListView extends Fragment {
    private final String TAG = "PROJECT_LIST_VIEW";
    ListView lvProjects;

    Callback<ValidateModel<List<ProjectInfo>>> onGetProject = new Callback<ValidateModel<List<ProjectInfo>>>() {

        @Override
        public void onResponse(Call<ValidateModel<List<ProjectInfo>>> call, Response<ValidateModel<List<ProjectInfo>>> response) {

            if (response.isSuccessful()){
                List<ProjectInfo> items = response.body().getEntity();
                if (items != null && items.size() > 0) {
                    ProjectArrayAdapter adapter = new ProjectArrayAdapter(ProjectListView.this.getContext(), items);
                    lvProjects.setAdapter(adapter);
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

        View view =inflater.inflate(R.layout.project_list_view, null);
        lvProjects = view.findViewById(R.id.lvProjects);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Fragment1 onActivityCreated");
    }

    public void onStart() {
        super.onStart();
        Log.d(TAG, "Fragment1 onStart");
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
}
