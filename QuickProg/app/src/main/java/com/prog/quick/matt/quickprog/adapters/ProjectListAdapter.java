package com.prog.quick.matt.quickprog.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prog.quick.matt.quickprog.OnItemClickListener;
import com.prog.quick.matt.quickprog.ProjectViewHolder;
import com.prog.quick.matt.quickprog.R;
import com.prog.quick.matt.quickprog.models.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectViewHolder> {

    private List<Project> projects = new ArrayList<Project>();
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public ProjectListAdapter(LayoutInflater inflater, OnItemClickListener listener) {
        this.inflater = inflater;
        this.listener = listener;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProjectViewHolder(inflater.inflate(R.layout.project_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        holder.bind(projects.get(position));
        holder.setOnItemClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void addProject(int pos, Project project) {
        projects.add(pos, project);
        notifyItemInserted(pos);
    }

    public void deleteProject(int pos) {
        projects.remove(pos);
        notifyItemRemoved(pos);
    }

    public void moveProject(int srcPos, int destPos) {
        Project project = projects.remove(srcPos);
        projects.add(destPos, project);
        notifyItemMoved(srcPos, destPos);
    }

    public void setProjects(final List<Project> projects) {
        // Remove all deleted items.
        for (int i = this.projects.size() - 1; i >= 0; --i) {
            if (projects.indexOf(this.projects.get(i)) < 0) {
                deleteProject(i);
            }
        }

        // Add and move items.
        for (int i = 0; i < projects.size(); ++i) {
            Project project = projects.get(i);
            int pos = this.projects.indexOf(project);
            if (pos < 0) {
                addProject(i, project);
            } else if (pos != i) {
                moveProject(i, pos);
            }
        }
    }

    public Project getItem(int position) {
        return projects.get(position);
    }
}