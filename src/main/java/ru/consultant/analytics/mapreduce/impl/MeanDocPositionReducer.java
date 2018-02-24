package ru.consultant.analytics.mapreduce.impl;


import ru.consultant.analytics.mapreduce.Reducer;
import ru.consultant.analytics.mapreduce.Store;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MeanDocPositionReducer implements Reducer {

    @Override
    public void reduce(String key, Iterator<String> value, Store store) {
        long temp = 0;

        while (value.hasNext()) {
            temp += Long.parseLong(value.next());
        }

        temp = Math.round(temp * 1.0 / (store.getBucket().get(key).size()));

        List<String> strings = new LinkedList<>();
        strings.add(temp + "");

        Map<String, List<String>> bucket = store.getBucket();
        bucket.put(key, strings);

    }
}
