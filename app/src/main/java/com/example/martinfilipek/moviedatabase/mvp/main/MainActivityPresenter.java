package com.example.martinfilipek.moviedatabase.mvp.main;

import com.example.martinfilipek.moviedatabase.mvp.BasePresenter;

/**
 * Created by Martin Filipek on 08.04.2018.
 */
public class MainActivityPresenter extends BasePresenter<MainActivityView> {
    @Override
    protected void inject() {
        getPresenterComponent().inject(this);
    }
}
