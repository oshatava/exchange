package com.osh.exchangeapp.presenter;

import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.presenter.interfaces.Presenter;

/**
 * Created by oleg on 2/9/2017.
 */

public interface MainActivityPresenter extends Presenter{

    void onItemClicked(ExchangeKey exchange);
    void onUpdateRates();
}
