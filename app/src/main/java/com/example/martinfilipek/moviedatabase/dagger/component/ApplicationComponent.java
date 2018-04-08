package com.example.martinfilipek.moviedatabase.dagger.component;

import android.content.Context;

import com.example.martinfilipek.moviedatabase.dagger.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

    PresenterComponent presenterComponent();
}
