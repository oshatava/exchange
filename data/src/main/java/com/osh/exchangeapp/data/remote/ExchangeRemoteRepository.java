package com.osh.exchangeapp.data.remote;

import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;

import java.util.List;

/**
 * Created by oleg on 2/6/2017.
 */

public interface ExchangeRemoteRepository extends RemoteRepository{
    List<Exchange> getExchangeRates(List<RateSourceDescription> keys) throws Exception;
}
