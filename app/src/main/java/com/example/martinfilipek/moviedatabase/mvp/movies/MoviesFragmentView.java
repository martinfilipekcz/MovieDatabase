package com.example.martinfilipek.moviedatabase.mvp.movies;

import android.support.annotation.StringRes;

import com.example.martinfilipek.moviedatabase.model.response.movie.Movie;
import com.example.martinfilipek.moviedatabase.mvp.BaseView;

import java.util.List;

/**
 * Created by Martin Filipek on 08.04.2018.
 */
public interface MoviesFragmentView extends BaseView {

    void showLoading();

    void hideLoading();

    void showError();

    void showNoConnection();

    void onMoviesDownloaded(List<Movie> moviesList);
}
