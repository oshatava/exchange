package com.osh.exchangeapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.osh.exchangeapp.Application;
import com.osh.exchangeapp.R;
import com.osh.exchangeapp.application.AppComponent;
import com.osh.exchangeapp.application.HasAppComponent;
import com.osh.exchangeapp.utils.ViewUtils;

/**
 * Created by oleg on 2/7/2017.
 */

public class BaseActivity extends AppCompatActivity implements HasAppComponent {
    @Override
    public AppComponent getAppComponent() {
        return Application.getAppComponent(this);
    }

    public void hideWait() {
        ViewUtils.hide(this, R.id.wait);
    }

    public void showWait() {
        ViewUtils.show(this, R.id.wait);
    }

    public void showError(Throwable error) {
        error.printStackTrace();
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
    }

}
