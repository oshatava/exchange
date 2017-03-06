package com.osh.exchangeapp.data.local;

import android.content.Context;

import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by oleg on 2/6/2017.
 */

public interface LocalRepository {
    void clean();
}
