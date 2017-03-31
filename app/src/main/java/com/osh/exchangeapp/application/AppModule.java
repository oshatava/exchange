package com.osh.exchangeapp.application;

import com.osh.exchangeapp.data.realm.di.RealmModule;
import com.osh.exchangeapp.data.repository.RepositoryModule;
import com.osh.exchangeapp.data.retrofit.di.RetrofitModule;
import com.osh.exchangeapp.domain.executor.PostExecutionThread;
import com.osh.exchangeapp.domain.executor.ThreadExecutor;
import com.osh.exchangeapp.domain.interactor.InteractorModule;
import com.osh.exchangeapp.navigator.AppNavigator;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by oleg on 2/7/2017.
 */
@Module(includes = {
        RealmModule.class,  // local data storage provider
        RetrofitModule.class, // remote data provider
        RepositoryModule.class, // repository
        InteractorModule.class // interactors
})
public class AppModule  {
    private AppNavigator navigator;

    public AppModule(AppNavigator navigator) {
        this.navigator = navigator;
    }

    @Provides
    public ThreadExecutor provideThreadExecutor(){
        return () -> Schedulers.newThread();
    }

    @Provides
    public PostExecutionThread providePostExecutionThread(){
        return () -> AndroidSchedulers.mainThread();
    }

    @Provides
    public AppNavigator provideAppNavigator(){
        return navigator;
    }
}
