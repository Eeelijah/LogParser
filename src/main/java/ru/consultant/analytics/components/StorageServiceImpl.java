package ru.consultant.analytics.components;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.consultant.analytics.mapreduce.impl.MapReduce;
import ru.consultant.analytics.model.MapResult;
import ru.consultant.analytics.model.SessionEntity;
import ru.consultant.analytics.parser.EventFactory;
import ru.consultant.analytics.parser.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class StorageServiceImpl implements StorageService {

    @Override
    public List<MapResult> store(MultipartFile file) {
        EventFactory eventFactory = new EventFactory();
        List<SessionEntity> sessionEntities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                SessionEntity sessionEntity = Parser.parse(line, eventFactory);
                sessionEntities.add(sessionEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        MapReduce mapReduce = new MapReduce();
        return mapReduce.mapReduce(sessionEntities);

    }
}
