package ru.consultant.analytics.mapreduce.impl;

import ru.consultant.analytics.mapreduce.Mapper;
import ru.consultant.analytics.mapreduce.Reducer;
import ru.consultant.analytics.mapreduce.Store;
import ru.consultant.analytics.model.*;

import java.util.*;

public class MapReduce {


    public List<MapResult> mapReduce(List<SessionEntity> sessionEntities) {

        List<MapResult> mapResults = new ArrayList<>();

        Mapper mapper = new MapperImpl();

        Reducer frequencyReducer = new FrequencyReducer();
        Reducer userCountReducer = new UserCountReducer();
        Reducer meanDocPositionReducer = new MeanDocPositionReducer();

        Store frequencyStore = new FrequencyStore();
        Store userCountStore = new UserCountStore();
        Store meanDocPositionStore = new MeanDocPositionStore();
        Store clickCountStore = new FrequencyStore();
        Store reqFreqWithoutClickStore = new FrequencyStore();

        for(SessionEntity sessionEntity : sessionEntities) {
            sessionEntity.getQueryEvents().forEach(e -> mapper.map("1", e.getQueryString(), frequencyStore));
            sessionEntity.getQueryEvents().forEach(e -> mapper.map(sessionEntity.getUserId().toString(), e.getQueryString(), userCountStore));
            sessionEntity.getClickEvents().forEach(e -> mapper.map(e.clickDocPosition().toString(), e.getQueryEvent().getQueryString(), meanDocPositionStore));
            sessionEntity.getClickEvents().forEach(e -> mapper.map("1", e.getQueryEvent().getQueryString(), clickCountStore));
            sessionEntity.getQueryEvents().forEach(e -> {
                List<ClickEvent> clickEvents = e.getClickEvents();
                if(clickEvents == null || clickEvents.isEmpty()) {
                    mapper.map("1", e.getQueryString(), reqFreqWithoutClickStore);
                } else {
                    mapper.map("0", e.getQueryString(), reqFreqWithoutClickStore);
                }
            });
        }

        for (String query : frequencyStore.getBucket().keySet()) {
            frequencyReducer.reduce(query, frequencyStore.getBucket().get(query).iterator(), frequencyStore);
            userCountReducer.reduce(query, userCountStore.getBucket().get(query).iterator(), userCountStore);
            meanDocPositionReducer.reduce(query, meanDocPositionStore.getBucket().get(query).iterator(), meanDocPositionStore);
            frequencyReducer.reduce(query, clickCountStore.getBucket().get(query).iterator(), clickCountStore);
            frequencyReducer.reduce(query, reqFreqWithoutClickStore.getBucket().get(query).iterator(), reqFreqWithoutClickStore);

            MapResult mapResult = new MapResult();
            mapResult.setQuery(query);
            mapResult.setFrequency(frequencyStore.getBucket().get(query).get(0));
            mapResult.setUserCount(userCountStore.getBucket().get(query).get(0));
            mapResult.setDocPosition(meanDocPositionStore.getBucket().get(query).get(0));
            mapResult.setClickCount(clickCountStore.getBucket().get(query).get(0));
            mapResult.setReqFrequencyWithoutClick(reqFreqWithoutClickStore.getBucket().get(query).get(0));
            mapResults.add(mapResult);
        }

        return mapResults;
    }
}
