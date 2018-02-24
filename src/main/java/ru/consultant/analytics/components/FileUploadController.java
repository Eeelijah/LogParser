package ru.consultant.analytics.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.consultant.analytics.model.MapResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@RestController
public class FileUploadController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/")
    public RedirectView handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        List<MapResult> mapResultList = storageService.store(file);
        redirectAttributes.addFlashAttribute("mapResults", mapResultList);
        return new RedirectView("/", true);
    }


    @GetMapping("/")
    public ModelAndView indexPage(Model model, @ModelAttribute(name ="mapResults") Object data) throws IOException {

        ModelAndView modelAndView = new ModelAndView("uploadForm");

        if(data instanceof List) {
            model.addAttribute("mapResults", data);
        } else {
            MapResult mapResult = new MapResult();
            mapResult.setReqFrequencyWithoutClick("Файл не выбран");
            mapResult.setClickCount("Файл не выбран");
            mapResult.setQuery("Файл не выбран");
            mapResult.setDocPosition("Файл не выбран");
            mapResult.setUserCount("Файл не выбран");
            mapResult.setFrequency("Файл не выбран");
            List<MapResult> mapResults = Arrays.asList(new MapResult[] {mapResult});
            model.addAttribute("mapResults", mapResults);

        }
        return modelAndView;
    }

}