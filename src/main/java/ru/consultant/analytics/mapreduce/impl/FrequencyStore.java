package ru.consultant.analytics.mapreduce.impl;


import ru.consultant.analytics.mapreduce.Store;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FrequencyStore implements Store {

    private Map<String, List<String>> bucket;

    public FrequencyStore() {
        this.bucket = new HashMap<>();
    }

    @Override
    public Map<String, List<String>> getBucket() {
        return bucket;
    }

    @Override
    public void collect(String key, String value) {
        if (bucket.get(key) == null) {
            List<String> gg = new LinkedList<>();
            gg.add(value);
            bucket.put(key, gg);
        }
        else {
            bucket.get(key).add(value);
        }
    }
}
