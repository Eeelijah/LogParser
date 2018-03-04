package ru.consultant.analytics.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SessionEntity {

    private Integer userId;
    private LocalDate sessionStart;
    private LocalDate sessionEnd;
    private List<QueryEvent> queryEvents;
    private List<ClickEvent> clickEvents;

    @Override
    public String toString() {
        return "SessionEntity{" +
                "userId=" + userId +
                ", sessionStart=" + sessionStart +
                ", sessionEnd=" + sessionEnd +
                ", queryEvents=" + queryEvents +
                ", clickEvents=" + clickEvents +
                '}';
    }
}
