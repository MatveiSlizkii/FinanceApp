package by.it_academy.jd2.hw.example.messenger.services;

import by.it_academy.jd2.hw.example.messenger.dao.api.IScheduledOperationStorage;
import by.it_academy.jd2.hw.example.messenger.model.dto.Operation;
import by.it_academy.jd2.hw.example.messenger.model.dto.Schedule;
import by.it_academy.jd2.hw.example.messenger.model.dto.ScheduledOperation;
import by.it_academy.jd2.hw.example.messenger.model.entity.ScheduledOperationEntity;
import by.it_academy.jd2.hw.example.messenger.services.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class ScheduledOperationService implements IScheduledOperationService {

    @Value("${account_url}")
    private String accountUrl;

    @Value("${classifier_currency_url}")
    private String currencyUrl;

    @Value("${classifier_category_url}")
    private String categoryUrl;

    private final IScheduledOperationStorage scheduledOperationStorage;
    private final ConversionService conversionService;
    private final IScheduleService scheduleService;
    private final IOperationService operationService;
    private final ISchedulerService schedulerService;
    private final RestTemplate restTemplate;

    @PersistenceContext
    private EntityManager em;

    public ScheduledOperationService(IScheduledOperationStorage scheduledOperationStorage,
                                     ConversionService conversionService,
                                     IScheduleService scheduleService,
                                     IOperationService operationService,
                                     ISchedulerService schedulerService) {
        this.scheduledOperationStorage = scheduledOperationStorage;
        this.conversionService = conversionService;
        this.scheduleService = scheduleService;
        this.operationService = operationService;
        this.schedulerService = schedulerService;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public ScheduledOperation get(UUID uuid) {
        //TODO проверка принадлежит ли операция данномк пользователю
        return conversionService.convert(scheduledOperationStorage.getById(uuid), ScheduledOperation.class);
    }

    @Override
    @Transactional
    public ScheduledOperation save(ScheduledOperation scheduledOperation) {
        //TODO чек операции
        if (scheduledOperation == null) {
            throw new ValidationException(new ValidationError("scheduledOperation", MessageError.MISSING_OBJECT));
        }

        List<ValidationError> errors = new ArrayList<>();


        Operation operation = operationService.save(scheduledOperation.getOperation());
        Schedule schedule = scheduleService.save(scheduledOperation.getSchedule());

        this.checkOperation(operation, errors);
        this.checkSchedule(schedule, errors);

        LocalDateTime localDateTime = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        scheduledOperation.setUuid(uuid);
        scheduledOperation.setDt_create(localDateTime);
        scheduledOperation.setDt_update(localDateTime);
        scheduledOperation.getOperation().setDt_create(operation.getDt_create());
        scheduledOperationStorage.save(conversionService.convert(scheduledOperation, ScheduledOperationEntity.class));
       this.schedulerService.create(scheduledOperation);
        return scheduledOperation;
    }

    @Override
    public Page<ScheduledOperation> getAll(Pageable pageable) {
        //TODO перебить с использованием JWT
        List<ScheduledOperation> scheduledOperations = new ArrayList<>();
        scheduledOperationStorage.findAll().forEach((o)->
                scheduledOperations.add(conversionService.convert(o, ScheduledOperation.class)));
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), scheduledOperations.size());
        return new PageImpl<>(scheduledOperations.subList(start, end), pageable, scheduledOperations.size());
    }

    @Override
    public ScheduledOperation update(UUID uuid, ScheduledOperation scheduledOperationRaw) {

        //TODO чек операции на JWT
        //TODO чек уида

        List<ValidationError> errors = new ArrayList<>();

        ScheduledOperationEntity scheduledOperationEntity = em.find(ScheduledOperationEntity.class, uuid);
        em.refresh(scheduledOperationEntity, LockModeType.OPTIMISTIC);
        //TODO перебить нормально

        if (scheduledOperationRaw.getSchedule() != null) {
            scheduledOperationEntity.setSchedule(scheduledOperationRaw.getSchedule().getUuid());
        }
        if (scheduledOperationRaw.getOperation() != null) {
            scheduledOperationEntity.setOperation(scheduledOperationRaw.getOperation().getUuid());
        }

        return conversionService.convert(scheduledOperationEntity, ScheduledOperation.class);
    }


    private void checkSchedule(Schedule schedule, List<ValidationError> errors) {
        if (schedule == null) {
            errors.add(new ValidationError("schedule", MessageError.MISSING_OBJECT));
            return;
        }

        if (schedule.getInterval() < 0) {
            errors.add(new ValidationError("interval", "Интервал должен быть положительным"));
        } else if (schedule.getInterval() > 0 && schedule.getTime_unit() == null) {
            errors.add(new ValidationError("timeUnit", MessageError.MISSING_OBJECT));
        }

        if (schedule.getStop_time() != null
                && schedule.getStart_time() != null
                && schedule.getStop_time().isAfter(schedule.getStop_time())) {
            errors.add(new ValidationError("startTime and stopTime",
                    "Дата окончания не может быть раньше даты начала"));
        }
    }

    private void checkOperation(Operation operation, List<ValidationError> errors) {
        if (operation == null) {
            errors.add(new ValidationError("operation", MessageError.MISSING_OBJECT));
            return;
        }
        //TODO когда прикрутится юзер

//        String idAccount = "account (id счёта)";
//        String idCategory = "category (id категории)";
//        String idCurrency = "currency (id валюты)";
//
//        String currencyClassifierUrl = this.currencyUrl + "/" + operation.getCurrency();
//        String categoryClassifierUrl = this.categoryUrl + "/" + operation.getCategory();
//        String accountServiceUrl = this.accountUrl + "/" + operation.getAccount();

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Object> entity = new HttpEntity<>(headers);
//        String token = JwtTokenUtil.generateAccessToken(this.userHolder.getUser());
//        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

//        if (operation.getAccount() == null) {
//            errors.add(new ValidationError(idAccount, MessageError.MISSING_FIELD));
//        } else {
//            try {
//                this.restTemplate.exchange(accountServiceUrl, HttpMethod.GET, entity, String.class);
//            } catch (HttpStatusCodeException e) {
//                errors.add(new ValidationError(idAccount, MessageError.ID_NOT_EXIST));
//            }
//        }
//
//        if (operation.getCategory() == null) {
//            errors.add(new ValidationError(idCategory, MessageError.MISSING_FIELD));
//        } else {
//            try {
//                this.restTemplate.exchange(categoryClassifierUrl, HttpMethod.GET, entity, String.class);
//            } catch (HttpStatusCodeException e) {
//                errors.add(new ValidationError(idCategory, MessageError.ID_NOT_EXIST));
//            }
//        }

        if (operation.getValue() == 0) {
            errors.add(new ValidationError("value", "Передана нулевая сумма операции"));
        }

//        if (operation.getCurrency() == null) {
//            errors.add(new ValidationError(idCurrency, MessageError.MISSING_FIELD));
//        } else {
//            try {
//                this.restTemplate.exchange(currencyClassifierUrl, HttpMethod.GET, entity, String.class);
//            } catch (HttpStatusCodeException e) {
//                errors.add(new ValidationError(idCurrency, MessageError.ID_NOT_EXIST));
//            }
//        }
    }
}

