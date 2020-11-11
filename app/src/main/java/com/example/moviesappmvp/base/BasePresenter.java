package com.example.moviesappmvp.base;

import com.example.moviesappmvp.network.NetworkManager;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter {
    protected NetworkManager networkManager;
    protected BaseView view;
    protected CompositeDisposable disposable;

    public BasePresenter( BaseView view ) {
        networkManager = new NetworkManager();
        this.view = view;
        disposable =new CompositeDisposable();
    }

    public void dispose(){
        disposable.clear();
        disposable.dispose();
    }
}