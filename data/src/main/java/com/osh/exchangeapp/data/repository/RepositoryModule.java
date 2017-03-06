package com.osh.exchangeapp.data.repository;

import com.osh.exchangeapp.data.local.ExchangeLocalRepository;
import com.osh.exchangeapp.data.remote.ExchangeRemoteRepository;
import com.osh.exchangeapp.domain.repository.ExchangeRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by oleg on 2/6/2017.
 */

@Module
public class RepositoryModule {

    @Provides
    public ExchangeRepository provideUserRepository(ExchangeRemoteRepository exchangeRemoteRepository, ExchangeLocalRepository exchangeLocalRepository){
        return new ExchangeRepositoryImpl(exchangeLocalRepository, exchangeRemoteRepository);
    }

}
