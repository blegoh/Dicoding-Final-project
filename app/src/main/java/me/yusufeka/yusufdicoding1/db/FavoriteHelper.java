package me.yusufeka.yusufdicoding1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import me.yusufeka.yusufdicoding1.models.Movie;

import static me.yusufeka.yusufdicoding1.db.DatabaseContract.TABLE_NAME;
import static me.yusufeka.yusufdicoding1.db.DatabaseContract.DictionaryColumns.*;

public class FavoriteHelper {

    private Context context;

    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<Movie> getAllData() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Movie> arrayList = new ArrayList<>();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                boolean adult = cursor.getInt(cursor.getColumnIndexOrThrow(ADULT)) == 1;
                movie.setAdult(adult);
                String genres = cursor.getString(cursor.getColumnIndexOrThrow(GENRES));
                String genre[] = genres.split(",");
                ArrayList<Integer> g = new ArrayList<>();
                for (String gn : genre) {
                    g.add(Integer.parseInt(gn));
                }
                movie.setGenreIds(g);
                movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));

                arrayList.add(movie);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public boolean isFavorited(int id){
        Cursor cursor = database.query(TABLE_NAME, null, _ID + " = ?", new String[]{id+""}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        return cursor.getCount() > 0;
    }

    public void toggleFavorite(Movie movie){
        if (isFavorited(movie.getId())){
            delete(movie.getId());
        }else {
            insert(movie);
        }
    }

    public long insert(Movie movie) {
        String g = "";
        for (int i= 0; i < movie.getGenreIds().size();i++){
            if (i == 0)
                g += movie.getGenreIds().get(i)+"";
            else
                g += ","+movie.getGenreIds().get(i);
        }
        ContentValues initialValues = new ContentValues();
        initialValues.put(_ID, movie.getId());
        initialValues.put(ADULT, movie.getAdult());
        initialValues.put(GENRES, g);
        initialValues.put(ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        initialValues.put(OVERVIEW, movie.getOverview());
        initialValues.put(POSTER_PATH, movie.getPosterPath());
        initialValues.put(RELEASE_DATE, movie.getReleaseDate());
        initialValues.put(TITLE, movie.getTitle());
        initialValues.put(VOTE_AVERAGE, movie.getVoteAverage());
        return database.insert(TABLE_NAME, null, initialValues);
    }

    public int delete(int id) {
        return database.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(TABLE_NAME,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(TABLE_NAME
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(TABLE_NAME,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(TABLE_NAME,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(TABLE_NAME,_ID + " = ?", new String[]{id});
    }
}
