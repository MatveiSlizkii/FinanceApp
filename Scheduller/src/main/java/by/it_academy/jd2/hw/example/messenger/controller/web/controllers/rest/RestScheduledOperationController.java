package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.services.api.IOperationService;
import by.it_academy.jd2.hw.example.messenger.services.api.IScheduleService;
import by.it_academy.jd2.hw.example.messenger.services.api.IScheduledOperationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sсheduler/operation")
public class RestScheduledOperationController {
    private final ConversionService conversionService;
    private final IOperationService operationService;
    private final IScheduleService scheduleService;
    private final IScheduledOperationService scheduledOperationService;

    public RestScheduledOperationController(ConversionService conversionService,
                                            IOperationService operationService,
                                            IScheduleService scheduleService,
                                            IScheduledOperationService scheduledOperationService) {
        this.conversionService = conversionService;
        this.operationService = operationService;
        this.scheduleService = scheduleService;
        this.scheduledOperationService = scheduledOperationService;
    }

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Page<ScheduledOperation> index(@RequestParam(name = "page") int page,
                                          @RequestParam(name = "size") int size) {
        //TODO ошибка на пейдж и сайз
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);

        return scheduledOperationService.getAll(pageable);
}


    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public ScheduledOperation create(@RequestBody ScheduledOperation scheduledOperation) {
        //TODO проверка на переданы ли все необходимые данные
        //TODO и нормально ли они парсятся
        return scheduledOperationService.save(scheduledOperation);
    }

    //TODO put
    @RequestMapping(
            value = {"{uuid}/dt_update/{dt_update}", "{uuid}/dt_update/{dt_update}/"},
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public ScheduledOperation update(@RequestBody ScheduledOperation scheduledOperation,
                          @PathVariable UUID uuid,
                          @PathVariable Long dt_update) {
        //TODO проверка на переданы ли все необходимые данные
        //TODO и нормально ли они парсятся
        //TODO есть ли такая запланированная операция

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt_update),
                        ZoneId.systemDefault());

        if (!scheduledOperationService.get(uuid).getDt_update().equals(localDateTime)){
            throw new IllegalArgumentException("Данная версия для обновления устарела," +
                                                "обновите, пожалуйста страницу");
        }

        return scheduledOperationService.update(uuid, scheduledOperation);
    }
}
