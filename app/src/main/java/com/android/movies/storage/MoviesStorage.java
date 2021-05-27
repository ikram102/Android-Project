package com.android.movies.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.movies.model.MovieDetails;
import com.android.movies.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MoviesStorage {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public MoviesStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.STORED_MOVIES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void storeData(List<MovieDetails> listSaved) {
        if (listSaved != null) {
            Gson gson = new Gson();
            String moviesJson = gson.toJson(listSaved);
            editor.putString("movies", moviesJson);
            editor.commit();

        }

    }

    public List<MovieDetails> retrieveData() {
        List<MovieDetails> movieDetailsList = new ArrayList<>();
        Gson gson = new Gson();
        String moviesJson = sharedPreferences.getString("movies", "");
        Type type = new TypeToken<List<MovieDetails>>() {
        }.getType();
        movieDetailsList = gson.fromJson(moviesJson, type);
        return movieDetailsList;
    }
}
