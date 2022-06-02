package by.it_academy.jd2.hw.example.messenger.services.scheduler;

import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.dto.OperationRequest;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.services.OperationService;
import by.it_academy.jd2.hw.example.messenger.services.ScheduledOperationService;
import netscape.javascript.JSObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@Transactional
public class CreateOperationJob implements Job {

    private final OperationService operationService;
    private final RestTemplate restTemplate;
    private final ConversionService conversionService;

    public CreateOperationJob(OperationService operationService, ConversionService conversionService) {
        this.operationService = operationService;
        this.restTemplate = new RestTemplate();
        this.conversionService = conversionService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String idOperation = context.getMergedJobDataMap().getString("operation");
        Operation operation = this.operationService.get(UUID.fromString(idOperation));
        OperationRequest operationRequest = conversionService.convert(operation, OperationRequest.class);
        String url = "http://localhost:8080/api/account/" + operation.getAccount() + "/operation/";
        HttpEntity<OperationRequest> request = new HttpEntity<>(operationRequest);
        this.restTemplate.postForObject(url, request, OperationRequest.class);
    }


}
