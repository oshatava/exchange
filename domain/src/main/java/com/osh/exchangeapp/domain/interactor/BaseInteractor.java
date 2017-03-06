package com.osh.exchangeapp.domain.interactor;

import com.osh.exchangeapp.domain.executor.PostExecutionThread;
import com.osh.exchangeapp.domain.executor.ThreadExecutor;
import com.osh.exchangeapp.domain.interactor.Interactor;


import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by oleg on 1/24/2017.
 */

public abstract class BaseInteractor<Repository> implements Interactor {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;
    private final Repository repository;

    public Repository getRepository() {
        return repository;
    }

    public BaseInteractor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Repository repository) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
        this.repository = repository;
    }

    public <T> void execute(Observable<T> action, DisposableObserver<T> observer) {
        final Observable<T> observable = action
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    public <T> void execute(Observable<T> action, Consumer<T> onNext, Consumer<Throwable> onError) {
        final Observable<T> observable = action
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribe (onNext, onError));
    }

    public <T> void execute(Observable<T> action, Consumer<T> onNext) {
        final Observable<T> observable = action
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribe (onNext));
    }

    @Override
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    private void addDisposable(Disposable disposable) {
        if(disposable!=null && disposables!=null) {
            disposables.add(disposable);
        }
    }
}
