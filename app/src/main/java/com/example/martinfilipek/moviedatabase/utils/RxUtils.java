package com.example.martinfilipek.moviedatabase.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
public class RxUtils {

    public static <T> SingleTransformer<T, T> applySingleSchedulers() {
        return upstream -> upstream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public static void clearDisposable(CompositeDisposable compositeDisposable) {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public static CompositeDisposable newDisposableIfDisposed(CompositeDisposable compositeDisposable) {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            return new CompositeDisposable();
        }

        return compositeDisposable;
    }
}
