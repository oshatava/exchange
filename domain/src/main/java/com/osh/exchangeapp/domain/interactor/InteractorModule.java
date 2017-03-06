package com.osh.exchangeapp.domain.interactor;

import com.osh.exchangeapp.domain.executor.PostExecutionThread;
import com.osh.exchangeapp.domain.executor.ThreadExecutor;
import com.osh.exchangeapp.domain.repository.ExchangeRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by oleg on 2/6/2017.
 */

@Module
public class InteractorModule {

    @Provides
    public ExchangeInterator provideExchangeInterator(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ExchangeRepository exchangeRepository){
        return new ExchangeInterator(threadExecutor, postExecutionThread, exchangeRepository);
    }


}
