package com.osh.exchangeapp.domain.repository;

import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.Widget;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by oleg on 2/6/2017.
 */

public interface WidgetRepository {

    Observable<Widget> getWidget(int id);
    Observable<Widget> setWidget(Widget widget);
    Observable<Void> clearWidgets();

    // sync methods
    List<Widget> getWidgets();
    List<Widget> getOrCreateWidgets(List<Integer> ids);
    Widget getWidgetSync(int id);

    List<Widget> setWidgets(List<Widget> widgets);
    Widget setWidgetSync(Widget widgets);

    void removeWidget(Integer id);

}
