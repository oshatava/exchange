package com.osh.exchangeapp.data.retrofit;

import com.osh.exchangeapp.data.remote.RemoteRepository;

/**
 * Created by oleg on 2/6/2017.
 */

public class BaseRemoteRepository<RetrofitService> implements RemoteRepository {

    private RetrofitService retrofitService;

    public RetrofitService getRetrofitService() {
        return retrofitService;
    }

    public BaseRemoteRepository(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }
}
