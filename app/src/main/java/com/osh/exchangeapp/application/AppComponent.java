package com.osh.exchangeapp.application;

import com.osh.exchangeapp.activity.MainActivity;
import com.osh.exchangeapp.widgets.ExchangeRateWidget;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by oleg on 2/7/2017.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(ExchangeRateWidget exchangeRateWidget);
}