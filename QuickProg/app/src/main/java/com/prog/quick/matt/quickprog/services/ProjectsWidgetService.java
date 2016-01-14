package com.prog.quick.matt.quickprog.services;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.prog.quick.matt.quickprog.R;
import com.prog.quick.matt.quickprog.data.ProjectContract;
import com.prog.quick.matt.quickprog.fragments.ProjectDetailFragment;

/**
 * Created by matt on 1/12/16.
 */
public class ProjectsWidgetService extends RemoteViewsService {

    private static final String TAG = ProjectsWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ProjectsRemoteViewFactory(getApplicationContext());
    }

    private class ProjectsRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context context;
        private Cursor cursor;

        public ProjectsRemoteViewFactory (Context context) {
            this.context = context;
        }

        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate: ");
        }

        @Override
        public void onDataSetChanged() {
            if (cursor != null) {
                cursor.close();
            }

            cursor = context.getContentResolver().query(ProjectContract.ProjectEntry.CONTENT_URI, null, null, null, null);
            Log.d(TAG, "onDataSetChanged: ");
        }

        @Override
        public void onDestroy() {
            if (cursor != null) {
                cursor.close();
            }
        }

        @Override
        public int getCount() {
            return (cursor != null) ? cursor.getCount() : 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            cursor.moveToPosition(position);
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_project_list_item);
            rv.setTextViewText(R.id.widget_project_list_item_name, cursor.getString(cursor.getColumnIndex("name")));

            ProjectDetailFragment.Progress progress = ProjectDetailFragment.Progress.values()[cursor.getInt(cursor.getColumnIndex("progress"))];
            rv.setTextViewText(R.id.widget_project_list_item_progress, progress.getDisplayName());
            rv.setTextColor(R.id.widget_project_list_item_progress, progress.getColor());

            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
