package com.prog.quick.matt.quickprog.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matt on 1/8/16.
 */
public class Project {

    private static final String TAG = Project.class.getSimpleName();

    private String id;
    private String name;
    private String description;

    public Project(String name, String description) {
        id = "undefined";
        this.name = name;
        this.description = description;
    }

    public Project(JSONObject data) {
        try {
            id = data.getString("_id");
        } catch (JSONException e) {
            Log.e(TAG, "Project: ", e);
        }

        try {
            name = data.getString("name");
        } catch (JSONException e) {
            Log.e(TAG, "Project: ", e);
        }

        try {
            description = data.getString("description");
        } catch (JSONException e) {
            Log.e(TAG, "Project: ", e);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String str = "";
        str += "["
                + "id: " + id + ", "
                + "name: " + name + ", "
                + "description: " + description
                + "]";
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Project project = (Project) o;

        if (id == null || project.getId() == null) {
            return false;
        }

        return id.equals(project.getId());
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }
}
