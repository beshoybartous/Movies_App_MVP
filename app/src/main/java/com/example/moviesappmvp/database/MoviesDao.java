package com.example.moviesappmvp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(MoviesEntity movie);
    @Delete
    Completable delete(MoviesEntity movie);

    @Query( "Select id from movies where id= :id" )
    Single<Integer> getSpecificMovie(int id);

    @Query( "Select id from movies" )
    Maybe<List<Integer>> getMoviesId();

    @Query( "Select * from movies" )
    Maybe<List<MoviesEntity>> getMovies();
}
