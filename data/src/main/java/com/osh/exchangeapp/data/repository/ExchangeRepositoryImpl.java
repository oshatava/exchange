package com.osh.exchangeapp.data.repository;

import com.osh.exchangeapp.data.local.ExchangeLocalRepository;
import com.osh.exchangeapp.data.mapper.CollectionMapper;
import com.osh.exchangeapp.data.remote.ExchangeRemoteRepository;
import com.osh.exchangeapp.data.utils.CollectionUtils;
import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;
import com.osh.exchangeapp.domain.repository.ExchangeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by oleg on 2/6/2017.
 */

public class ExchangeRepositoryImpl extends BaseRepository<ExchangeLocalRepository, ExchangeRemoteRepository> implements ExchangeRepository {


    private final String TAG = getClass().getSimpleName();

    public ExchangeRepositoryImpl(ExchangeLocalRepository localRepository, ExchangeRemoteRepository remoteRepository) {
        super(localRepository, remoteRepository);
    }


    @Override
    public Observable<List<Currency>> findCurrencies(String filter) {
        return Observable.create(e -> {
            try {
                e.onNext(getLocalRepository().findCurrencies(filter));
            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<List<Currency>> getAllCurrencies() {
        return Observable.create(e -> {
            try {
                e.onNext(getLocalRepository().getAllCurrencies());
            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Void> setCurrencies(List<Currency> currencies) {
        return Observable.create(e -> {
            try {
                getLocalRepository().setCurrencies(currencies);
                e.onNext(null);
            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Void> createCurrencies() {
        return Observable.create(e -> {
            try {
                getLocalRepository().createCurrencies();
                getLocalRepository().createDefaultExchangeKeys();
                e.onNext(null);
            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<ExchangeKey> getExchangeKey(int id) {
        return Observable.create(e -> {
            try {
                ExchangeKey exchangeKey = getLocalRepository().getExchangeKey(id);
                if(exchangeKey!=null) {
                    List<RateSourceDescription> rate = new ArrayList<>();
                    //CollectionMapper.map(rate, exchangeKeys, ExchangeKey::getKey);
                    rate.add(exchangeKey.getKey());
                    List<Exchange> exchanges = getExchangeRatesSync(rate);
                    if(exchanges!=null)
                        if(exchanges.size()>0)
                            exchangeKey.setExchange(exchanges.get(0));
                }

                e.onNext(exchangeKey);

            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<List<ExchangeKey>> getExchangeKeys() {
        return Observable.create(e -> {
            try {
                List<ExchangeKey> exchangeKeys = getLocalRepository().getExchangeKeys();
                if(exchangeKeys!=null) {
                    List<RateSourceDescription> rate = new ArrayList<>();
                    CollectionMapper.map(rate, exchangeKeys, ExchangeKey::getKey);
                    List<RateSourceDescription> rateUniq = CollectionUtils.distinct(rate,
                            (l, r) -> l.getSourceName().equals(r.getSourceName())
                            && l.getKey().equals(r.getKey())
                    );

                    List<Exchange> exchanges = getExchangeRatesSync(rateUniq);

                    CollectionUtils.forEach(exchangeKeys, i -> {
                        Exchange e1 = CollectionUtils.with(exchanges).findFirst(
                                exch -> exch.getSlave().getId().equals(i.getSlave().getId())
                                && exch.getMaster().getId().equals(i.getMaster().getId())
                                && exch.getSource().equals(i.getSource()));

                        if(e1 !=null)
                            i.setExchange(e1);
                    });


                }

                e.onNext(exchangeKeys);

            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<ExchangeKey> setExchangeKey(ExchangeKey exchangeKey) {
        return Observable.create(e -> {
            try {
                int wid = exchangeKey.getWidgetId();
                if(wid!=0) {
                    CollectionUtils
                            .with(getLocalRepository().getExchangeKeys())
                            .findAll(k->k.getWidgetId()==wid&&k.getId()!=exchangeKey.getId())
                            .forEach(k-> {
                                k.setWidgetId(0);
                                getLocalRepository().setExchangeKey(k);
                            });
                }
                getLocalRepository().setExchangeKey(exchangeKey);
                e.onNext(exchangeKey);
            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Void> setExchangeKeys(List<ExchangeKey> exchangeKeys) {
        return Observable.create(e -> {
            try {
                getLocalRepository().setExchangeKeys(exchangeKeys);
                e.onNext(null);
            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<List<ExchangeKey>> createDefaultExchangeKeys(){
        return Observable.create(e -> {
            try {
                e.onNext(getLocalRepository().createDefaultExchangeKeys());
            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }

    @Override
    public void setExchangeKeysSync(List<ExchangeKey> keys) {
        getLocalRepository().setExchangeKeys(keys);
    }

    @Override
    public Observable<List<Exchange>> getExchangeRates(List<RateSourceDescription> keys) {
        return Observable.create(e -> {
            // get from local first
            try {
                e.onNext(getLocalRepository().getExchangeRates(keys));
            } catch (Exception ex) {
                ex.printStackTrace();
                e.onError(ex);
            }

            // update from remote and save to cache
            try {
                List<Exchange> ret = getRemoteRepository().getExchangeRates(keys);
                getLocalRepository().saveExchangeRates(ret);
                if (ret != null) {
                    e.onNext(ret);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                e.onError(ex);
            }

            e.onComplete();
        });
    }

    @Override
    public List<Exchange> getExchangeRatesSync(List<RateSourceDescription> keys) throws Exception {
        List<Exchange> ret = getRemoteRepository().getExchangeRates(keys);
        getLocalRepository().saveExchangeRates(ret);

        return ret;
    }


    @Override
    public Observable<List<Exchange>> getExchangeRateHistorical(ExchangeKey key, long from, long to) {
        return Observable.create(e -> {
            try {
                e.onNext(getLocalRepository().getExchangeRateHistorical(key, from, to));
            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }



}
