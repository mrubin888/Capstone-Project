package com.prog.quick.matt.quickprog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prog.quick.matt.quickprog.R;
import com.prog.quick.matt.quickprog.models.Project;

import java.util.ArrayList;

/**
 * Created by matt on 1/8/16.
 */
public class ProjectListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Project> projects;

    public ProjectListAdapter(Context context) {
        this(context, new ArrayList<Project>());
    }

    public ProjectListAdapter(Context context, ArrayList<Project> projects) {
        this.context = context;
        this.projects = projects;
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int position) {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ProjectHolder {
        TextView nameView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Project project = projects.get(position);

        ProjectHolder holder = new ProjectHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.project_list_item, null);

        holder.nameView = (TextView) root.findViewById(R.id.project_list_item_name);
        holder.nameView.setText(project.getName());

        return root;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
        notifyDataSetChanged();
    }
}
