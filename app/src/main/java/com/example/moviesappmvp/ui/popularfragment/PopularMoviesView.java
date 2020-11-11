package com.example.moviesappmvp.ui.popularfragment;

import com.example.moviesappmvp.base.BaseView;
import com.example.moviesappmvp.model.MovieModel;
import com.example.moviesappmvp.response.MoviesResponse;

import java.util.List;

public interface PopularMoviesView extends BaseView {
    public void getMovies(MoviesResponse movies);

}
