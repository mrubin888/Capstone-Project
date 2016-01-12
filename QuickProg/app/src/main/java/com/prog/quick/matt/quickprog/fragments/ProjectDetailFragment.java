package com.prog.quick.matt.quickprog.fragments;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.prog.quick.matt.quickprog.R;
import com.prog.quick.matt.quickprog.data.ProjectContract;

import static com.prog.quick.matt.quickprog.R.style.AppTheme;

/**
 * Created by matt on 1/10/16.
 */
public class ProjectDetailFragment extends Fragment {

    private static final String TAG = ProjectDetailFragment.class.getSimpleName();

    private int progressIndex;

    private enum Progress {
        NEW("New", R.color.colorNew),
        IN_PROGRESS("In progress", R.color.colorInProgress),
        COMPLETE("Complete", R.color.colorComplete);

        private String displayName;
        private int color;

        Progress(String displayName, int color) {
            this.displayName = displayName;
            this.color = color;
        }

        public String getDisplayName() { return displayName; }

        public int getColor() { return color; }
    };

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

        String id = getArguments().getString("id");

        Log.d(TAG, "onCreateView: " + id);
        Uri uri = ProjectContract.ProjectEntry.CONTENT_URI
                .buildUpon()
                .appendPath(id)
                .build();

        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor.getCount() != 1) {
            Log.w(TAG, "Fetched " + cursor.getCount() + " movies from SQLite (expected 1)");
        }
        else {
            cursor.moveToFirst();
            Log.d(TAG, "onCreateView: " + cursor.getString(cursor.getColumnIndex("name")));
            loadViewContentFromCursor(rootView, cursor);
            cursor.close();

            Log.d(TAG, "onCreateView: COMPLETE");
        }

        return rootView;
    }

    private void loadViewContentFromCursor(View rootView, Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String description = cursor.getString(cursor.getColumnIndex("description"));

        progressIndex = cursor.getInt(cursor.getColumnIndex("progress"));

        TextView nameTextView = (TextView) rootView.findViewById(R.id.project_detail_name);
        nameTextView.setText(name);

        TextView descriptionTextView = (TextView) rootView.findViewById(R.id.project_detail_description);
        descriptionTextView.setText(description);

        loadProgressUI(rootView);
    }

    private void loadProgressUI (final View rootView) {
        Progress progress = Progress.values()[progressIndex];

        TextView progressTextView = (TextView) rootView.findViewById(R.id.project_detail_progress);
        progressTextView.setText(progress.getDisplayName());
        progressTextView.setTextColor(getResources().getColor(progress.getColor()));

        Button progressButton = (Button) rootView.findViewById(R.id.project_detail_progress_button);
        if (progress != Progress.COMPLETE) {
            Progress nextProgress = Progress.values()[progressIndex+1];
            progressButton.setText("Mark as " + nextProgress.getDisplayName());
            progressButton.setBackgroundColor(getResources().getColor(nextProgress.getColor()));

            progressButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = ProjectContract.ProjectEntry.CONTENT_URI
                            .buildUpon()
                            .appendPath(id)
                            .build();
                    ContentValues values = new ContentValues();
                    values.put("progress", progressIndex + 1);
                    int result = getActivity().getContentResolver().update(uri, values, null, null);
                    if (result != -1) {
                        progressIndex++;
                        loadProgressUI(rootView);
                    }
                }
            });
        }
        else {
            progressButton.setVisibility(View.GONE);
        }

    }
}
