package com.osh.exchangeapp.data.mapper;

import com.osh.exchangeapp.data.realm.entity.ExchangeKeyLocal;
import com.osh.exchangeapp.data.realm.entity.ExchangeLocal;
import com.osh.exchangeapp.data.retrofit.entity.ExchangeRemote;
import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by oleg on 2/6/2017.
 */

public class ExchangeMapper extends BaseMapper<Exchange, ExchangeLocal, ExchangeRemote> {
    public Exchange fromLocal(ExchangeLocal u){
        if(u==null)
            return null;
        return new Exchange(
                new Currency(u.getMasterCurrencyKey()),
                new Currency(u.getSlaveCurrencyKey()),
                u.getSource(),
                u.getLastTrade(),
                u.getChange(),
                u.getDayLow(),
                u.getDayHigh(),
                u.getDate());
    }

    public Exchange fromRemote(ExchangeRemote u){
        if(u==null)
            return null;

        return new Exchange(
                new Currency(u.getMasterCurrencyKey()),
                new Currency(u.getSlaveCurrencyKey()),
                u.getSource().toString(),
                u.getLastTrade(),
                u.getChange(),
                u.getDayLow(),
                u.getDayHigh(),
                dateFromString(u.getDate(), u.getTime())
                );
    }

    public ExchangeLocal toLocal(Exchange u) {
        if(u==null)
            return null;
        return new ExchangeLocal(
                u.getMaster().getId(),
                u.getSlave().getId(),
                u.getSource(),
                u.getLastTrade(),
                u.getChange(),
                u.getDayLow(),
                u.getDayHigh(),
                u.getDate()
                );
    }


    private long dateFromString(String date, String time) {
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy hh:mma");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date dt = format.parse(date + " " + time);
            if (dt!=null)
                return dt.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Calendar.getInstance().getTimeInMillis();
    }


}
