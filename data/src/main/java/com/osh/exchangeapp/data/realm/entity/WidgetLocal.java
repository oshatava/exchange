package com.osh.exchangeapp.data.realm.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by oleg on 3/1/2017.
 */

public class WidgetLocal  extends RealmObject {
    @PrimaryKey
    int id;

    int bgColor;
    int textColor;
    int exchangeKeyId;

    public WidgetLocal() {
    }

    public WidgetLocal(int id, int textColor, int bgColor, int exchangeKeyId) {
        this.id = id;
        this.textColor = textColor;
        this.bgColor = bgColor;
        this.exchangeKeyId = exchangeKeyId;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getExchangeKeyId() {
        return exchangeKeyId;
    }

    public void setExchangeKeyId(int exchangeKeyId) {
        this.exchangeKeyId = exchangeKeyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
