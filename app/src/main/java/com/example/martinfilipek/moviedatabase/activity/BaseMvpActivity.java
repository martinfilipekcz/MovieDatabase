package com.example.martinfilipek.moviedatabase.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.martinfilipek.moviedatabase.mvp.BaseView;
import com.example.martinfilipek.moviedatabase.mvp.Presenter;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
public abstract class BaseMvpActivity<P extends Presenter<V>, V extends BaseView> extends BaseActivity {

    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPresenter(savedInstanceState);
        mPresenter.onViewAttached((V) this);
    }

    @Override
    public void onDestroy() {
        mPresenter.onViewDetached();
        super.onDestroy();
    }

    protected void initPresenter(final @Nullable Bundle savedInstanceState){
        Bundle args = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        mPresenter = getPresenter(args);
    }

    protected P getPresenter(){
        return mPresenter;
    }

    protected abstract P getPresenter(final @Nullable Bundle args);
}
