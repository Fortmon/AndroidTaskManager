package ru.university.ifmo.also.taskmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.university.ifmo.also.taskmanager.CreateProjectActivity;
import ru.university.ifmo.also.taskmanager.R;
import ru.university.ifmo.also.taskmanager.adapter.TaskArrayAdapter;
import ru.university.ifmo.also.taskmanager.adapter.UserArrayAdapter;
import ru.university.ifmo.also.taskmanager.model.TaskFilter;
import ru.university.ifmo.also.taskmanager.model.TaskInfo;
import ru.university.ifmo.also.taskmanager.model.UserInfo;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;
import ru.university.ifmo.also.taskmanager.server.Utility;

public class UserListView extends Fragment {
    private static final String TAG="USER_LIST_VIEW";
    private Context context;
    private Button btnInviteUser;
    private ListView lvUsers;
    private String projectId;
    AdapterView.OnItemClickListener onRowClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View rowView, int position, long id) {
        }
    };

    public void setProjectId (String value){
        projectId = value;
    }

    Callback<ValidateModel<List<UserInfo>>> onGetUsers = new Callback<ValidateModel<List<UserInfo>>>() {
        @Override
        public void onResponse(Call<ValidateModel<List<UserInfo>>> call, Response<ValidateModel<List<UserInfo>>> response) {
            if(response.isSuccessful()){
                List<UserInfo> items = response.body().getEntity();
                if (items != null && items.size() > 0) {
                    UserArrayAdapter adapter = new UserArrayAdapter(UserListView.this.getContext(), items);
                    lvUsers.setAdapter(adapter);
                    lvUsers.setOnItemClickListener(onRowClick);
                    registerForContextMenu(lvUsers);
                }
            }
            else{
                Log.d(TAG, "get users failure: " + response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<ValidateModel<List<UserInfo>>> call, Throwable t) {
            Log.d(TAG, "get users failure: " + t );
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

        View view =inflater.inflate(R.layout.user_list_view, null);
        context = view.getContext();
        lvUsers = view.findViewById(R.id.lvUsers);
        btnInviteUser = view.findViewById(R.id.btnInviteUser);
        btnInviteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment inviteUser = new InviteUser();
                inviteUser.show(getFragmentManager(), "invite_user");
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

        ApiManager.getUsers( projectId, onGetUsers);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        ApiManager.getUsers( projectId, onGetUsers);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Исключить");
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
