package com.example.martinfilipek.moviedatabase.dagger.module;

import com.example.martinfilipek.moviedatabase.mvp.Presenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
@Module
public class PresenterModule {

    private final Presenter mActivity;

    public PresenterModule(final Presenter activity){
        mActivity = activity;
    }

    @Provides
    Presenter providePresenter(){
        return mActivity;
    }
}
