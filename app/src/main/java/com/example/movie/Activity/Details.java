package com.example.movie.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movie.API.MovieImages;
import com.example.movie.API.MovieVideo;
import com.example.movie.API.MovieVideoResult;
import com.example.movie.API.Poster;
import com.example.movie.API.RelatedMovies;
import com.example.movie.API.RelatedMoviesResult;
import com.example.movie.API.Result;
import com.example.movie.Adapters.ImagesAdapter;
import com.example.movie.Adapters.RelatedMoviesAdapter;
import com.example.movie.Classes.Constans;
import com.example.movie.Classes.User;
import com.example.movie.Classes.WebViewController;
import com.example.movie.Retrofit.Client;
import com.example.movie.Database.DatabaseHelper;
import com.example.movie.Database.FavoritesDatabase;
import com.example.movie.Interface.GetMovie;
import com.example.movie.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details extends AppCompatActivity {

    TextView title, description;
    ImageButton btn_like;
    WebView webView;
    RecyclerView recyclerView, recyclerView2;
    RelatedMoviesAdapter adapter;
    ImagesAdapter imagesAdapter;
    private List<RelatedMoviesResult> relatedlist;
    private List<Poster> posterList;
    LinearLayoutManager layoutManager, layoutManager2;
    DatabaseHelper db;

    FavoritesDatabase favoriteDbHelper;
    Result favorites;

    String moviename, moviedescription, movieimage, video_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title=findViewById(R.id.title);
        description=findViewById(R.id.desc);
        btn_like=findViewById(R.id.like);
        webView=findViewById(R.id.webView);

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFavorite();
                Log.d("ido", favorites.getOriginalTitle());
                Log.d("ido", favorites.getId().toString());
            }
        });

        Intent intent= getIntent();
        if(intent.hasExtra("originaltitle")){
            moviename= getIntent().getExtras().getString("originaltitle");
            moviedescription= getIntent().getExtras().getString("shortdescription");
            title.setText(moviename);
            description.setText(moviedescription);
       }
        else{
            Toast.makeText(this,"NO API DATA",Toast.LENGTH_LONG).show();
        }
        getRelatedModies();
        getVideo();
        getImages();
    }

    public void saveFavorite(){
        User user=new User();
        int id =user.getId();
        favoriteDbHelper = new FavoritesDatabase(this);
        db= new DatabaseHelper(this);
        favorites = new Result();

        favorites.setId(id);
        favorites.setOriginalTitle(moviename);
        favorites.setOverview(moviedescription);
        favorites.setPosterPath(movieimage);

        favoriteDbHelper.addFavorite(favorites);
    }

    private void getRelatedModies(){
        relatedlist=new ArrayList<>();
        adapter= new RelatedMoviesAdapter(this,relatedlist);

        recyclerView = findViewById(R.id.recycler_view1);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadRelated();
    }

    private void loadRelated(){
        final int movie_id=getIntent().getExtras().getInt("id");
        try{
            if(Constans.API_KEY.isEmpty()){
                Toast.makeText(getApplicationContext(),"No API key",Toast.LENGTH_SHORT).show();
                return;
            }
            Client client = new Client();
            GetMovie apiservice = client.getClient().create(GetMovie.class);
            Call<RelatedMovies> call = apiservice.getRelatedMovies(movie_id,Constans.API_KEY);
            call.enqueue(new Callback<RelatedMovies>() {
                @Override
                public void onResponse(Call<RelatedMovies> call, Response<RelatedMovies> response) {
                    List<RelatedMoviesResult> moviesResults=response.body().getResults();
                    recyclerView.setAdapter(new RelatedMoviesAdapter(getApplicationContext(),moviesResults));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<RelatedMovies> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();

        }
    }

    private void getVideo(){
        final int movie_id=getIntent().getExtras().getInt("id");
        try{
            if(Constans.API_KEY.isEmpty()){
                Toast.makeText(getApplicationContext(),"No API key",Toast.LENGTH_SHORT).show();
                return;
            }
            Client client = new Client();
            GetMovie apiservice = client.getClient().create(GetMovie.class);
            Call<MovieVideo> call = apiservice.getTrailer(movie_id,Constans.API_KEY);
            call.enqueue(new Callback<MovieVideo>() {
                @Override
                public void onResponse(Call<MovieVideo> call, Response<MovieVideo> response) {
                    List<MovieVideoResult> trailer = response.body().getResults();
                    webView.setWebViewClient(new WebViewController());
                    webView.getSettings().setJavaScriptEnabled(true);
                    video_key = trailer.get(0).getKey();
                    webView.loadUrl("https://www.youtube.com/watch?v=" + video_key);
                }

                @Override
                public void onFailure(Call<MovieVideo> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(Details.this,"Error fetching trailer data",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();

        }
    }

    private void getImages(){
        posterList=new ArrayList<>();
        imagesAdapter= new ImagesAdapter(this,posterList);

        recyclerView2 = findViewById(R.id.recycler_view2);

        layoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView2.setLayoutManager(layoutManager2);

        recyclerView2.setAdapter(imagesAdapter);
        imagesAdapter.notifyDataSetChanged();

        loadImages();
    }

    private void loadImages(){
        final int movie_id=getIntent().getExtras().getInt("id");
        try{
            if(Constans.API_KEY.isEmpty()){
                Toast.makeText(getApplicationContext(),"No API key",Toast.LENGTH_SHORT).show();
                return;
            }
            Client client = new Client();
            GetMovie apiservice = client.getClient().create(GetMovie.class);
            Call<MovieImages> call = apiservice.getMovieImages(movie_id,Constans.API_KEY);
            call.enqueue(new Callback<MovieImages>() {
                @Override
                public void onResponse(Call<MovieImages> call, Response<MovieImages> response) {
                    List<Poster> posters=response.body().getPosters();
                    recyclerView2.setAdapter(new ImagesAdapter(getApplicationContext(),posters));
                    recyclerView2.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<MovieImages> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

}
