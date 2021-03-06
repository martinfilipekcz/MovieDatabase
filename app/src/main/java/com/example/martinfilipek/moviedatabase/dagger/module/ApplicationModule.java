package com.example.martinfilipek.moviedatabase.dagger.module;

import android.app.Application;
import android.content.Context;


import dagger.Module;
import dagger.Provides;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application){
        mApplication = application;
    }

    @Provides
    Application provideApplication(){
        return mApplication;
    }

    @Provides
    Context provideContext(){
        return mApplication.getApplicationContext();
    }

}
