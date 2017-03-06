package com.osh.exchangeapp.view.main;

import android.content.Context;
import java.text.DecimalFormat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.osh.exchangeapp.R;
import com.osh.exchangeapp.domain.Exchange;
import com.osh.exchangeapp.domain.ExchangeKey;
import com.osh.exchangeapp.utils.DateUtils;
import com.osh.exchangeapp.utils.ViewUtils;
import com.osh.exchangeapp.view.impl.BaseDataListItemView;

/**
 * Created by oleg on 3/3/2017.
 */

public class ExchangeListItem extends BaseDataListItemView<ExchangeKey, ExchangeListItemListener> {

    private TextView masterAmount;
    private TextView masterCurrency;
    private TextView slaveAmount;
    private TextView slaveCurrency;

    private TextView min;
    private TextView max;
    private TextView change;
    private TextView resource;
    private TextView updateDate;
    private ImageView changeDirection;


    public ExchangeListItem(Context context) {
        super(context);
    }

    public ExchangeListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExchangeListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean onInitView() {
        masterAmount = ViewUtils.findViewById(this, R.id.master_amount);
        masterCurrency = ViewUtils.findViewById(this, R.id.master);
        slaveAmount = ViewUtils.findViewById(this, R.id.slave_amount);
        slaveCurrency = ViewUtils.findViewById(this, R.id.slave);

        min = ViewUtils.findViewById(this, R.id.min);
        max = ViewUtils.findViewById(this, R.id.max);
        change = ViewUtils.findViewById(this, R.id.change);
        resource = ViewUtils.findViewById(this, R.id.resource);
        updateDate = ViewUtils.findViewById(this, R.id.update_date);
        changeDirection = ViewUtils.findViewById(this, R.id.change_direction);
        return slaveAmount!=null;
    }

    @Override
    protected void onUpdateView(ExchangeKey data) {
        setOnClickListener(v->{
            if(getListener()!=null)
                getListener().onClick(data);
        });
        DecimalFormat format = new DecimalFormat("0.00");
        DecimalFormat formatChange = new DecimalFormat("0.0000");
        masterCurrency.setText(data.getMaster().getId());
        masterAmount.setText(format.format(data.getAmount()));

        slaveCurrency.setText(data.getSlave().getId());
        slaveAmount.setText(format.format(data.getLastTrade()));

        change.setText(formatChange.format(Math.abs(data.getChange())));
        if(data.getChange()>0)
            changeDirection.setImageResource(R.drawable.ic_trending_up_black_24dp);
        else if(data.getChange()<0)
            changeDirection.setImageResource(R.drawable.ic_trending_down_black_24dp);
        else
            changeDirection.setImageResource(R.drawable.ic_trending_flat_black_24dp);


        min.setText(format.format(data.getDayLow()));
        max.setText(format.format(data.getDayHigh()));

        resource.setText(data.getSource());
        updateDate.setText(DateUtils.formatDate(getContext(),data.getDate()));
    }
}
