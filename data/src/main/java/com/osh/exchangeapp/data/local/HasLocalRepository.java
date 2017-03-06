package com.osh.exchangeapp.data.local;

/**
 * Created by oleg on 2/6/2017.
 */

public interface HasLocalRepository<T extends LocalRepository> {
    T getLocalRepository();
}
