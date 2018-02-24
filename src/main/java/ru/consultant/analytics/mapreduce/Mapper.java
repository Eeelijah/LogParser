package ru.consultant.analytics.mapreduce;

public interface Mapper {
    void map(String key, String value, Store store);
}
