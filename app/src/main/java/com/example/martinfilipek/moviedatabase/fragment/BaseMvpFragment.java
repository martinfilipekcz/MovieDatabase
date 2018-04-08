package com.example.martinfilipek.moviedatabase.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.martinfilipek.moviedatabase.mvp.BaseView;
import com.example.martinfilipek.moviedatabase.mvp.Presenter;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
public abstract class BaseMvpFragment<P extends Presenter<V>, V extends BaseView> extends BaseFragment {

    private P mPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPresenter(savedInstanceState);
        mPresenter.onViewAttached((V) this);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onViewDetached();
        super.onDestroyView();
    }

    protected void initPresenter(final @Nullable Bundle savedInstanceState){
        Bundle args = savedInstanceState == null ? getArguments() : savedInstanceState;
        mPresenter = getPresenter(args);
    }


    protected P getPresenter(){
        return mPresenter;
    }

    protected abstract P getPresenter(final @Nullable Bundle args);
}
