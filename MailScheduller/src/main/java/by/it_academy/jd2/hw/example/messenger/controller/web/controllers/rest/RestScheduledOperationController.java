package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledReport;
import by.it_academy.jd2.hw.example.messenger.services.api.IScheduledReportService;
import by.it_academy.jd2.hw.example.messenger.services.api.ISchedulerService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/mail/scheduler")
public class RestScheduledOperationController {
    private final ConversionService conversionService;
    private final IScheduledReportService scheduledReportService;
    private final ISchedulerService schedulerService;

    public RestScheduledOperationController(ConversionService conversionService,
                                            IScheduledReportService scheduledReportService,
                                            ISchedulerService schedulerService) {
        this.conversionService = conversionService;
        this.scheduledReportService = scheduledReportService;
        this.schedulerService = schedulerService;
    }

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Page<ScheduledReport> index(@RequestParam(name = "page") int page,
                                       @RequestParam(name = "size") int size) {
        //TODO ошибка на пейдж и сайз
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);

        return scheduledReportService.getAll(pageable);
    }


    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public ScheduledReport create(@RequestBody ScheduledReport scheduledReport) {
        //TODO проверка на переданы ли все необходимые данные
        //TODO и нормально ли они парсятся
        return scheduledReportService.create(scheduledReport);
    }

    //TODO put
    @RequestMapping(
            value = {"{uuid}/dt_update/{dt_update}", "{uuid}/dt_update/{dt_update}/"},
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public ScheduledReport update(@RequestBody ScheduledReport scheduledReport,
                                  @PathVariable UUID uuid,
                                  @PathVariable Long dt_update) {
        //TODO проверка на переданы ли все необходимые данные
        //TODO и нормально ли они парсятся
        //TODO есть ли такая запланированная операция
        schedulerService.stop(uuid);
        ScheduledReport scheduledReport1 = scheduledReportService.update(uuid, scheduledReport);
        System.out.println();
        return scheduledReport1;
    }
}
