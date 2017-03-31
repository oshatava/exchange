package com.osh.exchangeapp.view;

import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.view.interfaces.IView;

import java.util.List;

/**
 * Created by oleg on 2/10/2017.
 */
public interface WidgetView extends IView {
    void showRate(ExchangeKey exchange);
}
