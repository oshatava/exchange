package com.osh.exchangeapp.data.realm.di;

import android.content.Context;

import com.osh.exchangeapp.data.local.ExchangeLocalRepository;
import com.osh.exchangeapp.data.local.WidgetLocalRepository;
import com.osh.exchangeapp.data.realm.ExchangeLocalRepositoryImpl;
import com.osh.exchangeapp.data.realm.WidgetLocalRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by oleg on 2/6/2017.
 */

@Module
public class RealmModule {
    private Context context;

    public RealmModule(Context context){
        this.context = context;
        try{
            Realm.getDefaultInstance();
        }catch (Exception e){
            Realm.init(context);
        }
    }

    @Provides
    public ExchangeLocalRepository provideExchangeLocalRepository(){
        return new ExchangeLocalRepositoryImpl(context);
    }

    @Provides
    public WidgetLocalRepository provideWidgetLocalRepository(){
        return new WidgetLocalRepositoryImpl(context);
    }


}
