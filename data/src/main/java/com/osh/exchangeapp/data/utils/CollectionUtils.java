package com.osh.exchangeapp.data.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleg on 3/4/2017.
 */

public class CollectionUtils {
    public static <T> List<T> distinct(List <T> in, Compare<T> comparator){
        List<T> out = new ArrayList<T>();
        for(T t:in){
            if(!exist(out, t , comparator)){
                out.add(t);
            }
        }
        return out;
    }


    public static <T> boolean exist(List <T> in, T item, Compare<T> comparator){
        for(T t:in){
            if(comparator.isEqual(t, item))
                return true;
        }

        return false;
    }

    public static <T, A> T find(List <T> in, Check<T> finder){
        for(T t:in){
            if(finder.is(t))
                return t;
        }
        return null;
    }


    public static <T> void forEach(List <T> in, Each<T> onEach){
        if(in==null) return;
        if(onEach == null) return;

        for (T i:in)
            onEach.onEach(i);
    }


    public interface Each<T>{
        void onEach(T i);
    }

    public interface Compare<T>{
        boolean isEqual(T l, T r);
    }

    public interface Check<T>{
        boolean is(T i);
    }
}
