package com.example.moviesappmvp.ui.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.moviesappmvp.R;
import com.example.moviesappmvp.base.BaseActivity;
import com.example.moviesappmvp.base.BaseView;
import com.example.moviesappmvp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends BaseActivity<MoviePresenter, ActivityMainBinding> implements BaseView {

    private static final String KEY_MOVIE_ID_LIST = "movies id";
    List<Integer> moviesIDList = new ArrayList<>();

    public static void startMoviesActivity(Context context, List<Integer> moviesID) {
        Intent moviesActivityIntent = new Intent(context, MoviesActivity.class);
        moviesActivityIntent.putIntegerArrayListExtra(KEY_MOVIE_ID_LIST, (ArrayList<Integer>) moviesID);
        context.startActivity(moviesActivityIntent);
    }

    @Override
    protected ActivityMainBinding setViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected MoviePresenter setPresenter() {
        return new MoviePresenter(this, this);
    }

    @Override
    protected void onPostCreated() {
        if (getIntent().getSerializableExtra(KEY_MOVIE_ID_LIST) != null) {
            moviesIDList = getIntent().getIntegerArrayListExtra(KEY_MOVIE_ID_LIST);
        }
        // EventBus.getDefault().postSticky(new MoviesIdEvent(moviesIDList));

        NavController navController = Navigation.findNavController(this, R.id.myNavHostFragment);
        NavigationUI.setupWithNavController(viewBinding.bottomNav, navController);
        viewBinding.bottomNav.setBackground(null);
        viewBinding.bottomNav.getMenu().getItem(1).setEnabled(false);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.popular_movie_fragment ||
                        destination.getId() == R.id.top_rated_movie_fragment) {
                    viewBinding.fabHome.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                } else {
                    viewBinding.fabHome.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                }
            }
        });

        viewBinding.fabHome.setOnClickListener(view -> {
            navController.navigateUp();
            navController.navigate(R.id.favourite_movies_fragment);
        });
    }


}