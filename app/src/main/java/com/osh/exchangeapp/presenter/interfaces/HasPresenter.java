package com.osh.exchangeapp.presenter.interfaces;

/**
 * Created by oleg on 2/10/2017.
 */

public interface HasPresenter<PresenterClass> {
    void setPresenter(PresenterClass presenter);
    PresenterClass getPresenter();
}
