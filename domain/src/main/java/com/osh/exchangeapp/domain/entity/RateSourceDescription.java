package com.osh.exchangeapp.domain.entity;

import com.osh.exchangeapp.domain.Currency;

/**
 * Created by oleg on 3/4/2017.
 */

public class RateSourceDescription {
    String sourceName;
    String key;

    public RateSourceDescription(String sourceName, String key) {
        this.key = key;
        this.sourceName = sourceName;
    }

    public String getKey() {
        return key;
    }

    public String getSourceName() {
        return sourceName;
    }


}
