package com.example.movie;

import com.example.movie.API.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMovie { //Service
    @GET("search/movie?api_key=263b7f01ce9bd66de47a10a8aa7f6055")

     Call<Example> getAllData(@Query("query")String query, @Query("page")String pageIndex );

    @GET("movie/popular")
    Call<Example> getPopularMovies(@Query("api_key") String apiKey, @Query ("page") int page);



}
