package com.example.moviesappmvp.ui.splashscreen;

import android.os.Handler;

import com.example.moviesappmvp.cache.SharedPref;
import com.example.moviesappmvp.base.BaseActivity;
import com.example.moviesappmvp.databinding.ActivitySplashBinding;
import com.example.moviesappmvp.ui.mainactivity.MoviesActivity;

import java.util.List;

public class SplashActivity extends BaseActivity<SplashScreenPresenter, ActivitySplashBinding>implements SplashScreenView {



    @Override
    protected ActivitySplashBinding setViewBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected SplashScreenPresenter setPresenter() {
        return new SplashScreenPresenter(this,this);
    }

    @Override
    protected void onPostCreated() {
        presenter.getData();
    }

    @Override
    public void getMoviesId(List<Integer> moviesId) {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPref.setMoviesIDMap(moviesId);
                MoviesActivity.startMoviesActivity(SplashActivity.this,moviesId);
                finish();
            }
        },3000);

    }

}