package com.osh.exchangeapp.data.retrofit.services;

import com.osh.exchangeapp.data.retrofit.entity.ExchangeRemote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by oleg on 2/6/2017.
 */
public interface ExchangeRetrofitService {
    @GET("d/quotes.csv?f=nl1c1ghd1t1")
    Call<List<ExchangeRemote>> getExchangeRates(@Query("s") String keys);
}
