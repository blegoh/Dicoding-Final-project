package me.yusufeka.yusufdicoding1.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import me.yusufeka.yusufdicoding1.adapters.ListMovieAdapter;
import me.yusufeka.yusufdicoding1.R;
import me.yusufeka.yusufdicoding1.db.FavoriteHelper;
import me.yusufeka.yusufdicoding1.models.Movie;

public class FavoriteFragment extends Fragment {

    private RecyclerView rvMovie;

    private ListMovieAdapter listMovieAdapter;

    ProgressBar progressBar;

    FavoriteHelper favoriteHelper;

    ArrayList<Movie> movies;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("movies", movies);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovie = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        listMovieAdapter = new ListMovieAdapter(getContext());
        rvMovie.setAdapter(listMovieAdapter);
        favoriteHelper = new FavoriteHelper(getContext());
        movies = new ArrayList<>(0);
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("movies");
        } else {
            loadFavorites();
        }
        listMovieAdapter.setListMovie(movies);
    }

    private void loadFavorites(){
        progressBar.setVisibility(View.VISIBLE);
        favoriteHelper.open();
        movies = favoriteHelper.getAllData();
        favoriteHelper.close();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }
}
