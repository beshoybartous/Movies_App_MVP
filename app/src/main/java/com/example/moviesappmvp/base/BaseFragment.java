package com.example.moviesappmvp.base;

import android.content.Context;
import android.content.SharedPreferences;
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
    public static final String STORE_FILE_NAME = "Movies_ID";
    SharedPreferences mPrefs;

    SharedPreferences.Editor prefsEditor;
    protected abstract V setViewBinding();
    protected abstract P setPresenter();
    protected abstract void onPostCreated();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding=setViewBinding();
        presenter=setPresenter();
        mPrefs = getActivity().getSharedPreferences(STORE_FILE_NAME, Context.MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        onPostCreated();
        return viewBinding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
