package ru.consultant.analytics.mapreduce.impl;

import ru.consultant.analytics.mapreduce.Reducer;
import ru.consultant.analytics.mapreduce.Store;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class FrequencyReducer implements Reducer {

    @Override
    public void reduce(String key, Iterator<String> value, Store store) {
        int temp = 0;

        while (value.hasNext()) {
            temp += Integer.parseInt(value.next());
        }

        List<String> strings = new LinkedList<>();
        strings.add(temp + "");

        Map<String, List<String>> bucket = store.getBucket();
        bucket.put(key, strings);
    }
}
