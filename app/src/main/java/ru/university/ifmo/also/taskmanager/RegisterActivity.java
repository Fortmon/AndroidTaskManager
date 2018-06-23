package ru.university.ifmo.also.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.university.ifmo.also.taskmanager.model.UserRegisterResponse;
import ru.university.ifmo.also.taskmanager.model.ValidateModel;
import ru.university.ifmo.also.taskmanager.server.ApiManager;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Register Activity";

    Callback<ValidateModel<UserRegisterResponse>> onRegister = new Callback<ValidateModel<UserRegisterResponse>>() {
        @Override
        public void onResponse(Call<ValidateModel<UserRegisterResponse>> call, Response<ValidateModel<UserRegisterResponse>> response) {
            if (response.isSuccessful()){

            }
        }

        @Override
        public void onFailure(Call<ValidateModel<UserRegisterResponse>> call, Throwable t) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "View was pressed");

        switch (v.getId()) {
            case R.id.btnRegister: {
                //ApiManager.register
                break;
            }
            default:{
                break;
            }
        }
    }
}
