package com.android.movies.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.movies.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailsActivity extends AppCompatActivity {

    Context context;
    ImageView image;
    TextView titletv, releaseDatetv, adulttv, descriptiontv, ratetv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        context = getApplicationContext();
        image = findViewById(R.id.image);
        titletv = findViewById(R.id.title);
        ratetv = findViewById(R.id.rate);
        releaseDatetv = findViewById(R.id.releaseDate);
        adulttv = findViewById(R.id.adult);
        descriptiontv = findViewById(R.id.description);
        getIncomingData();
    }

    public void getIncomingData() {
        Intent intent = getIntent();
        String movieName = intent.getExtras().getString("movieName");
        String rate = intent.getExtras().getString("rate");
        String date = intent.getExtras().getString("date");
        String poster = intent.getExtras().getString("poster");
        String description = intent.getExtras().getString("description");
        String voteCount = intent.getExtras().getString("vote_count");
        boolean isAdult = intent.getExtras().getBoolean("adult");
        Glide
                .with(context)
                .load(poster)
                .apply(RequestOptions.centerCropTransform())
                .into(image);
        titletv.setText(movieName);
        ratetv.setText("Rate : " + rate + "/10\n");
        releaseDatetv.setText("Release date : " + date);
        //adulttv.setText("" + isAdult);
        descriptiontv.setText(description);
    }
}