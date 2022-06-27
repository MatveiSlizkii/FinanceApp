package by.it_academy.jd2.hw.example.messenger.services.scheduler;

import by.it_academy.jd2.hw.example.messenger.controller.web.controllers.rest.utils.JwtTokenUtil;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.model.dto.User;
import by.it_academy.jd2.hw.example.messenger.services.api.CustomUserDetails;
import by.it_academy.jd2.hw.example.messenger.services.api.IScheduledOperationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.UUID;

@Component
@Transactional
public class CreateOperationJob implements Job {

    private final IScheduledOperationService scheduledOperationService;
    private final RestTemplate restTemplate;
    private final ConversionService conversionService;

    public CreateOperationJob(IScheduledOperationService scheduledOperationService, ConversionService conversionService) {
        this.scheduledOperationService = scheduledOperationService;
        this.restTemplate = new RestTemplate();
        this.conversionService = conversionService;
    }
    @Value("${account_url}")
    private String accountUrl;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String idOperation = context.getMergedJobDataMap().getString("operation");
        ScheduledOperation scheduledOperation = scheduledOperationService.get(UUID.fromString(idOperation));
        Operation operation = scheduledOperation.getOperation();
        LocalDateTime localDateTime = LocalDateTime.now();
        operation.setDate(localDateTime.toInstant(TimeZone.getDefault().toZoneId().
                getRules().getOffset(localDateTime)).toEpochMilli());
        String url = accountUrl + operation.getAccount() + "/operation/";


        CustomUserDetails customUserDetails = new CustomUserDetails(operation.getLogin(), "Sd");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Operation> entity = new HttpEntity<>(operation, headers);
        String token = JwtTokenUtil.generateAccessToken(customUserDetails);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        this.restTemplate.postForObject(url, entity, Operation.class);
    }


}
