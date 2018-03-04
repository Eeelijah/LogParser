package ru.consultant.analytics.mapreduce;

import ru.consultant.analytics.model.MapData;
import java.util.Iterator;

public interface Reducer {
    void setColumn(MapData.MapColumn column);
    void reduce(String key, Iterator<String> value, Store store);
}
