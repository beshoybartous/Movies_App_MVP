package com.example.moviesappmvp.ui.splashscreen;

import android.content.Context;

import com.example.moviesappmvp.base.BasePresenter;
import com.example.moviesappmvp.base.BaseView;
import com.example.moviesappmvp.database.MoviesEntity;
import com.example.moviesappmvp.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.observers.DisposableMaybeObserver;

public class SplashScreenPresenter extends BasePresenter {
    SplashScreenView view;
    public SplashScreenPresenter(SplashScreenView view, Context context) {
        super(view, context);
        this.view=view;
    }
    public void getData(){
        Maybe<List<Integer>> observable = moviesDao.getMoviesId();
        disposable.add(
                observable.subscribeWith(new DisposableMaybeObserver<List<Integer>>() {
                    @Override
                    public void onSuccess(List<Integer> moviesId) {
                        view.getMoviesId(moviesId);
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
}
