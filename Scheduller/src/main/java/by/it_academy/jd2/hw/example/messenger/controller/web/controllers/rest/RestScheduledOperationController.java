package by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest;

import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.services.api.*;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.UnableToInterruptJobException;
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
    private final IScheduledOperationService scheduledOperationService;
    private final ISchedulerService schedulerService;
    private final Scheduler scheduler;


    public RestScheduledOperationController(ConversionService conversionService,
                                            IScheduledOperationService scheduledOperationService,
                                            ISchedulerService schedulerService,
                                            Scheduler scheduler) {
        this.conversionService = conversionService;
        this.scheduledOperationService = scheduledOperationService;
        this.schedulerService = schedulerService;
        this.scheduler = scheduler;
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
        return scheduledOperationService.save(scheduledOperation);
    }



    @PutMapping(value = {"{uuid}/dt_update/{dt_update}", "{uuid}/dt_update/{dt_update}/"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ScheduledOperation update(@RequestBody ScheduledOperation scheduledOperation,
                          @PathVariable UUID uuid,
                          @PathVariable Long dt_update) throws UnableToInterruptJobException {
        schedulerService.update(uuid, scheduledOperation);
        scheduledOperationService.delete(uuid);
        //обновить запись в бд ScheduledOperation
        //создать новый шедулер
        ScheduledOperation scheduledOperation1 = scheduledOperationService.save(scheduledOperation);

        return scheduledOperation1;
    }

}
