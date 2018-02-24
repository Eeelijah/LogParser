package ru.consultant.analytics.mapreduce;

import java.util.List;
import java.util.Map;

public interface Store {
    void collect(String key, String value);
    Map<String, List<String>> getBucket();
}
