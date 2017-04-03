package com.osh.exchangeapp.data.realm;

import android.content.Context;
import android.util.Log;

import com.osh.exchangeapp.data.R;
import com.osh.exchangeapp.data.conf.Constants;
import com.osh.exchangeapp.data.local.CacheUtils;
import com.osh.exchangeapp.data.local.ExchangeLocalRepository;
import com.osh.exchangeapp.data.mapper.CurrencyMapper;
import com.osh.exchangeapp.data.mapper.ExchangeKeyMapper;
import com.osh.exchangeapp.data.mapper.ExchangeMapper;
import com.osh.exchangeapp.data.realm.entity.CurrencyLocal;
import com.osh.exchangeapp.data.realm.entity.ExchangeKeyLocal;
import com.osh.exchangeapp.data.realm.entity.ExchangeLocal;
import com.osh.exchangeapp.data.realm.utils.RealmUtils;
import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmQuery;
import io.realm.Sort;

/**
 * Created by oleg on 2/6/2017.
 */

public class ExchangeLocalRepositoryImpl extends BaseLocalRepository<ExchangeLocal> implements ExchangeLocalRepository {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private ExchangeMapper exchangeMapper = new ExchangeMapper();
    private CurrencyMapper currencyMapper = new CurrencyMapper();
    private ExchangeKeyMapper exchangeKeyMapper = new ExchangeKeyMapper();

    public ExchangeLocalRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<Currency> findCurrencies(String filter) {
        List<CurrencyLocal> list = RealmUtils.getAll(query -> query
                .beginGroup()
                    .contains("id", filter)
                    .or()
                    .contains("name", filter)
                    .or()
                    .contains("country", filter)
                .endGroup(),
                CurrencyLocal.class);

        return currencyMapper.fromLocal(list);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        List<CurrencyLocal> list = RealmUtils.getAll(CurrencyLocal.class);
        return currencyMapper.fromLocal(list);
    }

    @Override
    public void setCurrencies(List<Currency> currencies) {
        RealmUtils.setAll(CurrencyLocal.class, currencyMapper.toLocal(currencies));
    }

    @Override
    public void createCurrencies() {
        List<CurrencyLocal> list = new ArrayList<>();
        String [] currenciesCode = context.getResources().getStringArray(R.array.currencies_code);
        String [] currenciesName = context.getResources().getStringArray(R.array.currencies_name);
        for (int i=0; i<Math.min(currenciesCode.length, currenciesName.length); i++){
            list.add(new CurrencyLocal(currenciesCode[i], currenciesName[i], ""));
        }
        RealmUtils.setAll(CurrencyLocal.class, list);
    }

    @Override
    public List<ExchangeKey> getExchangeKeys() {
        List<ExchangeKeyLocal> list = RealmUtils.getAll(ExchangeKeyLocal.class);
        return exchangeKeyMapper.fromLocal(list);
    }

    @Override
    public ExchangeKey getExchangeKey(int id) {
        ExchangeKeyLocal key = RealmUtils.get(id, ExchangeKeyLocal.class);
        return exchangeKeyMapper.fromLocal(key);
    }


    @Override
    public void setExchangeKey(ExchangeKey exchangeKey) {
        if(exchangeKey.getId()==0){
            int id = RealmUtils.getMaxId(ExchangeKeyLocal.class)+1;
            exchangeKey.setId(id);
        }

        RealmUtils.set(exchangeKeyMapper.toLocal(exchangeKey));
    }

    @Override
    public void setExchangeKeys(List<ExchangeKey> exchangeKeys) {
        RealmUtils.setAll(ExchangeKeyLocal.class, exchangeKeyMapper.toLocal(exchangeKeys));
    }

    @Override
    public List<ExchangeKey> createDefaultExchangeKeys() {
        List<ExchangeKeyLocal> list = new ArrayList<>();
        String [] defaultExchangeRatesMaster = context.getResources().getStringArray(R.array.default_exchange_rates_master);
        String [] defaultExchangeRatesSlave = context.getResources().getStringArray(R.array.default_exchange_rates_slave);

        int id = 1;
        for (int i=0; i<Math.min(defaultExchangeRatesMaster.length, defaultExchangeRatesSlave.length); i++) {
            ExchangeKey key = new ExchangeKey(id++,
                    new Currency(defaultExchangeRatesMaster[i]),
                    new Currency(defaultExchangeRatesSlave[i]),
                    Constants.RateDataSource.YAHOO.toString(),
                    1, -1, -1, -1, false, false, false, id, 10 * 1000);

            list.add(exchangeKeyMapper.toLocal(key));
        }
        RealmUtils.setAll(ExchangeKeyLocal.class, list);
        return exchangeKeyMapper.fromLocal(list);
    }

    @Override
    public List<Exchange> getExchangeRates(List<RateSourceDescription> keys) {
        List<ExchangeLocal> list = new ArrayList<>();
        for(RateSourceDescription key:keys) {

            ExchangeLocal ret =
                    RealmUtils.get(query -> query
                                    .equalTo("currencyKey", key.getKey())
                                    .equalTo("source", key.getSourceName()),
                            ExchangeLocal.class, "date", Sort.DESCENDING);
            if(ret!=null)
                list.add(ret);

        }
        return exchangeMapper.fromLocal(list);
    }

    @Override
    public List<Exchange> getExchangeRateHistorical(ExchangeKey key, long from, long to) {
        return null;
    }

    @Override
    public void saveExchangeRates(List<Exchange> exchanges) {
        RealmUtils.addAll(ExchangeLocal.class, exchangeMapper.toLocal(exchanges));
    }


    @Override
    public void clean() {
        Log.d(TAG, "clean");
        RealmUtils.deleteAll(ExchangeLocal.class);
        RealmUtils.deleteAll(CurrencyLocal.class);
        RealmUtils.deleteAll(ExchangeKeyLocal.class);
    }
}
