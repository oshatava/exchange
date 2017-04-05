package com.osh.exchangeapp.presenter;

import com.osh.exchangeapp.presenter.interfaces.Presenter;
import com.osh.exchangeapp.view.ExchangeViewActivityView;
import com.osh.exchangeapp.view.WidgetActivityView;

/**
 * Created by oleg on 2/9/2017.
 */

public interface WidgetEditActivityPresenter extends Presenter<WidgetActivityView>{
    void onSave();
    void onCancel();
    // info
    void onAmountChanged(Float t);
    void onCurrencyMasterChanged(String c);
    void onCurrencySlaveChanged(String c);
    void onSourceChanged(String c);
    void onSwitchCurrencies();
    // view
    void onTextColorChanged(int textColor);
    void onBgColorChanged(int bgColor);
}
