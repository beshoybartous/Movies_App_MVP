package com.example.moviesappmvp.ui.popularfragment;

import android.content.Context;
import android.util.Log;

import com.example.moviesappmvp.base.BasePresenter;
import com.example.moviesappmvp.model.MovieModel;
import com.example.moviesappmvp.network.EndPoints;
import com.example.moviesappmvp.response.MoviesPayLoad;
import com.example.moviesappmvp.response.MoviesResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableCompletableObserver;

public class PopularMoviesPresenter extends BasePresenter {
    PopularMoviesView view;
    public PopularMoviesPresenter(PopularMoviesView view, Context context) {
        super(view,context);
        this.view=view;
    }
    public void getData(MoviesPayLoad parameter){
        Log.d("test1234", "getData: ");

        Observable<MoviesResponse> observable = networkManager.getData(EndPoints.POPULAR_MOVIES,parameter.toMap(), MoviesResponse.class);
        disposable.add(
                observable.subscribe( model-> {
                    if(model.getResults()!=null){
                        view.getMovies(model);
                    }
                })
        );
    }
    public void insertData(MovieModel movie){
        Completable observable = moviesDao.insert(movie.toEntity());
        disposable.add(
                observable.subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.isInserted(movie.getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.isInserted(-1);
                    }
                })
        );
    }

    public void deleteData(MovieModel movie) {
        Completable observable = moviesDao.delete(movie.toEntity());
        disposable.add(
                observable.subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.isDeleted(movie.getId());
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.isDeleted(-1);
                        dispose();
                    }
                })
        );
    }

}
