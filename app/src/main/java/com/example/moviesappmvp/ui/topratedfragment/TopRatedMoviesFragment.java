package com.example.moviesappmvp.ui.topratedfragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesappmvp.cache.SharedPref;
import com.example.moviesappmvp.adapter.MovieAdapter;
import com.example.moviesappmvp.base.BaseFragment;
import com.example.moviesappmvp.databinding.FragmentTopRatedMoviesBinding;
import com.example.moviesappmvp.model.MovieModel;
import com.example.moviesappmvp.network.EndPoints;
import com.example.moviesappmvp.response.MoviesPayLoad;
import com.example.moviesappmvp.response.MoviesResponse;
import com.example.moviesappmvp.ui.MoviesIdEvent;
import com.example.moviesappmvp.ui.detailactivity.DetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TopRatedMoviesFragment extends BaseFragment<TopRatedMoviesPresenter, FragmentTopRatedMoviesBinding> implements TopRatedMoviesView, MovieAdapter.MovieClickListener {
    private int totalNumberOfPages=0;
    MovieAdapter movieAdapter;
    int pageCounter=1;
    boolean isLoading=true;

    @Override
    protected FragmentTopRatedMoviesBinding setViewBinding() {
        if(viewBinding==null) {
            viewBinding = FragmentTopRatedMoviesBinding.inflate(getLayoutInflater());
        }
        return viewBinding;
    }

    @Override
    protected TopRatedMoviesPresenter setPresenter() {
        return new TopRatedMoviesPresenter(this,getContext());
    }

    @Override
    protected void onPostCreated() {
        movieAdapter=new MovieAdapter(getContext(),TopRatedMoviesFragment.this);
        viewBinding.rvTopRatedMovies.setAdapter(movieAdapter);
        MoviesPayLoad moviesPayLoad = new MoviesPayLoad(EndPoints.API_KEY, pageCounter);
        presenter.getData(moviesPayLoad);
        viewBinding.rvTopRatedMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int listSize = ((LinearLayoutManager) recyclerView.getLayoutManager()).getItemCount() - 2;
                if (lastVisibleItemPosition >= listSize && isLoading && pageCounter < totalNumberOfPages) {
                    isLoading = false;
                    pageCounter++;
                    MoviesPayLoad moviesPayLoad2 = new MoviesPayLoad(EndPoints.API_KEY, pageCounter);
                    presenter.getData(moviesPayLoad2);
                }
            }
        });
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
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMovies(MoviesResponse movies) {
        if(movies!=null){
            totalNumberOfPages=movies.getTotalPages();
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

    @Override
    public void addToFavourite(MovieModel movie) {
        presenter.insertData(movie);
    }

    @Override
    public void isInserted(Integer id) {
        SharedPref.addValue(id);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeFromFavourite(MovieModel model) {
        presenter.deleteData(model);
    }

    @Override
    public void isDeleted(Integer id) {
        SharedPref.removeValue(id);
        movieAdapter.notifyDataSetChanged();
    }
}