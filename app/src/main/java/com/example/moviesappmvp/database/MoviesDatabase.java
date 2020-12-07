package com.example.moviesappmvp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities={MoviesEntity.class},version=4,exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase moviesDatabaseInstance;
    public abstract MoviesDao moviesDao();

    public static synchronized MoviesDatabase getInstance(Context context) {
        if (moviesDatabaseInstance == null) {
            moviesDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), MoviesDatabase.class
                    , "movies").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return moviesDatabaseInstance;
    }
}

