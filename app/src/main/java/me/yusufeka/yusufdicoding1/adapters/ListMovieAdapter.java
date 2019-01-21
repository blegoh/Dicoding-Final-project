package me.yusufeka.yusufdicoding1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

import me.yusufeka.yusufdicoding1.DetailMovieActivity;
import me.yusufeka.yusufdicoding1.R;
import me.yusufeka.yusufdicoding1.models.Movie;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> listMovie;

    public ListMovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie, viewGroup, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.title.setText(getListMovie().get(position).getTitle());
        viewHolder.description.setText(getListMovie().get(position).getOverview());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+getListMovie().get(position).getPosterPath())
                .into(viewHolder.picture);
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView description;

        ImageView picture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            picture = itemView.findViewById(R.id.picture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailMovieActivity.class);
                    intent.putExtra("movie", (Serializable) getListMovie().get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
