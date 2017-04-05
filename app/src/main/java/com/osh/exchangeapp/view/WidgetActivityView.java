package com.osh.exchangeapp.view;

import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.Widget;
import com.osh.exchangeapp.view.interfaces.IView;

/**
 * Created by oleg on 2/10/2017.
 */
public interface WidgetActivityView extends IView {
    void showRate(ExchangeKey exchanges);
    void showWidget(Widget widget);
}
