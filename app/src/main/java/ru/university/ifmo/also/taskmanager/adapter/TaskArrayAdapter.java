package ru.university.ifmo.also.taskmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.university.ifmo.also.taskmanager.R;
import ru.university.ifmo.also.taskmanager.model.ProjectInfo;
import ru.university.ifmo.also.taskmanager.model.TaskInfo;

public class TaskArrayAdapter extends ArrayAdapter<TaskInfo> {
    private final Context context;
    private final List<TaskInfo> items;

    public TaskArrayAdapter(Context context, List<TaskInfo> items){
        super(context, -1, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_list_view_item, parent, false);
        TextView lblTaskTitle = rowView.findViewById(R.id.lblTaskTitle);
        TextView lblDescription = rowView.findViewById(R.id.lblTaskDescription);
        TextView lblTaskId = rowView.findViewById(R.id.lblTaskId);

        lblTaskTitle.setText(items.get(position).getTitle());
        lblDescription.setText(items.get(position).getDescription());
        lblTaskId.setText(items.get(position).getId().toString());

        return rowView;
    }
}
