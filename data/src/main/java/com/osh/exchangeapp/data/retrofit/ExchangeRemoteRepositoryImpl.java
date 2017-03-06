package com.osh.exchangeapp.data.retrofit;

import android.util.Log;

import com.osh.exchangeapp.data.conf.Constants;
import com.osh.exchangeapp.data.mapper.ExchangeMapper;
import com.osh.exchangeapp.data.remote.ExchangeRemoteRepository;
import com.osh.exchangeapp.data.retrofit.entity.ExchangeRemote;
import com.osh.exchangeapp.data.retrofit.services.ExchangeRetrofitService;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;

import java.util.List;

/**
 * Created by oleg on 2/6/2017.
 */

public class ExchangeRemoteRepositoryImpl extends BaseRemoteRepository<ExchangeRetrofitService> implements ExchangeRemoteRepository {

    private final String TAG = getClass().getSimpleName();
    private ExchangeMapper mapper = new ExchangeMapper();

    public ExchangeRemoteRepositoryImpl(ExchangeRetrofitService exchangeRetrofitService) {
        super(exchangeRetrofitService);
    }


    @Override
    public List<Exchange> getExchangeRates(List<RateSourceDescription> keys) throws Exception {
        StringBuilder keyString = new StringBuilder();
        for (RateSourceDescription key : keys) {
            if(key.getSourceName().contains(Constants.RateDataSource.YAHOO.toString())) {
                keyString.append(key.getKey());
                keyString.append("=x ");
            }
        }

        List<ExchangeRemote> exchangeRemoteList = getRetrofitService().getExchangeRates(keyString.toString().trim()).execute().body();
        Log.d(TAG, "getExchangeRates():" + exchangeRemoteList);
        return mapper.fromRemote(exchangeRemoteList);
    }
}
