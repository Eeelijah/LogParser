package ru.consultant.analytics.parser;


import ru.consultant.analytics.model.Event;
import ru.consultant.analytics.model.EventEnum;

public class EventFactory {

    public Event getInstance (EventEnum eventEnum, String... parseStr) {
        return eventEnum.getInstance(parseStr);
    }

}
