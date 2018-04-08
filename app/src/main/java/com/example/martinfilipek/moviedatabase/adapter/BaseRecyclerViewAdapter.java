package com.example.martinfilipek.moviedatabase.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.martinfilipek.moviedatabase.App;
import com.example.martinfilipek.moviedatabase.view.LoadingView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Martin Filipek on 08.04.2018.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseRecyclerViewHolder> {

    protected @LayoutRes
    int mItemResId;

    protected List<T> mItems;

    protected OnItemClickListener<T> mClickListener;

    public BaseRecyclerViewAdapter(@LayoutRes int itemResId, List<T> items) {
        mItemResId = itemResId;
        mItems = items;
    }

    protected View getViewHolderView(ViewGroup parent) {
        return getViewHolderView(parent, mItemResId);
    }

    protected View getViewHolderView(ViewGroup parent, @LayoutRes int itemResId) {
        return LayoutInflater.from(parent.getContext()).inflate(itemResId, parent, false);
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }

        return 0;
    }

    public void setItems(List<? extends T> items) {
        if (mItems != null) {
            int count = mItems.size();
            mItems.clear();
            notifyItemRangeRemoved(0, count);
        } else {
            mItems = new ArrayList<>();
        }

        addItems(items);
    }

    public void addItems(List<? extends T> items) {
        addItems(items, mItems.size());
    }

    public void addItems(List<? extends T> items, int position) {
        if (mItems != null) {
            mItems.addAll(position, items);

            notifyItemRangeInserted(position, items.size());
        } else {
            setItems(items);
        }
    }

    private T getItem(int position) {
        return mItems.get(position);
    }

    @NonNull
    @Override
    public abstract BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        T item = getItem(position);
        holder.bind(item);
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item, int adapterPosition, View v);
    }

    public abstract class BaseRecyclerViewHolder<D> extends RecyclerView.ViewHolder implements View.OnClickListener {

        private D mData;
        protected Context mContext;

        public BaseRecyclerViewHolder(View itemView) {
            super(itemView);

            mContext = App.getInstance().getApplicationContext();

            if (mClickListener != null) {
                itemView.setOnClickListener(this);
            }

            ButterKnife.bind(this, itemView);
        }

        protected Context getContext() {
            return mContext;
        }

        protected void bind(D data) {
            mData = data;
        }

        protected D getData() {
            return mData;
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(getItem(getAdapterPosition()), getAdapterPosition(), v);
            }
        }

        protected String fetchString(@StringRes int stringResId) {
            return mContext.getResources().getString(stringResId);
        }

        protected String fetchString(@StringRes int stringResId, Object... params) {
            return mContext.getResources().getString(stringResId, params);
        }

        protected int fetchColor(@ColorRes int colorResId) {
            return ContextCompat.getColor(mContext, colorResId);
        }

        protected Drawable fetchDrawable(@DrawableRes int drawableResId) {
            return ContextCompat.getDrawable(mContext, drawableResId);
        }

        protected String fetchPlural(@PluralsRes int pluralsResId, int count) {
            return mContext.getResources().getQuantityString(pluralsResId, count, count);
        }

        protected void loadImage(ImageView target, String imgUrl) {
            Picasso.with(mContext)
                    .load(imgUrl)
                    .fit()
                    .into(target);
        }
    }
}
