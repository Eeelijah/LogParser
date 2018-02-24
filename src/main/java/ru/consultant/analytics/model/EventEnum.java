package ru.consultant.analytics.model;


import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum EventEnum {

    QUERY("QUERY") {
        @Override
        public Event getInstance(String... args) {
            QueryEvent queryEvent = new QueryEvent();
            queryEvent.setQueryDate(LocalDate.parse(args[0]));
            queryEvent.setQueryString(args[1]);
            return queryEvent;
        }
    },

    SERPLIST("SERPLIST") {
        @Override
        public Event getInstance(String... args) {
            SerpListEvent serpListEvent = new SerpListEvent();
            serpListEvent.setResultId(Long.parseLong(args[0]));

            String[] strings = args[1].replaceAll("\\(", "").replaceAll("\\)", " ").split(" ");
            List<Long> docIds = new ArrayList<>();
            Arrays.asList(strings).forEach( s -> docIds.add(Long.parseLong(s)));
            serpListEvent.setDocIds(docIds);

            return serpListEvent;
        }
    },

    CLICK("CLICK") {
        @Override
        public Event getInstance(String... args) {
            ClickEvent clickEvent = new ClickEvent();
            clickEvent.setDocId(Long.parseLong(args[1]));
            return clickEvent;
        }
    };

    private String tag;

    EventEnum(String tag) {
        this.tag = tag;
    }

    public abstract Event getInstance(String... args);

}
