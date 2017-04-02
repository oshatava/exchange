package com.osh.exchangeapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.osh.exchangeapp.R;
import com.osh.exchangeapp.activity.ExchangeEditViewActivity;
import com.osh.exchangeapp.data.utils.CollectionUtils;

import java.util.List;

import javax.inject.Inject;

public class ViewUtils {

    public static void hideSoftwareKeyboard(final Context context, final View view) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if(view!=null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void showSoftwareKeyboard(final Context context, final View view) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void makeImageViewSquare(Activity activity, ImageView imageView) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int swidth = display.getWidth();
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = swidth;
        imageView.setLayoutParams(params);
    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public static <T> T findViewById(View parent, int id){
        if(parent!=null && id!=0){
            return (T)parent.findViewById(id);
        }
        return null;
    }


    public static <T> T findViewById(Activity parent, int id){
        if(parent!=null && id!=0){
            return (T)parent.findViewById(id);
        }
        return null;
    }

    public static <T extends View> T inflate(ViewGroup root, int layoutResId){
        LayoutInflater layoutInflater = LayoutInflater.from(root.getContext());
        T v = (T)layoutInflater.inflate(layoutResId, root, false);
        return v;
    }

    public static void hide(View view) {
        if(view!=null){
            view.setVisibility(View.GONE);
        }
    }

    public static void show(View view) {
        if(view!=null){
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void show(View root, int id) {
        View view = findViewById(root, id);
        if(view!=null){
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void show(Activity root, int id) {
        View view = findViewById(root, id);
        if(view!=null){
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hide(View root, int id) {
        View view = findViewById(root, id);
        if(view!=null){
            view.setVisibility(View.GONE);
        }
    }

    public static void hide(Activity root, int id) {
        View view = findViewById(root, id);
        if(view!=null){
            view.setVisibility(View.GONE);
        }
    }

    public static void hideSoft(View root, int id) {
        View view = findViewById(root, id);
        if(view!=null){
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void onClick(ViewGroup root, int id, View.OnClickListener clickListener) {
        View v = findViewById(root, id);
        if(v!=null)
            v.setOnClickListener(clickListener);
    }

    public static void onClick(Activity root, int id, View.OnClickListener clickListener) {
        View v = findViewById(root, id);
        if(v!=null)
            v.setOnClickListener(clickListener);
    }

    public static void onTextChanged(Activity root, int id, @NonNull OnTextChanged onTextChanged) {
        View v = findViewById(root, id);
        if(v!=null)
            if(v instanceof EditText)
                ((EditText) v).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        onTextChanged.onTextChanged(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
    }

    public static void onTextChangedAsInt(Activity root, int id, @NonNull OnTextChangedAsInt onTextChanged) {
        onTextChanged(root, id, text -> {
            Integer ret = null;
            try{
                ret = Integer.parseInt(text);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(ret!=null)
                onTextChanged.onTextChanged(ret);
        });
    }

    public static void onTextChangedAsDecimal(Activity root, int id, @NonNull OnTextChangedAsDecimal onTextChanged) {
        onTextChanged(root, id, text -> {
            Float ret = null;
            try{
                ret = Float.parseFloat(text);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(ret!=null)
                onTextChanged.onTextChanged(ret);
        });
    }


    public static void setUpSpinner(Activity root, int id, int dataArrayResId, String selectedText,  OnTextChanged onItemSelected) {
        View v = findViewById(root, id);
        if(! (v instanceof Spinner)) return;
        Spinner spinner = (Spinner) v;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root, dataArrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int selection = adapter.getPosition(selectedText);
        if(selection>=0)
            spinner.setSelection(selection);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelected.onTextChanged(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static <T> void setUpSpinner(Activity root, int id, List<T> items, String selectedText, OnTextChanged onItemSelected) {
        View v = findViewById(root, id);
        if(! (v instanceof Spinner)) return;
        Spinner spinner = (Spinner) v;
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root, dataArrayResId, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(root,
                android.R.layout.simple_spinner_item,
                CollectionUtils.map(items, i->i.toString()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int selection = adapter.getPosition(selectedText);
        if(selection>=0)
            spinner.setSelection(selection);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelected.onTextChanged(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static void setText(Activity root, int id, float f) {
        View v = findViewById(root, id);
        if(v!=null)
            if(v instanceof EditText){
                ((EditText) v).setText(Float.toString(f));
            }
    }

    public static void setText(Activity root, int id, int i) {
        View v = findViewById(root, id);
        if(v!=null)
            if(v instanceof EditText){
                ((EditText) v).setText(Integer.toString(i));
            }
    }

    public static void setText(Activity root, int id, String t) {
        View v = findViewById(root, id);
        if(v!=null)
            if(v instanceof EditText){
                ((EditText) v).setText(t);
            }
    }


    public interface OnTextChanged{
        void onTextChanged(String text);
    }

    public interface OnTextChangedAsInt{
        void onTextChanged(Integer i);
    }

    public interface OnTextChangedAsDecimal{
        void onTextChanged(Float f);
    }

    public interface OnEachView{
        void onView(View v);
    }

    public static void iterateViews(View view, OnEachView onEachView){
        if(onEachView==null)
            return;

        onEachView.onView(view);
        if(view instanceof ViewGroup){
            for(int i = 0; i<((ViewGroup) view).getChildCount(); i++)
                iterateViews(((ViewGroup) view).getChildAt(i), onEachView);
        }
    }
}
