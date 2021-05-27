package com.android.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<MovieDetails> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieDetails> getResults() {
        return results;
    }

    public void setResults(List<MovieDetails> results) {
        this.results = results;
    }
}
