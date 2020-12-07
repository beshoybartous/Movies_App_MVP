package com.example.moviesappmvp.ui.favouritefragment;

import com.example.moviesappmvp.base.BaseView;
import com.example.moviesappmvp.model.MovieModel;

import java.util.List;

public interface FavouriteMoviesView extends BaseView {
    void getMovies(List<MovieModel> movies);
    void isDeleted(Integer id);
}
