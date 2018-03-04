package ru.consultant.analytics.mapreduce.impl;

import ru.consultant.analytics.mapreduce.Mapper;
import ru.consultant.analytics.mapreduce.Store;


public class MapperImpl implements Mapper {

    @Override
    public void map(String key, String value, Store store) {
        store.collect(key, value.trim().toLowerCase());
    }
}
