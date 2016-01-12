package com.prog.quick.matt.quickprog.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prog.quick.matt.quickprog.data.ProjectContract.ProjectEntry;

/**
 * Created by matt on 1/11/16.
 */
public class ProjectDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "project.db";

    public ProjectDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PROJECT_TABLE = "CREATE TABLE " + ProjectEntry.TABLE_NAME + " (" +
                ProjectEntry._ID + " TEXT PRIMARY KEY, " +

                ProjectEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ProjectEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                ProjectEntry.COLUMN_PROGRESS + " INTEGER NOT NULL DEFAULT 0, " +

                ProjectEntry.COLUMN_DATE_CREATED + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);";

        db.execSQL(SQL_CREATE_PROJECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProjectEntry.TABLE_NAME);
        onCreate(db);
    }
}
