package com.osh.exchangeapp.data.realm.entity;

import com.osh.exchangeapp.data.local.CacheUtils;
import com.osh.exchangeapp.data.local.LocalEntity;
import com.osh.exchangeapp.domain.Currency;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by oleg on 2/6/2017.
 */

public class ExchangeLocal extends RealmObject {
    @PrimaryKey
    private String id;

    private float lastTrade;
    private float change;
    private float dayLow;
    private float dayHigh;
    private long date;
    private String masterCurrencyKey;
    private String slaveCurrencyKey;
    private String source;
    private String currencyKey;

    public ExchangeLocal() {

    }

    public ExchangeLocal(String masterCurrencyKey, String slaveCurrencyKey, String source, float lastTrade, float change, float dayLow, float dayHigh, long date) {
        this.change = change;
        this.masterCurrencyKey = masterCurrencyKey;
        this.slaveCurrencyKey = slaveCurrencyKey;
        this.date = date;
        this.dayHigh = dayHigh;
        this.dayLow = dayLow;
        this.lastTrade = lastTrade;
        this.source = source;
        this.id = this.masterCurrencyKey+this.slaveCurrencyKey+" "+this.source+"-"+this.date;
        this.currencyKey = this.masterCurrencyKey+this.slaveCurrencyKey;
    }

    public float getChange() {
        return change;
    }

    public String getMasterCurrencyKey() {
        return masterCurrencyKey;
    }

    public String getSlaveCurrencyKey() {
        return slaveCurrencyKey;
    }

    public long getDate() {
        return date;
    }

    public float getDayHigh() {
        return dayHigh;
    }

    public float getDayLow() {
        return dayLow;
    }

    public String getId() {
        return id;
    }

    public float getLastTrade() {
        return lastTrade;
    }

    public String getSource() {
        return source;
    }

    public String getCurrencyKey(){
        return currencyKey;
    }


    @Override
    public String toString() {
        return "ExchangeLocal["+getMasterCurrencyKey()+"/"+getSlaveCurrencyKey()+"]"+" lastTrade:"+getLastTrade()+" change:"+getChange();
    }
}
