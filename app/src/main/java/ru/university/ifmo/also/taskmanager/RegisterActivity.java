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
import ru.university.ifmo.also.taskmanager.model.UserInfo;
import ru.university.ifmo.also.taskmanager.model.UserRegisterRequest;
import ru.university.ifmo.also.taskmanager.model.UserRegisterResponse;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Register Activity";

    Callback<ValidateModel<UserInfo>> onRegister = new Callback<ValidateModel<UserInfo>>() {
        @Override
        public void onResponse(Call<ValidateModel<UserInfo>> call, Response<ValidateModel<UserInfo>> response) {
            if (response.isSuccessful()){
                Toast.makeText(RegisterActivity.this, "Пользователь зарегистрирован", Toast.LENGTH_LONG).show();
                RegisterActivity.super.onBackPressed();
            }
            else{
                Log.d(TAG, "register failure: " + response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<ValidateModel<UserInfo>> call, Throwable t) {
            Log.d(TAG, "register failure: " + t);
        }
    };

    EditText tbLogin;
    EditText tbEmail;
    EditText tbFirstName;
    EditText tbLastName;
    EditText tbPassword;
    EditText tbRepeatPassword;
    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        tbLogin = findViewById( R.id.tbLogin);
        tbEmail = findViewById(R.id.tbEmail);
        tbFirstName = findViewById(R.id.tbFirstName);
        tbLastName = findViewById(R.id.tbLastName);
        tbPassword = findViewById(R.id.tbPassword);
        tbRepeatPassword = findViewById(R.id.tbRepeatPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "View was pressed");

        switch (v.getId()) {
            case R.id.btnRegister: {
                UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
                userRegisterRequest.setEmail(tbEmail.getText().toString());
                userRegisterRequest.setLogin(tbLogin.getText().toString());
                userRegisterRequest.setFirstName(tbFirstName.getText().toString());
                userRegisterRequest.setLastName(tbLastName.getText().toString());
                userRegisterRequest.setPassword(tbPassword.getText().toString());

                ApiManager.register(userRegisterRequest, onRegister);
                //ApiManager.register
                break;
            }
            default:{
                break;
            }
        }
    }
}
