package me.yusufeka.yusufdicoding1.networks;

import me.yusufeka.yusufdicoding1.responses.GenreResponse;
import me.yusufeka.yusufdicoding1.responses.MovieResponse;
import me.yusufeka.yusufdicoding1.responses.NowPlayingResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("/3/search/movie")
    Call<MovieResponse> listMovies(@Query("api_key") String apiKey,@Query("language") String language,@Query("query") String query);

    @GET("/3/genre/movie/list")
    Call<GenreResponse> listGenres(@Query("api_key") String apiKey);

    @GET("/3/movie/now_playing")
    Call<NowPlayingResponse> nowPlaying(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("/3/movie/upcoming")
    Call<NowPlayingResponse> upComing(@Query("api_key") String apiKey, @Query("language") String language);
}
