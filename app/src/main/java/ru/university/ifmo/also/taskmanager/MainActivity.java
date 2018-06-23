package ru.university.ifmo.also.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.university.ifmo.also.taskmanager.adapter.ProjectArrayAdapter;
import ru.university.ifmo.also.taskmanager.model.ProjectInfo;
import ru.university.ifmo.also.taskmanager.model.UserLoginResponse;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;
import ru.university.ifmo.also.taskmanager.server.Utility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "MAIN ACTIVITY";
    String[] menuItems;
    ListView lvProjects;
    Button btnCreateProject;
    DrawerLayout drawerMenuLayout;
    ListView drawerMenuList;


    Callback<ValidateModel<List<ProjectInfo>>> onGetProject = new Callback<ValidateModel<List<ProjectInfo>>>() {
        @Override
        public void onResponse(Call<ValidateModel<List<ProjectInfo>>> call, Response<ValidateModel<List<ProjectInfo>>> response) {

            if (response.isSuccessful()){
                List<ProjectInfo> items = response.body().getEntity();
                if (items != null && items.size() > 0) {
                    ProjectArrayAdapter adapter = new ProjectArrayAdapter(MainActivity.this, items);
                    lvProjects.setAdapter(adapter);
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


    Callback<ValidateModel<UserLoginResponse>> onLogin = new Callback<ValidateModel<UserLoginResponse>>() {
        @Override
        public void onResponse(Call<ValidateModel<UserLoginResponse>> call, Response<ValidateModel<UserLoginResponse>> response) {
            if (response.isSuccessful()) {
                ValidateModel<UserLoginResponse> userLoginResponse = response.body();
                if ( userLoginResponse != null) {
                    if (userLoginResponse.getErrors().isEmpty()) {
                        String accessToken = "Bearer " + userLoginResponse.getEntity().getToken();
                        SharedPreferences sharedPreferences = getSharedPreferences(Utility.getAppName(), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("access_token", accessToken );
                        editor.apply();
                        Utility.setAccessToken(accessToken);
                        Utility.setIsLoginned(true);

                        ApiManager.getAllProjects(onGetProject);
                    }
                }
            } else {
                Log.d(TAG, "login failure: " + response.errorBody());
                Intent intent = new Intent(MainActivity.this, LoginActivity.class );
                startActivity(intent);
            }
        }

        @Override
        public void onFailure(Call<ValidateModel<UserLoginResponse>> call, Throwable t) {
            Log.d(TAG, "login failure: " + t);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class );
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        lvProjects = findViewById(R.id.lvProjects);
        btnCreateProject = findViewById(R.id.btnCreateProject);
        btnCreateProject.setOnClickListener(this);

        if (!Utility.getIsLoginned()){
            SharedPreferences sharedPreferences = getSharedPreferences(Utility.getAppName(), MODE_PRIVATE);
            String login = sharedPreferences.getString("login","");
            String password = sharedPreferences.getString("password","");

            if (!login.isEmpty() && !password.isEmpty()){
                ApiManager.login(login, password, onLogin);
            }
            else{
                Intent intent = new Intent(this, LoginActivity.class );
                startActivity(intent);
            }
        }

        if (Utility.getIsLoginned()) {
            ApiManager.getAllProjects(onGetProject);
        }

        menuItems = getResources().getStringArray(R.array.menu_items);
        drawerMenuLayout = findViewById(R.id.drawer_layout);
        drawerMenuList = findViewById(R.id.left_drawer);

     /*   drawerMenuList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, menuItems));*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity: onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity: onResume()");
        if (!Utility.getIsLoginned()) {
            //  finish();
        }
        else {
            ApiManager.getAllProjects(onGetProject);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity: onDestroy()");
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "View was pressed");

        switch (v.getId()) {
            case R.id.btnCreateProject: {
                Intent intent = new Intent(this, CreateProjectActivity.class );
                startActivity(intent);
                ApiManager.getAllProjects(onGetProject);
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_logout: {
                SharedPreferences sharedPreferences = getSharedPreferences(Utility.getAppName(), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("access_token");
                editor.remove("password");
                editor.commit();
                Utility.setAccessToken("");

                Intent intent = new Intent(this, LoginActivity.class );
                startActivity(intent);
                //finish();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
