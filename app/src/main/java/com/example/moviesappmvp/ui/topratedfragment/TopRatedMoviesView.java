package com.example.moviesappmvp.ui.topratedfragment;

import com.example.moviesappmvp.base.BaseView;
import com.example.moviesappmvp.model.MovieModel;
import com.example.moviesappmvp.response.MoviesResponse;

import java.util.List;

public interface TopRatedMoviesView extends BaseView {
    void getMovies(MoviesResponse movies);
    void isInserted(Integer id);
    void isDeleted(Integer id);

}