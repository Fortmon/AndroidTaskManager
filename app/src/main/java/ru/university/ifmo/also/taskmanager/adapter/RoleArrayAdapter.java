package ru.university.ifmo.also.taskmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.university.ifmo.also.taskmanager.model.Role;
import ru.university.ifmo.also.taskmanager.R;

public class RoleArrayAdapter extends ArrayAdapter<Role> {
    private final Context context;
    private final List<Role> items;

    public RoleArrayAdapter(Context context, List<Role> items) {
        super(context, -1, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.invite_user_role_item, parent, false);
        TextView cbRole = rowView.findViewById(R.id.tbRoleTitle);


        cbRole.setText(items.get(position).getTitle());
        //lblDescription.setText(items.get(position).getDescription());

        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.invite_user_role_item, parent, false);


            TextView cbRole = convertView.findViewById(R.id.tbRoleTitle);
         //   TextView txtRoleValue = convertView.findViewById(R.id.txtRoleValue);

            cbRole.setText(items.get(position).getTitle());
           // txtRoleValue.setText(items.get(position).getValue());
        }
        return convertView;
    }
}
