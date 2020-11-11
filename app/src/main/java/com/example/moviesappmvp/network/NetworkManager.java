package com.example.moviesappmvp.network;

import android.util.Log;

import com.example.moviesappmvp.model.ErrorModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NetworkManager {

    public <T> Observable<T> getData(String path,Map<String, Object> params ,Class<T> tClass){
        Observable<Response<JsonElement>> call=RetrofitClient.getApiService().getData(path,params);
        return  call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(jsonElementResponse -> parseResponse(jsonElementResponse, tClass));
    }

    private <T> T parseResponse(Response<JsonElement> response,Class<T>tClass) throws Exception{
        try {
            if(!response.isSuccessful()){
                throw  new ErrorModel(1,response.errorBody().toString());
            }
            else{
                return new Gson().fromJson(response.body().toString(), tClass);
            }
        }
        catch (Exception e) {
            throw e;
        }
    }
}
