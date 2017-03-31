package com.osh.exchangeapp.presenter.interfaces;

/**
 * Created by oleg on 2/9/2017.
 */

public interface Presenter <View> {
    void setView(View view);

    void onStart();
    void onPause();
    void onStop();

    void onError(Throwable throwable);

}
