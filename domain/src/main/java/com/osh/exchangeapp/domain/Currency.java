package com.osh.exchangeapp.domain;

/**
 * Created by oleg on 3/1/2017.
 */

public class Currency {
    String id;
    String name;
    String country;

    public Currency(String id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Currency(String id) {
        this.id = id;
        this.name = id;
        this.country = "";
    }

    public String getCountry() {
        return country;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id;
    }
}
