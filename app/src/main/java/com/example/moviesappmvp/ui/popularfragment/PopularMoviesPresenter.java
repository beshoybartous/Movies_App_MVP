package com.example.moviesappmvp.ui.popularfragment;

import android.util.Log;

import com.example.moviesappmvp.base.BasePresenter;
import com.example.moviesappmvp.network.EndPoints;
import com.example.moviesappmvp.response.MoviesPayLoad;
import com.example.moviesappmvp.response.MoviesResponse;

import io.reactivex.Observable;

public class PopularMoviesPresenter extends BasePresenter {
    PopularMoviesView view;
    public PopularMoviesPresenter(PopularMoviesView view) {
        super(view);
        this.view=view;
    }
    public void getData(MoviesPayLoad parameter){
        Observable<MoviesResponse> observable = networkManager.getData(EndPoints.POPULAR_MOVIES,parameter.toMap(), MoviesResponse.class);
        disposable.add(
                observable.subscribe( model-> {
                    if(model.getResults()!=null){
                        view.getMovies(model);
                    }
                })
        );
    }

}
