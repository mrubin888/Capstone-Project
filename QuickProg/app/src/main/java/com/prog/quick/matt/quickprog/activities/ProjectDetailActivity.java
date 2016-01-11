package com.prog.quick.matt.quickprog.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.prog.quick.matt.quickprog.fragments.ProjectDetailFragment;
import com.prog.quick.matt.quickprog.R;

/**
 * Created by matt on 1/10/16.
 */
public class ProjectDetailActivity extends AppCompatActivity {

    private static final String TAG = ProjectDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_detail_activity);
        Log.d(TAG, "onCreate: ");

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putString("id", getIntent().getStringExtra("id"));


            Log.d(TAG, "onCreate: 2");
            ProjectDetailFragment projectDetailFragment = new ProjectDetailFragment();
            projectDetailFragment.setArguments(args);

            Log.d(TAG, "onCreate: 3");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.project_details_container, projectDetailFragment)
                    .commit();
        }
    }
}
