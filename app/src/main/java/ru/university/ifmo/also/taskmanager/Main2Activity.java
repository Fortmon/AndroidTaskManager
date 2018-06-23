package ru.university.ifmo.also.taskmanager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MAIN_ACTIVITY";
    FragmentTransaction fragmentTransaction;
    Fragment frListProjects;


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

//                        ApiManager.getAllProjects(onGetProject);
                    }
                }
            } else {
                Log.d(TAG, "login failure: " + response.errorBody());
                Intent intent = new Intent(Main2Activity.this, LoginActivity.class );
                startActivity(intent);
            }
        }

        @Override
        public void onFailure(Call<ValidateModel<UserLoginResponse>> call, Throwable t) {
            Log.d(TAG, "login failure: " + t);
            Intent intent = new Intent(Main2Activity.this, LoginActivity.class );
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        frListProjects = new ProjectListView();


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
          //  ApiManager.getAllProjects(onGetProject);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentTransaction = getFragmentManager().beginTransaction();

        switch (id){
            case R.id.nav_projects:{
                fragmentTransaction.add(R.id.content_frame, frListProjects);
                break;
            }

            case R.id.nav_exit:{
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

            default:{
                break;
            }
        }

        fragmentTransaction.commit();

       /* if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
           // ApiManager.getAllProjects(onGetProject);
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
}
