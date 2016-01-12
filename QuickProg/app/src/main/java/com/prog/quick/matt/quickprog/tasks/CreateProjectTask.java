package com.prog.quick.matt.quickprog.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.prog.quick.matt.quickprog.listeners.OnCreateProjectTaskCompleteListener;
import com.prog.quick.matt.quickprog.models.Project;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by matt on 1/8/16.
 */
public class CreateProjectTask extends AsyncTask<Project, Void, Boolean> {

    private static final String TAG = CreateProjectTask.class.getSimpleName();

    private OnCreateProjectTaskCompleteListener listener;

    public CreateProjectTask(OnCreateProjectTaskCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Project... params) {
        if (params.length != 1) {
            Log.e(TAG, "doInBackground: Create Project Task should take exactly one param");
        }

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
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            JSONObject postData = new JSONObject();
            postData.put("name", params[0].getName());
            postData.put("description", params[0].getDescription());

            Log.d(TAG, "doInBackground: " + postData.toString());

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(postData.toString());

            writer.flush();
            writer.close();
            os.close();
            int responseCode=connection.getResponseCode();

            Log.d(TAG, "doInBackground: RESPONSE: " + responseCode);
        }
        catch (Exception e) {
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

        return true;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        listener.onCreateProjectTaskComplete(success);
    }
}
