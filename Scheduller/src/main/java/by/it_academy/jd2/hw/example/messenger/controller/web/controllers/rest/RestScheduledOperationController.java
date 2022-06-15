package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.services.api.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/scheduler/operation")
public class RestScheduledOperationController {
    private final ConversionService conversionService;
    private final IOperationService operationService;
    private final IScheduleService scheduleService;
    private final IScheduledOperationService scheduledOperationService;

    private final ISchedulerService schedulerService;

    public RestScheduledOperationController(ConversionService conversionService,
                                            IOperationService operationService,
                                            IScheduleService scheduleService,
                                            IScheduledOperationService scheduledOperationService,
                                            ISchedulerService schedulerService) {
        this.conversionService = conversionService;
        this.operationService = operationService;
        this.scheduleService = scheduleService;
        this.scheduledOperationService = scheduledOperationService;
        this.schedulerService = schedulerService;
    }


    @GetMapping(value = {"", "/"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<ScheduledOperation> index(@RequestParam(name = "page") @Min(value = 0, message = MessageError.PAGE_NUMBER) int page,
                                          @RequestParam(name = "size") @Min(value = 1, message = MessageError.PAGE_SIZE)   int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);
        return scheduledOperationService.getAll(pageable);
}



    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduledOperation create(@RequestBody ScheduledOperation scheduledOperation) {
        //TODO проверка на переданы ли все необходимые данные
        //TODO и нормально ли они парсятся
        return scheduledOperationService.save(scheduledOperation);
    }



    @GetMapping(value = {"{uuid}/dt_update/{dt_update}", "{uuid}/dt_update/{dt_update}/"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
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
    @PostMapping(value = {"test", "test/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduledOperation test(@RequestBody ScheduledOperation scheduledOperation) {
        schedulerService.update(UUID.fromString("15486e31-5923-48f8-b33b-d534f66fb18e"),
                null, scheduledOperation);
        return null;
    }

}
