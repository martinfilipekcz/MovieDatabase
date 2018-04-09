package com.example.martinfilipek.moviedatabase.fragment.movies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.martinfilipek.moviedatabase.R;
import com.example.martinfilipek.moviedatabase.adapter.movies.MoviesAdapter;
import com.example.martinfilipek.moviedatabase.fragment.BaseMvpFragment;
import com.example.martinfilipek.moviedatabase.model.response.movie.Movie;
import com.example.martinfilipek.moviedatabase.mvp.movies.MoviesFragmentPresenter;
import com.example.martinfilipek.moviedatabase.mvp.movies.MoviesFragmentView;
import com.example.martinfilipek.moviedatabase.view.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Martin Filipek on 08.04.2018.
 */
public class MoviesFragment extends BaseMvpFragment<MoviesFragmentPresenter, MoviesFragmentView>
        implements MoviesFragmentView {

    @BindView(R.id.loadingMovies)
    LoadingView vLoading;
    @BindView(R.id.swipeRefreshMovies)
    SwipeRefreshLayout vSwipeRefresh;
    @BindView(R.id.recyclerMovies)
    RecyclerView vRecycler;

    private MoviesAdapter mAdapter;

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBaseActivity().setTitle(R.string.movies_title);
        getBaseActivity().setSubtitle(R.string.subtitle_popular);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vLoading.setOnReloadClickListener(() -> getPresenter().downloadMovies(true));

        vLoading.setState(LoadingView.State.LOADING);

        initAdapter();

        vSwipeRefresh.setOnRefreshListener(() -> getPresenter().downloadMovies(false));
    }

    @Override
    public void onResume() {
        super.onResume();

        getPresenter().downloadMovies(true);
    }

    @Override
    protected MoviesFragmentPresenter getPresenter(@Nullable Bundle args) {
        return new MoviesFragmentPresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_movies;
    }

    private void initAdapter(){
        mAdapter = new MoviesAdapter(new ArrayList<>());

        vRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        vRecycler.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(((item, adapterPosition, v) -> onMovieClicked(item)));
    }

    @Override
    public void showLoading() {
        vLoading.setState(LoadingView.State.LOADING);
    }

    @Override
    public void hideLoading() {
        vSwipeRefresh.setRefreshing(false);
        vLoading.setState(LoadingView.State.NORMAL);
    }

    @Override
    public void showError() {
        vSwipeRefresh.setRefreshing(false);
        vLoading.setState(LoadingView.State.ERROR);
    }

    @Override
    public void showNoConnection() {
        vSwipeRefresh.setRefreshing(false);
        vLoading.setState(LoadingView.State.NO_CONNECTION);
    }

    @Override
    public void onMoviesDownloaded(List<Movie> moviesList) {
        mAdapter.setItems(moviesList);

        hideLoading();
    }

    private void onMovieClicked(Movie movie){

    }
}
