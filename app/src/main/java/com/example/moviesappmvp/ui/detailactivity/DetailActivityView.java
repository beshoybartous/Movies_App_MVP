package com.example.moviesappmvp.ui.detailactivity;

import com.example.moviesappmvp.base.BaseView;

public interface DetailActivityView extends BaseView {
     void isInserted(boolean success);
     void isDeleted(boolean success);
}
