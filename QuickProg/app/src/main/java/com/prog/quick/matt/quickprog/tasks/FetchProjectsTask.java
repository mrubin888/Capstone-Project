package com.prog.quick.matt.quickprog.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.prog.quick.matt.quickprog.data.ProjectContract;
import com.prog.quick.matt.quickprog.listeners.OnFetchProjectsTaskCompleteListener;
import com.prog.quick.matt.quickprog.models.Project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by matt on 1/8/16.
 */
public class FetchProjectsTask extends AsyncTask<String, Void, ArrayList<Project>> {

    private static final String TAG = FetchProjectsTask.class.getSimpleName();

    private Context context;
    private OnFetchProjectsTaskCompleteListener listener;

    public FetchProjectsTask(Context context, OnFetchProjectsTaskCompleteListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected ArrayList<Project> doInBackground(String... params) {

        String resultStr = "";
        ArrayList<Project> projects = new ArrayList<Project>();

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String urlStr = "http://162.243.8.98:8082/api/project";
        URL url = null;
        try {
            url = new URL(urlStr);
        }
        catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                return null;
            }

            resultStr = buffer.toString();
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        try {
            JSONArray resultsArr = new JSONArray(resultStr);
            for (int i = 0; i < resultsArr.length(); i++) {
                JSONObject obj = (JSONObject) resultsArr.get(i);
                Project project = new Project(obj);
                projects.add(project);
            }
        }
        catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        ArrayList<ContentValues> cvList = new ArrayList<ContentValues>();
        for (Project project : projects) {
            ContentValues cv = new ContentValues();
            cv.put(ProjectContract.ProjectEntry._ID, project.getId());
            cv.put(ProjectContract.ProjectEntry.COLUMN_NAME, project.getName());
            cv.put(ProjectContract.ProjectEntry.COLUMN_DESCRIPTION, project.getDescription());

            cvList.add(cv);
        }

        if (!cvList.isEmpty()) {
            ContentValues[] cvArr = new ContentValues[cvList.size()];
            cvList.toArray(cvArr);

            int inserted = context.getContentResolver().bulkInsert(ProjectContract.ProjectEntry.CONTENT_URI, cvArr);
            Log.d(TAG, "Inserted " + inserted);
        }

        return projects;
    }

    @Override
    protected void onPostExecute(ArrayList<Project> projects) {
        listener.onFetchProjectsTaskComplete(projects);
    }
}
