package by.it_academy.jd2.hw.example.messenger.services.scheduler;

import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.dto.OperationRequest;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.services.api.IScheduledOperationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
        OperationRequest operationRequest = conversionService.convert(operation, OperationRequest.class);
        String url = accountUrl + operation.getAccount() + "/operation/";
        HttpEntity<OperationRequest> request = new HttpEntity<>(operationRequest);
        this.restTemplate.postForObject(url, request, OperationRequest.class);
    }


}
