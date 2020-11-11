package com.example.moviesappmvp.ui.detailactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.moviesappmvp.base.BaseActivity;
import com.example.moviesappmvp.databinding.ActivityDetailBinding;
import com.example.moviesappmvp.databinding.ActivityMainBinding;
import com.example.moviesappmvp.model.MovieModel;
import com.squareup.picasso.Picasso;

public class DetailActivity extends BaseActivity<DetailActivityPresenter,ActivityDetailBinding>implements DetailActivityView {
    private static final String KEY_MOVIE_DETAIL="movie detail";
    public static void startDetailActivity(Context context, MovieModel movie){
        Intent detailActivityIntent=new Intent(context, DetailActivity.class);
        detailActivityIntent.putExtra(KEY_MOVIE_DETAIL, movie);
        context.startActivity(detailActivityIntent);
    }

    @Override
    protected ActivityDetailBinding setViewBinding() {
        return ActivityDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected DetailActivityPresenter setPresenter() {
        return new DetailActivityPresenter(this);
    }

    @Override
    protected void onPostCreated() {
        if(getIntent().getSerializableExtra(KEY_MOVIE_DETAIL)!=null){
            MovieModel movie= (MovieModel) getIntent().getSerializableExtra(KEY_MOVIE_DETAIL);
            viewBinding.tvMovieTitle.setText(movie.getTitle());
            viewBinding.tvOverview.setText(movie.getOverview());
            viewBinding.tvReleaseDate.setText(movie.getRelease_date());
            viewBinding.rbRating.setRating((float)movie.getVote_average());
            Picasso.with(this).load( movie.getPoster_path()).into(viewBinding.ivMoviePoster);
        }
    }

}