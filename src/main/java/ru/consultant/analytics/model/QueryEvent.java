package ru.consultant.analytics.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class QueryEvent implements Event {

    private LocalDate queryDate;
    private String queryString;
    private List<Long> docsId;
    private List<ClickEvent> clickEvents;


    public void addClickEvent(ClickEvent clickEvent) {
        if(clickEvents == null)
            clickEvents = new ArrayList<>();

        clickEvents.add(clickEvent);
    }

    @Override
    public EventEnum getEventEnum() {
        return EventEnum.QUERY;
    }
}
