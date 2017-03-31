package com.osh.exchangeapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.osh.exchangeapp.activity.BaseActivity;
import com.osh.exchangeapp.application.AppComponent;
import com.osh.exchangeapp.application.AppConst;
import com.osh.exchangeapp.application.AppModule;
import com.osh.exchangeapp.application.DaggerAppComponent;
import com.osh.exchangeapp.data.realm.di.RealmModule;
import com.osh.exchangeapp.data.repository.RepositoryModule;
import com.osh.exchangeapp.data.retrofit.di.RetrofitModule;
import com.osh.exchangeapp.domain.interactor.InteractorModule;
import com.osh.exchangeapp.navigator.AppNavigator;
import com.osh.exchangeapp.navigator.impl.AppNavigatorImpl;

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
    private AppNavigator navigator;

    @Override
    public void onCreate() {
        super.onCreate();
        navigator = new AppNavigatorImpl(this);


        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(navigator))
                .realmModule(new RealmModule(this))
                .repositoryModule(new RepositoryModule())
                .retrofitModule(new RetrofitModule(AppConst.baseUrl))
                .interactorModule(new InteractorModule())
                .build();


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks () {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                if(activity instanceof BaseActivity)
                    navigator.setCurrentActivity((BaseActivity)activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
