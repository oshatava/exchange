package com.osh.exchangeapp.data.retrofit.converters;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oleg on 3/2/2017.
 */

public class CSVConverter extends Converter.Factory {
    private GsonConverterFactory factory;

    public CSVConverter(GsonConverterFactory factory) {
        this.factory = factory;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        String sType = type.toString();
        if(sType.contains("List") && sType.contains("ExchangeRemote")){
            return new CSVResponseBodyConverter();
        }
        return factory.responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return factory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
