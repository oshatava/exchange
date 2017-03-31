package com.osh.exchangeapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
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
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.interactor.ExchangeInterator;
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
        interactor.getExchangeKeys(keys -> {
            for(int i=0; i<Math.min(appWidgetIds.length, keys.size()); i++) {
                ExchangeKey data = keys.get(i);
                updateAppWidget(context, data, appWidgetManager, appWidgetIds[i]);
            }
        }, this::onError);


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

