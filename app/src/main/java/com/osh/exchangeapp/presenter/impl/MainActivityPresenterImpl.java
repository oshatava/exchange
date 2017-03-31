package com.osh.exchangeapp.presenter.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.interactor.ExchangeInterator;
import com.osh.exchangeapp.navigator.AppNavigator;
import com.osh.exchangeapp.presenter.MainActivityPresenter;
import com.osh.exchangeapp.view.MainActivityView;

import java.util.List;

/**
 * Created by oleg on 3/2/2017.
 */

public class MainActivityPresenterImpl extends BasePresenter<ExchangeInterator, MainActivityView> implements MainActivityPresenter {

    private List<ExchangeKey> keys;
    private AppNavigator appNavigator;


    public MainActivityPresenterImpl(AppNavigator appNavigator, ExchangeInterator exchangeInterator, MainActivityView mainActivityView) {
        super(exchangeInterator, mainActivityView);
        this.appNavigator = appNavigator;
    }

    private Handler handler;
    private Runnable update = new Runnable() {
        @Override
        public void run() {
            onUpdateRates();
        }
    };


    public void onUpdateRates() {
        getModel().getExchangeKeys(this::onExchangeKeysGot, this::onError);
    }

    private void scheduleUpdate() {
        handler.removeCallbacks(update);
        handler.postDelayed(update, 10*1000);
    }

    @Override
    public void onStart() {
        super.onStart();
        handler = new Handler(Looper.getMainLooper());
        getView().showWait();
        getModel().getAllCurrencies(this::onCurrencies, this::onError);

    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(update);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(update);
    }

    private void onCurrencies(List<Currency> currencies) {
        if(currencies.size()==0) {
            setUpDefaults();
        }else{
            start();
        }
    }

    private void start() {
        getModel().getExchangeKeys(this::onExchangeKeysGot, this::onError);

    }

    private void onExchangeKeysGot(List<ExchangeKey> keys) {
        getView().hideWait();
        if(keys.size()!=0){
            this.keys = keys;
            if(hasView()) {
                getView().showRates(keys);
            }
        }
        scheduleUpdate();
    }

    private void setUpDefaults() {
        getModel().createCurrencies(this::onCurrenciesCreated, this::onError);
    }

    private void onCurrenciesCreated(Void aVoid) {
        start();
    }

    @Override
    public void onAddNewExchange() {
        appNavigator.showExchangeEditor(0);
    }

    @Override
    public void onItemClicked(ExchangeKey exchange) {
        appNavigator.showExchangeEditor(exchange.getId());
    }


}
