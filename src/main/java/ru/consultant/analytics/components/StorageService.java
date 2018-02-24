package ru.consultant.analytics.components;


import org.springframework.web.multipart.MultipartFile;
import ru.consultant.analytics.model.MapResult;

import java.util.List;


public interface StorageService {

    List<MapResult> store(MultipartFile file);

}