package com.example.movie.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie.API.Poster;
import com.example.movie.API.RelatedMoviesResult;
import com.example.movie.Activity.Details;
import com.example.movie.R;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {
    private Context context;
    private List<Poster> imageslist;

    public ImagesAdapter(Context context, List<Poster> imageslist) {
        this.context = context;
        this.imageslist = imageslist;
    }

    @NonNull
    @Override
    public ImagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagesitem, parent, false);
        return new ImagesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(imageslist.get(position).getFilePath()).into(holder.image);;
    }

    @Override
    public int getItemCount() {
        return imageslist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.oneimage);
        }
    }
}