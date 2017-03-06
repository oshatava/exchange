package com.osh.exchangeapp.domain;

import com.osh.exchangeapp.domain.entity.RateSourceDescription;

import javax.xml.transform.Source;

/**
 * Created by oleg on 1/23/2017.
 */

//"USD/EUR",0.9484,+0.0029,0.9443,0.9496,"3/1/2017","9:23am"

public class Exchange {
    private float lastTrade;
    private float change;
    private float dayLow;
    private float dayHigh;
    private long date;
    private Currency master;
    private Currency slave;
    private String source;

    public Exchange(Currency master, Currency slave, String source, float lastTrade, float change, float dayLow, float dayHigh, long date) {
        this.master = master;
        this.slave = slave;
        this.lastTrade = lastTrade;
        this.change = change;
        this.dayLow = dayLow;
        this.dayHigh = dayHigh;
        this.date = date;
        this.source = source;
    }

    public float getChange() {
        return change;
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
        return master.getId()+"/"+slave.getId();
    }

    public float getLastTrade() {
        return lastTrade;
    }

    public String getSource() {
        return source;
    }

    public Currency getMaster() {
        return master;
    }

    public Currency getSlave() {
        return slave;
    }

    @Override
    public String toString() {
        return "Exchange["+getId()+"] lastTrade:"+getLastTrade()+" change:"+getChange();
    }
}
