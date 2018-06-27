package ru.university.ifmo.also.taskmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.university.ifmo.also.taskmanager.CreateProjectActivity;
import ru.university.ifmo.also.taskmanager.R;
import ru.university.ifmo.also.taskmanager.TaskDetail;
import ru.university.ifmo.also.taskmanager.TasksActivity;
import ru.university.ifmo.also.taskmanager.adapter.ProjectArrayAdapter;
import ru.university.ifmo.also.taskmanager.adapter.TaskArrayAdapter;
import ru.university.ifmo.also.taskmanager.model.ProjectInfo;
import ru.university.ifmo.also.taskmanager.model.TaskFilter;
import ru.university.ifmo.also.taskmanager.model.TaskInfo;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;
import ru.university.ifmo.also.taskmanager.server.Utility;

public class TaskListView extends Fragment {
    private final String TAG = "TASK_LIST_VIEW";
    ListView lvTasks;
    String projectId;
    Context context;
    Button btnCreateTask;

    AdapterView.OnItemClickListener onRowClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View rowView, int position, long id) {
            TextView lblTaskId = rowView.findViewById(R.id.lblTaskId);

            Intent intent = new Intent(TaskListView.this.context, TaskDetail.class);
            intent.putExtra("taskId", lblTaskId.getText().toString());
            startActivityForResult(intent, 1);
        }
    };

    Callback<ValidateModel<List<TaskInfo>>> onGetTasks = new Callback<ValidateModel<List<TaskInfo>>>() {
        @Override
        public void onResponse(Call<ValidateModel<List<TaskInfo>>> call, Response<ValidateModel<List<TaskInfo>>> response) {
            if(response.isSuccessful()){
                List<TaskInfo> items = response.body().getEntity();
                if (items != null && items.size() > 0) {
                    TaskArrayAdapter adapter = new TaskArrayAdapter(TaskListView.this.getContext(), items);
                    lvTasks.setAdapter(adapter);
                    lvTasks.setOnItemClickListener(onRowClick);
                    registerForContextMenu(lvTasks);
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
        context = view.getContext();
        lvTasks = view.findViewById(R.id.lvTasks);
        btnCreateTask = view.findViewById(R.id.btnCreateTask);
        btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListView.this.context , TaskDetail.class);
                startActivityForResult(intent, 1);
            }
        });

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
        TaskFilter taskFilter = Utility.getTaskFilter();

        ApiManager.getAllTasks(taskFilter == null ? new TaskFilter() : taskFilter, onGetTasks);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        TaskFilter taskFilter = Utility.getTaskFilter();
        ApiManager.getAllTasks(taskFilter == null ? new TaskFilter() : taskFilter, onGetTasks);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Редактировать");
        menu.add(0, v.getId(), 0, "Удалить");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
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
