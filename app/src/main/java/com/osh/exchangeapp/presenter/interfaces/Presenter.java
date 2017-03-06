package com.osh.exchangeapp.presenter.interfaces;

/**
 * Created by oleg on 2/9/2017.
 */

public interface Presenter {
        void onStart();

        void onStop();

        void onError(Throwable throwable);
}
