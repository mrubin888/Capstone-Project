package com.prog.quick.matt.quickprog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by matt on 1/6/16.
 */
public class CreateProjectActivity extends AppCompatActivity {
    
    private static final String TAG = CreateProjectActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project_activity);
    }
}
