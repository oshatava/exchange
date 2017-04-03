package com.osh.exchangeapp.presenter;

import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.presenter.interfaces.Presenter;
import com.osh.exchangeapp.view.ExchangeViewActivityView;

/**
 * Created by oleg on 2/9/2017.
 */

public interface ExchangeViewActivityPresenter extends Presenter<ExchangeViewActivityView>{
    void onSave();
    void onCancel();
    void onAmountChanged(Float t);
    void onCurrencyMasterChanged(String c);
    void onCurrencySlaveChanged(String c);
    void onSourceChanged(String c);
    void onSwitchCurrencies();
}
