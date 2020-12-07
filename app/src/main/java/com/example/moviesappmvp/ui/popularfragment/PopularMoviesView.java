package com.example.moviesappmvp.ui.popularfragment;

import com.example.moviesappmvp.base.BaseView;
import com.example.moviesappmvp.response.MoviesResponse;

public interface PopularMoviesView extends BaseView {
    void getMovies(MoviesResponse movies);
    void isInserted(Integer id);
    void isDeleted(Integer id);
}
