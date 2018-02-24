package ru.consultant.analytics.mapreduce.impl;


import ru.consultant.analytics.mapreduce.Store;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MeanDocPositionStore implements Store {

    private Map<String, List<String>> bucket;

    public MeanDocPositionStore() {
        this.bucket = new HashMap<>();
    }

    @Override
    public Map<String, List<String>> getBucket() {
        return bucket;
    }

    @Override
    public void collect(String key, String value) {
        if (this.bucket.get(key) == null) {
            List<String> gg = new LinkedList<>();
            gg.add(value);
            this.bucket.put(key, gg);
        }
        else {
            this.bucket.get(key).add(value);
        }
    }
}
