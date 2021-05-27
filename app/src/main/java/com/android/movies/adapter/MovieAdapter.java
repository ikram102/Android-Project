package com.android.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.movies.R;
import com.android.movies.model.MovieDetails;
import com.android.movies.utils.Constants;
import com.android.movies.views.DetailsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviesviewHolder> {

    Context context;
    List<MovieDetails> movieDetailsList;

    public MovieAdapter(Context context, List<MovieDetails> movieDetailsList) {
        this.context = context;
        this.movieDetailsList = movieDetailsList;
    }

    public void setMovieDetailsList(List<MovieDetails> movieDetailsList) {
        this.movieDetailsList = movieDetailsList;
        notifyDataSetChanged();
    }

    @Override
    public MoviesviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_movie, parent, false);
        return new MoviesviewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MoviesviewHolder holder, final int position) {
        String poster_url = Constants.IMAGE_URL_BASE_PATH + movieDetailsList.get(position).getImageUrl();
        holder.movieName.setText(movieDetailsList.get(position).getTitle());
        holder.movieRate.setText(movieDetailsList.get(position).getVote());
        Glide
                .with(context)
                .load(poster_url)
                .apply(RequestOptions.centerCropTransform())
                .into(holder.image);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + movieDetailsList.get(position));
                Constants.showToast(context, movieDetailsList.get(position).getTitle());
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("movieName", movieDetailsList.get(position).getTitle());
                intent.putExtra("rate", movieDetailsList.get(position).getVote());
                intent.putExtra("poster", Constants.IMAGE_URL_BASE_PATH + movieDetailsList.get(position).getImageUrl());
                intent.putExtra("description", movieDetailsList.get(position).getDescription());
                intent.putExtra("date", movieDetailsList.get(position).getReleaseDate());
                intent.putExtra("adult", movieDetailsList.get(position).isAdult());
                intent.putExtra("vote_count", movieDetailsList.get(position).getVote_count());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (movieDetailsList != null) {
            return movieDetailsList.size();
        }
        return 0;

    }

    public class MoviesviewHolder extends RecyclerView.ViewHolder {
        LinearLayout parentLayout;
        TextView movieName, movieRate;
        ImageView image;

        public MoviesviewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            movieName = itemView.findViewById(R.id.title);
            movieRate = itemView.findViewById(R.id.rate);
            image = itemView.findViewById(R.id.image);
        }
    }
}
