package com.example.martinfilipek.moviedatabase.model.response.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Martin Filipek on 08.04.2018.
 */
public class MovieResponse {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<Movie> movieList;

    public int getPage() {
        return page;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
