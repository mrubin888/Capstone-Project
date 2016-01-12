package com.prog.quick.matt.quickprog.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by matt on 1/11/16.
 */
public class ProjectContentProvider extends ContentProvider {
    private static final String TAG = ProjectContentProvider.class.getSimpleName();

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private static ProjectDBHelper projectDBHelper;

    private static final int PROJECTS = 1;
    private static final int PROJECT_BY_ID = 2;

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(ProjectContract.CONTENT_AUTHORITY, ProjectContract.PATH_PROJECT, PROJECTS);
        matcher.addURI(ProjectContract.CONTENT_AUTHORITY, ProjectContract.PATH_PROJECT + "/*", PROJECT_BY_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        projectDBHelper = new ProjectDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (uriMatcher.match(uri)) {
            case PROJECTS:
                retCursor = projectDBHelper.getReadableDatabase().query(ProjectContract.ProjectEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case PROJECT_BY_ID:
                String id = uri.getPathSegments().get(1);
                retCursor = projectDBHelper.getReadableDatabase().query(ProjectContract.ProjectEntry.TABLE_NAME,
                        projection,
                        "_id=?",
                        new String[]{id},
                        null,
                        null,
                        null
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        };

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case PROJECTS:
                return ProjectContract.ProjectEntry.CONTENT_TYPE;
            case PROJECT_BY_ID:
                return ProjectContract.ProjectEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = projectDBHelper.getWritableDatabase().insertWithOnConflict(ProjectContract.ProjectEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String id = null;

        if (uriMatcher.match(uri) == PROJECT_BY_ID) {
            id = uri.getPathSegments().get(1);
        }

        if (id != null) {
            return projectDBHelper.getWritableDatabase().delete(ProjectContract.ProjectEntry.TABLE_NAME, "_id=?", new String[]{id});
        }
        return -1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String id = null;

        if (uriMatcher.match(uri) == PROJECT_BY_ID) {
            id = uri.getPathSegments().get(1);
        }

        if (id != null) {
            return projectDBHelper.getWritableDatabase().update(ProjectContract.ProjectEntry.TABLE_NAME, values, "_id=?", new String[]{id});
        }
        return -1;
    }
}
