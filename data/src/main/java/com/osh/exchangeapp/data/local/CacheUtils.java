package com.osh.exchangeapp.data.local;

import com.osh.exchangeapp.data.conf.Constants;

/**
 * Created by oleg on 2/6/2017.
 */

public class CacheUtils {
    public static boolean isExpired(Object o) {
        if (o != null)
            if (o instanceof LocalEntity) {
                return System.currentTimeMillis() - ((LocalEntity) o).getCacheCreateDate() < Constants.MAX_CACHE_ENTITY_LIFETIME;
            }
        return false;
    }
}
