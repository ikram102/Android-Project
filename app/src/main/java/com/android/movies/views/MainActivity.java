package com.android.movies.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.movies.R;
import com.android.movies.adapter.MovieAdapter;
import com.android.movies.controller.ApisCalls;
import com.android.movies.model.MovieDetails;
import com.android.movies.model.Movies;
import com.android.movies.storage.MoviesStorage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<MovieDetails> movieDetailsList;
    List<Movies> movies;
    RecyclerView recyclerView;
    MovieAdapter moviesAdapter;
    MoviesStorage moviesStorage;
    Context context;
    EditText searched;
    String searchedMovie;
    ApisCalls apisCalls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieDetailsList = new ArrayList<>();
        movies = new ArrayList<>();
        context = getApplicationContext();
        moviesStorage = new MoviesStorage(context);
        recyclerView = findViewById(R.id.moviesRecyclerView);
        searched = findViewById(R.id.search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        moviesAdapter = new MovieAdapter(getApplicationContext(), movieDetailsList);
        recyclerView.setAdapter(moviesAdapter);
        apisCalls = new ApisCalls(context, movieDetailsList, moviesAdapter, moviesStorage);

        //Call load Movies
        apisCalls.loadMovies();
        searched.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchedMovie = searched.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    apisCalls.searchMovies(searchedMovie);
                    return true;
                }

                return false;
            }
        });
        // For testing FireBase notifications
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("FCM Token", "KO");
                        }
                        String token = task.getResult().getToken();
                        Log.d("FCM Token", token);

                    }
                });

    }



}