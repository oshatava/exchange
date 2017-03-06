package com.osh.exchangeapp.data.realm.utils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by oleg on 2/6/2017.
 */

public class RealmUtils {

    public interface Query<T extends RealmModel>{
        RealmQuery<T> query(RealmQuery<T> query);
    }

    public static <T extends RealmModel> void deleteAll(Class<T> tClass){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<T> results = realm
                .where(tClass)
                .findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static  <T extends RealmModel> T get(int id, Class<T> tClass){
        Realm realm = Realm.getDefaultInstance();
        final T result = realm
                .where(tClass)
                .equalTo("id", id)
                .findFirst();
        T ret = realm.copyFromRealm(result);
        realm.close();
        return ret;
    }

    public static  <T extends RealmModel> T get(Query<T> query, Class<T> tClass){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<T> realmQuery = query.query(realm.where(tClass));
        final T result = realmQuery.findFirst();
        T ret = realm.copyFromRealm(result);
        realm.close();
        return ret;
    }

    public static  <T extends RealmModel> T get(Query<T> query, Class<T> tClass, String orderBy, Sort sort){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<T> realmQuery = query.query(realm.where(tClass));
        final RealmResults<T> results = realmQuery.findAllSorted(orderBy, sort);
        T result = results.size()>0?results.first():null;
        T ret = result!=null?realm.copyFromRealm(result):null;
        realm.close();
        return ret;
    }

    public static  <T extends RealmModel> int getMaxId(Class<T> tClass){
        Realm realm = Realm.getDefaultInstance();
        Number max = realm.where(tClass).max("id");
        realm.close();
        return max!=null?max.intValue():0;
    }


    public static  <T extends RealmModel> List<T> getAll(Query<T> query, Class<T> tClass){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<T> realmQuery = query.query(realm.where(tClass));
        final RealmResults<T> result = realmQuery.findAll();
        List<T> ret = realm.copyFromRealm(result);
        realm.close();
        return ret;
    }

    public static  <T extends RealmModel> void set(T data){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
        realm.close();
    }

    public static  <T extends RealmModel> void setAll(Class<T> tClass, List<T> data){
        deleteAll(tClass);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
        realm.close();
    }

    public static  <T extends RealmModel> void addAll(Class<T> tClass, List<T> data){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
        realm.close();
    }

    public static  <T extends RealmModel> List<T> getAll(Class<T> tClass){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<T> result = realm
                .where(tClass)
                .findAll();
        List<T> ret = realm.copyFromRealm(result);
        realm.close();
        return ret;
    }

    public static  <T extends RealmModel> void delete(int id, Class<T> tClass){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<T> results = realm
                .where(tClass)
                .equalTo("id", id)
                .findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

}
