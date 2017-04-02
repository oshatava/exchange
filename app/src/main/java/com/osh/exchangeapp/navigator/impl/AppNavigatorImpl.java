package com.osh.exchangeapp.navigator.impl;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

import com.osh.exchangeapp.R;
import com.osh.exchangeapp.activity.BaseActivity;
import com.osh.exchangeapp.activity.ExchangeEditViewActivity;
import com.osh.exchangeapp.activity.MainActivity;
import com.osh.exchangeapp.navigator.AppNavigator;
import com.osh.exchangeapp.widgets.ExchangeRateWidget;

import java.lang.ref.WeakReference;

/**
 * Created by oleg on 3/30/2017.
 */

public class AppNavigatorImpl implements AppNavigator {

    private WeakReference<Context> contextRef = new WeakReference<Context>(null);
    private WeakReference<BaseActivity> currentActivityRef = new WeakReference<BaseActivity>(null);

    public AppNavigatorImpl(Context context) {
        this.contextRef = new WeakReference<Context>(context);
    }

    public void setCurrentActivity(BaseActivity activity){
        this.currentActivityRef = new WeakReference<BaseActivity>(activity);
    }

    @Override
    public void showExchangeEditor(int id) {
        BaseActivity activity = getCurrentActivity();
        if(activity==null) return;

        Intent intent = new Intent(activity, ExchangeEditViewActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);

    }

    @Override
    public void showMain() {
        Context context = contextRef.get();
        if (context == null) return;


        if (getCurrentActivity() != null) {
            Intent intent = new Intent(context, MainActivity.class);
            getCurrentActivity().startActivity(intent);
            getCurrentActivity().finish();
            getCurrentActivity().overridePendingTransition(0, R.anim.fade_out);
        } else {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    @Override
    public void updateWidget(int id) {
        Context context = contextRef.get();
        if (context == null) return;

        Intent intent = new Intent(context, ExchangeRateWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = {id};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        context.sendBroadcast(intent);

    }

    @Override
    public void back() {
        if(getCurrentActivity()!=null)
            getCurrentActivity().finish();
    }

    public BaseActivity getCurrentActivity() {
        return currentActivityRef.get();
    }

}
