package com.example.moviesappmvp.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity <P extends BasePresenter,V extends ViewBinding> extends AppCompatActivity {
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
        onPostCreated();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

}
