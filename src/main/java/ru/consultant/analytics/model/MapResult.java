package ru.consultant.analytics.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MapResult {

    private String query;
    private String frequency;
    private String userCount;
    private String docPosition;
    private String clickCount;
    private String reqFrequencyWithoutClick;

}
