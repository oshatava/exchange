package com.osh.exchangeapp.domain.interactor;
import com.osh.exchangeapp.domain.Widget;
import com.osh.exchangeapp.domain.executor.PostExecutionThread;
import com.osh.exchangeapp.domain.executor.ThreadExecutor;
import com.osh.exchangeapp.domain.repository.WidgetRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by oleg on 1/24/2017.
 */

public class WidgetInterator extends BaseInteractor<WidgetRepository> {
    public WidgetInterator(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, WidgetRepository widgetRepository) {
        super(threadExecutor, postExecutionThread, widgetRepository);
    }

    public void getWidget(int id, Consumer<Widget> onWidget, Consumer<Throwable> onError){
        execute(getRepository().getWidget(id), onWidget, onError);
    }

    public List<Widget> getOrCreateWidgets(List<Integer> ids){
        return getRepository().getOrCreateWidgets(ids);
    }

    public List<Widget> getWidgets(){
        return getRepository().getWidgets();
    }

    public Widget getWidget(int id){
        return getRepository().getWidgetSync(id);
    }

    public List<Widget> setWidgets(List<Widget> widgets){
        return getRepository().setWidgets(widgets);
    }

    public Widget setWidget(Widget widgets){
        return getRepository().setWidgetSync(widgets);
    }

    public void setWidget(Widget widget, Consumer<Widget> onWidget, Consumer<Throwable> onError){
        execute(getRepository().setWidget(widget), onWidget, onError);
    }

    public void clearWidgets(Consumer<Void> onClear, Consumer<Throwable> onError){
        execute(getRepository().clearWidgets(), onClear, onError);
    }

}
