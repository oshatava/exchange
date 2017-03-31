package com.osh.exchangeapp.navigator;

import com.osh.exchangeapp.activity.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * Created by oleg on 3/30/2017.
 */

public interface AppNavigator {
    void showMain();
    void showExchangeEditor(int id);
    void setCurrentActivity(BaseActivity activity);
    void back();
}
