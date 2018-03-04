package ru.consultant.analytics.mapreduce;

import ru.consultant.analytics.model.MapData;
import ru.consultant.analytics.model.MapResult;

import java.util.List;
import java.util.Map;

public interface Store {
    void collect(String key, String value);
    Map<String, MapData> getBuckets();
    MapData getBucket(String key);
}
