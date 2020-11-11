package com.example.moviesappmvp.ui.topratedfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviesappmvp.R;
import com.example.moviesappmvp.adapter.MovieAdapter;
import com.example.moviesappmvp.base.BaseFragment;
import com.example.moviesappmvp.databinding.FragmentPopularMoviesBinding;
import com.example.moviesappmvp.databinding.FragmentTopRatedMoviesBinding;
import com.example.moviesappmvp.model.MovieModel;
import com.example.moviesappmvp.network.EndPoints;
import com.example.moviesappmvp.response.MoviesPayLoad;
import com.example.moviesappmvp.ui.detailactivity.DetailActivity;
import com.example.moviesappmvp.ui.popularfragment.PopularMoviesPresenter;
import com.example.moviesappmvp.ui.popularfragment.PopularMoviesView;

import java.util.List;

public class TopRatedMoviesFragment extends BaseFragment<TopRatedMoviesPresenter, FragmentTopRatedMoviesBinding> implements TopRatedMoviesView, MovieAdapter.MovieClickListener {
    private int totalNumberOfPages=0;
    @Override
    protected FragmentTopRatedMoviesBinding setViewBinding() {
        if(viewBinding==null) {
            viewBinding = FragmentTopRatedMoviesBinding.inflate(getLayoutInflater());
        }
        return viewBinding;
    }

    @Override
    protected TopRatedMoviesPresenter setPresenter() {
        return new TopRatedMoviesPresenter(this);
    }
    MovieAdapter movieAdapter;
    int pageCounter=1;
    boolean isLoading=true;
    @Override
    protected void onPostCreated() {
        MoviesPayLoad moviesPayLoad=new MoviesPayLoad(EndPoints.API_KEY, pageCounter);
        presenter.getData(moviesPayLoad);
        movieAdapter=new MovieAdapter(getContext(),TopRatedMoviesFragment.this);
        viewBinding.rvTopRatedMovies.setAdapter(movieAdapter);
        viewBinding.rvTopRatedMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int listSize=((LinearLayoutManager) recyclerView.getLayoutManager()).getItemCount()-2;
                if (lastVisibleItemPosition>=listSize&&isLoading&&pageCounter<totalNumberOfPages) {
                    isLoading=false;
                    pageCounter++;
                    MoviesPayLoad moviesPayLoad2=new MoviesPayLoad(EndPoints.API_KEY, pageCounter);
                    presenter.getData(moviesPayLoad2);
                }
            }
        });
    }

    @Override
    public void getMovies(List<MovieModel> movies) {
        if(movies.size()>0){
            movieAdapter.updateList(movies);
            isLoading=true;
        }
    }

    @Override
    public void onCLick(MovieModel movie) {
        DetailActivity.startDetailActivity(getContext(), movie);
    }
}