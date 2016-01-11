package com.prog.quick.matt.quickprog.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prog.quick.matt.quickprog.R;

/**
 * Created by matt on 1/10/16.
 */
public class ProjectDetailFragment extends Fragment {

    private static final String TAG = ProjectDetailFragment.class.getSimpleName();

    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
        Log.d(TAG, "onCreate: " + id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.project_detail_fragment, container, false);

        return rootView;
    }
}
