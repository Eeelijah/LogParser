package ru.consultant.analytics.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"queryEvent"})
public class ClickEvent implements Event{

    private Long docId;
    private QueryEvent queryEvent;

    @Override
    public EventEnum getEventEnum() {
        return EventEnum.CLICK;
    }

    public Integer clickDocPosition() {
        return queryEvent.getDocsId().indexOf(docId) + 1;
    }
}
