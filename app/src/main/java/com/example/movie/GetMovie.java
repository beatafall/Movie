package com.example.movie;

import com.example.movie.API.Example;
import com.example.movie.API.MovieImages;
import com.example.movie.API.MovieVideo;
import com.example.movie.API.RelatedMovies;
import com.example.movie.Database.FavoritesDatabase;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMovie { //Service
    @GET("search/movie?api_key=263b7f01ce9bd66de47a10a8aa7f6055")

     Call<Example> getAllData(@Query("query")String query);

    @GET("movie/popular")
    Call<Example> getPopularMovies(@Query("api_key") String apiKey, @Query ("page") int page);

    @GET("movie/{movie_id}/videos")
    Call<MovieVideo> getTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Call<RelatedMovies> getRelatedMovies(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/images")
    Call<MovieImages> getMovieImages(@Path("movie_id") int id, @Query("api_key") String apiKey);

//    @GET("movie/nowplay")
//    Call<MovieImages> getNowPlayingMovies(@Path("movie_id") int id, @Query("api_key") String apiKey);
}
