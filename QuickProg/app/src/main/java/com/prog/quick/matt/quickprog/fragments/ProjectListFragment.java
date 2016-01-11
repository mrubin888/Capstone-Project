package com.prog.quick.matt.quickprog.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
    private ProjectListAdapter projectListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectListAdapter = new ProjectListAdapter(getContext());

        new FetchProjectsTask(fetchProjectsTaskCompleteListener).execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View rootView = inflater.inflate(R.layout.project_list_fragment, container);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");

                Intent intent = new Intent(getActivity(), CreateProjectActivity.class);
                startActivity(intent);
            }
        });
        ListView projectListView = (ListView) rootView.findViewById(R.id.project_list);
        projectListView.setAdapter(projectListAdapter);
        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectListAdapter adapter = (ProjectListAdapter) parent.getAdapter();
                Project clickedProject = (Project) adapter.getItem(position);

                Callback callback = (Callback) getActivity();
                callback.onItemSelected(clickedProject.getId());
            }
        });

        return rootView;
    }

    private OnFetchProjectsTaskCompleteListener fetchProjectsTaskCompleteListener = new OnFetchProjectsTaskCompleteListener() {
        @Override
        public void onFetchProjectsTaskComplete(ArrayList<Project> projects) {
            Log.d(TAG, "onFetchProjectsTaskComplete: " + projects);
            projectListAdapter.setProjects(projects);
        }
    };

    public interface Callback {
        public void onItemSelected(String id);
    }
}
