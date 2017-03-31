package com.osh.exchangeapp.data.local;

import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;

import java.util.List;

/**
 * Created by oleg on 2/6/2017.
 */

public interface ExchangeLocalRepository extends LocalRepository  {

    List<Currency> findCurrencies(String filter);
    List<Currency> getAllCurrencies();
    void setCurrencies(List<Currency> currencies);
    void createCurrencies();

    // Saved Exchange rates to show
    ExchangeKey getExchangeKey(int id);
    List<ExchangeKey> getExchangeKeys();

    void setExchangeKey(ExchangeKey exchangeKey);
    void setExchangeKeys(List<ExchangeKey> exchangeKeys);
    List<ExchangeKey> createDefaultExchangeKeys();

    // Retrieve actual Exchange rates for keys
    List<Exchange> getExchangeRates(List<RateSourceDescription> keys);

    // Retrieve actual Exchange rate for key for period
    List<Exchange> getExchangeRateHistorical(ExchangeKey key, long from, long to);


    void saveExchangeRates(List<Exchange> ret);

}
