package ru.consultant.analytics.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SessionEntity {

    private Long userId;
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
