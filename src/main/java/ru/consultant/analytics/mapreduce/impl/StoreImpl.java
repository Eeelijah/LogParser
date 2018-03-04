package ru.consultant.analytics.mapreduce.impl;


import ru.consultant.analytics.mapreduce.Store;
import ru.consultant.analytics.model.*;
import ru.consultant.analytics.model.MapData.MapColumn;
import ru.consultant.analytics.parser.EventFactory;
import ru.consultant.analytics.parser.Parser;

import java.util.*;

public class StoreImpl implements Store {

    private Map<String, MapData> buckets;
    private EventFactory eventFactory;

    public StoreImpl() {
        this.buckets = new HashMap<>();
        this.eventFactory = new EventFactory();
    }

    public Map<String, MapData> getBuckets() {
        return buckets;
    }

    @Override
    public MapData getBucket(String key) {
        return buckets.get(key);
    }

    private void putInBucket(String key, String value, MapColumn column) {
        MapData mapData = buckets.get(key);

        if(mapData == null) {
            mapData = new MapData();
            mapData.getData().get(column).add(value);
            buckets.put(key, mapData);
            return;
        }

        if(column == MapColumn.USER_COUNT) { // нам нужны только уникальные userId
            List<String> objects = mapData.getData().get(column);
            if(!objects.contains(value))
                objects.add(value);
        } else {
            mapData.getData().get(column).add(value);
        }
    }

    @Override
    public void collect(String key, String value) {
        SessionEntity sessionEntity = Parser.parse(key, eventFactory);
        List<QueryEvent> queryEvents = sessionEntity.getQueryEvents();
        List<ClickEvent> clickEvents = sessionEntity.getClickEvents();

        for(QueryEvent queryEvent : queryEvents) {
            String queryString = queryEvent.getQueryString();
            putInBucket(queryString, "1", MapColumn.FREQUENCY);
            putInBucket(queryString, sessionEntity.getUserId().toString(), MapColumn.USER_COUNT);

            List<ClickEvent> clicks = queryEvent.getClickEvents();
            if(clicks == null || clicks.isEmpty()) {
                putInBucket(queryString, "1", MapColumn.REQ_WITHOUT_CLICK);
            }
        }

        for(ClickEvent clickEvent : clickEvents) {
            String queryString = clickEvent.getQueryEvent().getQueryString();
            putInBucket(queryString, clickEvent.clickDocPosition().toString(), MapColumn.DOC_POSITION);
            putInBucket(queryString, "1", MapColumn.CLICK_COUNT);
        }
    }
}
