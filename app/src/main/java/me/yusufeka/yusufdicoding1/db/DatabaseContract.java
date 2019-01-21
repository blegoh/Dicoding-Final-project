package me.yusufeka.yusufdicoding1.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_NAME = "favorites";

    public static final class DictionaryColumns implements BaseColumns {

        static String TITLE = "title";

        static String RELEASE_DATE = "release_date";

        static String OVERVIEW = "overview";

        static String ADULT = "adult";

        static String GENRES = "genres";

        static String POSTER_PATH = "poster_path";

        static String VOTE_AVERAGE = "vote_average";

        static String ORIGINAL_LANGUAGE = "original_language";

    }

    public static final String AUTHORITY = "me.yusufeka.yusufdicoding1";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
