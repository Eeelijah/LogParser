package ru.consultant.analytics.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class MapData {

    public enum MapColumn {
        FREQUENCY,
        USER_COUNT,
        DOC_POSITION,
        CLICK_COUNT,
        REQ_WITHOUT_CLICK;

        public static List<MapColumn> getItems() {
            return Arrays.asList(values());
        }
    }

    private Map<MapColumn, List<String>> data;

    public MapData() {
        this.data = new HashMap<>();

        MapColumn.getItems().forEach(col -> data.put(col, new ArrayList<>()));
    }

}
