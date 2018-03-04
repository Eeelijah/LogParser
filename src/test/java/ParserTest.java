import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.consultant.analytics.components.StorageService;
import ru.consultant.analytics.mapreduce.MapReduce;
import ru.consultant.analytics.model.ClickEvent;
import ru.consultant.analytics.model.MapResult;
import ru.consultant.analytics.model.QueryEvent;
import ru.consultant.analytics.model.SessionEntity;
import ru.consultant.analytics.parser.EventFactory;
import ru.consultant.analytics.parser.Parser;
import ru.consultant.analytics.web.Application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ParserTest {

    @MockBean
    private StorageService storageService;

    @Test
    public void mapReduceTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        MapReduce mapReduce = new MapReduce();
        List<MapResult> mapResults = mapReduce.mapReduce(classLoader.getResourceAsStream("sessions"));
        MapResult mapResult = mapResults.stream()
                                        .filter(x -> x.getQuery().equals("{1}"))
                                        .findFirst()
                                        .get();

        assertThat(mapResult.getClickCount()).isEqualTo("9");
        assertThat(mapResult.getDocPosition()).isEqualTo("4");
        assertThat(mapResult.getFrequency()).isEqualTo("2");
        assertThat(mapResult.getReqFrequencyWithoutClick()).isEqualTo("0");
        assertThat(mapResult.getUserCount()).isEqualTo("2");


    }

    @Test
    public void parserTest() {
        Parser parser = new Parser();
        EventFactory eventFactory = new EventFactory();
        String logString = "6\t2016-07-20\tQUERY 2016-07-20 {Доверенность}\tSERPLIST 0 (6565)(1264)(1404)(8009)(7575)(6012)(4763)(2071)(2348)(188)\tCLICK 0 6565\tCLICK 0 6012\t2016-07-20";
        SessionEntity parsedEntity = parser.parse(logString, eventFactory);

        SessionEntity sample = new SessionEntity();
        sample.setUserId(6);
        sample.setSessionStart(LocalDate.parse("2016-07-20"));
        sample.setSessionEnd(LocalDate.parse("2016-07-20"));

        List<ClickEvent> clickEvents = new ArrayList<>();
        ClickEvent clickEvent = new ClickEvent();
        ClickEvent clickEvent2 = new ClickEvent();
        clickEvent.setDocId(6565L);
        clickEvent2.setDocId(6012L);
        clickEvents.add(clickEvent);
        clickEvents.add(clickEvent2);

        List<QueryEvent> queryEvents = new ArrayList<>();
        QueryEvent queryEvent = new QueryEvent();
        queryEvent.setQueryString("{Доверенность}");
        queryEvent.setQueryDate(LocalDate.parse("2016-07-20"));
        queryEvent.setDocsId(Arrays.asList(new Long[]{6565L, 1264L, 1404L, 8009L, 7575L, 6012L, 4763L, 2071L, 2348L, 188L}));
        queryEvent.setClickEvents(clickEvents);
        clickEvent.setQueryEvent(queryEvent);
        clickEvent2.setQueryEvent(queryEvent);
        queryEvents.add(queryEvent);

        sample.setClickEvents(clickEvents);
        sample.setQueryEvents(queryEvents);

        assertThat(sample).isEqualTo(parsedEntity);
    }
}
