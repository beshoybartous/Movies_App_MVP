package com.example.moviesappmvp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviesappmvp.databinding.ItemMovieBinding;
import com.example.moviesappmvp.model.MovieModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.RecyclerViewHolder> {
    protected Context context;
    List<MovieModel> movieModel=new ArrayList<>();
    final MovieClickListener movieClickListener;
    public MovieAdapter(Context context,MovieClickListener movieClickListener){
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
        public RecyclerViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        protected void setBinding(MovieModel movieModel){
            Picasso.with(binding.getRoot().getContext()  ).load( movieModel.getPoster_path()).into(binding.ivMoviePoster);
            binding.tvMovieTitle.setText(movieModel.getTitle());
            binding.tvReleaseDate.setText(movieModel.getRelease_date());
            binding.rbRating.setRating((float) movieModel.getVote_average()/2);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            movieClickListener.onCLick(movieModel.get(position));
        }
    }

    public interface MovieClickListener{
        void onCLick(MovieModel movie);
    }
}
