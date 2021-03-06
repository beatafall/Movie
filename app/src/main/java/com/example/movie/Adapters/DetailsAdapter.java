package com.example.movie.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie.API.Result;
import com.example.movie.Activity.Details;
import com.example.movie.R;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder>  {
    List<Result> movielist;
    Context context;

    public DetailsAdapter(List<Result> s, Context context) {
        this.movielist = s;
        this.context=context;
    }

    @NonNull
    @Override
    public DetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_details, parent, false);
        return new DetailsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.ViewHolder holder, int position) {
        holder.title.setText(movielist.get(position).getOriginalTitle());
        holder.shortdescription.setText(movielist.get(position).getOverview());
    }

    @Override
    public int getItemCount() {
        return movielist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView shortdescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            shortdescription = itemView.findViewById(R.id.desc);
        }
    }

    public void add(Result movie){
        movielist.add(movie);
        notifyItemInserted(movielist.size()-1);
    }

    public void addAll(List<Result> movies){
        for(Result m: movies){
            add(m);
        }
    }
    //add empty item
    public void addBottemIttem(){
        add(new Result());
    }

    public void removedLastEmptyItem(){
        int position = movielist.size()-1;
        Result item =  getItem(position);
        if(item != null){
            movielist.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Result getItem(int position){
        return movielist.get(position);
    }
}
