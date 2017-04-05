package com.osh.exchangeapp.domain;

/**
 * Created by oleg on 3/1/2017.
 */

public class Widget {
    int id;
    int bgColor;
    int textColor;
    int exchangeKeyId;


    public Widget(int id, int textColor, int bgColor, int exchangeKeyId) {
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
