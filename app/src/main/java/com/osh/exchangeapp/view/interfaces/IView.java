package com.osh.exchangeapp.view.interfaces;

/**
 * Created by oleg on 2/9/2017.
 */

public interface IView {
    void hideWait();
    void showWait();
    void showError(Throwable error);
}
