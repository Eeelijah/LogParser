package ru.consultant.analytics.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude ={"clickEvents"})
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
