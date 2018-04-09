package com.example.martinfilipek.moviedatabase.activity.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.example.martinfilipek.moviedatabase.R;
import com.example.martinfilipek.moviedatabase.activity.BaseMvpActivity;
import com.example.martinfilipek.moviedatabase.fragment.movies.MoviesFragment;
import com.example.martinfilipek.moviedatabase.mvp.main.MainActivityPresenter;
import com.example.martinfilipek.moviedatabase.mvp.main.MainActivityView;

import butterknife.BindView;


public class MainActivity extends BaseMvpActivity<MainActivityPresenter, MainActivityView>
        implements MainActivityView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawerMain)
    DrawerLayout vDrawerLayout;
    @BindView(R.id.navigationMain)
    NavigationView vNavigationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, vDrawerLayout, vToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        vDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        vNavigationLayout.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null){
            addFragment(MoviesFragment.newInstance());
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainActivityPresenter getPresenter(@Nullable Bundle args) {
        return new MainActivityPresenter();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.nav_movie_fragment:
                break;
            case R.id.nav_serial_fragment:
                break;
            case R.id.nav_about_fragment:
                break;
        }

        vDrawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }
}
