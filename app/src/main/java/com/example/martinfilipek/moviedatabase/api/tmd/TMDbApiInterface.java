package com.example.martinfilipek.moviedatabase.api.tmd;

import com.example.martinfilipek.moviedatabase.model.response.movie.MovieResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Declaration of TMD api server methods
 *
 * Created by Martin Filipek on 08.04.2018.
 */
public interface TMDbApiInterface {

    @GET("movie/popular")
    Single<MovieResponse> downloadPopularMovies(@Query("language") String language);

}
