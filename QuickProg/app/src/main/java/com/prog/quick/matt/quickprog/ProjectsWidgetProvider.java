package com.prog.quick.matt.quickprog;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.prog.quick.matt.quickprog.activities.MainActivity;
import com.prog.quick.matt.quickprog.services.ProjectsWidgetService;

/**
 * Created by matt on 1/12/16.
 */
public class ProjectsWidgetProvider extends AppWidgetProvider {

    private static final String TAG = ProjectsWidgetProvider.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate: ");

        for (int i = 0; i < appWidgetIds.length; i++) {
            int id = appWidgetIds[i];

            Intent serviceIntent = new Intent(context, ProjectsWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rvs = new RemoteViews(context.getPackageName(), R.layout.widget_project_list);
            rvs.setRemoteAdapter(id, R.id.widget_project_list_view, serviceIntent);

            rvs.setEmptyView(R.id.widget_projects, R.id.widget_projects_empty);

            Intent viewIntent = new Intent(context, MainActivity.class);
            viewIntent.setAction(Intent.ACTION_VIEW);
            viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            viewIntent.setData(Uri.parse(viewIntent.toUri(Intent.URI_INTENT_SCHEME)));

            PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);
            rvs.setPendingIntentTemplate(R.id.widget_project_list_view, viewPendingIntent);

            appWidgetManager.updateAppWidget(id, rvs);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
