package com.example.martinfilipek.moviedatabase.api.tmd;

import android.support.annotation.StringRes;

import com.example.martinfilipek.moviedatabase.App;
import com.example.martinfilipek.moviedatabase.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton which call TMD api server
 *
 * Created by Martin Filipek on 08.04.2018.
 */
public class TMDbApiClient {

    private TMDbApiInterface mTMDbApiServices;

    @Inject
    public TMDbApiClient(){}

    public TMDbApiInterface getTMDbApiSevices(){
        if (mTMDbApiServices == null){
            String endpoint = fetchString(R.string.TMDb_base_url);
            OkHttpClient client = createClientBuilder().build();
            Retrofit retrofit = initRetrofit(endpoint, client);

            mTMDbApiServices = retrofit.create(TMDbApiInterface.class);
        }

        return mTMDbApiServices;
    }

    private OkHttpClient.Builder createClientBuilder(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("api_get", fetchString(R.string.TMDb_api_key))
                            .build();

                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    return chain.proceed(requestBuilder.build());
                });
    }

    private Retrofit initRetrofit(String endpoint, OkHttpClient client){
        Gson gson = new GsonBuilder().serializeNulls().create();

        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    private String fetchString(@StringRes int stringResId){
        return App.getInstance().getString(stringResId);
    }
}
