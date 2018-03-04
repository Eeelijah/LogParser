package ru.consultant.analytics.mapreduce.impl;

import lombok.Getter;
import lombok.Setter;
import ru.consultant.analytics.mapreduce.Reducer;
import ru.consultant.analytics.mapreduce.Store;
import ru.consultant.analytics.model.MapData;
import ru.consultant.analytics.model.MapData.MapColumn;

import java.util.*;

@Getter
@Setter
public class ReducerImpl implements Reducer {

    private MapColumn column;

    @Getter
    @Setter
    private class IteratorPair {
        private Integer size;
        private Integer sum;

        public IteratorPair(Iterator<String> iterator) {
            Integer sum = 0, size = 0;
            while (iterator.hasNext()) {
                sum += Integer.parseInt(iterator.next());
                size+= 1;
            }
            this.size = size;
            this.sum = sum;
        }
    }

    @Override
    public void reduce(String key, Iterator<String> value, Store store) {
        MapData mapData = store.getBucket(key);
        IteratorPair pair = new IteratorPair(value);

        if(column == MapColumn.DOC_POSITION) {
            Integer pos = new Long(Math.round(pair.getSum() * 1.0 / pair.getSize())).intValue();
            reduceList(mapData.getData().get(column), pos);
        } else
            reduceList(mapData.getData().get(column), pair.getSize());
    }

    private void reduceList(List<String> list, Integer integer) {
        list.clear();
        list.add(integer.toString());
    }
}
