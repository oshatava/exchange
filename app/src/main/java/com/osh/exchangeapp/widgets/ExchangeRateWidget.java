package com.osh.exchangeapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.osh.exchangeapp.Application;
import com.osh.exchangeapp.R;
import com.osh.exchangeapp.activity.MainActivity;
import com.osh.exchangeapp.application.AppComponent;
import com.osh.exchangeapp.application.AppConst;
import com.osh.exchangeapp.application.HasAppComponent;
import com.osh.exchangeapp.data.utils.CollectionUtils;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.Widget;
import com.osh.exchangeapp.domain.interactor.ExchangeInterator;
import com.osh.exchangeapp.domain.interactor.WidgetInterator;
import com.osh.exchangeapp.domain.repository.ExchangeRepository;
import com.osh.exchangeapp.utils.DateUtils;
import com.osh.exchangeapp.utils.ViewUtils;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of App Widget functionality.
 */
public class ExchangeRateWidget extends AppWidgetProvider {

    private static final String TAG = "ExchangeRateWidget";

    public AppComponent getAppComponent(Context context) {
        return Application.getAppComponent(context);
    }


    @Inject
    public ExchangeInterator interactor;

    @Inject
    public WidgetInterator widgetInterator;

    private void updateAppWidget(Context context, ExchangeKey data, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.exchange_rate_widget);

        DecimalFormat format = new DecimalFormat("0.00");
        DecimalFormat formatChange = new DecimalFormat("0.0000");



        views.setTextViewText(R.id.master, data.getMaster().getId());
        views.setTextViewText(R.id.master_amount, format.format(data.getAmount()));
        views.setTextViewText(R.id.slave, data.getSlave().getId());
        views.setTextViewText(R.id.slave_amount, format.format(data.getLastTrade()));

        views.setTextViewText(R.id.min, format.format(data.getDayLow()));
        views.setTextViewText(R.id.max, format.format(data.getDayHigh()));
        views.setTextViewText(R.id.resource, data.getSource());
        views.setTextViewText(R.id.update_date, DateUtils.formatDate(context,data.getDate()));

        views.setTextViewText(R.id.change, formatChange.format(Math.abs(data.getChange())));

        if(data.getChange()>0)
            views.setImageViewResource (R.id.change_direction, R.drawable.ic_trending_up_black_24dp);
        else if(data.getChange()<0)
            views.setImageViewResource (R.id.change_direction, R.drawable.ic_trending_down_black_24dp);
        else
            views.setImageViewResource (R.id.change_direction, R.drawable.ic_trending_flat_black_24dp);

        Intent active = new Intent(context, MainActivity.class);
        active.setAction(AppConst.ACTION_WIDGET_RECEIVER);
        active.putExtra("wid", appWidgetId);

        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        views.setOnClickPendingIntent(R.id.widget_root, actionPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void onError(Throwable throwable) {
        Log.d(TAG, throwable.toString());
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        getAppComponent(context).inject(this);

        /*
        int[] _appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, this.getClass()));
        if(appWidgetIds!=null)
            Log.d("WIDGET", "onEnabled" + CollectionUtils.print(_appWidgetIds));
            */

        List<Widget> widgetList = widgetInterator.getOrCreateWidgets(CollectionUtils.with(appWidgetIds).list());
        CollectionUtils.Builder<Widget> widgets = CollectionUtils.with(widgetList);
        /*
        CollectionUtils.Builder<Widget> widgetsToUpdate = CollectionUtils
                .with(appWidgetIds)
                .map(id->widgets.findFirst(w->w.getId()==id));
                */

        interactor.getExchangeKeys(keys -> {

            /*
            for(int i=0; i<Math.min(appWidgetIds.length, keys.size()); i++) {
                ExchangeKey data = keys.get(i);
                updateAppWidget(context, data, appWidgetManager, appWidgetIds[i]);
            }
            */

            CollectionUtils.Builder<ExchangeKey> exchanges = CollectionUtils.with(keys);

            widgets.forEach(w-> {
                ExchangeKey key = null;
                if (w.getExchangeKeyId() == 0) {
                    key = exchanges.findFirst(k -> k.getWidgetId() == 0);
                    if (key != null) {
                        key.setWidgetId(w.getId());
                        w.setExchangeKeyId(key.getId());
                    }
                } else {
                    key = exchanges.findFirst(k -> k.getId() == w.getExchangeKeyId());
                    if(key==null){
                        key.setWidgetId(0);
                    }
                }

                if(key!=null){
                    updateAppWidget(context, key, appWidgetManager, w.getId());
                }

            });

            widgetInterator.setWidgets(widgets.list());
            interactor.setExchangeKeys(exchanges.list());

        }, this::onError);


    }

    @Override
    public void onEnabled(Context context) {
        Log.d("WIDGET", "onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        Log.d("WIDGET", "onDisabled");
    }
}

