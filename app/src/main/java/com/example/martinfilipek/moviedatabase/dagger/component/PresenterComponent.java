package com.example.martinfilipek.moviedatabase.dagger.component;

import com.example.martinfilipek.moviedatabase.dagger.module.PresenterModule;
import com.example.martinfilipek.moviedatabase.mvp.main.MainActivityPresenter;
import com.example.martinfilipek.moviedatabase.mvp.movies.MoviesFragmentPresenter;
import com.example.martinfilipek.moviedatabase.mvp.movies.MoviesFragmentView;

import dagger.Subcomponent;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
@Subcomponent(modules = PresenterModule.class)
public interface PresenterComponent {

    void inject(MainActivityPresenter mainActivityPresenter);

    void inject(MoviesFragmentPresenter moviesFragmentPresenter);
}
