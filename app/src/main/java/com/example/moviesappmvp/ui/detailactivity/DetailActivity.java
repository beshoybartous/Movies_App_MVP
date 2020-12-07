package com.example.moviesappmvp.ui.detailactivity;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.example.moviesappmvp.R;
import com.example.moviesappmvp.cache.SharedPref;
import com.example.moviesappmvp.base.BaseActivity;
import com.example.moviesappmvp.databinding.ActivityDetailBinding;
import com.example.moviesappmvp.model.MovieModel;
import com.example.moviesappmvp.ui.MoviesIdEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

public class DetailActivity extends BaseActivity<DetailActivityPresenter,ActivityDetailBinding>implements DetailActivityView {
    boolean isInDatabase=false;
    MovieModel movie;
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
        return new DetailActivityPresenter(this,this);
    }

    @Override
    protected void onPostCreated() {
        if(getIntent().getSerializableExtra(KEY_MOVIE_DETAIL)!=null){
            movie = (MovieModel) getIntent().getSerializableExtra(KEY_MOVIE_DETAIL);
            viewBinding.tvMovieTitle.setText(movie.getTitle());
            viewBinding.tvOverview.setText(movie.getOverview());
            viewBinding.tvReleaseDate.setText(movie.getReleaseDate());
            viewBinding.rbRating.setRating((float)movie.getVoteAverage());
            Picasso.with(this).load( movie.getPosterPath()).into(viewBinding.ivMoviePoster);
            if(SharedPref.contain(movie.getId())){
                viewBinding.fabFavourite.setImageResource(R.drawable.ic_favorite);
                isInDatabase=true;
            }
            viewBinding.fabFavourite.setOnClickListener(view -> {
                if(!isInDatabase){
                    presenter.insertData(movie);
                }
               else {
                   presenter.deleteData(movie);
               }
            });
        }
    }

    @Override
    public void isInserted(boolean success) {
        if(success){
            isInDatabase=true;
            viewBinding.fabFavourite.setImageResource(R.drawable.ic_favorite);
            SharedPref.addValue(movie.getId());
            EventBus.getDefault().postSticky(new MoviesIdEvent());
        }
        else{
            Toast.makeText(this, "Failed to save in database", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void isDeleted(boolean success) {
        if(success){
            isInDatabase=false;
            viewBinding.fabFavourite.setImageResource(R.drawable.ic_not_favourite);
            SharedPref.removeValue(movie.getId());
            EventBus.getDefault().postSticky(new MoviesIdEvent());
        }
        else{
            Toast.makeText(this, "Failed to delete from database", Toast.LENGTH_SHORT).show();
        }
    }

}