package com.example.movie.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.movie.API.Result;
import com.example.movie.Adapters.MovieListAdapter;
import com.example.movie.Database.FavoritesDatabase;
import com.example.movie.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    MovieListAdapter movieListAdapter;
    Context context;
    FavoritesDatabase favoritesDatabase;
    List<Result> movieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = v.findViewById(R.id.favoritelistmovies);
        movieList=new ArrayList<>();
        movieListAdapter= new MovieListAdapter(movieList,getActivity());

        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(movieListAdapter);
        movieListAdapter.notifyDataSetChanged();

        favoritesDatabase = new FavoritesDatabase(getActivity());

        //movieList=favoritesDatabase.getAllFavorite();

        Bundle bundle = this.getArguments();
        final int id = bundle.getInt("id");

        BottomNavigationView bottomNavigationView = v.findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.favorite);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        Home home = new Home();
                        home.setArguments(bundle);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, home);
                        ft.commit();
                        break;
                    case R.id.nowplay:
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("id", id);
                        NowPlaying now = new NowPlaying();
                        now.setArguments(bundle2);
                        FragmentTransaction ft3 = getFragmentManager().beginTransaction();
                        ft3.replace(R.id.fragment_container, now);
                        ft3.commit();
                        break;
                    case R.id.favorite:
                        break;
                    case R.id.profile:
                        Bundle bundleProfile = new Bundle();
                        bundleProfile.putInt("id", id);
                        Profile profileFragment = new Profile();
                        profileFragment.setArguments(bundleProfile);
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment_container, profileFragment);
                        fr.commit();
                        break;
                }
                return true;
            }
        });

        return v;
    }



}
