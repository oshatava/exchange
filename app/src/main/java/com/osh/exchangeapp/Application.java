package com.osh.exchangeapp;

import android.content.Context;

import com.osh.exchangeapp.application.AppComponent;
import com.osh.exchangeapp.application.AppConst;
import com.osh.exchangeapp.application.AppModule;
import com.osh.exchangeapp.application.DaggerAppComponent;
import com.osh.exchangeapp.data.realm.di.RealmModule;
import com.osh.exchangeapp.data.repository.RepositoryModule;
import com.osh.exchangeapp.data.retrofit.di.RetrofitModule;
import com.osh.exchangeapp.domain.interactor.InteractorModule;

/**
 * Created by oleg on 2/7/2017.
 */

public class Application extends android.app.Application{

    public static AppComponent getAppComponent(Context context){
        if(context != null){
            Application app = (Application) context.getApplicationContext();
            return app.appComponent;
        }
        return null;
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .realmModule(new RealmModule(this))
                .repositoryModule(new RepositoryModule())
                .retrofitModule(new RetrofitModule(AppConst.baseUrl))
                .interactorModule(new InteractorModule())
                .build();
    }
}
