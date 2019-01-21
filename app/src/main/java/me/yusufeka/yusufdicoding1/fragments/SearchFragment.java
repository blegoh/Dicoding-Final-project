package me.yusufeka.yusufdicoding1.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import me.yusufeka.yusufdicoding1.BuildConfig;
import me.yusufeka.yusufdicoding1.adapters.ListMovieAdapter;
import me.yusufeka.yusufdicoding1.networks.MovieClient;
import me.yusufeka.yusufdicoding1.R;
import me.yusufeka.yusufdicoding1.models.Movie;
import me.yusufeka.yusufdicoding1.responses.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    private EditText txtSearch;

    private Button btnSearch;

    private RecyclerView rvMovie;

    private ListMovieAdapter listMovieAdapter;

    ProgressBar progressBar;

    ArrayList<Movie> movies;

    public SearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("movies", movies);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtSearch = view.findViewById(R.id.txtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        rvMovie = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        listMovieAdapter = new ListMovieAdapter(getContext());
        rvMovie.setAdapter(listMovieAdapter);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMovies();
            }
        });
        movies = new ArrayList<>(0);
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("movies");
        } else {
            loadMovies();
        }
        listMovieAdapter.setListMovie(movies);
    }

    private void loadMovies(){
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> seacrhMovies = MovieClient.getMovieService().listMovies(BuildConfig.TMDB_API_KEY,"en",txtSearch.getText().toString());
        seacrhMovies.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    movies = new ArrayList<>(response.body().getResults());
                    listMovieAdapter.setListMovie(movies);
                }else{
                    Log.e("error", response.message());
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("anu", t.getMessage());
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
