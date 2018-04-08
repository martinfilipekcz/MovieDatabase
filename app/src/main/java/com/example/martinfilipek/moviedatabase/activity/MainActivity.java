package com.example.martinfilipek.moviedatabase.activity;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.martinfilipek.moviedatabase.R;
import com.example.martinfilipek.moviedatabase.mvp.main.MainActivityPresenter;
import com.example.martinfilipek.moviedatabase.mvp.main.MainActivityView;
import com.example.martinfilipek.moviedatabase.view.LoadingView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity<MainActivityPresenter, MainActivityView>
        implements MainActivityView {

    @BindView(R.id.loadingView)
    LoadingView vLoading;

    ArrayList<LoadingView.State> states = new ArrayList<>();

    private static final String TAG = "MainActivity";

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        states.add(LoadingView.State.LOADING);
        states.add(LoadingView.State.ERROR);
        states.add(LoadingView.State.NO_CONNECTION);
        states.add(LoadingView.State.EMPTY);
        states.add(LoadingView.State.NORMAL);

        vLoading.setOnReloadClickListener(() -> {});

        testLoading();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainActivityPresenter getPresenter(@Nullable Bundle args) {
        return new MainActivityPresenter();
    }

    private void testLoading() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                changeView();
                handler.postDelayed(this, 5000);
            }
        };

        handler.post(runnable);
    }

    private void changeView() {
        if (position < 5) {
            Log.d(TAG, "changeView: " + states.get(position));
            vLoading.setState(states.get(position));
            position++;
        }
    }
}
