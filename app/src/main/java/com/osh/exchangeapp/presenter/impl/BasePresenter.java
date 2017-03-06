package com.osh.exchangeapp.presenter.impl;

import com.osh.exchangeapp.domain.interactor.Interactor;
import com.osh.exchangeapp.presenter.interfaces.Presenter;
import com.osh.exchangeapp.view.interfaces.IView;

import java.lang.ref.WeakReference;


public class BasePresenter<Model extends Interactor, View extends IView> implements Presenter {

    private Model model;
    private WeakReference<View> viewRef;


    public BasePresenter(Model model, View view) {
        this.model = model;
        this.viewRef = new WeakReference<View>(view);
    }

    public Model getModel() {
        return model;
    }

    public View getView() {
        return viewRef.get();
    }

    public boolean hasModel(){
        return model!=null;
    }

    public boolean hasView(){
        return viewRef!=null && viewRef.get()!=null;
    }

    public void setView(View view) {
        this.viewRef = new WeakReference<View>(view);
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop() {
        if(hasModel())
            getModel().dispose();
    }

    @Override
    public void onError(Throwable throwable) {
        if(!hasView()) return;
        getView().hideWait();
        getView().showError(throwable);
    }

}
