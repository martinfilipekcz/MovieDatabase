package com.example.martinfilipek.moviedatabase.adapter.movies;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.martinfilipek.moviedatabase.R;
import com.example.martinfilipek.moviedatabase.adapter.BaseRecyclerViewAdapter;
import com.example.martinfilipek.moviedatabase.model.response.movie.Movie;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Martin Filipek on 08.04.2018.
 */
public class MoviesAdapter extends BaseRecyclerViewAdapter<Movie> {

    public MoviesAdapter(List<Movie> items){
        super(R.layout.item_movie, items);
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesViewHolder(getViewHolderView(parent));
    }

    class MoviesViewHolder extends BaseRecyclerViewHolder<Movie>{

        @BindView(R.id.imgMovie)
        ImageView vImgPoster;
        @BindView(R.id.txtMovieTitle)
        TextView vTxtTitle;
        @BindView(R.id.txtMovieReleaseDate)
        TextView vTxtReleaseDate;
        @BindView(R.id.txtMovieDescription)
        TextView vTxtDescription;

        public MoviesViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(Movie data) {
            super.bind(data);

            if (data != null){
                loadImage(vImgPoster, data.getPosterPath());
                vTxtTitle.setText(data.getTitle());
                vTxtReleaseDate.setText(data.getReleaseDate());
                vTxtDescription.setText(data.getOverview());
            }
        }
    }
}
