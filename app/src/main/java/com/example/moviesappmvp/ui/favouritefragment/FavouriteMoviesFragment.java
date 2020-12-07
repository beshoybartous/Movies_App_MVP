package com.example.moviesappmvp.ui.favouritefragment;

import com.example.moviesappmvp.cache.SharedPref;
import com.example.moviesappmvp.adapter.FavouriteMovieAdapter;
import com.example.moviesappmvp.base.BaseFragment;
import com.example.moviesappmvp.databinding.FragmentFavouriteMoviesBinding;
import com.example.moviesappmvp.model.MovieModel;
import com.example.moviesappmvp.ui.MoviesIdEvent;
import com.example.moviesappmvp.ui.detailactivity.DetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class FavouriteMoviesFragment extends BaseFragment<FavouriteMoviesPresenter, FragmentFavouriteMoviesBinding>implements FavouriteMoviesView, FavouriteMovieAdapter.MovieClickListener {
    FavouriteMovieAdapter movieAdapter;
    private static final int REQUEST_CODE=1;
    int clickedItemPosition=-1;
    MoviesIdEvent moviesIdEvent=new MoviesIdEvent();

    @Override
    protected FragmentFavouriteMoviesBinding setViewBinding() {
        if(viewBinding==null) {
            viewBinding = FragmentFavouriteMoviesBinding.inflate(getLayoutInflater());
        }
        return viewBinding;
    }

    @Override
    protected FavouriteMoviesPresenter setPresenter() {
        return new FavouriteMoviesPresenter(this,getContext());
    }

    @Override
    protected void onPostCreated() {
        movieAdapter=new FavouriteMovieAdapter(getContext(), FavouriteMoviesFragment.this);
        viewBinding.rvFavouriteMovies.setAdapter(movieAdapter);
        presenter.getFavouriteMovies();

    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventData(MoviesIdEvent moviesIdEvent){
        presenter.getFavouriteMovies();
    }
    @Override
    public void getMovies(List<MovieModel> movies) {
        if(movies!=null){
            if(movies.size()>0){
                movieAdapter.updateList(movies);
            }
        }
    }

    @Override
    public void isDeleted(Integer id) {
        movieAdapter.remove(id);
        SharedPref.removeValue(id);
    }

    @Override
    public void onCLick(MovieModel movie,int position) {
        DetailActivity.startDetailActivity(getContext(),movie);
    }

    @Override
    public void removeFromFavourite(MovieModel model) {
        presenter.deleteData(model);
    }
}