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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import me.yusufeka.yusufdicoding1.BuildConfig;
import me.yusufeka.yusufdicoding1.R;
import me.yusufeka.yusufdicoding1.adapters.ListMovieAdapter;
import me.yusufeka.yusufdicoding1.models.Movie;
import me.yusufeka.yusufdicoding1.networks.MovieClient;
import me.yusufeka.yusufdicoding1.responses.NowPlayingResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingFragment extends Fragment {

    private RecyclerView rvMovie;

    private ListMovieAdapter listMovieAdapter;

    ProgressBar progressBar;

    ArrayList<Movie> movies;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
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
        movies = new ArrayList<>(0);
        rvMovie.setAdapter(listMovieAdapter);
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("movies");
        } else {
            loadMovies();
        }
        listMovieAdapter.setListMovie(movies);
    }

    private void loadMovies() {
        progressBar.setVisibility(View.VISIBLE);
        Call<NowPlayingResponse> np = MovieClient.getMovieService().nowPlaying(BuildConfig.TMDB_API_KEY, "en");
        np.enqueue(new Callback<NowPlayingResponse>() {
            @Override
            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    movies = new ArrayList<Movie>(response.body().getResults());
                    listMovieAdapter.setListMovie(movies);
                } else {
                    Log.e("error", response.message());
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("error", t.getMessage());
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
