package com.prog.quick.matt.quickprog.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.prog.quick.matt.quickprog.tasks.CreateProjectTask;
import com.prog.quick.matt.quickprog.listeners.OnCreateProjectTaskCompleteListener;
import com.prog.quick.matt.quickprog.models.Project;
import com.prog.quick.matt.quickprog.R;

/**
 * Created by matt on 1/6/16.
 */
public class CreateProjectActivity extends AppCompatActivity {
    
    private static final String TAG = CreateProjectActivity.class.getSimpleName();
    
    private EditText editProjectName;
    private EditText editProjectDescription;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project_activity);

        editProjectName = (EditText) findViewById(R.id.edit_project_name);
        editProjectDescription = (EditText) findViewById(R.id.edit_project_description);
        
        Button createButton = (Button) findViewById(R.id.create_project_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String projectName = editProjectName.getText().toString();
                String projectDescription = editProjectDescription.getText().toString();
                
                Project project = new Project(projectName, projectDescription);
                new CreateProjectTask(onCreateProjectTaskCompleteListener).execute(project);
            }
        });
    }
    
    private OnCreateProjectTaskCompleteListener onCreateProjectTaskCompleteListener = new OnCreateProjectTaskCompleteListener() {
        @Override
        public void onCreateProjectTaskComplete(boolean success) {
            if (success) {
                Intent intent = new Intent(CreateProjectActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    };
}
