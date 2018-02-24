package ru.consultant.analytics.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SerpListEvent implements Event {

    private Long resultId;
    private List<Long> docIds;

    @Override
    public EventEnum getEventEnum() {
        return EventEnum.SERPLIST;
    }
}
