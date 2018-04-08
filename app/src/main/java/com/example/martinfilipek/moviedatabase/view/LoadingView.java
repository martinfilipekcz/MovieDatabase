package com.example.martinfilipek.moviedatabase.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.martinfilipek.moviedatabase.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Universal loading view.
 *
 * <p>
 *     States:
 *     NORMAL : shows child view
 *     LOADING : show progress layout
 *     EMPTY : can show error message
 *     ERROR : shows default or specific error message with reload button
 *     NO_CONNECTION : show no connection message, you must handle connection state change
 * </p>
 */
public class LoadingView extends FrameLayout {

    private View vChildLayout;
    @BindView(R.id.layoutLoadingProgress)
    LinearLayout vProgressLayout;
    @BindView(R.id.layoutLoadingEmpty)
    LinearLayout vEmptyLayout;
    @BindView(R.id.layoutLoadingError)
    LinearLayout vErrorLayout;
    @BindView(R.id.layoutLoadingNoConnection)
    LinearLayout vNoConnectionLayout;
    @BindView(R.id.txtLoadingErrorMsg)
    TextView vTxtErrorMsg;
    @BindView(R.id.txtLoadingEmptyMsg)
    TextView vTxtEmptyMsg;

    private OnReloadClickListener mOnReloadClickListener;

    private State mLastState;

    public LoadingView(@NonNull Context context) {
        super(context);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        inflate(getContext(), R.layout.layout_loading, this);
        vChildLayout = getChildAt(0);

        ButterKnife.bind(this);

        AppCompatButton btnReload = vErrorLayout.findViewById(R.id.btnLoadingErrorReload);

        if (btnReload != null && mOnReloadClickListener != null) {
            btnReload.setOnClickListener(v -> mOnReloadClickListener.onReloadClick());
        } else if (mOnReloadClickListener == null) {
            throw new IllegalStateException("You must to define OnReloadClickListener!");
        }
    }

    private void setMessage(String message) {
        if (vTxtEmptyMsg != null && vTxtErrorMsg != null) {

            switch (mLastState) {
                case ERROR:
                    vTxtErrorMsg.setText(message);
                    break;
                case EMPTY:
                    vTxtEmptyMsg.setText(message);
                    break;
            }
        }
    }

    public void setMessage(@StringRes int messageResId) {
        setMessage(getContext().getString(messageResId));
    }

    public void setState(State state) {
        if (!isInitialized()) {
            init();
        }

        if (state == mLastState) {
            return;
        }

        View layoutToHide = getCurrentShowingView();
        View layoutToShow;

        switch (state) {
            case LOADING:
                layoutToShow = vProgressLayout;
                break;
            case NORMAL:
                layoutToShow = vChildLayout;
                break;
            case ERROR:
                layoutToShow = vErrorLayout;
                break;
            case EMPTY:
                layoutToShow = vEmptyLayout;
                break;
            case NO_CONNECTION:
                layoutToShow = vNoConnectionLayout;
                break;
            default:
                return;
        }

        if ((layoutToHide == null || state == State.LOADING) && layoutToShow != null){
            layoutToShow.setVisibility(VISIBLE);
            hideUnusedViews(layoutToShow);
        } else if (layoutToHide != null && layoutToShow != null){
            animateLayoutChange(layoutToHide, layoutToShow);
        }

        mLastState = state;
    }

    private void animateLayoutChange(View viewToHide, View viewToShow) {
        viewToHide.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
        viewToHide.setVisibility(GONE);

        viewToShow.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        viewToShow.setVisibility(VISIBLE);

        hideUnusedViews(viewToShow);
    }

    private void hideUnusedViews(View visibleView){
        List<View> allViews = new ArrayList<View>();

        allViews.add(vChildLayout);
        allViews.add(vProgressLayout);
        allViews.add(vEmptyLayout);
        allViews.add(vErrorLayout);
        allViews.add(vNoConnectionLayout);

        for (View view : allViews){
            if (view != null && view != visibleView){
                view.setVisibility(GONE);
            }
        }
    }

    private View getCurrentShowingView() {
        if (mLastState == null) {
            return null;
        }

        switch (mLastState) {
            case LOADING:
                return vProgressLayout;
            case EMPTY:
                return vEmptyLayout;
            case ERROR:
                return vErrorLayout;
            case NO_CONNECTION:
                return vNoConnectionLayout;
            case NORMAL:
                return vChildLayout;
        }

        return null;
    }

    public void setOnReloadClickListener(OnReloadClickListener onReloadClickListener) {
        mOnReloadClickListener = onReloadClickListener;
    }

    private boolean isInitialized() {
        return vProgressLayout != null;
    }

    public enum State {
        NORMAL, LOADING, EMPTY, ERROR, NO_CONNECTION
    }

    public interface OnReloadClickListener {
        void onReloadClick();
    }
}
