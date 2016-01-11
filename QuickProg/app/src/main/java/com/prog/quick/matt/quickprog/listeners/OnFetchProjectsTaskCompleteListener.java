package com.prog.quick.matt.quickprog.listeners;

import com.prog.quick.matt.quickprog.models.Project;

import java.util.ArrayList;

/**
 * Created by matt on 1/8/16.
 */
public interface OnFetchProjectsTaskCompleteListener {
    void onFetchProjectsTaskComplete(ArrayList<Project> projects);
}
