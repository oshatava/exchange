package com.osh.exchangeapp.data.realm.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by oleg on 2/6/2017.
 */

public class ExchangeKeyLocal extends RealmObject {
    @PrimaryKey
    private int id;

    private String masterCurrencyId;
    private String slaveCurrencyId;
    private int order;

    private float amount;
    private float amountMin;
    private float amountMax;
    private float amountDelta;
    private boolean notifyMin;
    private boolean notifyMax;
    private boolean notifyDelta;
    private long updatePeriod;
    private String source;
    private int widgetId;

    public ExchangeKeyLocal() {

    }

    public ExchangeKeyLocal(int id, String masterCurrencyId, String slaveCurrencyId, String source) {
        this.masterCurrencyId = masterCurrencyId;
        this.slaveCurrencyId = slaveCurrencyId;
        this.source = source;
    }

    public ExchangeKeyLocal(int id,
                            String masterCurrencyId,
                            String slaveCurrencyId,
                            String source,
                            float amount,
                            float amountDelta,
                            float amountMax,
                            float amountMin,
                            boolean notifyDelta,
                            boolean notifyMax,
                            boolean notifyMin,
                            int order,
                            long updatePeriod,
                            int widgetId) {
        this.amount = amount;
        this.amountDelta = amountDelta;
        this.amountMax = amountMax;
        this.amountMin = amountMin;
        this.id = id;
        this.masterCurrencyId = masterCurrencyId;
        this.notifyDelta = notifyDelta;
        this.notifyMax = notifyMax;
        this.notifyMin = notifyMin;
        this.order = order;
        this.slaveCurrencyId = slaveCurrencyId;
        this.updatePeriod = updatePeriod;
        this.source = source;
        this.widgetId = widgetId;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public String getMasterCurrencyId() {
        return masterCurrencyId;
    }

    public String getSlaveCurrencyId() {
        return slaveCurrencyId;
    }

    public int getOrder() {
        return order;
    }


    public float getAmount() {
        return amount;
    }

    public float getAmountDelta() {
        return amountDelta;
    }

    public float getAmountMax() {
        return amountMax;
    }

    public float getAmountMin() {
        return amountMin;
    }

    public boolean isNotifyDelta() {
        return notifyDelta;
    }

    public boolean isNotifyMax() {
        return notifyMax;
    }

    public boolean isNotifyMin() {
        return notifyMin;
    }

    public long getUpdatePeriod() {
        return updatePeriod;
    }

    public String getSource() {
        return source;
    }

    public int getWidgetId() {
        return widgetId;
    }

    @Override
    public String toString() {
        return "ExchangeKeysLocal["+id+"]";
    }
}
