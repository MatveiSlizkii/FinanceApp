package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.model.Report;
import by.it_academy.jd2.hw.example.messenger.model.api.ReportType;
import by.it_academy.jd2.hw.example.messenger.services.api.IReportService;
import by.it_academy.jd2.hw.example.messenger.services.api.MessageError;
import by.it_academy.jd2.hw.example.messenger.services.claudinary.api.ICloudStorage;
import by.it_academy.jd2.hw.example.messenger.services.handlers.ReportHandlerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
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


    @GetMapping(value = {"/report", "/report/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<Report> index(@RequestParam(name = "page") @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                              @RequestParam(name = "size") @Min(value = 1, message = MessageError.PAGE_SIZE) int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);

        return reportService.getAll(pageable);
    }


    @GetMapping(value = "/{uuid}/export")
    @ResponseBody
    public ResponseEntity<Resource> download (@PathVariable (name = "uuid")UUID uuid) {
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + uuid + ".xlsx");

        Report report = reportService.get(uuid);

        byte[] bytes = cloudStorage.download(report.getExcelReport());

        return new ResponseEntity<>(new ByteArrayResource(bytes), header, HttpStatus.OK);
    }
    
    @RequestMapping(
            value = {"/report/{type}", "/report/{type}/"},
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Report create(@PathVariable(name = "type")ReportType type,
                        @RequestBody Map<String, Object> params){

        //TODO проверить через коллекцию
        //TODO чеки на то что лежит в параметрах с учетом юзера
        Report report = reportService.save(type, params);
        byte[] bytes = reportService.CreateExcel(type, params, report.getUuid());
        reportService.uploadInCloud(bytes, report.getUuid());
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
        Report report = reportService.get(uuid);
        return report.getStatus().name();
    }


}
