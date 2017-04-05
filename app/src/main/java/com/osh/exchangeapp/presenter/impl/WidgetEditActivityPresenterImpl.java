package com.osh.exchangeapp.presenter.impl;

import android.appwidget.AppWidgetManager;

import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.Widget;
import com.osh.exchangeapp.domain.interactor.ExchangeInterator;
import com.osh.exchangeapp.domain.interactor.WidgetInterator;
import com.osh.exchangeapp.navigator.AppNavigator;
import com.osh.exchangeapp.presenter.WidgetEditActivityPresenter;
import com.osh.exchangeapp.view.ExchangeViewActivityView;
import com.osh.exchangeapp.view.WidgetActivityView;

/**
 * Created by oleg on 3/2/2017.
 */

public class WidgetEditActivityPresenterImpl extends BasePresenter<WidgetInterator, WidgetActivityView> implements WidgetEditActivityPresenter {

    private ExchangeKey key;
    private Widget widget;
    private int id;
    private AppNavigator appNavigator;
    ExchangeInterator exchangeInterator;

    public WidgetEditActivityPresenterImpl(AppNavigator appNavigator,
                                           int id,
                                           ExchangeInterator exchangeInterator,
                                           WidgetInterator widgetInterator,
                                           WidgetActivityView view) {
        super(widgetInterator, view);
        this.id = id;
        this.appNavigator = appNavigator;
        this.exchangeInterator = exchangeInterator;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(hasView())
            getView().showWait();
        if(id!=0) {

            getModel().getWidget(id, this::onWidgetGot, this::onError);
        }
    }

    private void onWidgetGot(Widget widget) {
        this.widget = widget;
        if(widget.getExchangeKeyId()==0){
            onExchangeKeyGot(new ExchangeKey());
        }else{
            exchangeInterator.getExchangeKey(widget.getExchangeKeyId(), this::onExchangeKeyGot, this::onError);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void onExchangeKeyGot(ExchangeKey exchangeKey) {
        this.key = exchangeKey;
        if(hasView()) {
            getView().hideWait();
            getView().showRate(exchangeKey);
            getView().showWidget(widget);
        }
    }


    @Override
    public void onSave() {
        if(hasView())
            getView().showWait();
        exchangeInterator.setExchangeKey(this.key, this::onExchangeKeySaved, this::onError);
    }

    private void onExchangeKeySaved(ExchangeKey exchangeKey) {
        this.key = exchangeKey;

        this.widget.setExchangeKeyId(this.key.getId());
        getModel().setWidget(this.widget, this::onWidgetSaved, this::onError);
    }

    private void onWidgetSaved(Widget widget) {
        if(hasView()) {
            getView().hideWait();
            appNavigator.backSuccessful();
            appNavigator.updateWidget(widget.getId());
        }
    }

    @Override
    public void onBgColorChanged(int bgColor) {
        this.widget.setBgColor(bgColor);
    }

    @Override
    public void onTextColorChanged(int textColor) {
        this.widget.setTextColor(textColor);
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
    public void onSourceChanged(String c) {
        if (this.key!=null)
            key.setSource(c);
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
