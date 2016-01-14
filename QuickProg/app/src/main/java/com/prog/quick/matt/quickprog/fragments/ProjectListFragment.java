package com.prog.quick.matt.quickprog.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prog.quick.matt.quickprog.DividerItemDecoration;
import com.prog.quick.matt.quickprog.OnItemClickListener;
import com.prog.quick.matt.quickprog.adapters.ProjectListAdapter;
import com.prog.quick.matt.quickprog.R;
import com.prog.quick.matt.quickprog.activities.CreateProjectActivity;
import com.prog.quick.matt.quickprog.listeners.OnFetchProjectsTaskCompleteListener;
import com.prog.quick.matt.quickprog.models.Project;
import com.prog.quick.matt.quickprog.tasks.FetchProjectsTask;

import java.util.ArrayList;

/**
 * Created by matt on 1/6/16.
 */
public class ProjectListFragment extends Fragment {

    private static final String TAG = ProjectListFragment.class.getSimpleName();
    private RecyclerView projectRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new FetchProjectsTask(getContext(),fetchProjectsTaskCompleteListener).execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        View rootView = inflater.inflate(R.layout.project_list_fragment, container);

        projectRecyclerView = (RecyclerView) rootView.findViewById(R.id.project_list_recycler);
        projectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        projectRecyclerView.setAdapter(new ProjectListAdapter(inflater, new OnItemClickListener() {
            @Override
            public void onItemClicked(View v, int pos) {
                Log.d(TAG, "onItemClicked: " + pos);
                ProjectListAdapter adapter = (ProjectListAdapter) projectRecyclerView.getAdapter();
                Project clickedProject = adapter.getItem(pos);

                Callback callback = (Callback) getActivity();
                callback.onItemSelected(clickedProject.getId());
            }
        }));
        projectRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");

                Intent intent = new Intent(getActivity(), CreateProjectActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private OnFetchProjectsTaskCompleteListener fetchProjectsTaskCompleteListener = new OnFetchProjectsTaskCompleteListener() {
        @Override
        public void onFetchProjectsTaskComplete(ArrayList<Project> projects) {
            Log.d(TAG, "onFetchProjectsTaskComplete: " + projects);
            ProjectListAdapter adapter = (ProjectListAdapter) projectRecyclerView.getAdapter();
            adapter.setProjects(projects);
        }
    };

    public interface Callback {
        public void onItemSelected(String id);
    }
}
