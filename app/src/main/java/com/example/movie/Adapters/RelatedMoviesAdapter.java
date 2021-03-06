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
import com.example.movie.API.RelatedMoviesResult;
import com.example.movie.API.Result;
import com.example.movie.Activity.Details;
import com.example.movie.R;

import java.util.List;

public class RelatedMoviesAdapter extends RecyclerView.Adapter<RelatedMoviesAdapter.MyViewHolder>
{
    private Context context;
    private List<RelatedMoviesResult> relatedlist;

    public RelatedMoviesAdapter(Context context, List<RelatedMoviesResult> relatedlist){
        this.context=context;
        this.relatedlist=relatedlist;
    }
    @NonNull
    @Override
    public RelatedMoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.relatedmovies, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedMoviesAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(relatedlist.get(position).getPosterPath()).into(holder.image);
        holder.t.setText(relatedlist.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return relatedlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView t;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.relatedimage);
            t=itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        RelatedMoviesResult clickedDataItem = relatedlist.get(pos);
                        Intent intent = new Intent(context, Details.class);
                        intent.putExtra("id",relatedlist.get(pos).getId());
                        intent.putExtra("originaltitle", relatedlist.get(pos).getOriginalTitle());
                        intent.putExtra("shortdescription", relatedlist.get(pos).getOverview());
                        intent.putExtra("image",relatedlist.get(pos).getPosterPath());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }}
            });
        }
    }
}
