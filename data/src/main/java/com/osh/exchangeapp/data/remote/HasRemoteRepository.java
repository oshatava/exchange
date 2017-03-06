package com.osh.exchangeapp.data.remote;

import com.osh.exchangeapp.data.local.LocalRepository;

/**
 * Created by oleg on 2/6/2017.
 */

public interface HasRemoteRepository<T extends RemoteRepository> {
    T getRemoteRepository();
}
