package ru.consultant.analytics.mapreduce;

import java.util.Iterator;

public interface Reducer {
    void reduce(String key, Iterator<String> value, Store store);
}
