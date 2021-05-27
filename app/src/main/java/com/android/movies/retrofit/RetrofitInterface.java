package com.android.movies.retrofit;


import com.android.movies.model.Movies;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

//this interface defines the possible HTTP operations
public interface RetrofitInterface {

    @GET("movie/popular?api_key=085d66f8ec5db1d72256702cb8f4e89d")
    Observable<Movies> getMovies(@Query("page") int page);

    @GET("search/movie?api_key=085d66f8ec5db1d72256702cb8f4e89d")
    Observable<Movies> searchMovies(@Query("query") String query);


}
