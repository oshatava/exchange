package com.osh.exchangeapp.data.local;

import io.realm.annotations.PrimaryKey;

/**
 * Created by oleg on 2/6/2017.
 */

public interface LocalEntity {
    long getCacheCreateDate();
    void setCacheCreateDate(long cacheCreateDate);
    boolean isCacheable();
    void setCacheable(boolean cacheable);
}
