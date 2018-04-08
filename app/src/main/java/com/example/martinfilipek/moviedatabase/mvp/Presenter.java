package com.example.martinfilipek.moviedatabase.mvp;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
public interface Presenter<V extends BaseView> {

    void onViewAttached(V view);

    void onViewDetached();
}
