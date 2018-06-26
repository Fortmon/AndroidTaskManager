package ru.university.ifmo.also.taskmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.university.ifmo.also.taskmanager.CreateProjectActivity;
import ru.university.ifmo.also.taskmanager.R;
import ru.university.ifmo.also.taskmanager.adapter.RoleArrayAdapter;
import ru.university.ifmo.also.taskmanager.model.Role;
import ru.university.ifmo.also.taskmanager.server.ApiManager;
import ru.university.ifmo.also.taskmanager.server.Utility;

public class InviteUser extends DialogFragment {
    private static final String TAG = "INVITE_USER";
    private String projectId;
    TextView tvUserLogin;
    Spinner dpdRoles;

    Callback<Void> onInviteUser = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            Toast.makeText(InviteUser.this.getContext(), "Приглашение было отправлено", Toast.LENGTH_LONG).show();
            dismiss();
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Toast.makeText(InviteUser.this.getContext(), "Приглашение не было отправлено", Toast.LENGTH_LONG).show();
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Fragment1 onCreateView");

        List<Role> roles = new ArrayList<>();
        roles.add(new Role("Менеджер",1));
        roles.add(new Role("Программист",2));
        roles.add(new Role("Тестировщик",4));

        View view =inflater.inflate(R.layout.invite_user, null);

        dpdRoles = view.findViewById(R.id.dpdRoles);
        Button btnInvite = view.findViewById(R.id.btnInvite);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        final TextView tvUserLogin = view.findViewById(R.id.txtLogin);
        projectId = Utility.getProjectId();

        RoleArrayAdapter adapter = new RoleArrayAdapter(InviteUser.this.getContext(), roles);
        dpdRoles.setAdapter(adapter);

        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (projectId != null && tvUserLogin != null && !projectId.isEmpty() && !tvUserLogin.getText().toString().isEmpty()) {
                    int role = (int) dpdRoles.getSelectedItemId();
                    ApiManager.inviteUser(projectId, tvUserLogin.getText().toString(), role + 1, onInviteUser);
                }
                else {
                    Toast.makeText(InviteUser.this.getContext(), "Приглашение не было отправлено", Toast.LENGTH_LONG).show();
                }
                //InviteUser.this.
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
