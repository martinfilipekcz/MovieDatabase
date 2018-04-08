package com.example.martinfilipek.moviedatabase.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.martinfilipek.moviedatabase.activity.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected void setTitle(String title) {
        getBaseActivity().setTitle(title);
    }

    protected void setTitle(@StringRes int titleResId) {
        getBaseActivity().setTitle(titleResId);
    }

    protected void setSubtitle(String subtitle){
        getBaseActivity().setSubtitle(subtitle);
    }

    protected void setSubtitle(@StringRes int subtitleResId){
        setSubtitle(fetchString(subtitleResId));
    }

    public String getFragmentTag() {
        return this.getClass().getSimpleName();
    }

    protected String fetchString(@StringRes int stringResId) {
        if (getContext() == null) {
            throw new IllegalArgumentException("'fetchString(stringResId)' Context must not be null.");
        }

        return getContext().getResources().getString(stringResId);
    }

    protected String fetchString(@StringRes int stringResId, Object... params) {
        if (getContext() == null) {
            throw new IllegalArgumentException("'fetchString(stringResId, params)' Context must not be null.");
        }

        return getContext().getResources().getString(stringResId, params);
    }

    protected int fetchColor(@ColorRes int colorResId) {
        if (getContext() == null) {
            throw new IllegalArgumentException("'fetchColor(colorResId)' Context must not be null.");
        }

        return ContextCompat.getColor(getContext(), colorResId);
    }

    protected Drawable fetchDrawable(@DrawableRes int drawableResId) {
        if (getContext() == null) {
            throw new IllegalArgumentException("'fetchDrawable(drawableResId)' Context must not be null.");
        }

        return ContextCompat.getDrawable(getContext(), drawableResId);
    }

    protected String fetchPlural(@PluralsRes int pluralsResId, int count) {
        if (getContext() == null) {
            throw new IllegalArgumentException("'fetchPlural(pluralsResId, count)' Context must not be null.");
        }

        return getContext().getResources().getQuantityString(pluralsResId, count, count);
    }

    protected void loadImage(ImageView target, String imgUrl) {
        Picasso.with(getContext())
                .load(imgUrl)
                .fit()
                .into(target);
    }

    @LayoutRes
    protected abstract int getLayoutResId();
}
