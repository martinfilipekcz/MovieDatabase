package com.example.martinfilipek.moviedatabase.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.example.martinfilipek.moviedatabase.R;
import com.example.martinfilipek.moviedatabase.fragment.BaseFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.content)
    View vContent;
    @BindView(R.id.toolbar)
    protected Toolbar vToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());
        ButterKnife.bind(this, this);

        initToolbar();
    }

    private void initToolbar() {
        if (vToolbar != null) {
            setSupportActionBar(vToolbar);
        }
    }

    public void setSubtitle(String subtitle){
        if (getSupportActionBar() != null){
            getSupportActionBar().setSubtitle(subtitle);
        }
    }

    public void setSubtitle(@StringRes int subtitleResId){
        setSubtitle(getString(subtitleResId));
    }

    protected void setHomeUpAsEnabled(boolean enabled) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
        }
    }

    protected void addFragment(BaseFragment fragment) {
        addFragment(R.id.content, fragment);
    }

    protected void addFragment(int contentViewId, BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(contentViewId, fragment, fragment.getFragmentTag());
        fragmentTransaction.commit();
    }

    public void replaceFragment(BaseFragment fragment, boolean addToBackStack) {
        replaceFragment(R.id.content, fragment, addToBackStack);
    }

    public void replaceFragment(int contentViewId, BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(contentViewId, fragment, fragment.getFragmentTag());
        fragmentTransaction.addToBackStack(addToBackStack ? fragment.getFragmentTag() : null);
        fragmentTransaction.commit();
    }

    public void popBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void hideKeyboard() {
        hideKeyboard(this.getCurrentFocus());
    }

    public void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
            }
        }
    }

    public void createSnackBar(String message) {
        Snackbar.make(vContent, message, Snackbar.LENGTH_LONG).show();
    }

    public void createSnackBar(@StringRes int messageResId) {
        Snackbar.make(vContent, messageResId, Snackbar.LENGTH_LONG).show();
    }

    protected abstract int getLayoutResId();

    protected String fetchString(@StringRes int stringResId) {
        return getApplicationContext().getResources().getString(stringResId);
    }

    protected String fetchString(@StringRes int stringResId, Object... params) {
        return getApplicationContext().getResources().getString(stringResId, params);
    }

    protected int fetchColor(@ColorRes int colorResId) {
        return ContextCompat.getColor(getApplicationContext(), colorResId);
    }

    protected Drawable fetchDrawable(@DrawableRes int drawableResId) {
        return ContextCompat.getDrawable(getApplicationContext(), drawableResId);
    }

    protected String fetchPlural(@PluralsRes int pluralsResId, int count) {
        return getApplicationContext().getResources().getQuantityString(pluralsResId, count, count);
    }

    protected void loadImage(ImageView target, String imgUrl) {
        Picasso.with(getApplicationContext())
                .load(imgUrl)
                .fit()
                .into(target);
    }
}
