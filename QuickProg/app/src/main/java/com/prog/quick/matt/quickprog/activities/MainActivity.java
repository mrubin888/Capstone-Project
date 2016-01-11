package com.prog.quick.matt.quickprog.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.prog.quick.matt.quickprog.fragments.ProjectListFragment;
import com.prog.quick.matt.quickprog.R;

public class MainActivity extends AppCompatActivity implements ProjectListFragment.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemSelected(String id) {
        Log.d(TAG, "onItemSelected: " + id);
        Intent intent = new Intent(this, ProjectDetailActivity.class);
        intent.putExtra("id", id);

        startActivity(intent);
    }
}
