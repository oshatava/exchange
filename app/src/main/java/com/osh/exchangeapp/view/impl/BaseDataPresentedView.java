package com.osh.exchangeapp.view.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.osh.exchangeapp.R;
import com.osh.exchangeapp.presenter.interfaces.HasPresenter;
import com.osh.exchangeapp.utils.ViewUtils;
import com.osh.exchangeapp.view.interfaces.DataView;

public abstract class BaseDataPresentedView<EntityClass, PresenterClass> extends FrameLayout
        implements DataView<EntityClass>, HasPresenter<PresenterClass> {
    private EntityClass data;
    private PresenterClass presenter;
    private boolean viewIsCreated = false;

    /**
     * Init view
     * @return true if initialization is successful
     */
    protected abstract boolean onInitView();
    protected abstract void onUpdateView(EntityClass data);

    public BaseDataPresentedView(Context context) {
        this(context, null);
    }

    public BaseDataPresentedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseDataPresentedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    public void setPresenter(PresenterClass presenter) {
        this.presenter = presenter;
    }

    @Override
    public PresenterClass getPresenter() {
        return presenter;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    @Override
    public void showData(EntityClass data){
        this.data = data;
        updateView();
    }

    private void initView(){
        viewIsCreated = onInitView();
        updateView();
    }

    public void updateView(){
        if(!viewIsCreated || data == null)
            return;
        onUpdateView(data);
    }

    @Override
    public void hideWait() {
        ViewUtils.hide(this, R.id.wait);
    }

    @Override
    public void showWait() {
        ViewUtils.show(this, R.id.wait);
    }

    @Override
    public void showError(Throwable error) {
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
    }
}
