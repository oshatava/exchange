package com.osh.exchangeapp.view.interfaces;

/**
 * Created by oleg on 2/9/2017.
 */

public interface DataView<EntityClass> extends IView{
    void showData(EntityClass data);
}
