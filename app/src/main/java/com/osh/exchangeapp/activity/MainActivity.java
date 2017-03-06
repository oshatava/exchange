package com.osh.exchangeapp.activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.osh.exchangeapp.R;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.interactor.ExchangeInterator;
import com.osh.exchangeapp.presenter.MainActivityPresenter;
import com.osh.exchangeapp.presenter.impl.MainActivityPresenterImpl;
import com.osh.exchangeapp.utils.ViewUtils;
import com.osh.exchangeapp.view.MainActivityView;
import com.osh.exchangeapp.view.adapters.ViewEntityAdapter;
import com.osh.exchangeapp.view.main.ExchangeListItem;
import com.osh.exchangeapp.view.main.ExchangeListItemListener;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainActivityView {


    private final String TAG = getClass().getSimpleName();
    private MainActivityPresenter presenter;
    private RecyclerView list;
    private SwipeRefreshLayout refresh;
    private LinearLayoutManager layoutManager;
    private ViewEntityAdapter<ExchangeListItem, ExchangeKey, ExchangeListItemListener> adapter;


    @Inject
    public ExchangeInterator interactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenterImpl(interactor, this);

        refresh = ViewUtils.findViewById(this, R.id.refresh);
        refresh.setOnRefreshListener(() -> presenter.onUpdateRates());

        list = ViewUtils.findViewById(this, R.id.list);
        if (list != null) {
            list.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            list.setLayoutManager(layoutManager);
            adapter = new ViewEntityAdapter<>(R.layout.view_exchange_list_item, exchange -> {
                presenter.onItemClicked(exchange);
            });
            list.setAdapter(adapter);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onStop();
    }

    @Override
    public void showRates(List<ExchangeKey> exchanges) {
        Log.d(TAG, exchanges.toString());
        if(adapter!=null)
            adapter.setItems(exchanges);
        refresh.setRefreshing(false);
    }

}
