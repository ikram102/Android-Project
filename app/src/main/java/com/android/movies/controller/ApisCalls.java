package com.android.movies.controller;

import android.content.Context;
import android.util.Log;

import com.android.movies.adapter.MovieAdapter;
import com.android.movies.model.MovieDetails;
import com.android.movies.model.Movies;
import com.android.movies.retrofit.RetrofitClient;
import com.android.movies.retrofit.RetrofitInterface;
import com.android.movies.storage.MoviesStorage;
import com.android.movies.utils.Constants;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ApisCalls {
    Context context;
    List<MovieDetails> movieDetailsList;
    MovieAdapter moviesAdapter;
    MoviesStorage moviesStorage;

    public ApisCalls(Context context, List<MovieDetails> movieDetailsList, MovieAdapter moviesAdapter, MoviesStorage moviesStorage) {
        this.context = context;
        this.movieDetailsList = movieDetailsList;
        this.moviesAdapter = moviesAdapter;
        this.moviesStorage = moviesStorage;
    }

    public void loadMovies() {
        RetrofitInterface retrofitInterface = RetrofitClient.loadData(context).create(RetrofitInterface.class);
        Observable<Movies> call = retrofitInterface.getMovies(1);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Movies movies) {
                        movieDetailsList = movies.getResults();
                        Log.d("TAG", "Response = OK " + movieDetailsList);
                        moviesAdapter.setMovieDetailsList(movieDetailsList);
                        Log.d("Saving Movies ...", "" + movieDetailsList.toString());
                        moviesStorage.storeData(movieDetailsList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Constants.showToast(context, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void searchMovies(String movieSearch) {
        RetrofitInterface retrofitInterface = RetrofitClient.loadData(context).create(RetrofitInterface.class);
        Observable<Movies> call = retrofitInterface.searchMovies(movieSearch);
        if (!movieDetailsList.isEmpty() || movieDetailsList.size() != 0) {
            movieDetailsList.clear();
        }
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Movies movies) {
                        movieDetailsList = movies.getResults();
                        Log.d("TAG", "Response = OK " + movieDetailsList);
                        if (movieDetailsList.isEmpty() || movieDetailsList.size() == 0) {
                            Constants.showToast(context, "No movies found");
                        }
                        moviesAdapter.setMovieDetailsList(movieDetailsList);
                        Log.d("Saving Movies ...", "" + movieDetailsList.toString());
                        moviesStorage.storeData(movieDetailsList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Constants.showToast(context, e.getMessage());
                        Log.d("TAG", "Error = OK " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }


                });


    }
}
