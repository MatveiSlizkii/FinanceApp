package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.model.Report;
import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import by.it_academy.jd2.hw.example.messenger.services.api.IReportService;
import by.it_academy.jd2.hw.example.messenger.services.claudinary.api.ICloudStorage;
import by.it_academy.jd2.hw.example.messenger.services.handlers.ReportHandlerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RestReportController {

    private final IReportService reportService;
    private final ICloudStorage cloudStorage;
    private final ReportHandlerFactory handlerFactory;


    public RestReportController(IReportService reportService,
                                ICloudStorage cloudStorage,
                                ReportHandlerFactory handlerFactory) {
        this.reportService = reportService;
        this.cloudStorage = cloudStorage;
        this.handlerFactory = handlerFactory;
    }

    @RequestMapping(
            value = {"/report", "/report/"},
            method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Page<Report> index(@RequestParam(name = "page") int page,
                              @RequestParam(name = "size") int size) {
        //TODO проверка на пейдж и сайз
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);

        return reportService.getAll(pageable);
    }

    @RequestMapping(
            value = {"/account/{uuid}/export", "/account/{uuid}/export/"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public ResponseEntity<Resource> download (@PathVariable (name = "uuid")UUID uuid) {
        //TODO валидность уида, сущетвует ли
        Report report = reportService.get(uuid);
        byte[] bytes = cloudStorage.download(report.getExcelReport());
        //TODO получаем ссылку и скачиваем
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new ByteArrayResource(bytes));
    }
    
    @RequestMapping(
            value = {"/report/{type}", "/report/{type}/"},
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Report create(@PathVariable(name = "type")ReportType type,
                        @RequestBody Map<String, Object> params) throws IOException {


        Report report = reportService.save(type, params);

        //TODO проверка на сущетсвует ли такой тип
        //TODO проверка на необходимые значение в боди


        return report;
    }

    @RequestMapping(
            value = {"/account/{uuid}/export", "/account/{uuid}/export/"},
            method = RequestMethod.HEAD,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public String checkStatus (@PathVariable (name = "uuid")UUID uuid) {
        //TODO валидность уида, сущетвует ли
        Report report = reportService.get(uuid);
        //TODO проверить пашет ли
        //TODO на ошибки нашел/не нашел

        return report.getStatus().name();
    }


}
