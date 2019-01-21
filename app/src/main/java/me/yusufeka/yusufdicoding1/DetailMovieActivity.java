package me.yusufeka.yusufdicoding1;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.yusufeka.yusufdicoding1.db.FavoriteHelper;
import me.yusufeka.yusufdicoding1.models.Genre;
import me.yusufeka.yusufdicoding1.models.Movie;
import me.yusufeka.yusufdicoding1.networks.MovieClient;
import me.yusufeka.yusufdicoding1.responses.GenreResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieActivity extends AppCompatActivity {

    private ImageView poster;

    private TextView title;

    private TextView rating;

    private TextView adult;

    private TextView language;

    private TextView genres;

    private TextView release;

    private TextView overview;

    private List<Genre> list;

    private Movie movie;

    private Menu menu;

    private FavoriteHelper favoriteHelper;

    private boolean isFavorited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        poster = findViewById(R.id.poster);
        title = findViewById(R.id.title);
        rating = findViewById(R.id.rating);
        adult = findViewById(R.id.adult);
        language = findViewById(R.id.language);
        genres = findViewById(R.id.genres);
        release = findViewById(R.id.release);
        overview = findViewById(R.id.overview);
        favoriteHelper = new FavoriteHelper(this);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185" + movie.getPosterPath())
                .into(poster);
        title.setText(movie.getTitle());
        rating.setText(movie.getVoteAverage() + "");
        adult.setText((movie.getAdult()) ? "Yes" : "No");
        language.setText(movie.getOriginalLanguage());
        release.setText(movie.getReleaseDate());
        overview.setText(movie.getOverview());
        loadGenres();
    }

    private void loadGenres() {
        Call<GenreResponse> genresCall = MovieClient.getMovieService().listGenres("341deba4c77f3495a1b314a9b8ec8e97");
        genresCall.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if (response.isSuccessful()) {
                    String genresConcat = "";
                    list = response.body().getGenres();
                    for (int i = 0; i < movie.getGenreIds().size(); i++) {
                        if (i == 0) {
                            genresConcat += getGenreName(movie.getGenreIds().get(i));
                        } else {
                            genresConcat += ", " + getGenreName(movie.getGenreIds().get(i));
                        }
                    }
                    genres.setText(genresConcat);
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {

            }
        });
    }

    private String getGenreName(int id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id)
                return list.get(i).getName();
        }
        return "";
    }

    private void updateIcon(){
        if (isFavorited)
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_accent_24dp));
        else
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        this.menu = menu;
        favoriteHelper.open();
        isFavorited = favoriteHelper.isFavorited(movie.getId());
        favoriteHelper.close();
        updateIcon();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu1:
                favoriteHelper.open();
                favoriteHelper.toggleFavorite(movie);
                isFavorited = !isFavorited;
                favoriteHelper.close();
                updateIcon();
        }
        return true;
    }



}
