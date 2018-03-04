package ru.consultant.analytics.mapreduce;

import org.springframework.web.multipart.MultipartFile;
import ru.consultant.analytics.mapreduce.impl.MapperImpl;
import ru.consultant.analytics.mapreduce.impl.ReducerImpl;
import ru.consultant.analytics.mapreduce.impl.StoreImpl;
import ru.consultant.analytics.model.*;
import ru.consultant.analytics.model.MapData.MapColumn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class MapReduce {

    public List<MapResult> mapReduce(InputStream inputStream) throws IOException {


        Mapper mapper = new MapperImpl();
        Reducer reducer = new ReducerImpl();
        Store store = new StoreImpl();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        //Map phase
        while ((line = br.readLine()) != null) {
            mapper.map(line, "1", store);
        }

        //Reduce phase
        Map<String, MapData> buckets = store.getBuckets();
        for (Map.Entry<String, MapData> bucket : buckets.entrySet()) {
            for(Map.Entry<MapColumn, List<String>> res : bucket.getValue().getData().entrySet()) {
                reducer.setColumn(res.getKey());
                reducer.reduce(bucket.getKey(), res.getValue().iterator(), store);
            }
        }

        //Fill result
        List<MapResult> mapResults = new ArrayList<>();
        for (String query : store.getBuckets().keySet()) {
            MapData mapData = store.getBuckets().get(query);
            Map<MapColumn, List<String>> data = mapData.getData();

            MapResult mapResult = new MapResult();
            mapResult.setQuery(query);
            mapResult.setFrequency(data.get(MapColumn.FREQUENCY).get(0));
            mapResult.setUserCount(data.get(MapColumn.USER_COUNT).get(0));
            mapResult.setDocPosition(data.get(MapColumn.DOC_POSITION).get(0));
            mapResult.setClickCount(data.get(MapColumn.CLICK_COUNT).get(0));
            mapResult.setReqFrequencyWithoutClick(data.get(MapColumn.REQ_WITHOUT_CLICK).get(0));
            mapResults.add(mapResult);
        }

        return mapResults;
    }
}