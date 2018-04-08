package com.example.martinfilipek.moviedatabase.api;


import com.example.martinfilipek.moviedatabase.api.tmd.TMDbApiClient;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Singleton with all api calls
 *
 * Created by Martin Filipek on 08.04.2018.
 */
@Singleton
public class ApiServices {

    private final TMDbApiClient mTMDbApiClient;

    @Inject
    public ApiServices(final TMDbApiClient TMDbApiClient){
        mTMDbApiClient = TMDbApiClient;
    }
}
