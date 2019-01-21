package me.yusufeka.yusufdicoding1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static me.yusufeka.yusufdicoding1.db.DatabaseContract.TABLE_NAME;
import static me.yusufeka.yusufdicoding1.db.DatabaseContract.DictionaryColumns.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "db_favorites";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String CREATE_TABLE_FAVORITES = "create table " + TABLE_NAME +
            " (" + _ID + " integer primary key, " +
            TITLE + " text not null, " +
            RELEASE_DATE + " text not null, " +
            OVERVIEW + " text not null, " +
            ADULT + " integer not null, " +
            GENRES + " text not null, " +
            POSTER_PATH + " text not null, " +
            ORIGINAL_LANGUAGE + " text not null, " +
            VOTE_AVERAGE + " real not null);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}
