package com.example.moviesappmvp.ui.detailactivity;

import android.content.Context;

import com.example.moviesappmvp.base.BasePresenter;
import com.example.moviesappmvp.model.MovieModel;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class DetailActivityPresenter extends BasePresenter {
    DetailActivityView view;
    public DetailActivityPresenter(DetailActivityView view, Context context) {
        super(view,context);
        this.view=view;
    }
    public void insertData(MovieModel movie){
        Completable observable = moviesDao.insert(movie.toEntity());
        disposable.add(
                observable.subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.isInserted(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.isInserted(false);
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
                        view.isDeleted(true);
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.isDeleted(false);
                        dispose();
                    }
                })
        );
    }
}
