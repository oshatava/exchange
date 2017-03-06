package com.osh.exchangeapp.data.retrofit.entity;

import com.osh.exchangeapp.data.conf.Constants;
import com.osh.exchangeapp.data.local.CacheUtils;

/**
 * Created by oleg on 2/6/2017.
 */

public class ExchangeRemote {
    private float lastTrade;
    private float change;
    private float dayLow;
    private float dayHigh;
    private String date;
    private String time;
    private String masterCurrencyKey;
    private String slaveCurrencyKey;
    private Constants.RateDataSource  source = Constants.RateDataSource.YAHOO;

    public ExchangeRemote() {
    }

    public ExchangeRemote(String masterCurrencyKey, String slaveCurrencyKey, float lastTrade, float change, float dayLow, float dayHigh, String date, String time) {
        this.masterCurrencyKey = masterCurrencyKey;
        this.slaveCurrencyKey = slaveCurrencyKey;
        this.lastTrade = lastTrade;
        this.change = change;
        this.dayLow = dayLow;
        this.dayHigh = dayHigh;
        this.date = date;
        this.time = time;
    }

    public float getChange() {
        return change;
    }

    public float getDayHigh() {
        return dayHigh;
    }

    public float getDayLow() {
        return dayLow;
    }

    public float getLastTrade() {
        return lastTrade;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getMasterCurrencyKey() {
        return masterCurrencyKey;
    }

    public String getSlaveCurrencyKey() {
        return slaveCurrencyKey;
    }

    @Override
    public String toString() {
        return "ExchangeRemote["+masterCurrencyKey+"/"+slaveCurrencyKey+"]"+" lastTrade:"+lastTrade+" change:"+change;

    }

    public Constants.RateDataSource getSource() {
        return source;
    }
}
