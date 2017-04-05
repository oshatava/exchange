package com.osh.exchangeapp.data.mapper;

import com.osh.exchangeapp.data.realm.entity.ExchangeLocal;
import com.osh.exchangeapp.data.realm.entity.WidgetLocal;
import com.osh.exchangeapp.data.retrofit.entity.ExchangeRemote;
import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.Widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by oleg on 2/6/2017.
 */

public class WidgetMapper extends BaseLocalMapper<Widget, WidgetLocal> {

    public Widget fromLocal(WidgetLocal u){
        if(u==null)
            return null;
        return new Widget(u.getId(), u.getTextColor(), u.getBgColor(), u.getExchangeKeyId());
    }

    public WidgetLocal toLocal(Widget u) {
        if(u==null)
            return null;
        return new WidgetLocal(u.getId(), u.getTextColor(), u.getBgColor(), u.getExchangeKeyId());
    }

}
