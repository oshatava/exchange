package com.osh.exchangeapp.data.retrofit.di;

import com.osh.exchangeapp.data.remote.ExchangeRemoteRepository;
import com.osh.exchangeapp.data.retrofit.ExchangeRemoteRepositoryImpl;
import com.osh.exchangeapp.data.retrofit.converters.CSVConverter;
import com.osh.exchangeapp.data.retrofit.services.ExchangeRetrofitService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oleg on 2/6/2017.
 */

@Module
public class RetrofitModule {
    private String baseUrl;

    public RetrofitModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Singleton
    @Provides
    public Retrofit retrofit(){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new CSVConverter(GsonConverterFactory.create()))
                .build();
    }

    @Provides
    public ExchangeRetrofitService userRetrofitService(Retrofit retrofit){
        return retrofit.create(ExchangeRetrofitService.class);
    }

    @Provides
    public ExchangeRemoteRepository userRemoteRepository(ExchangeRetrofitService exchangeRetrofitService){
        return new ExchangeRemoteRepositoryImpl(exchangeRetrofitService);
    }


}
