package com.osh.exchangeapp.presenter;

import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.presenter.interfaces.Presenter;
import com.osh.exchangeapp.view.MainActivityView;

/**
 * Created by oleg on 2/9/2017.
 */

public interface MainActivityPresenter extends Presenter<MainActivityView>{

    void onItemClicked(ExchangeKey exchange);
    void onUpdateRates();

    void onAddNewExchange();
}
