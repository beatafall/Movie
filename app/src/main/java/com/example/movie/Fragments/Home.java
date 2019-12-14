package com.example.movie.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movie.API.Example;
import com.example.movie.API.Result;
import com.example.movie.Adapters.MovieListAdapter;
import com.example.movie.Client;
import com.example.movie.Constans;
import com.example.movie.GetMovie;
import com.example.movie.PaginationScrollListener;
import com.example.movie.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    RecyclerView recyclerView;
    List<Result> movieList;
    LinearLayoutManager layoutManager;
    MovieListAdapter movieListAdapter;
    Context context;
    EditText et_searchmovie;
    Button btn_search;

    private static final int START_PAGE = 1;
    private int TOTAL_PAGE = 20;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int CURRENT_PAGE = START_PAGE;

    Client client = new Client();
    GetMovie getMovie = client.getClient().create(GetMovie.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.listmovies);
        et_searchmovie= v.findViewById(R.id.searchmovie);
        btn_search = v.findViewById(R.id.btnsearch);

        movieList = new ArrayList<>();
        movieListAdapter = new MovieListAdapter(movieList, context);

        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieListAdapter);
        movieListAdapter.notifyDataSetChanged();


        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
                @Override
                protected void loadMoreItems() {
                    isLoading = true;
                    CURRENT_PAGE += 1;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadNextPage();
                        }
                    }, 1000);
                }

                @Override
                public int getTotalPageCount() {
                    return TOTAL_PAGE;
                }

                @Override
                public boolean isLastPage() {
                    return isLastPage;
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }
            });

            Call<Example> call = getMovie.getPopularMovies(Constans.API_KEY, START_PAGE);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    List<Result> movies = response.body().getResults();
                    recyclerView.setAdapter(new MovieListAdapter(movies, getActivity().getApplicationContext()));

                    if(CURRENT_PAGE <= TOTAL_PAGE ){
                        movieListAdapter.addBottemIttem();
                    }else{
                        isLastPage = true;
                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Log.d("error", t.getMessage());
                    Toast.makeText(getActivity(), "Error Fetching data", Toast.LENGTH_SHORT).show();
                }
            });

       // loadMovies();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchedMovie = et_searchmovie.getText().toString();
                Call<Example> call = getMovie.getAllData(searchedMovie);
                        call.enqueue(new Callback<Example>() {
                            @Override
                            public void onResponse(Call<Example> call, Response<Example> response) {
                                List<Result> movie = response.body().getResults();
                                recyclerView.setAdapter(new MovieListAdapter(movie, getActivity().getApplicationContext()));
                            }

                            @Override
                            public void onFailure(Call<Example> call, Throwable t) {
                                Log.d("error", t.getMessage());
                                Toast.makeText(getActivity(), "Error Fetching data", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
        });

        BottomNavigationView bottomNavigationView = v.findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        return v;
    }

    private void loadMovies() {
        try {
            recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
                @Override
                protected void loadMoreItems() {
                    isLoading = true;
                    CURRENT_PAGE += 1;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadNextPage();
                        }
                    }, 1000);
                }

                @Override
                public int getTotalPageCount() {
                    return TOTAL_PAGE;
                }

                @Override
                public boolean isLastPage() {
                    return isLastPage;
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }
            });

            Call<Example> call = getMovie.getPopularMovies(Constans.API_KEY, START_PAGE);
            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    List<Result> movies = response.body().getResults();
                    recyclerView.setAdapter(new MovieListAdapter(movies, getActivity().getApplicationContext()));

                    if(CURRENT_PAGE <= TOTAL_PAGE ){
                        movieListAdapter.addBottemIttem();
                    }else{
                        isLastPage = true;
                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Log.d("error", t.getMessage());
                    Toast.makeText(getActivity(), "Error Fetching data", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNextPage() {

        Call<Example> call = getMovie.getPopularMovies(Constans.API_KEY,CURRENT_PAGE);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                List<Result> movies = response.body().getResults();
                movieListAdapter.removedLastEmptyItem();
                isLoading=false;
                movieListAdapter.addAll(movies);

                if(CURRENT_PAGE != TOTAL_PAGE ){
                    movieListAdapter.addBottemIttem();
                }else{
                    isLastPage = true;
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("error", t.getMessage());
                Toast.makeText(getActivity(), "Error Fetching data", Toast.LENGTH_SHORT).show();
            }
        });


}

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment=null;

                    switch (menuItem.getItemId()){
                        case R.id.home:
                            selectedFragment = new Home();
                            break;
                        case R.id.favorite :
                            selectedFragment=new Favorites();
                            break;
                        case R.id.profile:
                            selectedFragment=new Profile();
                            break;
                    }
                    getActivity().getSupportFragmentManager().
                            beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };

}
