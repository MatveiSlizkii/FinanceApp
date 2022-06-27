package by.it_academy.jd2.hw.example.messenger.services.scheduler;

import by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest.utils.JwtTokenUtil;
import by.it_academy.jd2.hw.example.messenger.model.dto.Report;
import by.it_academy.jd2.hw.example.messenger.model.dto.ReportRequest;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledReport;
import by.it_academy.jd2.hw.example.messenger.services.ScheduledReportService;
import by.it_academy.jd2.hw.example.messenger.services.api.CustomUserDetails;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collection;
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

        //делаем логику построение периода отчета
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

        CustomUserDetails customUserDetails = new CustomUserDetails(report.getLogin(), "Sd");


        ReportRequest reportRequest = conversionService.convert(report, ReportRequest.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReportRequest> entity = new HttpEntity<>(reportRequest, headers);
        String token = JwtTokenUtil.generateAccessToken(customUserDetails);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        String url = "http://localhost:8083/api/report/" + report.getReportType();
        ReportRequest response = this.restTemplate.postForObject(url, entity, ReportRequest.class);


        //отправляем создавать отчет в report_service


        ResponseEntity<String> response1 = restTemplate.exchange(
                "http://localhost:8084/api/mail/report/" + response.getUuid(), HttpMethod.GET, entity, String.class);
        System.out.println(response1.getBody());
        //TODO перебить ссылки
        //TODO перебить на хидеры

        //TODO вставить перед ресттемплейт то что мы ожидаем получить
    }

}
