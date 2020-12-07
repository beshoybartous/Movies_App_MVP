package com.example.moviesappmvp.base;

import android.content.Context;

import com.example.moviesappmvp.database.MoviesDao;
import com.example.moviesappmvp.database.MoviesDatabase;
import com.example.moviesappmvp.network.NetworkManager;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter {
    protected NetworkManager networkManager;
    protected MoviesDao moviesDao;

    protected BaseView view;
    protected CompositeDisposable disposable;

    public BasePresenter(BaseView view, Context context ) {
        networkManager = new NetworkManager();
        this.view = view;
        disposable =new CompositeDisposable();
        MoviesDatabase moviesDatabase = MoviesDatabase.getInstance(context);
        moviesDao = moviesDatabase.moviesDao();

        if(moviesDao==null) {
        }
    }


    public void dispose(){
        disposable.clear();
        disposable.dispose();
    }
}