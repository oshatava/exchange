package com.osh.exchangeapp.view.interfaces;

/**
 * Created by oleg on 2/9/2017.
 */

public interface DataListItemView<EntityClass, ListenerClass> extends DataView<EntityClass> {
    void setListener(ListenerClass listener);
}
