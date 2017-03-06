package com.osh.exchangeapp.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by oleg on 1/24/2017.
 */

public interface Interactor {
    void dispose();
    <T> void execute(Observable<T> action, DisposableObserver<T> observer);
    <T> void execute(Observable<T> action, Consumer<T> onNext, Consumer<Throwable> onError);
    <T> void execute(Observable<T> action, Consumer<T> onNext);
}
