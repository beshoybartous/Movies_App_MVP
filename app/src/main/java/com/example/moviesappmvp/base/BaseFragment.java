package com.example.moviesappmvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<P extends BasePresenter,V extends ViewBinding> extends Fragment {
    protected P presenter;
    protected V viewBinding;
    protected abstract V setViewBinding();
    protected abstract P setPresenter();
    protected abstract void onPostCreated();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding=setViewBinding();
        presenter=setPresenter();
        onPostCreated();
        return viewBinding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
