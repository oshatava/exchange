package com.osh.exchangeapp.data.local;

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

public interface WidgetLocalRepository extends LocalRepository  {

    Widget getWidget(int id);
    List<Widget> getWidgets();

    Widget setWidget(Widget widget);
    List<Widget> setWidgets(List<Widget> widgets);

    void removeWidget(Integer id);
    void clearWidgets();

}
