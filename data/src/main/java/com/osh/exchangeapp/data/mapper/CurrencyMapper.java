package com.osh.exchangeapp.data.mapper;

import com.osh.exchangeapp.data.realm.entity.CurrencyLocal;
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

public class CurrencyMapper extends BaseMapper<Currency, CurrencyLocal, Object> {

    @Override
    public Currency fromLocal(CurrencyLocal u) {
        return new Currency(u.getId(), u.getName(), u.getCountry());
    }

    @Override
    public Currency fromRemote(Object u) {
        return null;
    }

    @Override
    public CurrencyLocal toLocal(Currency u) {
        return new CurrencyLocal(u.getId(), u.getName(), u.getCountry());
    }
}
