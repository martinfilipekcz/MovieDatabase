package com.example.martinfilipek.moviedatabase;

import android.app.Application;

import com.example.martinfilipek.moviedatabase.dagger.component.ApplicationComponent;
import com.example.martinfilipek.moviedatabase.dagger.module.ApplicationModule;
import com.example.martinfilipek.moviedatabase.dagger.component.DaggerApplicationComponent;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
public class App extends Application {

    private static App sApplication;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;
    }

    public static App getInstance(){
        return sApplication;
    }

    public ApplicationComponent getComponent(){
        if (mApplicationComponent == null){
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }

        return mApplicationComponent;
    }
}
