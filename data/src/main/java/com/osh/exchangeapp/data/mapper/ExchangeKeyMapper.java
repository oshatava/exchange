package com.osh.exchangeapp.data.mapper;

import com.osh.exchangeapp.data.realm.entity.ExchangeKeyLocal;
import com.osh.exchangeapp.data.realm.entity.ExchangeLocal;
import com.osh.exchangeapp.data.retrofit.entity.ExchangeRemote;
import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by oleg on 2/6/2017.
 */

public class ExchangeKeyMapper extends BaseMapper<ExchangeKey, ExchangeKeyLocal, Object> {

    public ExchangeKey fromLocal(ExchangeKeyLocal u){
        if(u==null)
            return null;
        return new ExchangeKey(
                u.getId(),
                new Currency(u.getMasterCurrencyId()),
                new Currency(u.getSlaveCurrencyId()),
                u.getSource(),
                u.getAmount(),
                u.getAmountDelta(),
                u.getAmountMax(),
                u.getAmountMin(),
                u.isNotifyDelta(),
                u.isNotifyMax(),
                u.isNotifyMin(),
                u.getOrder(),
                u.getUpdatePeriod(),
                u.getWidgetId()
        );
    }

    public ExchangeKeyLocal toLocal(ExchangeKey u) {
        if(u==null)
            return null;
        return new ExchangeKeyLocal(u.getId(),
                u.getMaster().getId(),
                u.getSlave().getId(),
                u.getSource(),
                u.getAmount(),
                u.getAmountDelta(),
                u.getAmountMax(),
                u.getAmountMin(),
                u.isNotifyDelta(),
                u.isNotifyMax(),
                u.isNotifyMin(),
                u.getOrder(),
                u.getUpdatePeriod(),
                u.getWidgetId()
        );
    }


    @Override
    public ExchangeKey fromRemote(Object u) {
        return null;
    }
}
