
package com.osh.exchangeapp.data.realm;

import android.content.Context;
import android.util.Log;

import com.osh.exchangeapp.data.R;
import com.osh.exchangeapp.data.conf.Constants;
import com.osh.exchangeapp.data.local.ExchangeLocalRepository;
import com.osh.exchangeapp.data.local.WidgetLocalRepository;
import com.osh.exchangeapp.data.mapper.CurrencyMapper;
import com.osh.exchangeapp.data.mapper.ExchangeKeyMapper;
import com.osh.exchangeapp.data.mapper.ExchangeMapper;
import com.osh.exchangeapp.data.mapper.WidgetMapper;
import com.osh.exchangeapp.data.realm.entity.CurrencyLocal;
import com.osh.exchangeapp.data.realm.entity.ExchangeKeyLocal;
import com.osh.exchangeapp.data.realm.entity.ExchangeLocal;
import com.osh.exchangeapp.data.realm.entity.WidgetLocal;
import com.osh.exchangeapp.data.realm.utils.RealmUtils;
import com.osh.exchangeapp.domain.Currency;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.Widget;
import com.osh.exchangeapp.domain.entity.RateSourceDescription;

import java.util.ArrayList;
import java.util.List;

import io.realm.Sort;

/**
 * Created by oleg on 2/6/2017.
 */

public class WidgetLocalRepositoryImpl extends BaseLocalRepository<WidgetLocal> implements WidgetLocalRepository {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private WidgetMapper widgetMapper = new WidgetMapper();

    public WidgetLocalRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void clearWidgets() {
        clean();
    }

    @Override
    public void removeWidget(Integer id) {
        RealmUtils.delete(id, WidgetLocal.class);
    }

    @Override
    public Widget getWidget(int id) {
        return widgetMapper.fromLocal(RealmUtils.get(id, WidgetLocal.class));
    }

    @Override
    public List<Widget> getWidgets() {
        return widgetMapper.fromLocal(RealmUtils.getAll(WidgetLocal.class));
    }

    @Override
    public Widget setWidget(Widget widget) {
        if(widget.getId()==0){
            int id = RealmUtils.getMaxId(WidgetLocal.class)+1;
            widget.setId(id);
        }
        RealmUtils.set(widgetMapper.toLocal(widget));
        return widget;
    }


    @Override
    public List<Widget> setWidgets(List<Widget> widgets) {
        for(Widget w:widgets)
            setWidget(w);
        return widgets;
    }


    @Override
    public void clean() {
        Log.d(TAG, "clean");
        RealmUtils.deleteAll(WidgetLocal.class);
    }
}
