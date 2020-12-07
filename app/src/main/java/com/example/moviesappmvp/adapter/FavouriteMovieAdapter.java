package com.example.moviesappmvp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesappmvp.R;
import com.example.moviesappmvp.databinding.ItemMovieBinding;
import com.example.moviesappmvp.model.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMovieAdapter extends RecyclerView.Adapter<FavouriteMovieAdapter.RecyclerViewHolder> {
    protected Context context;
    List<MovieModel> movieModel=new ArrayList<>();
    final MovieClickListener movieClickListener;
    List<Integer> moviesIDList=new ArrayList<>();

    public FavouriteMovieAdapter(Context context, MovieClickListener movieClickListener){
        this.context=context;
        this.movieClickListener=movieClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemMovieBinding movieBinding=ItemMovieBinding.inflate(inflater, parent, false);
        return new RecyclerViewHolder(movieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.setBinding(movieModel.get(position));
    }

    @Override
    public int getItemCount() {
        return movieModel.size();
    }

    public void remove(int id){
        for(int i=0;i<movieModel.size();i++){
            if(movieModel.get(i).getId()==id){
                movieModel.remove(i);
                notifyDataSetChanged();
                return;
            }
        }
    }
    public void setMoviesID(List<Integer> moviesIDList){
        this.moviesIDList=moviesIDList;
    }

    public void updateList(List<MovieModel> list){
        movieModel= list;
        notifyDataSetChanged();

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemMovieBinding binding;
        public RecyclerViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        protected void setBinding(MovieModel movieModel){
            boolean idExist=moviesIDList.contains(movieModel.getId());
            binding.btnFavourite.setImageResource(R.drawable.ic_favorite);
            Picasso.with(binding.getRoot().getContext()  ).load( movieModel.getPosterPath()).into(binding.ivMoviePoster);
            binding.tvMovieTitle.setText(movieModel.getTitle());
            binding.tvReleaseDate.setText(movieModel.getReleaseDate());
            binding.rbRating.setRating((float) movieModel.getVoteAverage()/2);
            binding.btnFavourite.setOnClickListener(this::onClick);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            if(view.getId()==R.id.btn_favourite){
                movieClickListener.removeFromFavourite(movieModel.get(position));
            }
            else {
                movieClickListener.onCLick(movieModel.get(position),movieModel.get(position).getId());
            }
        }
    }

    public interface MovieClickListener{
        void onCLick(MovieModel movie,int position);
        void removeFromFavourite(MovieModel model);
    }
}
