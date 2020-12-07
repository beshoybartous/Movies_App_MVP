package com.example.moviesappmvp.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity <P extends BasePresenter,V extends ViewBinding> extends AppCompatActivity {
    public static final String STORE_FILE_NAME = "Movies_ID";
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor prefsEditor;
    protected P presenter;
    protected V viewBinding;
    protected abstract V setViewBinding();
    protected abstract P setPresenter();
    protected abstract void onPostCreated();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding=setViewBinding();
        setContentView(viewBinding.getRoot());
        presenter=setPresenter();
        sharedPreferences = getSharedPreferences(STORE_FILE_NAME, Context.MODE_PRIVATE);
        prefsEditor = sharedPreferences.edit();
        onPostCreated();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

}
