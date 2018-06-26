package ru.university.ifmo.also.taskmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.university.ifmo.also.taskmanager.R;
import ru.university.ifmo.also.taskmanager.model.UserInfo;

public class UserArrayAdapter extends ArrayAdapter<UserInfo> {
    private final Context context;
    private final List<UserInfo> items;

    public UserArrayAdapter(Context context, List<UserInfo> items){
        super(context, -1, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.user_list_view_item, parent, false);
        TextView lblUserLogin = rowView.findViewById(R.id.lblUserLogin);
        //TextView lblUserName = rowView.findViewById(R.id.lblUserName);
        TextView lblUserId = rowView.findViewById(R.id.lblUserId);

        UserInfo user =items.get(position);

        lblUserLogin.setText(user.getLogin());
        //lblUserName.setText(user.getLastName() + user.getFirstName() );
        lblUserId.setText(user.getId().toString());

        return rowView;
    }
}
