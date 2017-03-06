package com.osh.exchangeapp.view;

import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.view.interfaces.IView;

import java.util.List;

/**
 * Created by oleg on 2/10/2017.
 */
public interface MainActivityView extends IView {
    void showRates(List<ExchangeKey> exchanges);
}
