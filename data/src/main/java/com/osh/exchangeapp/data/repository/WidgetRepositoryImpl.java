package com.osh.exchangeapp.data.repository;

import com.osh.exchangeapp.data.local.ExchangeLocalRepository;
import com.osh.exchangeapp.data.local.WidgetLocalRepository;
import com.osh.exchangeapp.data.mapper.CollectionMapper;
import com.osh.exchangeapp.data.remote.ExchangeRemoteRepository;
import com.osh.exchangeapp.data.utils.CollectionUtils;
import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.Widget;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;
import com.osh.exchangeapp.domain.repository.ExchangeRepository;
import com.osh.exchangeapp.domain.repository.WidgetRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by oleg on 2/6/2017.
 */

public class WidgetRepositoryImpl extends BaseRepositoryOnlyLocal<WidgetLocalRepository> implements WidgetRepository {


    private final String TAG = getClass().getSimpleName();

    public WidgetRepositoryImpl(WidgetLocalRepository localRepository) {
        super(localRepository);
    }


    @Override
    public List<Widget> getOrCreateWidgets(List<Integer> ids) {
        List<Widget> ret = new ArrayList<>();
        for(Integer id:ids) {
            Widget w = getLocalRepository().getWidget(id);
            if(w==null)
                w = new Widget(id, 0,0,0);
            ret.add(w);
        }
        return ret;
    }

    @Override
    public void removeWidget(Integer id) {
        getLocalRepository().removeWidget(id);
    }

    @Override
    public Widget getWidgetSync(int id) {
        return getLocalRepository().getWidget(id);
    }

    @Override
    public List<Widget> getWidgets() {
        return getLocalRepository().getWidgets();
    }

    @Override
    public List<Widget> setWidgets(List<Widget> widgets) {
        return getLocalRepository().setWidgets(widgets);
    }

    @Override
    public Widget setWidgetSync(Widget widget) {
        CollectionUtils.with(getWidgets())
                .findAll(w->w.getExchangeKeyId() == widget.getExchangeKeyId() && w.getId()!=widget.getId())
                .forEach(w->{
                    w.setExchangeKeyId(0);
                    getLocalRepository().setWidget(w);
                });
        return getLocalRepository().setWidget(widget);
    }

    @Override
    public Observable<Void> clearWidgets() {
        return Observable.create(e -> {
            try {
                getLocalRepository().clearWidgets();
                e.onNext(null);
            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Widget> getWidget(int id) {
        return Observable.create(e -> {
            try {
                Widget ret = getLocalRepository().getWidget(id);
                if(ret==null) {
                    ret = getLocalRepository().setWidget(new Widget(id, 0, 0, 0));
                }
                e.onNext(ret);
            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Widget> setWidget(Widget widget) {
        return Observable.create(e -> {
            try {
                Widget ret = getLocalRepository().setWidget(widget);
                e.onNext(ret);
            } catch (Exception ex) {
                e.onError(ex);
            }
            e.onComplete();
        });
    }



}
