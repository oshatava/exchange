package com.osh.exchangeapp.data.realm.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by oleg on 2/6/2017.
 */

public class CurrencyLocal extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private String country;

    public CurrencyLocal() {

    }

    public CurrencyLocal(String id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }


    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CurrencyLocal["+id+"]";
    }
}
