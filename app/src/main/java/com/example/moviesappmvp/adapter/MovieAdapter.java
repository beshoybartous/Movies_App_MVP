package com.example.moviesappmvp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesappmvp.R;
import com.example.moviesappmvp.cache.SharedPref;
import com.example.moviesappmvp.databinding.ItemMovieBinding;
import com.example.moviesappmvp.model.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.RecyclerViewHolder> {
    protected Context context;
    List<MovieModel> movieModel=new ArrayList<>();
    List<Integer> moviesIDList=new ArrayList<>();
    int counter=0;
    final MovieClickListener movieClickListener;
    public MovieAdapter(Context context,MovieClickListener movieClickListener){
        this.context=context;
        this.movieClickListener=movieClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        Log.d("counteris", String.valueOf(counter));
        counter++;
        ItemMovieBinding movieBinding=ItemMovieBinding.inflate(inflater, parent, false);
        return new RecyclerViewHolder(movieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.setBinding(movieModel.get(position));
    }
    public static final String STORE_FILE_NAME = "Movies_ID";

    @Override
    public int getItemCount() {
        return movieModel.size();
    }
    public void setMoviesID(List<Integer> moviesIDList){
       // this.moviesIDList=moviesIDList;
        notifyDataSetChanged();
    }
    public void updateList(List<MovieModel> list){
        if(movieModel.size()>0){
            movieModel= new ArrayList<MovieModel>() { { addAll(movieModel); addAll(list); } };
            notifyItemInserted(movieModel.size());
        }
        else{
            movieModel= list;
            notifyDataSetChanged();
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemMovieBinding binding;
        boolean isInDatabase=false;
        public RecyclerViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        protected void setBinding(MovieModel movieModel){
            isInDatabase=SharedPref.contain(movieModel.getId());
            if(isInDatabase){
                binding.btnFavourite.setImageResource(R.drawable.ic_favorite);
            }
            else{
                binding.btnFavourite.setImageResource(R.drawable.ic_not_favourite);
            }
            Picasso.with(binding.getRoot().getContext()  ).load( movieModel.getPosterPath()).into(binding.ivMoviePoster);
            binding.tvMovieTitle.setText(movieModel.getTitle());
            binding.tvReleaseDate.setText(movieModel.getReleaseDate());
            binding.rbRating.setRating((float) movieModel.getVoteAverage()/2);
            binding.getRoot().setOnClickListener(this);
            binding.btnFavourite.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            if(view.getId()==R.id.btn_favourite){
                if(!isInDatabase){
                    movieClickListener.addToFavourite(movieModel.get(position));
                }
                else{
                    movieClickListener.removeFromFavourite(movieModel.get(position));
                }
            }
            else {
                movieClickListener.onCLick(movieModel.get(position));
            }
        }
    }

    public interface MovieClickListener{
        void onCLick(MovieModel movie);
        void addToFavourite(MovieModel movie);
        void removeFromFavourite(MovieModel model);
    }
}
