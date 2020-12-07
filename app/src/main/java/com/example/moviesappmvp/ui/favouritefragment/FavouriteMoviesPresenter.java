package com.example.moviesappmvp.ui.favouritefragment;

import android.content.Context;
import com.example.moviesappmvp.base.BasePresenter;
import com.example.moviesappmvp.database.MoviesEntity;
import com.example.moviesappmvp.model.MovieModel;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

public class FavouriteMoviesPresenter extends BasePresenter {
    FavouriteMoviesView view;
    public FavouriteMoviesPresenter(FavouriteMoviesView view, Context context) {
        super(view, context);
        this.view=view;
    }
    void getFavouriteMovies(){
        Maybe<List<MoviesEntity>> observable = moviesDao.getMovies();
        disposable.add(
                 observable.subscribeWith(new DisposableMaybeObserver<List<MoviesEntity>>() {
                    @Override
                    public void onSuccess(List<MoviesEntity> moviesEntities) {
                        List<MovieModel>movieModelList=new ArrayList<>();
                        for (MoviesEntity movie:moviesEntities) {
                            movieModelList.add(movie.toModel());
                        }
                        view.getMovies(movieModelList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
