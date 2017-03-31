package com.osh.exchangeapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;

import com.osh.exchangeapp.R;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.domain.interactor.ExchangeInterator;
import com.osh.exchangeapp.navigator.AppNavigator;
import com.osh.exchangeapp.presenter.MainActivityPresenter;
import com.osh.exchangeapp.presenter.impl.MainActivityPresenterImpl;
import com.osh.exchangeapp.utils.ViewUtils;
import com.osh.exchangeapp.view.MainActivityView;
import com.osh.exchangeapp.view.adapters.ViewEntityAdapter;
import com.osh.exchangeapp.view.main.ExchangeListItem;
import com.osh.exchangeapp.view.main.ExchangeListItemListener;

import java.util.List;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 2000;
    private final String TAG = getClass().getSimpleName();

    @Inject
    public AppNavigator navigator;

    private Handler mHandler;
    private View logo;

    private Runnable onStartHandler = new Runnable() {
        @Override
        public void run() {
            navigator.showMain();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = ViewUtils.findViewById(this, R.id.logo);
        getAppComponent().inject(this);
        setFlagsFullscreen();
    }

    @SuppressLint("NewApi")
    private void setFlagsFullscreen() {
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);
            // Code below is for case when you press Volume up or Volume down.
            // Without this after pressing valume buttons navigation bar will
            // show up and don't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(visibility -> {
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            decorView.setSystemUiVisibility(flags);
                        }
                    });
        }
    }

    public Handler getHandler() {
        if(mHandler==null)
            mHandler = new Handler(Looper.getMainLooper());
        return mHandler;
    }

    private void startTimerWithHandler() {
        getHandler().postDelayed(onStartHandler, SPLASH_DISPLAY_LENGTH);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(SPLASH_DISPLAY_LENGTH);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        logo.setAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimerWithHandler();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getHandler().removeCallbacks(onStartHandler);
    }

}
