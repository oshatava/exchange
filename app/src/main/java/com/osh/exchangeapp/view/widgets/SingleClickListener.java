package com.osh.exchangeapp.view.widgets;

import android.os.SystemClock;
import android.view.View;

/**
 * Created by oleg on 11/9/2016.
 */

public abstract class SingleClickListener implements View.OnClickListener {
    private static final long THRESHOLD_MILLIS = 500L;
    private long lastClickMillis;

    @Override
    public void onClick(View v) {
        long now = SystemClock.elapsedRealtime();
        if (now - lastClickMillis > THRESHOLD_MILLIS) {
            onClicked(v);
        }
        lastClickMillis = now;
    }

    public abstract void onClicked(View v);
}