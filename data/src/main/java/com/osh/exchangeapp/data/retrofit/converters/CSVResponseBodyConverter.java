package com.osh.exchangeapp.data.retrofit.converters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.osh.exchangeapp.data.retrofit.entity.ExchangeRemote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by oleg on 3/3/2017.
 */

public class CSVResponseBodyConverter implements Converter<ResponseBody, List<ExchangeRemote>> {

    CSVResponseBodyConverter() {
    }

    @Override public List<ExchangeRemote> convert(ResponseBody value) throws IOException {
        try {
            List<ExchangeRemote> ret = new ArrayList<>();
            String valueStr = value.string();
            valueStr = valueStr != null ? valueStr : "";
            if (!valueStr.isEmpty()) {
                String [] lines = valueStr.split("\n");
                for(String line:lines) {
                    ExchangeRemote remote = parseLine(line);
                    if(remote!=null)
                        ret.add(remote);
                }
            }
            return ret;
        } finally {
            value.close();
        }
    }

    private ExchangeRemote parseLine(String line) {
        if(line==null) return null;
        if(line.isEmpty()) return new ExchangeRemote();


        String [] fields = line.split(",");

        String id = removeBraces(fields[0]);
        String [] mskeys = id.split("/");


        float lastTrade = Float.parseFloat(fields[1]);
        float change = Float.parseFloat(fields[2]);
        float dayLow= Float.parseFloat(fields[3]);
        float dayHigh= Float.parseFloat(fields[4]);
        String date = removeBraces(fields[5]);
        String time =  removeBraces(fields[6]);

        return new ExchangeRemote(getSafe(mskeys, 0), getSafe(mskeys, 1), lastTrade, change, dayLow, dayHigh, date, time);
    }

    private String removeBraces(String field) {
        if(field==null){
            return "";
        }
        return field.replace("\"", "");
    }

    private String getSafe(String [] fields, int index) {
        if(fields==null){
            return "";
        }
        if(index>=fields.length)
            return "";
        return fields[index];
    }

}
