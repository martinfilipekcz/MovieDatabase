package com.example.martinfilipek.moviedatabase.mvp.movies;

import com.example.martinfilipek.moviedatabase.App;
import com.example.martinfilipek.moviedatabase.api.ApiServices;
import com.example.martinfilipek.moviedatabase.model.response.movie.MovieResponse;
import com.example.martinfilipek.moviedatabase.mvp.BasePresenter;
import com.example.martinfilipek.moviedatabase.utils.RxUtils;
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Martin Filipek on 08.04.2018.
 */
public class MoviesFragmentPresenter extends BasePresenter<MoviesFragmentView> {

    @Inject
    ApiServices mApiServices;

    private boolean dataLoaded = false;

    @Override
    protected void inject() {
        getPresenterComponent().inject(this);
    }

    public void downloadMovies(boolean showLoading) {
        if (isViewAttached() && showLoading){
            getView().showLoading();
        }

        subscribeSingle(mApiServices.downloadPopularMovies(), new SingleObserver<MovieResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(MovieResponse movieResponse) {
                if (isViewAttached() && movieResponse != null) {
                    getView().onMoviesDownloaded(movieResponse.getMovieList());
                } else {
                    getView().showError();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                getView().showError();
            }
        });
    }

}
