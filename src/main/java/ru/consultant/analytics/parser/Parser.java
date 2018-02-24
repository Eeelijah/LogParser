package ru.consultant.analytics.parser;

import ru.consultant.analytics.model.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public static SessionEntity parse(String logString, EventFactory factory) {

        SessionEntity sessionEntity = new SessionEntity();
        List<Event> events = new ArrayList<>();

        List<QueryEvent> queryEvents = new ArrayList<>();
        List<ClickEvent> clickEvents = new ArrayList<>();
        sessionEntity.setQueryEvents(queryEvents);
        sessionEntity.setClickEvents(clickEvents);

        String [] splitByTabArray = logString.split("\\t");

        Long userId = Long.parseLong(splitByTabArray[0]);
        LocalDate sessionStart = LocalDate.parse(splitByTabArray[1]);
        LocalDate sessionEnd = LocalDate.parse(splitByTabArray[splitByTabArray.length-1]);

        sessionEntity.setUserId(userId);
        sessionEntity.setSessionStart(sessionStart);
        sessionEntity.setSessionEnd(sessionEnd);

        for(int i = 2; i < splitByTabArray.length - 1; i++) {
            String frag = splitByTabArray[i];

            for (EventEnum eventEnum : EventEnum.values()) {
                String tag = eventEnum.getTag();

                if (frag.contains(tag)) {
                    String value = frag.substring(frag.indexOf(tag) + tag.length() + 1);
                    String[] strings = value.split(" ");
                    strings[1] = value.substring(strings[0].length() + 1);
                    Event event = factory.getInstance(EventEnum.valueOf(tag), strings);
                    events.add(event);
                    break;
                }
            }
        }

        events.forEach(event -> {
            if(event instanceof QueryEvent)
                queryEvents.add((QueryEvent)event);
            else if(event instanceof ClickEvent)
                clickEvents.add((ClickEvent)event);
        });

        QueryEvent tempQueryEvent = new QueryEvent();
        for(Event event : events) {
            if(event instanceof QueryEvent) {
                tempQueryEvent = (QueryEvent)event;
            }
            if(event instanceof SerpListEvent) {
                tempQueryEvent.setDocsId(((SerpListEvent)event).getDocIds());
            }
            if(event instanceof ClickEvent) {
                ClickEvent clickEvent = (ClickEvent)event;
                tempQueryEvent.addClickEvent(clickEvent);
                clickEvent.setQueryEvent(tempQueryEvent);
            }
        }

        return sessionEntity;
    }
}
