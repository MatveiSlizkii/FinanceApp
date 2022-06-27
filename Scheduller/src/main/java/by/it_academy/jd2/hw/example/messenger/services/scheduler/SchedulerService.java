package by.it_academy.jd2.hw.example.messenger.services.scheduler;

import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.services.api.*;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class SchedulerService implements ISchedulerService {
    private final ConversionService conversionService;
    private final Scheduler scheduler;

    public SchedulerService(ConversionService conversionService, Scheduler scheduler) {
        this.conversionService = conversionService;
        this.scheduler = scheduler;
    }


    @Override
    public void create(ScheduledOperation scheduledOperation) {
        Schedule schedule = scheduledOperation.getSchedule();
        UUID idScheduledOperation = scheduledOperation.getUuid();
        JobDetail job = JobBuilder.newJob(CreateOperationJob.class)
                .withIdentity(idScheduledOperation.toString(), "operations")
                .usingJobData("operation", idScheduledOperation.toString())
                .build();

        long interval = schedule.getInterval();
        LocalDateTime startTime = schedule.getStart_time();


        TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger()
                .withIdentity(idScheduledOperation.toString(), "operations");

        if (startTime == null) {
            builder.startNow();
            startTime = LocalDateTime.now();
        } else {
            builder.startAt(this.conversionService.convert(startTime, Date.class));
        }

        if (interval > 0 && schedule.getTime_unit() != null) {
            SimpleScheduleBuilder ssb = null;
            CronScheduleBuilder csb = null;
            String expression;

            switch (schedule.getTime_unit()) {
                case SECOND:
                    ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds((int) interval);
                    break;
                case MINUTE:
                    ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes((int) interval);
                    break;
                case HOUR:
                    ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours((int) interval);
                    break;
                case DAY:
                    ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours((int) (interval * 24));
                    break;
                case WEEK:
                    ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours((int) (interval * 24 * 7));
                    break;
                case MONTH:
                    expression = String.format("%d %d %d %d * ?",
                            startTime.getSecond(),
                            startTime.getMinute(),
                            startTime.getHour(),
                            startTime.getDayOfMonth());
                    csb = CronScheduleBuilder.cronSchedule(expression);
                    break;
                case YEAR:
                    expression = String.format("%d %d %d %d %d ?",
                            startTime.getSecond(),
                            startTime.getMinute(),
                            startTime.getHour(),
                            startTime.getDayOfMonth(),
                            startTime.getMonthValue());
                    csb = CronScheduleBuilder.cronSchedule(expression);
                    break;
            }


            if (ssb != null) {
                builder.withSchedule(ssb.repeatForever());
            } else if (csb != null) {
                builder.withSchedule(csb);
            }
        }

        if (schedule.getStop_time() != null) {
            builder.endAt(this.conversionService.convert(schedule.getStop_time(), Date.class));
        }

        Trigger trigger = builder.build();

        try {
            this.scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException("Ошибка создания запланированной операции", e);
        }
    }

    @Override
    public void update(UUID uuidOperation,
                       ScheduledOperation scheduledOperation) {
        //останавливаем
        try {
            scheduler.interrupt(new JobKey(uuidOperation.toString(), "operations"));

        } catch (UnableToInterruptJobException e) {
            System.out.println("Не удалось остановить джобу");
        }

        //удаляем старую джобу
        try {
            scheduler.deleteJob(new JobKey(uuidOperation.toString(), "operations"));
        } catch (SchedulerException e) {
            throw new RuntimeException("Ошибка удаления старого шедулера");
        }

    }
}
