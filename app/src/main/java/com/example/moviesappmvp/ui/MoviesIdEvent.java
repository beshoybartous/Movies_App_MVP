package com.example.moviesappmvp.ui;

import android.util.Log;

import java.util.List;

public class MoviesIdEvent {
    List<Integer> moviesIdList;
    boolean firstInit=true;

    public MoviesIdEvent() {
    }

    public MoviesIdEvent(List<Integer> moviesIdList) {
        Log.d("welcomeineventbus", "MoviesIdEvent: ");
        this.moviesIdList = moviesIdList;
    }

    public boolean isFirstInit() {
        return firstInit;
    }

    public void setFirstInit(boolean firstInit) {
        this.firstInit = firstInit;
    }


    public List<Integer> getMoviesIdList(){
        return moviesIdList;
    }
    public void insertElement(int id){
        moviesIdList.add(id);
    }
    public void removeValue(int id){
        int pos=moviesIdList.indexOf(id);
        moviesIdList.remove(pos);
    }
}
