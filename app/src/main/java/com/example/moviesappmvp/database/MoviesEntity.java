package com.example.moviesappmvp.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.moviesappmvp.model.MovieModel;

@Entity(tableName = "movies")
public class MoviesEntity {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id=-1;
    private String posterPath;
    private String title;
    private String overView;
    private String releaseDate;
    private double rating;

    public MoviesEntity(int id, String posterPath, String title, String overView, String releaseDate, double rating) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.overView = overView;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }
    public MovieModel toModel(){
        return new MovieModel(posterPath,
                id,
                title,
                rating,
                overView,
                releaseDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
