package com.example.moviesappmvp.network;

import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {
    @GET
    Observable<Response<JsonElement>> getData(@Url String path , @QueryMap Map<String, Object> param);
}
