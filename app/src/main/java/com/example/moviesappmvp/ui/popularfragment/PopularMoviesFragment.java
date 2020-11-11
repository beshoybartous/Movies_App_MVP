package com.example.moviesappmvp.ui.popularfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.dynamicfeatures.Constants;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviesappmvp.R;
import com.example.moviesappmvp.adapter.MovieAdapter;
import com.example.moviesappmvp.base.BaseFragment;
import com.example.moviesappmvp.databinding.FragmentPopularMoviesBinding;
import com.example.moviesappmvp.model.MovieModel;
import com.example.moviesappmvp.network.EndPoints;
import com.example.moviesappmvp.response.MoviesPayLoad;
import com.example.moviesappmvp.response.MoviesResponse;
import com.example.moviesappmvp.ui.detailactivity.DetailActivity;

import java.util.List;

public class PopularMoviesFragment extends BaseFragment<PopularMoviesPresenter, FragmentPopularMoviesBinding> implements PopularMoviesView, MovieAdapter.MovieClickListener {
    private int totalNumberOfPages=0;
    @Override
    protected FragmentPopularMoviesBinding setViewBinding() {
        if(viewBinding==null) {
            viewBinding = FragmentPopularMoviesBinding.inflate(getLayoutInflater());
        }
        return viewBinding;
    }

    @Override
    protected PopularMoviesPresenter setPresenter() {
        return new PopularMoviesPresenter(this);
    }
    MovieAdapter movieAdapter;
    int pageCounter=1;
    boolean isLoading=true;
    @Override
    protected void onPostCreated() {
        MoviesPayLoad moviesPayLoad=new MoviesPayLoad(EndPoints.API_KEY, pageCounter);
        presenter.getData(moviesPayLoad);
        movieAdapter=new MovieAdapter(getContext(),PopularMoviesFragment.this);
        viewBinding.rvPopularMovies.setAdapter(movieAdapter);
        viewBinding.rvPopularMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    public void getMovies(MoviesResponse movies) {
        if(movies!=null){
            totalNumberOfPages=movies.getTotal_pages();
            if(movies.getResults().size()>0){
                movieAdapter.updateList(movies.getResults());
                isLoading=true;
            }
        }
    }

    @Override
    public void onCLick(MovieModel movie) {
        DetailActivity.startDetailActivity(getContext(), movie);
    }
}