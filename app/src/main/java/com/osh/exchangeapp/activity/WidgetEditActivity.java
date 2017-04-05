package com.osh.exchangeapp.activity;

import android.appwidget.AppWidgetManager;
import android.os.Bundle;
import android.util.Log;

import com.osh.exchangeapp.R;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.Widget;
import com.osh.exchangeapp.domain.interactor.ExchangeInterator;
import com.osh.exchangeapp.domain.interactor.WidgetInterator;
import com.osh.exchangeapp.navigator.AppNavigator;
import com.osh.exchangeapp.presenter.ExchangeViewActivityPresenter;
import com.osh.exchangeapp.presenter.WidgetEditActivityPresenter;
import com.osh.exchangeapp.presenter.impl.ExchangeViewActivityPresenterImpl;
import com.osh.exchangeapp.presenter.impl.WidgetEditActivityPresenterImpl;
import com.osh.exchangeapp.utils.ViewUtils;
import com.osh.exchangeapp.view.ExchangeViewActivityView;
import com.osh.exchangeapp.view.WidgetActivityView;

import javax.inject.Inject;

public class WidgetEditActivity extends BaseActivity implements WidgetActivityView {


    private final String TAG = getClass().getSimpleName();
    private WidgetEditActivityPresenter presenter;


    @Inject
    public ExchangeInterator interactor;

    @Inject
    public WidgetInterator widgetInterator;

    @Inject
    public AppNavigator appNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        setContentView(R.layout.activity_exchange_view);
        setResult(RESULT_CANCELED);

        int id = getId();
        if(id==AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
        }

        presenter = new WidgetEditActivityPresenterImpl(appNavigator, id, interactor, widgetInterator, this);
        ViewUtils.onClick(this, R.id.save, v->presenter.onSave());
        ViewUtils.onClick(this, R.id.cancel, v->presenter.onCancel());
        ViewUtils.onClick(this, R.id.switch_currencies, v->presenter.onSwitchCurrencies());

        ViewUtils.onTextChangedAsDecimal(this, R.id.amount, t->presenter.onAmountChanged(t));

    }

    private int getId() {
        if(getIntent()!=null)
            if(getIntent().hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID))
                return getIntent().getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return AppWidgetManager.INVALID_APPWIDGET_ID;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onStop();
    }

    @Override
    public void showRate(ExchangeKey exchanges) {
        Log.d(TAG, exchanges.toString());
        ViewUtils.setText(this, R.id.amount, exchanges.getAmount());

        ViewUtils.setUpSpinner(this, R.id.currencyMaster, R.array.currencies_code,
                exchanges.getMaster() != null ? exchanges.getMaster().getId() : "",
                c -> presenter.onCurrencyMasterChanged(c));
        ViewUtils.setUpSpinner(this, R.id.currencySlave, R.array.currencies_code,
                exchanges.getSlave() != null ? exchanges.getSlave().getId() : "",
                c -> presenter.onCurrencySlaveChanged(c));

        ViewUtils.setUpSpinner(this, R.id.dataSource, R.array.data_source,
                exchanges.getSource() != null ? exchanges.getSource() : "",
                c -> presenter.onSourceChanged(c));
    }

    @Override
    public void showWidget(Widget widget) {

    }
}
