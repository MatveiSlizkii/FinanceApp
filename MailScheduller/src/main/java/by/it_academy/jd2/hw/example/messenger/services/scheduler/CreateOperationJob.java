package by.it_academy.jd2.hw.example.messenger.services.scheduler;

import by.it_academy.jd2.hw.example.messenger.model.dto.Report;
import by.it_academy.jd2.hw.example.messenger.model.dto.ReportRequest;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledReport;
import by.it_academy.jd2.hw.example.messenger.services.ScheduledReportService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Transactional
public class CreateOperationJob implements Job {

    private final ScheduledReportService scheduledReportService;
    private final RestTemplate restTemplate;
    private final ConversionService conversionService;

    public CreateOperationJob(ScheduledReportService scheduledReportService, ConversionService conversionService) {
        this.scheduledReportService = scheduledReportService;
        this.restTemplate = new RestTemplate();
        this.conversionService = conversionService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String idReport = context.getMergedJobDataMap().getString("report");
        ScheduledReport scheduledReport = scheduledReportService.get(UUID.fromString(idReport));

        //фигачим логику построение периода отчета
        Report report = scheduledReport.getReport();
        Schedule schedule = scheduledReport.getSchedule();
        LocalDateTime toOld = report.getTo();
        LocalDateTime fromOld = report.getFrom();


        LocalDateTime toNew;
        LocalDateTime fromNew;
        long interval = schedule.getInterval();

        switch (schedule.getTimeUnit()) {
            case SECOND:
                toNew = toOld.plusSeconds(interval);
                fromNew = fromOld.plusSeconds(interval);
                break;
            case WEEK:
                toNew = toOld.plusWeeks(interval);
                fromNew = fromOld.plusWeeks(interval);
                break;
            case MONTH:
                toNew = toOld.plusMonths(interval);
                fromNew = fromOld.plusMonths(interval);
                break;
            case DAY:
                toNew = toOld.plusDays(interval);
                fromNew = fromOld.plusDays(interval);
                break;
            default:
                throw new IllegalStateException("Не передан TimeUnit");
        }
        Report reportNew = Report.Builder.createBuilder()
                .setTo(toNew)
                .setFrom(fromNew)
                .build();

        //апдейтим в базе
        scheduledReportService.updateReport(UUID.fromString(idReport), reportNew);

        //отправляем создавать отчет в report_service
        ReportRequest reportRequest = conversionService.convert(report, ReportRequest.class);
        String url = "http://localhost:8083/api/report/" + report.getReportType();
        HttpEntity<ReportRequest> request = new HttpEntity<>(reportRequest);
        ReportRequest response = restTemplate.postForObject(url, request, ReportRequest.class);
        System.out.println("Hi");

        ResponseEntity<String> response1 = restTemplate.getForEntity(
                "http://localhost:8084/api/mail/report/" + response.getUuid(), String.class);


        //TODO вставить перед ресттемплейт то что мы ожидаем получить
    }

}
