package com.osh.exchangeapp.view.widgets;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.osh.exchangeapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleg on 11/18/2016.
 */

public class BottomNavigationView extends LinearLayout{
    private final String TAG = getClass().getSimpleName();

    private int menuResId;
    private int itemBackgroundResId;

    private ItemSelectedListener itemSelectedListener;
    private int selectedItemId = 0;
    private List<Integer> menuItemsIds = new ArrayList<>();

    public void setItemSelectedListener(ItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    @Override
    public void setOrientation(int orientation) {
        //super.setOrientation(orientation);
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d(TAG, "onSaveInstanceState");
        return new StateHolder(super.onSaveInstanceState(), selectedItemId);
    }

    public void setSelectedItemId(int selectedItemId) {
        this.selectedItemId = selectedItemId;
        updateView();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.d(TAG, "onRestoreInstanceState");
        if(state instanceof StateHolder) {
            super.onRestoreInstanceState(((StateHolder) state).getSuperState());
            selectedItemId = ((StateHolder) state).getSelectedId();
        }else{
            super.onRestoreInstanceState(state);
        }
        updateView();
    }


    private void initAttr(Context context, AttributeSet attrs) {
        super.setOrientation(HORIZONTAL);

        if (attrs!=null) {

            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationView, 0, 0);
            itemBackgroundResId = a.getResourceId(R.styleable.BottomNavigationView_itemBgResId, 0);
            menuResId = a.getResourceId(R.styleable.BottomNavigationView_itemsMenu, 0);
            if(menuResId==0){
                Log.d(TAG, "menuResId should point to menu.");
            }else{
                initItems(context, menuResId);
            }
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateView();
    }

    private void initItems(Context context, int menuResId) {
        menuItemsIds.clear();

        removeAllViews();

        SupportMenuInflater menuInflater = new SupportMenuInflater(context);
        SupportMenu menu = new MenuBuilder(context){
            @Override
            public SubMenu addSubMenu(int group, int id, int categoryOrder, CharSequence title) {
                throw new UnsupportedOperationException("BottomNavigationView does not support submenus");
            }
        };
        menuInflater.inflate(menuResId, menu);

        for(int i=0; i<menu.size(); i++){

            MenuItem menuItem = menu.getItem(i);
            selectedItemId = i==0?menuItem.getItemId():selectedItemId;
            menuItemsIds.add(menuItem.getItemId());
            IMenuItem menuItemView = createMenuItem(context, menuItem);
            menuItemView.setId(menuItem.getItemId());
            if(itemBackgroundResId!=0){
                menuItemView.setBackground(itemBackgroundResId);
            }
            menuItemView.setIcon(menuItem.getIcon());
            menuItemView.setOnClickListener(new SingleClickListener() {
                @Override
                public void onClicked(View v) {
                    selectedItemId = v.getId();
                    fireOnItemSelected(v.getId());
                    updateView();
                }
            });

            addView((View)menuItemView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
        }
        menu.clear();

    }

    protected IMenuItem createMenuItem(Context context, MenuItem menuItem) {
        IMenuItem button = new MenuItemView(context);
        return button;
    }

    private void updateView() {
        for(Integer id: menuItemsIds){
            View v = findViewById(id);
            if(v instanceof  IMenuItem){
                ((IMenuItem)v).setSelected(v.getId()==selectedItemId);
            }
        }
    }

    private void fireOnItemSelected(int id) {
        if(itemSelectedListener!=null)
            itemSelectedListener.onItemSelected(id);
    }


    public interface ItemSelectedListener{
        void onItemSelected(int id);
    }

    public interface IMenuItem{
        void setIcon(Drawable drawable);
        void setBackground(int resId);
        void setSelected(boolean selected);
        void setOnClickListener(View.OnClickListener onItemClicked);
        void setId(int id);
    }

    private class MenuItemView extends FrameLayout implements IMenuItem{

        private ImageView view;

        public MenuItemView(Context context) {
            super(context);
            initView(context);
        }

        private void initView(Context context) {
            view = new ImageView(context);
            addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        }

        @Override
        public void setBackground(int resId) {
            setBackgroundResource(resId);
        }

        @Override
        public void setSelected(boolean selected) {
            view.setSelected(selected);
        }

        @Override
        public void setIcon(Drawable icon) {
            if (icon != null && view != null) {
                view.setImageDrawable(icon);
            }
        }
    }

    public static class StateHolder extends BaseSavedState {

        private int selectedId;

        public int getSelectedId() {
            return selectedId;
        }

        private StateHolder(Parcel in) {
            super(in);
            selectedId = in.readInt();
        }

        public StateHolder(Parcelable superState, int selectedId) {
            super(superState);
            this.selectedId = selectedId;
        }

        public static final Creator<StateHolder> CREATOR = new Creator<StateHolder>() {
            @Override
            public StateHolder createFromParcel(Parcel in) {
                return new StateHolder(in);
            }

            @Override
            public StateHolder[] newArray(int size) {
                return new StateHolder[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(selectedId);
        }
    }
}
