package com.osh.exchangeapp.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.osh.exchangeapp.R;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.interactor.ExchangeInterator;
import com.osh.exchangeapp.navigator.AppNavigator;
import com.osh.exchangeapp.presenter.ExchangeViewActivityPresenter;
import com.osh.exchangeapp.presenter.MainActivityPresenter;
import com.osh.exchangeapp.presenter.impl.ExchangeViewActivityPresenterImpl;
import com.osh.exchangeapp.presenter.impl.MainActivityPresenterImpl;
import com.osh.exchangeapp.utils.ViewUtils;
import com.osh.exchangeapp.view.ExchangeViewActivityView;
import com.osh.exchangeapp.view.adapters.ViewEntityAdapter;
import com.osh.exchangeapp.view.main.ExchangeListItem;
import com.osh.exchangeapp.view.main.ExchangeListItemListener;

import javax.inject.Inject;

public class ExchangeEditViewActivity extends BaseActivity implements ExchangeViewActivityView {


    private final String TAG = getClass().getSimpleName();
    private ExchangeViewActivityPresenter presenter;


    @Inject
    public ExchangeInterator interactor;

    @Inject
    public AppNavigator appNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        setContentView(R.layout.activity_exchange_view);
        presenter = new ExchangeViewActivityPresenterImpl(appNavigator, getId(), interactor, this);
        ViewUtils.onClick(this, R.id.save, v->presenter.onSave());
        ViewUtils.onClick(this, R.id.cancel, v->presenter.onCancel());
        ViewUtils.onClick(this, R.id.switch_currencies, v->presenter.onSwitchCurrencies());

        ViewUtils.onTextChangedAsDecimal(this, R.id.amount, t->presenter.onAmountChanged(t));


    }

    private int getId() {
        if(getIntent()!=null)
            if(getIntent().hasExtra("id"))
                return getIntent().getIntExtra("id", 0);
        return 0;
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
                exchanges.getMaster()!=null?exchanges.getMaster().getId():"",
                c->presenter.onCurrencyMasterChanged(c));
        ViewUtils.setUpSpinner(this, R.id.currencySlave, R.array.currencies_code,
                exchanges.getSlave()!=null?exchanges.getSlave().getId():"",
                c->presenter.onCurrencySlaveChanged(c));

    }

    @Override
    public void close() {
        this.finish();
    }
}
