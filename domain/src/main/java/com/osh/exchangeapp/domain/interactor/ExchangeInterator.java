package com.osh.exchangeapp.domain.interactor;
import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;
import com.osh.exchangeapp.domain.executor.PostExecutionThread;
import com.osh.exchangeapp.domain.executor.ThreadExecutor;
import com.osh.exchangeapp.domain.repository.ExchangeRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by oleg on 1/24/2017.
 */

public class ExchangeInterator extends BaseInteractor<ExchangeRepository> {
    public ExchangeInterator(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ExchangeRepository exchangeRepository) {
        super(threadExecutor, postExecutionThread, exchangeRepository);
    }

    public void findCurrencies(String filter, Consumer<List<Currency>> onCurrencies, Consumer<Throwable> onError){
        execute(getRepository().findCurrencies(filter), onCurrencies, onError);
    }

    public void getAllCurrencies(Consumer<List<Currency>> onCurrencies, Consumer<Throwable> onError){
        execute(getRepository().getAllCurrencies(), onCurrencies, onError);
    }

    public void setCurrencies(List<Currency> currencies, Consumer<Void> onCurrencies, Consumer<Throwable> onError){
        execute(getRepository().setCurrencies(currencies), onCurrencies, onError);
    }


    public void getExchangeRates(List<RateSourceDescription> keys, Consumer<List<Exchange>> onExchange, Consumer<Throwable> onError){
        execute(getRepository().getExchangeRates(keys), onExchange, onError);
    }

    public void getExchangeRateHistorical(ExchangeKey key, long from, long to, Consumer<List<Exchange>> onExchange, Consumer<Throwable> onError){
        execute(getRepository().getExchangeRateHistorical(key, from, to), onExchange, onError);
    }


    public void getExchangeKeys(Consumer<List<ExchangeKey>> onExchangeKeys, Consumer<Throwable> onError){
        execute(getRepository().getExchangeKeys(), onExchangeKeys, onError);
    }


    public void getExchangeKey(int id, Consumer<ExchangeKey> onExchangeKey, Consumer<Throwable> onError){
        execute(getRepository().getExchangeKey(id), onExchangeKey, onError);
    }


    public void setExchangeKeys(List<ExchangeKey> keys, Consumer<Void> onExchange, Consumer<Throwable> onError){
        execute(getRepository().setExchangeKeys(keys), onExchange, onError);
    }

    public void setExchangeKey(ExchangeKey key, Consumer<ExchangeKey> onExchange, Consumer<Throwable> onError){
        execute(getRepository().setExchangeKey(key), onExchange, onError);
    }

    public void createCurrencies(Consumer<Void> onOk, Consumer<Throwable> onError) {
        execute(getRepository().createCurrencies(), onOk, onError);
    }

    public void createDefaultExchangeKeys(Consumer<List<ExchangeKey>> onExchangeKeys, Consumer<Throwable> onError) {
        execute(getRepository().createDefaultExchangeKeys(), onExchangeKeys, onError);
    }
}
