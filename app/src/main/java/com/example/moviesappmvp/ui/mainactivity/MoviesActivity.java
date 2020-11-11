package com.example.moviesappmvp.ui.mainactivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.paging.PagedList;

import com.example.moviesappmvp.R;
import com.example.moviesappmvp.base.BaseActivity;
import com.example.moviesappmvp.base.BaseView;
import com.example.moviesappmvp.databinding.ActivityMainBinding;
import com.example.moviesappmvp.model.MovieModel;
import com.example.moviesappmvp.network.EndPoints;
import com.example.moviesappmvp.response.MoviesPayLoad;
import com.example.moviesappmvp.ui.popularfragment.PopularMoviesView;
import com.example.moviesappmvp.ui.popularfragment.PopularMoviesPresenter;

import java.util.List;

public class MoviesActivity extends BaseActivity<MoviePresenter, ActivityMainBinding>implements BaseView {

    @Override
    protected ActivityMainBinding setViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected MoviePresenter setPresenter() {
        return new MoviePresenter(this);
    }

    @Override
    protected void onPostCreated() {
        NavController navController = Navigation.findNavController(this, R.id.myNavHostFragment);
        NavigationUI.setupWithNavController(viewBinding.bottomNav, navController);
        viewBinding.bottomNav.setBackground(null);
        viewBinding.bottomNav.getMenu().getItem(1).setEnabled(false);
        viewBinding.fabHome.setOnClickListener(view -> {
            navController.navigateUp();
            navController.navigate(R.id.home_fragment);
        });
    }
}