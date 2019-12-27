package com.example.movie.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.movie.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NowPlaying extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_now_playing, container, false);

        Bundle bundle = this.getArguments();
        final int id= bundle.getInt("id");

        BottomNavigationView bottomNavigationView = v.findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.nowplay);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case  R.id.home:
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        Home home = new Home();
                        home.setArguments(bundle);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, home);
                        ft.commit();
                        break;
                    case R.id.favorite:
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("id",id);
                        Favorites favorites = new Favorites();
                        favorites.setArguments(bundle2);
                        FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                        ft2.replace(R.id.fragment_container,favorites);
                        ft2.addToBackStack(null);
                        ft2.commit();
                        break;
                    case R.id.profile:
                        Bundle bundleProfile = new Bundle();
                        bundleProfile.putInt("id",id);
                        Profile profileFragment = new Profile();
                        profileFragment.setArguments(bundleProfile);
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment_container,profileFragment);
                        fr.commit();
                        break;
                    case R.id.nowplay:
                        break;
                }
                return true;
            }
        });


        return v;
    }

}
