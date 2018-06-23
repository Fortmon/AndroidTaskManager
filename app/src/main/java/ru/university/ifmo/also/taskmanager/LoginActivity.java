package ru.university.ifmo.also.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.university.ifmo.also.taskmanager.model.UserLoginResponse;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;
import ru.university.ifmo.also.taskmanager.server.Utility;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="Login Activity";
    private static final String BASE_URL="http://192.168.1.43:5000/api/";

    Callback<ValidateModel<UserLoginResponse>> onLogin = new Callback<ValidateModel<UserLoginResponse>>() {
        @Override
        public void onResponse(Call<ValidateModel<UserLoginResponse>> call, Response<ValidateModel<UserLoginResponse>> response) {
            Log.d(TAG, "login successful");
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
                        /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);*/
                        finish();
                    }
                }
            } else {
                Log.d(TAG, "login failure: " + response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<ValidateModel<UserLoginResponse>> call, Throwable t) {
            Log.d(TAG, "login failure: " + t);
        }
    };

    EditText tbLogin;
    EditText tbPassword;
    Button btnLogin;
    Button btnRegister;
    CheckBox cbRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Log.d(TAG, "Start Login Activity");

        SharedPreferences sharedPreferences = getSharedPreferences(Utility.getAppName(), MODE_PRIVATE);
        String login = sharedPreferences.getString("login","");
        String password = sharedPreferences.getString("password","");


        tbLogin = findViewById(R.id.tbLogin);
        tbPassword = findViewById(R.id.tbPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        cbRememberMe = findViewById(R.id.cbRemember);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        if (!login.isEmpty()){
            tbLogin.setText(login);
            cbRememberMe.setChecked(true);
        }

        if (!password.isEmpty()){
            //SHOW WAIT
            ApiManager.login( login, password, onLogin );
        }
    }

    @Override
    public void onClick(View v){
        Log.d(TAG, "View was pressed");

        switch(v.getId()){
            case R.id.btnLogin:{
                ApiManager.login( tbLogin.getText().toString(), tbPassword.getText().toString(), onLogin );
                if (cbRememberMe.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences(Utility.getAppName(), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", tbLogin.getText().toString() );
                    editor.putString("password", tbPassword.getText().toString() );
                    editor.apply();
                }
                else{
                    SharedPreferences sharedPreferences = getSharedPreferences(Utility.getAppName(), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("login");
                    editor.apply();
                }
                break;
            }
            case R.id.btnRegister: {
                Log.d(TAG, "Button Register was pressed");
                Intent intent = new Intent(this, RegisterActivity.class );
                startActivity(intent);
                break;
            }
            default:{
                Log.d(TAG, "Something was pressed");
                break;
            }
        }
    }
}
