package com.osh.exchangeapp.application;

import com.osh.exchangeapp.activity.ExchangeEditViewActivity;
import com.osh.exchangeapp.activity.MainActivity;
import com.osh.exchangeapp.activity.SplashActivity;
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
    void inject(ExchangeEditViewActivity exchangeEditActivity);
    void inject(SplashActivity splashActivity);
}
