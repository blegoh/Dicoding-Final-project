package me.yusufeka.dicodingfavoriteapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.yusufeka.dicodingfavoriteapp.R;
import me.yusufeka.dicodingfavoriteapp.db.DatabaseContract;

public class FavoriteAdapter extends CursorAdapter {

    public FavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_favorite, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            TextView title = view.findViewById(R.id.title);
            TextView description = view.findViewById(R.id.description);
            ImageView picture = view.findViewById(R.id.picture);


            title.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.DictionaryColumns.TITLE));
            description.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.DictionaryColumns.OVERVIEW));
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185"+DatabaseContract.getColumnString(cursor,DatabaseContract.DictionaryColumns.POSTER_PATH))
                    .into(picture);
        }
    }
}
