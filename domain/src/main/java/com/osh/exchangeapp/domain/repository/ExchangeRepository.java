package com.osh.exchangeapp.domain.repository;

import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by oleg on 2/6/2017.
 */

public interface ExchangeRepository {
    // Currency
    Observable<List<Currency>> findCurrencies(String filter);
    Observable<List<Currency>> getAllCurrencies();
    Observable<Void> setCurrencies(List<Currency> currencies);
    Observable<Void> createCurrencies();

    // Saved Exchange rates to show
    Observable<List<ExchangeKey>> getExchangeKeys();
    Observable<Void> setExchangeKeys(List<ExchangeKey> exchangeKeys);
    Observable<List<ExchangeKey>> createDefaultExchangeKeys();

    // Retrieve actual Exchange rates for keys
    Observable<List<Exchange>> getExchangeRates(List<RateSourceDescription> keys);

    List<Exchange> getExchangeRatesSync(List<RateSourceDescription> keys) throws Exception;

    // Retrieve actual Exchange rate for key for period
    Observable<List<Exchange>> getExchangeRateHistorical(ExchangeKey key, long from, long to);


}
