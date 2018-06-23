package ru.university.ifmo.also.taskmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.university.ifmo.also.taskmanager.R;
import ru.university.ifmo.also.taskmanager.TasksActivity;
import ru.university.ifmo.also.taskmanager.model.ProjectInfo;

public class ProjectArrayAdapter extends ArrayAdapter<ProjectInfo> {
    private final Context context;
    private final List<ProjectInfo> items;

    View.OnClickListener onRowClick = new View.OnClickListener() {
        @Override
        public void onClick(View rowView) {
            TextView lblProject = rowView.findViewById(R.id.lblProjectTitle);
            TextView lblDescription = rowView.findViewById(R.id.lblProjectDescription);
            TextView lblProjectId = rowView.findViewById(R.id.lblProjectId);

            Intent intent = new Intent(ProjectArrayAdapter.this.context, TasksActivity.class);

            intent.putExtra("title", lblProject.getText().toString() );
            intent.putExtra("description", lblDescription.getText().toString() );
            intent.putExtra("projectId", lblProjectId.getText().toString() );

            ProjectArrayAdapter.this.context.startActivity(intent);
        }
    };

    public ProjectArrayAdapter(Context context, List<ProjectInfo> items){
        super(context, -1, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.project_list_view_item, parent, false);
        TextView lblProject = rowView.findViewById(R.id.lblProjectTitle);
        TextView lblDescription = rowView.findViewById(R.id.lblProjectDescription);
        TextView lblProjectId = rowView.findViewById(R.id.lblProjectId);
        rowView.setOnClickListener(onRowClick);

        lblProject.setText(items.get(position).getTitle());
        lblDescription.setText(items.get(position).getDescription());
        lblProjectId.setText(items.get(position).getId().toString());

        return rowView;
    }
}
