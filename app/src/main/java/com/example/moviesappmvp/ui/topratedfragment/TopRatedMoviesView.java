package com.example.moviesappmvp.ui.topratedfragment;

import com.example.moviesappmvp.base.BaseView;
import com.example.moviesappmvp.model.MovieModel;

import java.util.List;

public interface TopRatedMoviesView extends BaseView {
    public void getMovies(List<MovieModel> movies);

}