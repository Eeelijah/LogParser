package ru.consultant.analytics.components;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.consultant.analytics.mapreduce.MapReduce;
import ru.consultant.analytics.model.MapResult;


import java.io.*;
import java.util.List;


@Service
public class StorageServiceImpl implements StorageService {

    @Override
    public List<MapResult> store(MultipartFile file) throws IOException {
        MapReduce mapReduce = new MapReduce();
        return mapReduce.mapReduce(file.getInputStream());
    }
}