package com.osh.exchangeapp.presenter.impl;

import android.os.Handler;
import android.os.Looper;

import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.interactor.ExchangeInterator;
import com.osh.exchangeapp.navigator.AppNavigator;
import com.osh.exchangeapp.presenter.ExchangeViewActivityPresenter;
import com.osh.exchangeapp.view.ExchangeViewActivityView;
import com.osh.exchangeapp.view.MainActivityView;

import java.util.List;

/**
 * Created by oleg on 3/2/2017.
 */

public class ExchangeViewActivityPresenterImpl extends BasePresenter<ExchangeInterator, ExchangeViewActivityView> implements ExchangeViewActivityPresenter {

    private ExchangeKey key;
    private int id;
    private AppNavigator appNavigator;

    public ExchangeViewActivityPresenterImpl(AppNavigator appNavigator, int id, ExchangeInterator exchangeInterator, ExchangeViewActivityView view) {
        super(exchangeInterator, view);
        this.id = id;
        this.appNavigator = appNavigator;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(hasView())
            getView().showWait();
        if(id!=0)
            getModel().getExchangeKey(id, this::onExchangeKeyGot, this::onError);
        else
            onExchangeKeyGot(new ExchangeKey());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void onExchangeKeyGot(ExchangeKey exchangeKey) {
        this.key = exchangeKey;
        if(hasView())
            getView().showRate(exchangeKey);
    }


    @Override
    public void onSave() {
        if(hasView())
            getView().showWait();
        getModel().setExchangeKey(this.key, this::onExchangeKeySaved, this::onError);
    }

    private void onExchangeKeySaved(ExchangeKey exchangeKey) {
        this.key = exchangeKey;
        if(hasView()) {
            getView().hideWait();
            appNavigator.back();
        }
    }

    @Override
    public void onAmountChanged(Float t) {
        if(t!=null)
            this.key.setAmount(t);
    }

    @Override
    public void onCurrencyMasterChanged(String c) {
        if (this.key!=null)
            key.setMaster(new Currency(c));
    }

    @Override
    public void onCurrencySlaveChanged(String c) {
        if (this.key!=null)
            key.setSlave(new Currency(c));
    }

    @Override
    public void onSwitchCurrencies() {
        if(this.key!=null){
            Currency master = key.getMaster();
            Currency slave = key.getSlave();
            key.setMaster(slave);
            key.setSlave(master);
            getView().showRate(this.key);
        }
    }

    @Override
    public void onCancel() {
        appNavigator.back();
    }



}
