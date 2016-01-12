package com.prog.quick.matt.quickprog.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by matt on 1/11/16.
 */
public class ProjectContract {
    public static final String CONTENT_AUTHORITY = "com.prog.quick.matt.quickprog";
    public static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PROJECT = "project";

    public static final class ProjectEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROJECT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECT;

        public static final String TABLE_NAME = "project";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PROGRESS = "progress";

        public static final String COLUMN_DATE_CREATED = "date_created";
    }
}
