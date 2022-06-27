package by.it_academy.jd2.hw.example.messenger.services.scheduler;

import by.it_academy.jd2.hw.example.messenger.model.dto.Report;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledReport;
import by.it_academy.jd2.hw.example.messenger.services.api.ISchedulerService;
import by.it_academy.jd2.hw.example.messenger.services.api.ValidationException;
import org.quartz.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
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
    public void create(ScheduledReport ScheduledReport) {
        //TODO порверить на валидность шедулед репорт
        Schedule schedule = ScheduledReport.getSchedule();
        UUID idScheduledReport = ScheduledReport.getUuid();
        JobDetail job = JobBuilder.newJob(CreateOperationJob.class)
                .withIdentity(idScheduledReport.toString(), "report")
                .usingJobData("report", idScheduledReport.toString())
                .build();

        long interval = schedule.getInterval();
        LocalDateTime startTime = schedule.getStartTime();


        TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger()
                .withIdentity(idScheduledReport.toString(), "reports");

        if (startTime == null) {
            builder.startNow();
            startTime = LocalDateTime.now();
        } else {
            builder.startAt(this.conversionService.convert(startTime, Date.class));
        }

        if (interval > 0 && schedule.getTimeUnit() != null) {
            SimpleScheduleBuilder ssb = null;
            CronScheduleBuilder csb = null;
            String expression;

            switch (schedule.getTimeUnit()) {
                case SECOND:
                    ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds((int) interval);
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
            }


            if (ssb != null) {
                builder.withSchedule(ssb.repeatForever());
            } else if (csb != null) {
                builder.withSchedule(csb);
            }
        }

        if (schedule.getStopTime() != null) {
            builder.endAt(this.conversionService.convert(schedule.getStopTime(), Date.class));
        }

        Trigger trigger = builder.build();
        try {
            this.scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new ValidationException("Ошибка создания запланированной операции", e);
        }
    }

    @Override
    @Transactional
    public void stop(UUID uuid) {
        try {
            scheduler.interrupt(new JobKey(uuid.toString(), "report"));

        } catch (UnableToInterruptJobException e) {
            System.out.println("Не удалось остановить джобу");
        }

        //удаляем старую джобу
        try {
            scheduler.deleteJob(new JobKey(uuid.toString(), "operations"));
        } catch (SchedulerException e) {
            throw new RuntimeException("Ошибка удаления старого шедулера");
        }

    }
}
