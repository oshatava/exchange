package com.osh.exchangeapp.domain;

import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;

/**
 * Created by oleg on 3/1/2017.
 */

public class ExchangeKey {
    private int id;
    private Currency master;
    private Currency slave;
    private float amount;
    private float amountMin;
    private float amountMax;
    private float amountDelta;
    private boolean notifyMin;
    private boolean notifyMax;
    private boolean notifyDelta;
    private long updatePeriod;
    private int order;
    private String source;
    private Exchange exchange;

    public ExchangeKey(int id, Currency master, Currency slave, int order, String source) {
        this.id = id;
        this.master = master;
        this.slave = slave;
        this.amount = 1f;
        this.order = order;
        this.source = source;
    }

    public ExchangeKey() {
        this.amount = 1f;
    }



    public ExchangeKey(int id,
                       Currency master,
                       Currency slave,
                       String source,
                       float amount,
                       float amountDelta,
                       float amountMax,
                       float amountMin,
                       boolean notifyDelta,
                       boolean notifyMax,
                       boolean notifyMin,
                       int order,
                       long updatePeriod) {
        this.id = id;
        this.amount = amount;
        this.amountDelta = amountDelta;
        this.amountMax = amountMax;
        this.amountMin = amountMin;
        this.master = master;
        this.notifyDelta = notifyDelta;
        this.notifyMax = notifyMax;
        this.notifyMin = notifyMin;
        this.slave = slave;
        this.updatePeriod = updatePeriod;
        this.order = order;
        this.source = source;
    }

    @Override
    public String toString() {
        if(master!=null && slave!=null)
            return master.getId()+"/"+slave.getId();
        else
            return super.toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public Currency getSlave() {
        return slave;
    }

    public Currency getMaster() {
        return master;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getAmountDelta() {
        return amountDelta;
    }

    public void setAmountDelta(float amountDelta) {
        this.amountDelta = amountDelta;
    }

    public float getAmountMax() {
        return amountMax;
    }

    public void setAmountMax(float amountMax) {
        this.amountMax = amountMax;
    }

    public float getAmountMin() {
        return amountMin;
    }

    public void setAmountMin(float amountMin) {
        this.amountMin = amountMin;
    }

    public void setMaster(Currency master) {
        this.master = master;
    }

    public boolean isNotifyDelta() {
        return notifyDelta;
    }

    public void setNotifyDelta(boolean notifyDelta) {
        this.notifyDelta = notifyDelta;
    }

    public boolean isNotifyMax() {
        return notifyMax;
    }

    public void setNotifyMax(boolean notifyMax) {
        this.notifyMax = notifyMax;
    }

    public boolean isNotifyMin() {
        return notifyMin;
    }

    public void setNotifyMin(boolean notifyMin) {
        this.notifyMin = notifyMin;
    }

    public void setSlave(Currency slave) {
        this.slave = slave;
    }

    public long getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(long updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public RateSourceDescription getKey(){
        return new RateSourceDescription(getSource(), getMaster()+""+getSlave());
    }

    public double getChange() {
        if(getExchange()!=null){
            return getAmount()*getExchange().getChange();
        }
        return 0;
    }

    public double getDayLow() {
        if(getExchange()!=null){
            return getAmount()*getExchange().getDayLow();
        }
        return 0;
    }

    public double getDayHigh() {
        if(getExchange()!=null){
            return getAmount()*getExchange().getDayHigh();
        }
        return 0;
    }

    public double getLastTrade() {
        if(getExchange()!=null){
            return getAmount()*getExchange().getLastTrade();
        }
        return 0;
    }

    public long getDate() {
        if(getExchange()!=null){
            return getExchange().getDate();
        }
        return 0;
    }
}
